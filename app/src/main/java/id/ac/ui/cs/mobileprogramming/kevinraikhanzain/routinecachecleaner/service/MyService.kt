package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.App.Companion.CHANNEL_ID
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.MainActivity
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.MyReceiver
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.R


class MyService : Service() {
    private var myReceiver: MyReceiver = MyReceiver()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setContentText(applicationContext.getString(R.string.notif_content))
            .setSmallIcon(R.drawable.ic_storage)
            .setContentIntent(pendingIntent)
            .build()

        val filter = IntentFilter(Intent.ACTION_SCREEN_OFF)
        registerReceiver(myReceiver, filter)
        startForeground(1, notification)
        sendMessageToActivity(true)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun sendMessageToActivity(msg: Boolean) {
        val intent = Intent("CLEAR_CACHE_PROCESS_STATUS")
        // You can also include some extra data.
        intent.putExtra("WAITING_TO_CLEAR_CACHE", msg)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        sendMessageToActivity(false)
        unregisterReceiver(myReceiver)
    }
}
