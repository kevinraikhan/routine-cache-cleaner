package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData


class HistoryRepository(var application: Application) {
    private var historyDao: HistoryDao? = null
    private var allHistory: LiveData<MutableList<History>>? = null

    init {
        var historyDb: HistoryDatabase = HistoryDatabase.getInstance(application)!!
        historyDao = historyDb.historyDao()
        allHistory = historyDao?.getAllHistory()
    }

    fun insert(history: History?) {
        InsertHistoryAsyncTask(historyDao).execute(history)
    }

    fun getAllHistory(): LiveData<MutableList<History>>? {
        return allHistory
    }

    class InsertHistoryAsyncTask(var historyDao: HistoryDao?) : AsyncTask<History, Void, Void>() {
        override fun doInBackground(vararg history: History?): Void? {
            historyDao?.insertHistory(history[0]!!)
            return null
        }
    }
}