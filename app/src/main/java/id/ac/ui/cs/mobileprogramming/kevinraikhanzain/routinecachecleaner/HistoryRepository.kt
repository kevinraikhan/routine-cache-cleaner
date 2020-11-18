package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData


class HistoryRepository(var application: Application) {
    private var historyDao: HistoryDao? = null
    private var ramDao: RamDao? = null
    private var storageDao: StorageDao? = null
    private var allHistory: LiveData<MutableList<History>>? = null
    private var allRam: LiveData<MutableList<Ram>>? = null
    private var allStorage: LiveData<MutableList<Storage>>? = null

    init {
        var historyDb: HistoryDatabase = HistoryDatabase.getInstance(application)!!
        historyDao = historyDb.historyDao()
        ramDao = historyDb.ramDao()
        storageDao = historyDb.storageDao()
        allHistory = historyDao?.getAllHistory()
        allRam = ramDao?.getAllRam()
        allStorage = storageDao?.getAllStorage()
    }

    fun insertHistory(history: History?) {
        InsertHistoryAsyncTask(historyDao).execute(history)
    }

    fun insertRam(ram: Ram?) {
        InsertRamAsyncTask(ramDao).execute(ram)
    }

    fun insertStorage(storage: Storage?) {
        InsertStorageAsyncTask(storageDao).execute(storage)
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
            Log.d("LOGNYA", "InsertStorageAsyncTask()")
            return null
        }
    }
}