package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class StorageFragmentViewModel(application: Application) : AndroidViewModel(application) {


    private var repository: HistoryRepository? = null
    private var allStorage: LiveData<MutableList<Storage>>? = null

    fun calculateInternalStorage() {
        val internalMemTotal: Long = MemoryUtils.getTotalInternalMemory()
        val internalMemFree: Long = MemoryUtils.getFreeInternalMemory()
        val internalMemUsed: Long = internalMemTotal - internalMemFree
        val externalMemTotal: Long? = MemoryUtils.getTotalExternalMemory()
        val externalMemFree: Long? = MemoryUtils.getFreeExternalMemory()
        val externalMemUsed: Long = (externalMemTotal ?: 0) - (externalMemFree ?: 0)
        insert(
            Storage(
                internalMemFree,
                internalMemTotal,
                internalMemUsed,
                externalMemFree,
                externalMemTotal,
                externalMemUsed,
                id = 1
            )
        )
    }


    init {
        repository = HistoryRepository(getApplication())
        allStorage = repository!!.getAllStorage()
    }

    fun insert(storage: Storage) {
        repository?.insertStorage(storage)
    }

    fun getAllStorage(): LiveData<MutableList<Storage>>? {
        return allStorage
    }
}