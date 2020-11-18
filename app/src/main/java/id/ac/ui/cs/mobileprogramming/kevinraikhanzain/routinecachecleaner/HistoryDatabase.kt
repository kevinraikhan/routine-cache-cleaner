package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [History::class, Ram::class, Storage::class], version = 8)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao?
    abstract fun ramDao(): RamDao?
    abstract fun storageDao(): StorageDao?


    companion object {
        private var instance: HistoryDatabase? = null

        @Synchronized
        fun getInstance(context: Context): HistoryDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder<HistoryDatabase>(
                    context.applicationContext,
                    HistoryDatabase::class.java, "history_database"
                ).fallbackToDestructiveMigration().build()
            }
            return instance
        }
    }


}