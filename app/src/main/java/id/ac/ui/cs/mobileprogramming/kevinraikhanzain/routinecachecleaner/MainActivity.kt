package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import MainPageViewPagerAdapter
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {
    var pendingIntent: PendingIntent? = null
//    var myReceiver: MyReceiver = MyReceiver()

        lateinit var historyViewModel: HistoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MemoryUtils.getMemorySizeInBytes(applicationContext).toString()
        val adapter = MainPageViewPagerAdapter(supportFragmentManager)
        val storageFragment: StorageFragment = StorageFragment()
        val ramFragment: RamFragment = RamFragment()
        val historyFragment: HistoryFragment = HistoryFragment()
        adapter.addFragment(storageFragment, "Storage Detail")
        adapter.addFragment(ramFragment, "Ram Detail")
        adapter.addFragment(historyFragment, "History Detail")
        viewPagerMainPage.adapter = adapter
        tabLayout.setupWithViewPager(viewPagerMainPage)



        textViewTimeOfAutoClean.setOnClickListener {
            val serviceIntent = Intent(this, MyService::class.java)
            ContextCompat.startForegroundService(this, serviceIntent)
        }
        buttonClearNow.setOnClickListener {
            val serviceIntent = Intent(this, MyService::class.java)
            stopService(serviceIntent)
        }

        historyViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(HistoryViewModel::class.java)
        textViewAutoClear.setOnClickListener {
            historyViewModel.insert(History("ABC", 1000, 20))
            Toast.makeText(this, "KEPENCET GAN ", Toast.LENGTH_SHORT).show()
        }

//        val adapterHistory = HistoryAdapter()
//        recycler_view.layoutManager = LinearLayoutManager(this)
//        recycler_view.setHasFixedSize(true)
//        recycler_view.adapter = adapterHistory
//        // COBA COBA VIEWMODEL
//        historyViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(HistoryViewModel::class.java)
//        historyViewModel.getAllHistory()?.observe(this, Observer {
//            Toast.makeText(this, "onChanged BRUBAH", Toast.LENGTH_SHORT).show()
//            var theResult: String = ""
//            it.forEach {
//                theResult += "[${it.title} | ${it.time}] "
//            }
//            adapterHistory.setHistory(it)
//            textViewCacheSize.text = theResult
//        })
//        textViewAutoClear.setOnClickListener {
//            historyViewModel.insert(History("ABC", 1000, 20))
//            Toast.makeText(this, "KEPENCET GAN ", Toast.LENGTH_SHORT).show()
//        }

        // END OF COBA COBA VIEWMODEL

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
////        val resultnya = "Total : ${bytesToHuman(totalMemory())} | Free : ${bytesToHuman(freeMemory())}\n\nTotal : ${bytesToHuman(totalMemoryExt() ?: 0)} | Free : ${bytesToHuman(freeMemoryExt() ?: 0)}\n\nTotal RAM : ${bytesToHuman(getMemorySizeInBytes())} | Available RAM : ${bytesToHuman(getMemoryAvailInBytes())}"
//        abc.text = bytesToHuman(calculateCache()).toString()
//        textViewCacheSize.text = bytesToHuman(calculateCache()).toString()
    }

    fun calculateCache(): Long {
        var totalCache: Long = 0
        val dir = File("/sdcard/Android/data")
        dir.listFiles()?.forEach {
            Log.d("LOGNYA", "HAHAAAAA NIH ${it.name}")
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
//        registerReceiver(myReceiver, filter)
    }

}