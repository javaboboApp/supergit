package com.theappexperts.supergit.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.theappexperts.supergit.models.Commit

@Dao
interface GitRepoDao {

    @Query("SELECT * FROM DBUser")
    fun getLocalUsers():LiveData<List<DBUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: DBUser): Long

    @Delete
    fun deleteUser(user: DBUser) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRespositories(repositories:  List<DBGitRepository>): LongArray

    @Query("SELECT * FROM DBGitRepository WHERE owner_name =:userName AND private_repo = 'public'")
    fun getPublicRepositoriesByUser(userName: String):LiveData<List<DBGitRepository>>

    @Query("SELECT * FROM DBGitRepository WHERE owner_name =:userName ")
    fun getPrivateRepositories(userName: String):LiveData<List<DBGitRepository>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommits(commits: List<DBCommit>): LongArray

    @Query("SELECT * FROM DBCommit WHERE repo_id =:repoId ORDER BY timestamp DESC")
    fun getCommitsByRepoId( repoId: Long):LiveData<List<DBCommit>>


}