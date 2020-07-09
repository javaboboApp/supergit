package com.theappexperts.supergit.persistence

import android.content.Context
import androidx.room.Room

object DatabaseFactory  {

    fun getDBInstance(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "GitRepo")
            .fallbackToDestructiveMigration()
            .build()

}