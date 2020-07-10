package com.theappexperts.supergit.persistence

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GitRepoDao {

    @Query("SELECT * FROM DBGitRepository WHERE owner_name =:userName")
    fun getRepositoriesByUser( userName: String):LiveData<List<DBGitRepository>>

    @Query("SELECT * FROM DBGitRepository ")
    fun getRepositories():LiveData<List<DBGitRepository>>

    @Query("SELECT * FROM DBUser")
    fun getLocalUsers():LiveData<List<DBUser>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: DBUser): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRespositories(user:  List<DBGitRepository>): LongArray

    @Delete
    fun deleteUser(user: DBUser) : Int
}