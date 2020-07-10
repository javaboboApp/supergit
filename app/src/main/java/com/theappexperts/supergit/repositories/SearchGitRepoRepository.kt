package com.theappexperts.supergit.repositories

import com.theappexperts.supergit.models.GitUser
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.theappexperts.supergit.AppExecutors
import com.theappexperts.supergit.mappers.asDbMoodel
import com.theappexperts.supergit.mappers.asDomainModel
import com.theappexperts.supergit.mappers.asListUserTransfer
import com.theappexperts.supergit.models.GitRepository
import com.theappexperts.supergit.network.*
import com.theappexperts.supergit.utils.Resource
import com.theappexperts.supergit.persistence.AppDatabase
import com.theappexperts.supergit.utils.ERROR_INSERTING
import com.theappexperts.supergit.utils.Event
import com.theappexperts.supergit.utils.NetworkBoundResource
import com.theappexperts.supergit.utils.Utitlites.runDelayForTesting
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception

private const val TAG = "SearchGitRepoRepository"

interface ISearchGitRepo {
    fun searchRepositories(user: String): LiveData<Resource<List<GitRepositoryTransfer>>>
    fun searchUser(userName: String): LiveData<Resource<List<GitUser>>>
    fun getCurrentUsers(): LiveData<Resource<List<GitUser>>>
    fun insertUser(user: GitUser): LiveData<Resource<GitUser>>
}

class SearchGitRepoRepository(
    private val gitRepoService: IGitRepoService,
    private val database: AppDatabase,
    var createDelay: Boolean = false
) : ISearchGitRepo {

    override fun getCurrentUsers(): LiveData<Resource<List<GitUser>>> {
        val result = MediatorLiveData<Resource<List<GitUser>>>()
        result.value = Resource.loading(null)

        val localUser = database.gitRepoDao.getLocalUsers()
        result.addSource(localUser) {
            result.removeSource(localUser)
            //only in the debug version...
            runDelayForTesting(createDelay) {
                result.value = Resource.success(Event(it.asDomainModel()))
            }
        }

        return result


    }


    override fun insertUser(user: GitUser): LiveData<Resource<GitUser>> {
        val result = MutableLiveData<Resource<GitUser>>()
        result.value = Resource.loading(null)
        AppExecutors.instance?.diskIO()?.execute {
            try {
                val valueReturned = database.gitRepoDao.insertUser(user.asDbMoodel())
                if (valueReturned == -1L) {
                    result.postValue(Resource.error(ERROR_INSERTING, null))
                    return@execute
                }
                //only in the debug version...
                runDelayForTesting(createDelay) {
                    result.postValue(Resource.success(Event(user)))
                }
            } catch (exception: Exception) {
                result.postValue(Resource.error(exception.message, null))
            }
        }

        return result

    }

    override fun searchUser(userName: String): LiveData<Resource<List<GitUser>>> {
        val searchUserLiveData = MediatorLiveData<Resource<List<GitUser>>>()
        searchUserLiveData.value = Resource.loading(null)
        searchUserLiveData.addSource(gitRepoService.searchUser(userName)) {
            searchUserLiveData.removeSource(searchUserLiveData)
            //only in the debug version...
            runDelayForTesting(createDelay) {
                when (it) {
                    is ApiSuccessResponse -> {
                        searchUserLiveData.value =
                            Resource.success(Event(it.body.asListUserTransfer().take(4)))
                    }
                    is ApiErrorResponse -> {
                        searchUserLiveData.value = Resource.error(it.errorMessage, null)
                    }
                    is ApiEmptyResponse -> {
                        searchUserLiveData.value = Resource.success(Event(listOf()))
                    }
                }
            }
        }


        return searchUserLiveData
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

