package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat

import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.App.Companion.CHANNEL_ID


class MyService : Service() {
    lateinit var player: MediaPlayer
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Example Service")
            .setContentText("HALO INPUT")
            .setSmallIcon(R.drawable.ic_setting_icon)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)


        return START_NOT_STICKY
    }

    override fun onStart(intent: Intent?, startId: Int) {


    }

    override fun onDestroy() {
        Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show()
        return null
    }
}
