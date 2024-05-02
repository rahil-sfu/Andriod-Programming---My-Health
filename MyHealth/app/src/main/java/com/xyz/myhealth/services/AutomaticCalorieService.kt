package com.xyz.myhealth.services

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.*
import android.provider.CalendarContract
import java.util.concurrent.ArrayBlockingQueue
import weka.core.Attribute
import weka.core.DenseInstance
import weka.core.Instance
import weka.core.Instances
import java.text.DecimalFormat
import java.util.ArrayList
import kotlin.math.sqrt
import com.xyz.myhealth.FFT.*

class AutomaticCalorieService: Service(), SensorEventListener {
    private lateinit var notificationManager: NotificationManager
    private lateinit var myBroadcastReceiver: BroadcastReceiver
    private var CHANNEL_ID= "channel id"
    private var NOTIFY_ID= 1
    private lateinit var  myBinder : MyBinder
    private var msgHandler: Handler? = null
    private val mFeatLen = ACCELEROMETER_BLOCK_CAPACITY + 2
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private var mServiceTaskType = 0
    private lateinit var mLabel: String
    private lateinit var mDataset: Instances
    private lateinit var mClassAttribute: Attribute
    private lateinit var mAsyncTask: AutomaticCalorieService.OnSensorChangedTask
    private lateinit var mAccBuffer: ArrayBlockingQueue<Double>
    companion object{
        var STOP_SERVICE_ACTION = "stop service"
        const val ACCELEROMETER_BUFFER_CAPACITY = 2048
        const val ACCELEROMETER_BLOCK_CAPACITY = 64

        const val ACTIVITY_ID_STANDING = 0
        const val ACTIVITY_ID_WALKING = 1
        const val ACTIVITY_ID_RUNNING = 2
        const val ACTIVITY_ID_OTHER = 2

        const val SERVICE_TASK_TYPE_COLLECT = 0
        const val SERVICE_TASK_TYPE_CLASSIFY = 1

        const val ACTION_MOTION_UPDATED = "MYRUNS_MOTION_UPDATED"

        const val CLASS_LABEL_KEY = "label"
        const val CLASS_LABEL_STANDING = "Standing"
        const val CLASS_LABEL_WALKING = "Walking"
        const val CLASS_LABEL_RUNNING = "Running"
        const val CLASS_LABEL_OTHER = "Others"

        const val FEAT_FFT_COEF_LABEL = "fft_coef_"
        const val FEAT_MAX_LABEL = "max"
        const val FEAT_SET_NAME = "accelerometer_features"

        const val FEATURE_FILE_NAME = "features.arff"
        const val RAW_DATA_NAME = "raw_data.txt"
        const val FEATURE_SET_CAPACITY = 10000

        const val NOTIFICATION_ID = 1
    }

    override fun onCreate() {
        super.onCreate()
        myBinder = MyBinder()
        mAccBuffer = ArrayBlockingQueue<Double>(ACCELEROMETER_BUFFER_CAPACITY)

        myBroadcastReceiver = MyBroadcastReceiver()

        val intentFilter = IntentFilter()
        intentFilter.addAction(STOP_SERVICE_ACTION)
        registerReceiver(myBroadcastReceiver, intentFilter)
    }
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            val m = sqrt(
                (event.values[0] * event.values[0] + event.values[1] * event.values[1] + (event.values[2]
                        * event.values[2])).toDouble()
            )

            // Inserts the specified element into this queue if it is possible
            // to do so immediately without violating capacity restrictions,
            // returning true upon success and throwing an IllegalStateException
            // if no space is currently available. When using a
            // capacity-restricted queue, it is generally preferable to use
            // offer.
            try {
                mAccBuffer.add(m)
            } catch (e: IllegalStateException) {

                // Exception happens when reach the capacity.
                // Doubling the buffer. ListBlockingQueue has no such issue,
                // But generally has worse performance
                val newBuf = ArrayBlockingQueue<Double>(mAccBuffer.size * 2)
                mAccBuffer.drainTo(newBuf)
                mAccBuffer = newBuf
                mAccBuffer.add(m)
            }
        }
    }
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onBind(p0: Intent?): IBinder? {
        return myBinder
    }
    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }
    override fun onDestroy() {
        mSensorManager.unregisterListener(this)
        super.onDestroy()
        stopSelf()

    }
    override fun onStartCommand(intent: Intent?, flag: Int,startId: Int): Int{
        setUpSensor()
        return START_NOT_STICKY
    }

    //tester code provided
    private fun setUpSensor() {
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST)
        mServiceTaskType = SERVICE_TASK_TYPE_COLLECT

        // Create the container for attributes
        val allAttr = ArrayList<Attribute>()

        // Adding FFT coefficient attributes
        val df = DecimalFormat("0000")
        for (i in 0 until ACCELEROMETER_BLOCK_CAPACITY) {
            allAttr.add(Attribute(FEAT_FFT_COEF_LABEL + df.format(i.toLong())))
        }
        // Adding the max feature
        allAttr.add(Attribute(FEAT_MAX_LABEL))

        // Declare a nominal attribute along with its candidate values
        val labelItems = ArrayList<String>(3)
        labelItems.add(CLASS_LABEL_STANDING)
        labelItems.add(CLASS_LABEL_WALKING)
        labelItems.add(CLASS_LABEL_RUNNING)
        labelItems.add(CLASS_LABEL_OTHER)
        mClassAttribute = Attribute(CLASS_LABEL_KEY, labelItems)
        allAttr.add(mClassAttribute)

        // Construct the dataset with the attributes specified as allAttr and
        // capacity 10000
        mDataset = Instances(FEAT_SET_NAME, allAttr, FEATURE_SET_CAPACITY)

        // Set the last column/attribute (standing/walking/running) as the class
        // index for classification
        mDataset.setClassIndex(mDataset.numAttributes() - 1)
        mAsyncTask = OnSensorChangedTask()
        mAsyncTask.execute()
    }
    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            stopSelf()
            unregisterReceiver(myBroadcastReceiver)
        }
    }

    inner class MyBinder : Binder() {
        fun setMsgHandler(msgHandler: Handler) {
            this@AutomaticCalorieService.msgHandler = msgHandler
        }
    }
    @SuppressLint("StaticFieldLeak")
    inner class OnSensorChangedTask : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg arg0: Void?): Void? {
            val inst: Instance = DenseInstance(mFeatLen)
            inst.setDataset(mDataset)
            var blockSize = 0
            val fft = FFT(ACCELEROMETER_BLOCK_CAPACITY)
            val accBlock = DoubleArray(ACCELEROMETER_BLOCK_CAPACITY)
            val im = DoubleArray(ACCELEROMETER_BLOCK_CAPACITY)
            var max: Double
            while (true) {
                try {
                    if (isCancelled() == true) {
                        return null
                    }
                    accBlock[blockSize++] = mAccBuffer.take().toDouble()
                    if (blockSize == ACCELEROMETER_BLOCK_CAPACITY) {
                        blockSize = 0
                        max = .0
                        for (`val` in accBlock) {
                            if (max < `val`) {
                                max = `val`
                            }
                        }
                        fft.fft(accBlock, im)
                        for (i in accBlock.indices) {
                            val mag = sqrt(accBlock[i] * accBlock[i] + im[i]
                                    * im[i])
                            inst.setValue(i, mag)
                            im[i] = .0
                        }

                        // Append max after frequency component
                        inst.setValue(ACCELEROMETER_BLOCK_CAPACITY, max)
                        mLabel = getLabel(WekaClassifier.classify(inst.toDoubleArray().toTypedArray()).toInt())
                        mDataset.add(inst)

                        if(msgHandler!= null){
                            val bundle = Bundle()
                            bundle.putString("Activity Type", mLabel)
                            bundle.putBoolean("sensorChange", true)
                            val message = msgHandler!!.obtainMessage()
                            message.data = bundle
                            msgHandler!!.sendMessage(message)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        private fun getLabel(activityId: Int): String{
            return if(activityId == ACTIVITY_ID_STANDING)
                CLASS_LABEL_STANDING
            else if(activityId == ACTIVITY_ID_WALKING)
                CLASS_LABEL_WALKING
            else if(activityId == ACTIVITY_ID_RUNNING)
                CLASS_LABEL_RUNNING
            else
                CLASS_LABEL_OTHER
        }
        override fun onCancelled() {
            if (mServiceTaskType == SERVICE_TASK_TYPE_CLASSIFY) {
                super.onCancelled()
                return
            }
            super.onCancelled()
        }
    }

}