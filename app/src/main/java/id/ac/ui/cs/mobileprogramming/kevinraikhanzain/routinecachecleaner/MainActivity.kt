package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.app.ActivityManager
import android.app.PendingIntent
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    var pendingIntent: PendingIntent? = null
    var myReceiver: MyReceiver = MyReceiver()


    fun floatForm(d: Double): String {
        return DecimalFormat("#.##").format(d)
    }

    fun getMemorySizeInBytes(): Long {
        val context = applicationContext
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.totalMem
    }

    fun getMemoryAvailInBytes(): Long {
        val context = applicationContext
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.availMem
    }


    fun bytesToHuman(size: Long): String? {
        val Kb = 1 * 1024.toLong()
        val Mb = Kb * 1024
        val Gb = Mb * 1024
        val Tb = Gb * 1024
        val Pb = Tb * 1024
        val Eb = Pb * 1024
        if (size < Kb) return floatForm(size.toDouble()) + " byte"
        if (size >= Kb && size < Mb) return floatForm(size.toDouble() / Kb) + " Kb"
        if (size >= Mb && size < Gb) return floatForm(size.toDouble() / Mb) + " Mb"
        if (size >= Gb && size < Tb) return floatForm(size.toDouble() / Gb) + " Gb"
        if (size >= Tb && size < Pb) return floatForm(size.toDouble() / Tb) + " Tb"
        if (size >= Pb && size < Eb) return floatForm(size.toDouble() / Pb) + " Pb"
        return if (size >= Eb) floatForm(size.toDouble() / Eb) + " Eb" else "???"
    }

    /*************************************************************************************************
     * Returns size in bytes.
     *
     * If you need calculate external memory, change this:
     * StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
     * to this:
     * StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
     */
    fun totalMemory(): Long {
//        val statFs = StatFs(Environment.getDataDirectory().absolutePath)
//        return statFs.blockCountLong * statFs.blockSizeLong
        return Environment.getDataDirectory().totalSpace
    }

    fun freeMemory(): Long {
//        val statFs = StatFs(Environment.getDataDirectory().absolutePath)
//        return statFs.availableBlocksLong * statFs.blockSizeLong
        return Environment.getDataDirectory().freeSpace
    }
    fun totalMemoryExt(): Long? {
        val storage = File("/storage")
        var sdcardFile: File? = null
        storage.listFiles()?.forEach {
            if (it.name != "emulated" && it.name != "self") {
            sdcardFile = it
            }
        }
        return sdcardFile?.totalSpace
    }

    fun freeMemoryExt(): Long? {
        val storage = File("/storage")
        var sdcardFile: File? = null
        storage.listFiles()?.forEach {
            if (it.name != "emulated" && it.name != "self") {
                sdcardFile = it
            }
        }
        return sdcardFile?.freeSpace
    }


    lateinit var historyViewModel: HistoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // COBA COBA VIEWMODEL
        historyViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(HistoryViewModel::class.java)
        historyViewModel.getAllHistory()?.observe(this, Observer {
            Toast.makeText(this, "onChanged BRUBAH", Toast.LENGTH_SHORT).show()
            it.forEach {
                var awal:String = textViewCacheSize.text.toString()
                textViewCacheSize.text = awal + "${it.title} | ${it.time}"
            }
        })
        abc.setOnClickListener {
            historyViewModel.insert(History(2, "[HALO]", 33, 2222))

        }


        // LOGIC SEE MEMORY AND CLEAR CACHE
//        ActivityCompat.requestPermissions(
//            this@MainActivity,
//            arrayOf(
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//
//            ), 1
//        )
//        buttonA.setOnClickListener {
//            val intent = Intent(this, SettingsActivity::class.java)
//            startActivity(intent)
//            Toast.makeText(this, "Hello Javatpoint", Toast.LENGTH_SHORT).show()
//
//        }
//
//        abc.setOnClickListener {
////            startService(Intent(this, MyService::class.java))
//            val myIntent = Intent(this@MainActivity, MyService::class.java)
//            pendingIntent = PendingIntent.getService(this@MainActivity, 0, myIntent, 0)
//            val alarmManager =
//                getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            val calendar: Calendar = Calendar.getInstance()
//            calendar.timeInMillis = System.currentTimeMillis()
//            calendar.add(Calendar.SECOND, 12)
////            alarmManager[AlarmManager.RTC_WAKEUP, calendar.timeInMillis] = pendingIntent
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, 15000, pendingIntent)
//            Toast.makeText(this@MainActivity, "Starting Service Alarm", Toast.LENGTH_LONG).show()
//        }
//
//        val resultnya = "Total : ${bytesToHuman(totalMemory())} | Free : ${bytesToHuman(freeMemory())}\n\nTotal : ${bytesToHuman(totalMemoryExt() ?: 0)} | Free : ${bytesToHuman(freeMemoryExt() ?: 0)}\n\nTotal RAM : ${bytesToHuman(getMemorySizeInBytes())} | Available RAM : ${bytesToHuman(getMemoryAvailInBytes())}"
//        abc.text = resultnya
    }

    fun calculateCache(): Long {
        var totalCache: Long = 0
        val dir = File("/sdcard/Android/data")
        dir.listFiles()?.forEach {
            if (it.name == "cache") {
                totalCache += calculateDirectorySize(it)
            }
        }
        return (totalCache / 1024)
    }

    // Returned directory size in byte
    fun calculateDirectorySize(dir: File): Long {
        var result: Long = 0
        if (dir.exists()) {
            dir.listFiles()?.forEach {
                Log.d("LOGNYA", "JALAN NIH ${it.name}")
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


    fun deleteCache() {
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

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(myReceiver, filter)
    }

}