package com.theappexperts.supergit.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.theappexperts.supergit.models.GitRepository
import com.theappexperts.supergit.persistence.DBGitRepository
import com.theappexperts.supergit.persistence.DBUser


@Database(entities = [DBGitRepository::class, DBUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val gitRepoDao: GitRepoDao
}