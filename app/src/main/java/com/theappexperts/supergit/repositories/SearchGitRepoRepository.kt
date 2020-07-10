package com.theappexperts.supergit.repositories

import com.theappexperts.supergit.models.GitUser
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.theappexperts.supergit.AppExecutors
import com.theappexperts.supergit.mappers.asDbMoodel
import com.theappexperts.supergit.mappers.asDomainModel
import com.theappexperts.supergit.mappers.asGitRepositoryModel
import com.theappexperts.supergit.mappers.asListUserTransfer
import com.theappexperts.supergit.models.GitRepository
import com.theappexperts.supergit.network.*
import com.theappexperts.supergit.utils.Resource
import com.theappexperts.supergit.persistence.AppDatabase
import com.theappexperts.supergit.utils.ERROR_INSERTING
import com.theappexperts.supergit.utils.Event
import com.theappexperts.supergit.utils.GET_REPOSITORIES_PARAM_TYPE
import java.lang.Exception

private const val TAG = "SearchGitRepoRepository"

interface ISearchGitRepo {
    fun getPublicRepositoriesByUser(username: String): MediatorLiveData<Resource<List<GitRepository>>>
    fun searchUser(userName: String): LiveData<Resource<List<GitUser>>>
    fun getCurrentUsers(): LiveData<Resource<List<GitUser>>>
    fun insertUser(user: GitUser): LiveData<Resource<GitUser>>
    fun deleteUser(user: GitUser): LiveData<Resource<GitUser>>
}

class SearchGitRepoRepository(
    private val gitRepoService: IGitRepoService,
    private val database: AppDatabase
) : ISearchGitRepo {

    override fun getCurrentUsers(): LiveData<Resource<List<GitUser>>> {
        val result = MediatorLiveData<Resource<List<GitUser>>>()
        result.value = Resource.loading(null)
        val localUser = database.gitRepoDao.getLocalUsers()
        result.addSource(localUser){
            result.removeSource(localUser)
            result.value = Resource.success(Event(it.asDomainModel()))
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
                result.postValue(Resource.success(Event(user)))
            } catch (exception: Exception) {
                result.postValue(Resource.error(exception.message, null))
            }
        }

        return result

    }

    override fun deleteUser(user: GitUser): LiveData<Resource<GitUser>> {
        val result = MutableLiveData<Resource<GitUser>>()
        result.value = Resource.loading(null)
        AppExecutors.instance?.diskIO()?.execute {
            try {
                val valueReturned = database.gitRepoDao.deleteUser(user.asDbMoodel())
                /*if (valueReturned == -1L) {
                    result.postValue(Resource.error(ERROR_INSERTING, null))
                    return@execute
                }*/
                result.postValue(Resource.success(Event(user)))
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
            when (it) {
                is ApiSuccessResponse -> {
                    searchUserLiveData.value = Resource.success(Event(it.body.asListUserTransfer().take(4)))
                }
                is ApiErrorResponse -> {
                    searchUserLiveData.value = Resource.error(it.errorMessage, null)
                }
                is ApiEmptyResponse -> {
                    searchUserLiveData.value = Resource.success(Event(listOf()))
                }
            }
        }


        return searchUserLiveData
    }


    override fun getPublicRepositoriesByUser(userName: String): MediatorLiveData<Resource<List<GitRepository>>> {

        val repositoriesLiveData = MediatorLiveData<Resource<List<GitRepository>>>()
        repositoriesLiveData.value = Resource.loading(null)
        repositoriesLiveData.addSource(gitRepoService.getPublicRepositoriesByUser(userName,GET_REPOSITORIES_PARAM_TYPE)){
            repositoriesLiveData.removeSource(repositoriesLiveData)
            when(it){
                is ApiSuccessResponse -> {
                    repositoriesLiveData.value = Resource.success(Event(it.body.asGitRepositoryModel()))
                }
                is ApiErrorResponse -> {
                    repositoriesLiveData.value = Resource.error(it.errorMessage, null)
                }
                is ApiEmptyResponse -> {
                    repositoriesLiveData.value = Resource.success(Event(listOf()))
                }
            }
        }
        return repositoriesLiveData
    }


}

