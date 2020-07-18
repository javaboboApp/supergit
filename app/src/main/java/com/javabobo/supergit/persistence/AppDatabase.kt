package com.javabobo.supergit.persistence

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [DBGitRepository::class, DBUser::class, DBCommit::class], version = 18)
abstract class AppDatabase : RoomDatabase() {
    abstract val gitRepoDao: GitRepoDao
}