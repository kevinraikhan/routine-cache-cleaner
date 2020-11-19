package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.App.Companion.CHANNEL_ID
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.MainActivity
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.MyReceiver
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.R


class MyService : Service() {
    private var myReceiver: MyReceiver = MyReceiver()

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
            .setSmallIcon(R.drawable.ic_circle)
            .setContentIntent(pendingIntent)
            .build()

        val filter = IntentFilter(Intent.ACTION_SCREEN_OFF)
        registerReceiver(myReceiver, filter)
        startForeground(1, notification)

        return START_NOT_STICKY
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
