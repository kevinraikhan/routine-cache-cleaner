package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.PowerManager
import android.util.Log
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("LOGNYA", "Berhasil Broadcast nya nih gan")
        PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            val noConnectivity = intent.getBooleanExtra(
                ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
            );
            if (noConnectivity) {
                Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

