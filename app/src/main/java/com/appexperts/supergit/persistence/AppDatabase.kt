package com.bridge.androidtechnicaltest.persistence

import androidx.room.RoomDatabase


//@Database(entities = [GitRepository::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val gitRepoDao: GitRepoDao
}