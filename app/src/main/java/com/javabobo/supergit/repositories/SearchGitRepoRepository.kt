package com.javabobo.supergit.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.bridge.androidtechnicaltest.persistence.AppDatabase
import com.javabobo.supergit.AppExecutors
import com.javabobo.supergit.models.*
import com.javabobo.supergit.network.ApiResponse
import com.javabobo.supergit.network.GitRepositoryTransfer
import com.javabobo.supergit.network.IGitRepoService
import com.javabobo.supergit.network.SearchGitUsersContainer
import com.javabobo.supergit.persistence.asDomainModel
import com.javabobo.supergit.utils.NetworkBoundResource
import com.javabobo.supergit.utils.Resource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

private const val TAG = "SearchGitRepoRepository"

interface ISearchGitRepo {
    fun searchRepositories(user: String): LiveData<Resource<List<GitRepositoryTransfer>>>
    suspend fun searchUser(userName: String)
    fun getSearchUserLiveData(): LiveData<SearchGitUsersContainer>
    fun getCurrentUsersLiveData(): LiveData<List<GitUser>>
    fun insertUser(user: GitUser)
}

class SearchGitRepoRepository(
    private val gitRepoService: IGitRepoService,
    private val database: AppDatabase
) : ISearchGitRepo {

    override fun getCurrentUsersLiveData(): LiveData<List<GitUser>> {
        return Transformations.map(database.gitRepoDao.getLocalUsers()) {
            it.asDomainModel()
        }
    }

    private val searchUserLiveData = MutableLiveData<SearchGitUsersContainer>()
    override fun getSearchUserLiveData(): LiveData<SearchGitUsersContainer> {
        return searchUserLiveData
    }

    override fun insertUser(user: GitUser) {
        database.gitRepoDao.insertUser(user.asDbMoodel())
    }

    override suspend fun searchUser(userName: String){
        withContext(Dispatchers.IO) {
            try {
                var getSearchUser = gitRepoService.searchUser(userName)
                val userResult = getSearchUser.await()
                searchUserLiveData.postValue(userResult)
            } catch (e: IOException) {

            }
        }
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

