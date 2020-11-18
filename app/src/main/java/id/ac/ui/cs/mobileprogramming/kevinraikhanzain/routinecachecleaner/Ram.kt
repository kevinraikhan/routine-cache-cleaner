package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ram_table")
data class Ram(
    var free: Long?,
    var total: Long?,
    var used: Long,
    @PrimaryKey val id: Int = 1
)
