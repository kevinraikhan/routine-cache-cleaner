package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: HistoryRepository? = null
    private var allHistory: LiveData<MutableList<History>>? = null

    init {
        repository = HistoryRepository(application)
        allHistory = repository!!.getAllHistory();

    }

    fun insert(history: History?) {
        repository!!.insert(history)
    }

    fun getAllHistory(): LiveData<MutableList<History>>? {
        return allHistory
    }
}