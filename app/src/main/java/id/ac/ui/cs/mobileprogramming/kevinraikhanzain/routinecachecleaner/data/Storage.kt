package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "storage_table")
data class Storage(
    var internalFree: Long?,
    var internalTotal: Long?,
    var internalUsed: Long?,
    var externalFree: Long?,
    var externalTotal: Long?,
    var externalUsed: Long?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
