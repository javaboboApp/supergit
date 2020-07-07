package com.bridge.androidtechnicaltest.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.javabobo.supergit.models.GitRepository


//@Database(entities = [GitRepository::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val gitRepoDao: GitRepoDao
}