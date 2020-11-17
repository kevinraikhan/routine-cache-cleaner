package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import android.widget.Toast

class MyService : Service() {
    lateinit var player: MediaPlayer
    override fun onStart(intent: Intent?, startId: Int) {
        Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show()

        val x = Runnable {
            player = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI)
            player.isLooping = false
            player.start()
            stopSelf()
        }

        val y = Thread(x)
        y.start()
    }

    override fun onDestroy() {
        Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show()

    }

    override fun onBind(intent: Intent): IBinder? {
        Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show()
        return null
    }
}
