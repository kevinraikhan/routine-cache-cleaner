package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History)

    @Query("DELETE FROM history_table")
    fun deleteAllHistory()

    @Query("SELECT * FROM history_table ORDER BY time DESC")
    open fun getAllHistory(): LiveData<MutableList<History>>


}