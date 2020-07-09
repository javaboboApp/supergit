package com.theappexperts.supergit.persistence

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GitRepoDao {

//    @Query("SELECT * FROM DBGitRepository AS Repo INNER JOIN DBUser As User ON Repo.owner_name = User.username WHERE User.username =:userName")
//    fun getRepositories( userName: String):LiveData<List<DBGitRepository>>
    @Query("SELECT * FROM DBUser")
    fun getLocalUsers():LiveData<List<DBUser>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: DBUser): Long
    @Delete
    fun deleteUser(user: DBUser)
}