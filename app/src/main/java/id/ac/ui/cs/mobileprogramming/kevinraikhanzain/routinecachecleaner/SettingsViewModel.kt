package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data.Repository

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: Repository? = null

    init {
        repository =
            Repository(
                application
            )
    }

    fun deleteAllHistory() {
        repository?.deleteAllHistory()
    }
}