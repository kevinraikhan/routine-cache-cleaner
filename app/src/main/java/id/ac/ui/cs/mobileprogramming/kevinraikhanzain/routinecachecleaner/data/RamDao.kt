package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface RamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRam(ram: Ram)

    @Query("SELECT * FROM ram_table ORDER BY id DESC")
    fun getAllRam(): LiveData<MutableList<Ram>>


}