package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

@Entity(tableName = "history_table")
data class History(

    var title: String?,
    var amountCleared: Int,
    var time: Long,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)