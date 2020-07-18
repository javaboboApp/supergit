package com.javabobo.supergit.repositories

import com.javabobo.supergit.models.GitUser
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.javabobo.supergit.AppExecutors
import com.javabobo.supergit.mappers.*
import com.javabobo.supergit.models.Commit
import com.javabobo.supergit.models.GitRepository
import com.javabobo.supergit.network.*
import com.javabobo.supergit.network.TransferModel.CommitsContainerTransfer
import com.javabobo.supergit.network.TransferModel.GitRepositoryTransfer
import com.javabobo.supergit.persistence.AppDatabase
import com.javabobo.supergit.utils.*
import com.javabobo.supergit.utils.Utitlites.runDelayForTesting
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import java.util.concurrent.TimeUnit.*

private const val TAG = "SearchGitRepoRepository"

interface ISearchGitRepo {
    fun getPublicRepositoriesByUser(username: String): LiveData<Resource<List<GitRepository>>>
    fun getPublicAndPrivateRepositories(
        username: String,
        token: String
    ): LiveData<Resource<List<GitRepository>>>

    fun searchUser(userName: String): LiveData<Resource<List<GitUser>>>
    fun getCurrentUsers(): Flow<Resource<List<GitUser>>>
    fun insertUser(user: GitUser): LiveData<Resource<GitUser>>
    fun deleteUser(user: GitUser): LiveData<Resource<GitUser>>
    fun getCommits(
        userName: String,
        gitRepository: GitRepository,
        token: String? = null
    ): LiveData<Resource<List<Commit>>>
}

class SearchGitRepoRepository(
    private val gitRepoService: IGitRepoService,
    private val database: AppDatabase,
    var createDelay: Boolean = false
) : ISearchGitRepo {

    @InternalCoroutinesApi
    override fun getCurrentUsers(): Flow<Resource<List<GitUser>>> = flow {


        val localUser = database.gitRepoDao.getLocalUsers()

        localUser.collect {
            emit ( Resource.success(Event(it.asDomainModel())))
        }



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

    override fun deleteUser(user: GitUser): LiveData<Resource<GitUser>> {
        val result = MutableLiveData<Resource<GitUser>>()
        result.value = Resource.loading(null)
        AppExecutors.instance?.diskIO()?.execute {
            try {
                val valueReturned = database.gitRepoDao.deleteUser(user.asDbMoodel())
                if (valueReturned == -1) {
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


    override fun getPublicRepositoriesByUser(userName: String): LiveData<Resource<List<GitRepository>>> {
        return object :
            NetworkBoundResource<List<GitRepository>, List<GitRepositoryTransfer>>(appExecutors = AppExecutors.instance!!) {
            override fun saveCallResult(item: List<GitRepositoryTransfer>) {
                //running in a thread...
                database.gitRepoDao.insertRespositories(item.asDatabaseModel(userName))
            }

            override fun shouldFetch(data: List<GitRepository>?): Boolean {
                //TODO  24 H make a request
                return shouldFecthCondiction(data)
            }

            override fun loadFromDb(): LiveData<List<GitRepository>> =
                Transformations.map(database.gitRepoDao.getPublicRepositoriesByUser(userName)) { listDBRepo -> listDBRepo.asListDomainModel() }


            override fun createCall(): LiveData<ApiResponse<List<GitRepositoryTransfer>>> {
                return gitRepoService.getPublicRepositoriesByUser(
                    userName,
                    GET_REPOSITORIES_PARAM_TYPE
                )
            }

        }.asLiveData()
    }

    private fun shouldFecthCondiction(data: List<GitRepository>?): Boolean {
        if (data == null || data.isEmpty())
            return true
        val firstRepo = data[0].date ?: return true

        val currentTim = System.currentTimeMillis()
        val diffMili = currentTim - firstRepo
        val diffHour = MILLISECONDS.toHours(diffMili)
        return diffHour > MAX_HOURS_UPDATED
    }

    override fun getPublicAndPrivateRepositories(
        userName: String,
        token: String
    ): LiveData<Resource<List<GitRepository>>> {
        return object :
            NetworkBoundResource<List<GitRepository>, List<GitRepositoryTransfer>>(appExecutors = AppExecutors.instance!!) {
            override fun saveCallResult(item: List<GitRepositoryTransfer>) {
                val user = item.asDatabaseModel(userName)

                //running in a thread...
                database.gitRepoDao.insertRespositories(user)
            }

            override fun shouldFetch(data: List<GitRepository>?): Boolean {
                //TODO  24 H make a request
                return shouldFetch(data)
            }

            override fun loadFromDb(): LiveData<List<GitRepository>> =

                Transformations.map(database.gitRepoDao.getPrivateRepositories(userName)) { listDBRepo ->
                    listDBRepo.asListDomainModel()
                }


            override fun createCall(): LiveData<ApiResponse<List<GitRepositoryTransfer>>> {
                return gitRepoService.getPublicAndPrivateRepositories("token " + token)
            }

        }.asLiveData()
    }


    override fun getCommits(
        userName: String,
        gitRepository: GitRepository,
        token: String?
    ): LiveData<Resource<List<Commit>>> {
        return object :
            NetworkBoundResource<List<Commit>, List<CommitsContainerTransfer>>(appExecutors = AppExecutors.instance!!) {
            override fun saveCallResult(item: List<CommitsContainerTransfer>) {
                database.gitRepoDao.insertCommits(item.asListDBCommits(gitRepository.id))
            }

            override fun shouldFetch(data: List<Commit>?): Boolean {
                //TODO 24h make a request
                return true
            }

            override fun loadFromDb(): LiveData<List<Commit>> =
                Transformations.map(database.gitRepoDao.getCommitsByRepoId(gitRepository.id)) { listDBCommit -> listDBCommit.asListCommitDomainModel() }

            override fun createCall(): LiveData<ApiResponse<List<CommitsContainerTransfer>>> {


                return token?.let {
                    gitRepoService.getCommit(
                        userName,
                        gitRepository.name,
                        "token $token"
                    )
                } ?: gitRepoService.getCommit(userName, gitRepository.name)
            }
        }.asLiveData()
    }
}

