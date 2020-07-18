package com.javabobo.supergit.utils

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.javabobo.supergit.persistence.AppDatabase
import com.javabobo.supergit.persistence.GitRepoDao
import org.junit.After
import org.junit.Before
import java.lang.Exception


open class SuperGitDatabaseTest {
    // system under test
    protected lateinit var superGitDatabase: AppDatabase
    val gitRepoDao: GitRepoDao
        get() = superGitDatabase.gitRepoDao

    @Before
    fun init() {
        //Create a temporally database
        superGitDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun finish() {
        try {
            superGitDatabase.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}