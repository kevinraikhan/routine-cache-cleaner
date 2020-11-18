package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class RamFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: HistoryRepository? = null
    private var allRam: LiveData<MutableList<Ram>>? = null

    fun calculateInternalStorage() {
        val freeMem = MemoryUtils.getMemoryAvailInBytes(getApplication())
        val totalMem = MemoryUtils.getMemorySizeInBytes(getApplication())
        val usedMem = totalMem - freeMem

        insert(Ram(freeMem, totalMem, usedMem))
    }


    init {
        repository = HistoryRepository(getApplication())
        allRam = repository!!.getAllRam()
    }

    fun insert(ram: Ram) {
        repository?.insertRam(ram)
    }

    fun getAllStorage(): LiveData<MutableList<Ram>>? {
        return allRam
    }
}