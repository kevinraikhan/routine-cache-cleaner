package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data.History
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data.Repository


class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: Repository? = null
    private var allHistory: LiveData<MutableList<History>>? = null

    init {
        repository =
            Repository(
                application
            )
        allHistory = repository!!.getAllHistory();

    }

    fun insert(history: History?) {
        repository!!.insertHistory(history)
    }

    fun getAllHistory(): LiveData<MutableList<History>>? {
        return allHistory
    }
}