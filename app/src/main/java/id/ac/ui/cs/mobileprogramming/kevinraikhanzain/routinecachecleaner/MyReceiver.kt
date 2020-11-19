package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data.CacheDatabase
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data.History
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data.HistoryDao
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.service.MyService
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.util.MemoryUtils
import kotlin.concurrent.thread

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when {
            intent.action.equals(Intent.ACTION_SCREEN_OFF) -> {
                Toast.makeText(context, "Screen OFF", Toast.LENGTH_SHORT).show()
                val db: CacheDatabase? = CacheDatabase.getInstance(context)

                thread {
                    (db?.historyDao() as HistoryDao).insertHistory(
                        History(
                            MemoryUtils.calculateCache(),
                            System.currentTimeMillis()
                        )
                    )
                }
                MemoryUtils.clearCache()
                context.stopService(Intent(context, MyService::class.java))
            }
        }
    }
}

