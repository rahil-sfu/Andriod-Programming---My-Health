package com.example.garvit_sardana_myruns5.Service

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xyz.myhealth.services.AutomaticCalorieService

class AutomaticCalorieServiceViewModel: ViewModel(), ServiceConnection {

    private var myMessageHandler: MyMessageHandler
    init{
        myMessageHandler = MyMessageHandler(Looper.getMainLooper())
    }

    private val _activityType = MutableLiveData<String>()

    val activityType: LiveData<String>
        get() = _activityType
    override fun onServiceConnected(name: ComponentName, iBinder: IBinder) {
        val tempBinder = iBinder as AutomaticCalorieService.MyBinder
        tempBinder.setMsgHandler(myMessageHandler)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        TODO("Not yet implemented")
    }
    inner class MyMessageHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            val bundle = msg.data
            _activityType.value = bundle.getString("Activity Type")
        }
    }
}