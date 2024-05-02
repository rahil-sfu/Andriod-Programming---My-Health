package com.xyz.myhealth.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver: BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            val serviceIntent = Intent("com.xyz.myhealth.services.NotificationService");
            context.startService(serviceIntent);
        }
    }
}
