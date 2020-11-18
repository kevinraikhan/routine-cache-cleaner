package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History)

    @Query("SELECT * FROM history_table ORDER BY time DESC")
    open fun getAllHistory(): LiveData<MutableList<History>>
}