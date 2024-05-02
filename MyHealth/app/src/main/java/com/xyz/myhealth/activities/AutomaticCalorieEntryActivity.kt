package com.xyz.myhealth.activities

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.garvit_sardana_myruns5.Service.AutomaticCalorieServiceViewModel
import com.xyz.myhealth.R
import com.xyz.myhealth.services.AutomaticCalorieService
import com.xyz.myhealth.services.AutomaticCalorieService.Companion.CLASS_LABEL_RUNNING
import com.xyz.myhealth.services.AutomaticCalorieService.Companion.CLASS_LABEL_STANDING
import com.xyz.myhealth.services.AutomaticCalorieService.Companion.CLASS_LABEL_WALKING
import com.xyz.myhealth.services.CalorieService
import kotlin.properties.Delegates

class AutomaticCalorieEntryActivity : AppCompatActivity() {
    lateinit var saveBtn: Button
    lateinit var cancelBtn: Button
    private lateinit var typeView: TextView

    private lateinit var myViewModel: AutomaticCalorieServiceViewModel
    private lateinit var serviceIntent: Intent

    private lateinit var activityType:String

    var startTime: Float= 0f

    private var walkingCount = 0
    private var standingCount = 0
    private var runningCount = 0
    private var othersCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_automatic_calorie_entry)
        myViewModel = ViewModelProvider(this).get(AutomaticCalorieServiceViewModel::class.java)
        serviceIntent = Intent(this, AutomaticCalorieService::class.java)
        saveBtn = findViewById(R.id.autoEntrySaveButton)
        cancelBtn = findViewById(R.id.autoEntryCancelButton)
        typeView = findViewById(R.id.auto_activity)
        myViewModel.activityType.observe(this){
            typeView.text = "Activity Type: ".plus(myViewModel.activityType.value)
            activityType = myViewModel.activityType.value.toString()
            if(activityType == CLASS_LABEL_STANDING)
                standingCount++
            else if(activityType == CLASS_LABEL_WALKING)
                walkingCount++
            else if(activityType == CLASS_LABEL_RUNNING)
                runningCount++
            else
                othersCount++
        }
        cancelBtn.setOnClickListener(){
            finish()
        }
        saveBtn.setOnClickListener(){
            val endTime = System.currentTimeMillis()
            val time = (endTime- startTime)/1000
            var calories by Delegates.notNull<Float>()
            if(activityType == CLASS_LABEL_WALKING)
                calories = 250/3600 * time
            else if (activityType == CLASS_LABEL_RUNNING)
                calories = 400/3600 * time
            else if(activityType == CLASS_LABEL_STANDING)
                calories = 150/3600 * time
            else
                calories = 400/3600 * time
            CalorieService.addManualEntry(
                "w@w",
                activityType,
                time.toInt(),
                calories,
                100F
            )

            finish()
        }
        startTime = System.currentTimeMillis().toFloat()
        this.startService(serviceIntent)
        bindService(serviceIntent)
    }

    private fun bindService(intent: Intent){
        val appContext = this.applicationContext
        appContext.bindService(intent, myViewModel, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        val appContext = this.applicationContext
        appContext.unbindService(myViewModel)
        val intent = Intent()
        intent.action = AutomaticCalorieService.STOP_SERVICE_ACTION
        sendBroadcast(intent)
    }
}