package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.settings_activity.*
import java.io.File


class SettingsActivity : AppCompatActivity() {
    private lateinit var viewModel: SettingsViewModel
    private lateinit var createdHtmlFilePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.setting_title)

        initViewModel()
        initObserver()
        initOnClickListener()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(application)
        )
            .get(SettingsViewModel::class.java)
    }

    private fun initObserver() {
        viewModel.getAllHistory()?.observe(this, Observer {
            createdHtmlFilePath = viewModel.createHistoryListHTML()!!
        })
    }

    private fun initOnClickListener() {
        textViewClearHistory.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Clear History")
                .setMessage("Do you really want to clear history?")
                .setPositiveButton(
                    android.R.string.yes
                ) { _, _ ->
                    viewModel.deleteAllHistory()
                }
                .setNegativeButton(android.R.string.no, null).show()
        }

        textViewShareHistory.setOnClickListener {


            val path: Uri =
                FileProvider.getUriForFile(
                    this,
                    "id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner",
                    File(createdHtmlFilePath)
                )

            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, "This is one image I'm sharing.")
            shareIntent.putExtra(Intent.EXTRA_STREAM, path)
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.type = "text/html"
            startActivity(Intent.createChooser(shareIntent, "Share..."))
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}