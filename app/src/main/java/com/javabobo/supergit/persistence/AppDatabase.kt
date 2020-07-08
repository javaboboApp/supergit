package com.bridge.androidtechnicaltest.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.javabobo.supergit.models.GitRepository
import com.javabobo.supergit.persistence.DBGitRepository
import com.javabobo.supergit.persistence.DBUser


@Database(entities = [DBGitRepository::class, DBUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val gitRepoDao: GitRepoDao
}