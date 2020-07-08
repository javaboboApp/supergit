package com.bridge.androidtechnicaltest.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.javabobo.supergit.models.GitUser
import com.javabobo.supergit.persistence.DBUser

@Dao
interface GitRepoDao {

    @Query("SELECT * FROM DBGitRepository AS Repo INNER JOIN DBUser As User ON Repo.ownerName = User.username WHERE User.username =:userName")
    fun getRepositories( userName: String)
    @Query("SELECT * FROM DBUser")
    fun getLocalUsers():LiveData<List<DBUser>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: GitUser)
}