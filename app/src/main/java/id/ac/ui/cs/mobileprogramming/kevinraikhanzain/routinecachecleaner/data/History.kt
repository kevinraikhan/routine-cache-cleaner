package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class History(
    var amountCleared: Long,
    var time: Long,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)