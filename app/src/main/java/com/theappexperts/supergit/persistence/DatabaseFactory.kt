package com.theappexperts.reddit.persistence

import android.content.Context
import androidx.room.Room
import com.bridge.androidtechnicaltest.persistence.AppDatabase

object DatabaseFactory  {

    fun getDBInstance(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "GitRepo")
            .fallbackToDestructiveMigration()
            .build()

}