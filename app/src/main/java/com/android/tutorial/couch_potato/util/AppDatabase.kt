package com.android.tutorial.couch_potato.util

import androidx.fragment.app.FragmentActivity
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.tutorial.couch_potato.model.MovieHistory

@Database(entities = [MovieHistory::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getMovieHistoryDao():MovieHistoryDao

    companion object{
        private var instance: AppDatabase? = null
        fun getDatabase(activity: FragmentActivity):AppDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(activity, AppDatabase::class.java, "movie_news")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}