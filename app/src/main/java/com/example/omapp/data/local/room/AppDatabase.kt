package com.example.omapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.omapp.DATABASE_NAME
import com.example.omapp.DATABASE_VERSION

@TypeConverters(Converters::class)
@Database(
    entities = [MovieRoomModel::class, MovieFavoriteRoomModel::class],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =

            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()

    }

    abstract fun movieDao(): MovieDao

}