package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import kotlin.concurrent.thread


class Repository(var application: Application) {
    private var historyDao: HistoryDao? = null
    private var ramDao: RamDao? = null
    private var storageDao: StorageDao? = null
    private var allHistory: LiveData<MutableList<History>>? = null
    private var allRam: LiveData<MutableList<Ram>>? = null
    private var allStorage: LiveData<MutableList<Storage>>? = null

    init {
        var db: CacheDatabase = CacheDatabase.getInstance(application)!!
        historyDao = db.historyDao()
        ramDao = db.ramDao()
        storageDao = db.storageDao()
        allHistory = historyDao?.getAllHistory()
        allRam = ramDao?.getAllRam()
        allStorage = storageDao?.getAllStorage()
    }

    fun insertHistory(history: History?) {
        InsertHistoryAsyncTask(
            historyDao
        ).execute(history)
    }

    fun insertRam(ram: Ram?) {
        InsertRamAsyncTask(
            ramDao
        ).execute(ram)
    }

    fun insertStorage(storage: Storage?) {
        InsertStorageAsyncTask(
            storageDao
        ).execute(storage)
    }

    fun getAllHistory(): LiveData<MutableList<History>>? {
        return allHistory
    }

    fun getAllRam(): LiveData<MutableList<Ram>>? {
        return allRam
    }

    fun getAllStorage(): LiveData<MutableList<Storage>>? {
        return allStorage
    }

    fun deleteAllHistory() {
        thread {
            historyDao?.deleteAllHistory()
        }
    }

    class InsertHistoryAsyncTask(var historyDao: HistoryDao?) : AsyncTask<History, Void, Void>() {
        override fun doInBackground(vararg history: History?): Void? {
            historyDao?.insertHistory(history[0]!!)
            return null
        }
    }

    class InsertRamAsyncTask(var ramDao: RamDao?) : AsyncTask<Ram, Void, Void>() {
        override fun doInBackground(vararg ram: Ram?): Void? {
            ramDao?.insertRam(ram[0]!!)
            return null
        }
    }

    class InsertStorageAsyncTask(var storageDao: StorageDao?) : AsyncTask<Storage, Void, Void>() {
        override fun doInBackground(vararg storage: Storage?): Void? {
            storageDao?.insertStorage(storage[0]!!)
            return null
        }
    }
}