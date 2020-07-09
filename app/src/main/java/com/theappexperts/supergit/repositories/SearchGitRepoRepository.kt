package com.theappexperts.supergit.repositories

import androidx.lifecycle.LiveData
import com.theappexperts.supergit.models.GitRepositoryTransfer
import com.theappexperts.supergit.models.User
import com.theappexperts.supergit.network.IGitRepoService
import com.theappexperts.supergit.utils.Resource
import com.javabobo.supergit.network.ApiResponse
import com.javabobo.supergit.network.GitRepositoryTransfer
import com.javabobo.supergit.network.SearchGitUsersContainer
import com.javabobo.supergit.persistence.asDomainModel
import com.javabobo.supergit.utils.NetworkBoundResource

private const val TAG = "SearchGitRepoRepository"

interface ISearchGitRepo {
    fun searchRepositories(user: String): LiveData<Resource<List<GitRepositoryTransfer>>>
    fun searchUser(userName: String): LiveData<ApiResponse<SearchGitUsersContainer>>
    fun getCurrentUsersLiveData(): LiveData<List<GitUser>>
    fun insertUser(user: GitUser)
}

class SearchGitRepoRepository(private val gitRepoService: IGitRepoService, private val database: AppDatabase) : ISearchGitRepo {

    override fun getCurrentUsersLiveData(): LiveData<List<GitUser>> {
        return Transformations.map(database.gitRepoDao.getLocalUsers()) {
            it.asDomainModel()
        }
    }

    override fun insertUser(user: GitUser) {
        database.gitRepoDao.insertUser(user.asDbMoodel())
    }

    override fun searchUser(userName: String): LiveData<ApiResponse<SearchGitUsersContainer>> {
        return gitRepoService.searchUser(userName)
    }


    override fun searchRepositories(userName: String): LiveData<Resource<List<GitRepositoryTransfer>>> {

        object : NetworkBoundResource<List<GitRepository>, List<GitRepositoryTransfer>>(
            AppExecutors.instance!!
        ) {
            override fun saveCallResult(item: List<GitRepositoryTransfer>) {
                TODO("Not yet implemented")
            }

            override fun shouldFetch(data: List<GitRepository>?): Boolean {
                TODO("Not yet implemented")
            }

            override fun loadFromDb(): LiveData<List<GitRepository>> {
                TODO("Not yet implemented")

            }

            override fun createCall(): LiveData<ApiResponse<List<GitRepositoryTransfer>>> {
                TODO("Not yet implemented")
            }
        }
        TODO("Not yet implemented")
    }


}

