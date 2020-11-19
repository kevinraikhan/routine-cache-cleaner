package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.util

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
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val memoryInfo = ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memoryInfo)
            return memoryInfo.totalMem
        }

        fun getMemoryAvailInBytes(context: Context): Long {
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val memoryInfo = ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memoryInfo)
            return memoryInfo.availMem
        }

        fun calculateCache(): Long {
            var totalCache: Long = 0
            val dir = File("/sdcard/Android/data")
            dir.listFiles()?.forEach {
                it.listFiles()?.forEach {
                    if (it.name == "cache") {
                        totalCache += calculateDirectorySize(it)
                    }
                }

            }

            return totalCache
        }

        // Returned directory size in byte
        fun calculateDirectorySize(dir: File): Long {
            var result: Long = 0
            if (dir.exists()) {
                dir.listFiles()?.forEach {
                    result += if (it.isDirectory) {
                        calculateDirectorySize(it)
                    } else {
                        it.length()
                    }
                }
                return result
            }

            return 0
        }


        fun clearCache() {
            val dir = File("/sdcard/Android/data")
            dir?.listFiles().forEach {
                it?.listFiles()?.forEach {
                    if (it.name == "cache") {
                        it?.listFiles()?.forEach {
                            it.deleteRecursively()
                        }
                    }
                }

                if (it.name == "abc") {
                    it.delete()
                }
            }
        }
    }
}