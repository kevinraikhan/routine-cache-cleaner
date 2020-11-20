package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.app.Application
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data.History
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data.Repository
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.util.StringUtils
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: Repository? = null
    private var allHistory: LiveData<MutableList<History>>? = null
    private var historyHtml: String = ""

    init {
        repository =
            Repository(
                application
            )
        allHistory = repository!!.getAllHistory()
    }


    fun getAllHistory(): LiveData<MutableList<History>>? {
        return allHistory
    }

    fun deleteAllHistory() {
        repository?.deleteAllHistory()
    }

    fun createHistoryListHTML(): String? {

        allHistory?.value?.forEach {
            historyHtml += "<h2>Cleared : ${StringUtils.bytesToHuman(it.amountCleared)} | at : ${StringUtils.milisToDateString(
                it.time
            )}</h2><br>"
        }

        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            .toString() + "/" + "LogRoutineCacheCleaner.html"
        val file = File(path)
        try {
            val out: OutputStream = FileOutputStream(file)
            if (historyHtml.isEmpty()) {
                historyHtml += "<h2>List Empty</h2>"
            }
            out.write(("<h1>History List</h1>$historyHtml").toByteArray())
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.path
    }
}