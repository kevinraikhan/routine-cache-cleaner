package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.util.MemoryUtils

class MainViewModel : ViewModel() {
    var cacheSize: MutableLiveData<Long> = MutableLiveData()
    fun calculateCache() {
        cacheSize.value = MemoryUtils.calculateCache()
    }
}