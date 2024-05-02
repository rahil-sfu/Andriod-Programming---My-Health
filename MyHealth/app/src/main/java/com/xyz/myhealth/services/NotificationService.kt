package com.xyz.myhealth.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.xyz.myhealth.MainActivity
import com.xyz.myhealth.R

/**
 * Notification service
 */
class NotificationService: Service(){

    override fun onCreate() {
        super.onCreate()

        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder = NotificationCompat.Builder(this, "channel id")
        notificationBuilder.setContentTitle("MyHealth")
        notificationBuilder.setContentText("It's been so Long!!!")
        notificationBuilder.setSubText("Drink some water! Keep Hydrated")
        notificationBuilder.setSmallIcon(R.drawable.ic_baseline_local_drink_24)
        val notification = notificationBuilder.build()
        if (Build.VERSION.SDK_INT > 26) {
            val notificationChannel = NotificationChannel("channel id", "channel name", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(1, notification)


    }
  override fun onStartCommand(intent:Intent, flags: Int, startId: Int): Int  {
        // START YOUR TASKS
        return super.onStartCommand(intent, flags, startId);
    }


    override fun onDestroy() {
        // STOP YOUR TASKS
        val notificationIntent = Intent(this, Notification::class.java)
        val contentIntent = PendingIntent.getService(
            this, 0, notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val alarm = getSystemService(ALARM_SERVICE) as AlarmManager
        alarm.cancel(contentIntent)
        alarm.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            System.currentTimeMillis(),
            60 * 1000 * 2,
            contentIntent
        )
        super.onDestroy();
    }



    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}