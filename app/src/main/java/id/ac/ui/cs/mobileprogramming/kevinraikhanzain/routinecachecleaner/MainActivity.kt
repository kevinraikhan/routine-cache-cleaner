package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import MainPageViewPagerAdapter
import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.switchmaterial.SwitchMaterial
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.service.MyService
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.util.StringUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


const val ONE_DAY_MILIS: Long = 1000 * 60 * 60 * 24
const val DEMO_TIME_ONE_MINUTE: Long = 60000
const val IS_AUTO_CLEAN_ON: String = "IS_AUTO_CLEAN_ON"

class MainActivity : AppCompatActivity() {

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        var isInWaitingToClearCache = false
        override fun onReceive(context: Context, intent: Intent) {
            // Get extra data included in the Intent
            this.isInWaitingToClearCache = intent.getBooleanExtra("WAITING_TO_CLEAR_CACHE", false)
            if (isInWaitingToClearCache) {

                textViewTimeOfAutoClean.text = "Time to clear cache!"
            } else {
                textViewTimeOfAutoClean.text = "Clear cache every 1 minute"
            }
        }
    }

    private lateinit var sharedPref: SharedPreferences

    private var pendingIntent: PendingIntent? = null
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref =
            this@MainActivity.getPreferences(Context.MODE_PRIVATE)


        checkPermission()
        initViewModel()
        initView()
        initClickListener()

        LocalBroadcastManager.getInstance(this).registerReceiver(
            mMessageReceiver, IntentFilter("CLEAR_CACHE_PROCESS_STATUS")
        )
//        textViewAutoClear.text = methodJNIMilisToDate(20).toString()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.calculateCache()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var isHasPermission = true
        grantResults.forEach {
            if (it != PackageManager.PERMISSION_GRANTED) {
                isHasPermission = false
            }
        }
        if (!isHasPermission) {
            val intent = Intent(this, NoPermissionActivity::class.java)
            startActivity(intent)
            finish()
        }
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

        val sharedPref = this@MainActivity.getPreferences(Context.MODE_PRIVATE) ?: return
        val isAutoCleanOn = sharedPref.getBoolean(IS_AUTO_CLEAN_ON, false)
        switchAutoClean.isChecked = isAutoCleanOn
        onSwitchClicked(isAutoCleanOn)
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
            onSwitchClicked((it as SwitchMaterial).isChecked)
        }
    }

    private fun onSwitchClicked(bool: Boolean) {
        val myIntent = Intent(this@MainActivity, MyService::class.java)
        pendingIntent = PendingIntent.getService(this@MainActivity, 0, myIntent, 0)
        val alarmManager =
            getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar: Calendar = Calendar.getInstance()
        if (bool) {
            /*
            *
            * Set the alarm to start at approximately 2:00 a.m.
            * Shell command to test this :
            * adb shell "su 0 toybox date 112000592020.55"
            * adb shell "su 0 toybox date 112001592020.55"
            *
            * */
            calendar.add(Calendar.SECOND, 4)


            // DEMONSTRATION Code Every 2 Minutes
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                DEMO_TIME_ONE_MINUTE,
                pendingIntent
            )

            // ACTUAL Code EVERYDAY at 02:00
//            calendar.set(Calendar.SECOND, 0)
//            calendar.set(Calendar.MINUTE, 0)
//            calendar.set(Calendar.HOUR_OF_DAY, 2)
//            if (calendar.before(Calendar.getInstance())) {
//                calendar.add(Calendar.DATE, 1)
//
//            }
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                ONE_DAY_MILIS,
//                pendingIntent
//            )
            Toast.makeText(
                this@MainActivity,
                getString(R.string.auto_on),
                Toast.LENGTH_LONG
            ).show()

            val editor = sharedPref.edit()
            editor.putBoolean(IS_AUTO_CLEAN_ON, true)
            editor.apply()

        } else {
            alarmManager.cancel(pendingIntent)
            Toast.makeText(
                this@MainActivity,
                getString(R.string.auto_off),
                Toast.LENGTH_LONG
            ).show()

            val editor = sharedPref.edit()
            editor.putBoolean(IS_AUTO_CLEAN_ON, false)
            editor.apply()
        }
    }


    private fun initTab() {
        val adapter = MainPageViewPagerAdapter(supportFragmentManager)
        val storageFragment = StorageFragment()
        val ramFragment = RamFragment()
        val historyFragment = HistoryFragment()
        adapter.addFragment(storageFragment, getString(R.string.storage_detail))
        adapter.addFragment(ramFragment, getString(R.string.ram_detail))
        adapter.addFragment(historyFragment, getString(R.string.history))
        viewPagerMainPage.adapter = adapter
        tabLayout.setupWithViewPager(viewPagerMainPage)
    }




}