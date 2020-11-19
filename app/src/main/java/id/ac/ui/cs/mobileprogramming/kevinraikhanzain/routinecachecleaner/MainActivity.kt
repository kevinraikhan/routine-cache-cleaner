package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import MainPageViewPagerAdapter
import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.service.MyService
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.util.StringUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

const val ONE_DAY_MILIS: Long = 1000 * 60 * 60 * 24

class MainActivity : AppCompatActivity() {

    private var pendingIntent: PendingIntent? = null
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermission()
        initViewModel()
        initView()
        initClickListener()
        garbage() // DELETE THIS
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.calculateCache()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE

            ), 1
        )
    }

    private fun initView() {
        mainViewModel.cacheSize.observe(this, Observer {
            textViewCacheSize.text = StringUtils.bytesToHuman(it)
        })
        initTab()
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(application)
        )
            .get(MainViewModel::class.java)
    }

    private fun initClickListener() {
        buttonClearNow.setOnClickListener {
            mainViewModel.clearCache()
        }
        buttonA.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        switchAutoClean.setOnClickListener {
            val myIntent = Intent(this@MainActivity, MyService::class.java)
            pendingIntent = PendingIntent.getService(this@MainActivity, 0, myIntent, 0)
            val alarmManager =
                getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val calendar: Calendar = Calendar.getInstance()

            if ((it as SwitchMaterial).isChecked) {
                /*
                *
                * Set the alarm to start at approximately 2:00 a.m.
                * Shell command to test this :
                * adb shell "su 0 toybox date 112000592020.55"
                * adb shell "su 0 toybox date 112001592020.55"
                *
                * */
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.HOUR_OF_DAY, 2)

                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    ONE_DAY_MILIS,
                    pendingIntent
                )
                Toast.makeText(
                    this@MainActivity,
                    "Auto clear is ON",
                    Toast.LENGTH_LONG
                ).show()

            } else {
                alarmManager.cancel(pendingIntent)
                Toast.makeText(
                    this@MainActivity,
                    "Auto clear is OFF",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }


    private fun initTab() {
        val adapter = MainPageViewPagerAdapter(supportFragmentManager)
        val storageFragment = StorageFragment()
        val ramFragment = RamFragment()
        val historyFragment = HistoryFragment()
        adapter.addFragment(storageFragment, "Storage Detail")
        adapter.addFragment(ramFragment, "Ram Detail")
        adapter.addFragment(historyFragment, "History Detail")
        viewPagerMainPage.adapter = adapter
        tabLayout.setupWithViewPager(viewPagerMainPage)
    }


    // DELETE THIS
    private fun garbage() {

    }
}