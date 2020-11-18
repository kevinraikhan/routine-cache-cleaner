package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import MainPageViewPagerAdapter
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data.History
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.service.MyService
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.util.MemoryUtils
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.util.StringUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var pendingIntent: PendingIntent? = null
    lateinit var mainViewModel: MainViewModel
//    var myReceiver: MyReceiver = MyReceiver()

    lateinit var historyViewModel: HistoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initView()
        garbage() // DELETE THIS
    }

    fun initView() {
        initTab()
        mainViewModel.cacheSize.observe(this, Observer {
            textViewCacheSize.text = StringUtils.bytesToHuman(it)
        })
        mainViewModel.calculateCache()


    }

    fun initViewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(application))
            .get(MainViewModel::class.java)
    }


    private fun initTab() {
        val adapter = MainPageViewPagerAdapter(supportFragmentManager)
        val storageFragment: StorageFragment = StorageFragment()
        val ramFragment: RamFragment = RamFragment()
        val historyFragment: HistoryFragment = HistoryFragment()
        adapter.addFragment(storageFragment, "Storage Detail")
        adapter.addFragment(ramFragment, "Ram Detail")
        adapter.addFragment(historyFragment, "History Detail")
        viewPagerMainPage.adapter = adapter
        tabLayout.setupWithViewPager(viewPagerMainPage)
    }


    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
//        registerReceiver(myReceiver, filter)
    }

    // DELETE THIS
    private fun garbage() {
        textViewTimeOfAutoClean.setOnClickListener {
            val serviceIntent = Intent(this, MyService::class.java)
            ContextCompat.startForegroundService(this, serviceIntent)
        }
        buttonClearNow.setOnClickListener {
            val serviceIntent = Intent(this, MyService::class.java)
            stopService(serviceIntent)
        }

        historyViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(HistoryViewModel::class.java)
        textViewAutoClear.setOnClickListener {
            historyViewModel.insert(
                History(
                    "ABC",
                    1000,
                    20
                )
            )
            Toast.makeText(this, "KEPENCET GAN ", Toast.LENGTH_SHORT).show()
        }
    }

}