package com.marvelstask.mahmoud.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.marvelstask.mahmoud.data.database.dao.CharactersDao
import com.marvelstask.mahmoud.data.database.entinies.ComicModel
import com.marvelstask.mahmoud.data.database.dao.ComicsDao
import com.marvelstask.mahmoud.data.database.entinies.CharacterModel


@Database(entities = [ComicModel::class,CharacterModel::class], version = AppDatabase.VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun comicsDao(): ComicsDao
    abstract fun charactersDao(): CharactersDao

    companion object {
        const val DB_NAME = "marvel_comics.db"
        const val VERSION = 1
        var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance as AppDatabase
        }

    }
}

