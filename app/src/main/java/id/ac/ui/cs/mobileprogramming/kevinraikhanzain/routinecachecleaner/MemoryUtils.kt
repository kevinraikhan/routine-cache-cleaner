package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.app.ActivityManager
import android.content.Context
import android.os.Environment
import java.io.File

class MemoryUtils {
    companion object {
        fun getTotalInternalMemory(): Long {
            return Environment.getDataDirectory().totalSpace
        }

        fun getFreeInternalMemory(): Long {
            return Environment.getDataDirectory().freeSpace
        }

        fun getTotalExternalMemory(): Long? {
            val storage = File("/storage")
            var sdcardFile: File? = null
            storage.listFiles()?.forEach {
                if (it.name != "emulated" && it.name != "self") {
                    sdcardFile = it
                }
            }
            return sdcardFile?.totalSpace
        }

        fun getFreeExternalMemory(): Long? {
            val storage = File("/storage")
            var sdcardFile: File? = null
            storage.listFiles()?.forEach {
                if (it.name != "emulated" && it.name != "self") {
                    sdcardFile = it
                }
            }
            return sdcardFile?.freeSpace
        }


        fun getMemorySizeInBytes(context: Context): Long {
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val memoryInfo = ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memoryInfo)
            return memoryInfo.totalMem
        }

        fun getMemoryAvailInBytes(context: Context): Long {
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val memoryInfo = ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memoryInfo)
            return memoryInfo.availMem
        }
    }
}