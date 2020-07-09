package com.bridge.androidtechnicaltest.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.javabobo.supergit.models.GitUser
import com.javabobo.supergit.persistence.DBGitRepository
import com.javabobo.supergit.persistence.DBUser

@Dao
interface GitRepoDao {

//    @Query("SELECT * FROM DBGitRepository AS Repo INNER JOIN DBUser As User ON Repo.owner_name = User.username WHERE User.username =:userName")
//    fun getRepositories( userName: String):LiveData<List<DBGitRepository>>
    @Query("SELECT * FROM DBUser")
    fun getLocalUsers():LiveData<List<DBUser>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: DBUser)
}