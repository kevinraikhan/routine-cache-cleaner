package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface StorageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStorage(storage: Storage)

    @Query("SELECT * FROM storage_table ORDER BY id DESC")
    open fun getAllStorage(): LiveData<MutableList<Storage>>
}