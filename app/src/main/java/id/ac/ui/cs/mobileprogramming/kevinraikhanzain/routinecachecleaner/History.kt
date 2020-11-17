package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

@Entity(tableName = "history_table")
data class History(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String?,
    var amountCleared: Int,
    var time: Long
)