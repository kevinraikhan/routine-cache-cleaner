package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data.Repository
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data.Ram
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.util.MemoryUtils

class RamFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: Repository? = null
    private var allRam: LiveData<MutableList<Ram>>? = null

    fun calculateInternalStorage() {
        val freeMem = MemoryUtils.getMemoryAvailInBytes(getApplication())
        val totalMem = MemoryUtils.getMemorySizeInBytes(getApplication())
        val usedMem = totalMem - freeMem

        insert(
            Ram(
                freeMem,
                totalMem,
                usedMem
            )
        )
    }


    init {
        repository =
            Repository(
                getApplication()
            )
        allRam = repository!!.getAllRam()
    }

    fun insert(ram: Ram) {
        repository?.insertRam(ram)
    }

    fun getAllStorage(): LiveData<MutableList<Ram>>? {
        return allRam
    }
}