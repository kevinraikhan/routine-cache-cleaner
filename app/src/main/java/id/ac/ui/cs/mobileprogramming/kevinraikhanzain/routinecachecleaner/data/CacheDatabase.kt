package id.ac.ui.cs.mobileprogramming.kevinraikhanzain.routinecachecleaner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [History::class, Ram::class, Storage::class], version = 8)
abstract class CacheDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao?
    abstract fun ramDao(): RamDao?
    abstract fun storageDao(): StorageDao?


    companion object {
        private var instance: CacheDatabase? =
            null

        @Synchronized
        fun getInstance(context: Context): CacheDatabase? {
            if (instance == null) {
                instance =
                    Room.databaseBuilder<CacheDatabase>(
                        context.applicationContext,
                        CacheDatabase::class.java, "history_database"
                    ).fallbackToDestructiveMigration().build()
            }
            return instance
        }
    }
}