package com.theappexperts.supergit.repositories

import com.theappexperts.supergit.models.GitUser
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.theappexperts.supergit.AppExecutors
import com.theappexperts.supergit.mappers.*
import com.theappexperts.supergit.models.Commit
import com.theappexperts.supergit.models.GitRepository
import com.theappexperts.supergit.network.*
import com.theappexperts.supergit.network.TransferModel.CommitsContainerTransfer
import com.theappexperts.supergit.network.TransferModel.GitRepositoryTransfer
import com.theappexperts.supergit.persistence.AppDatabase
import com.theappexperts.supergit.utils.*
import com.theappexperts.supergit.utils.Utitlites.runDelayForTesting
import java.lang.Exception

private const val TAG = "SearchGitRepoRepository"

interface ISearchGitRepo {
    fun getPublicRepositoriesByUser(username: String): LiveData<Resource<List<GitRepository>>>
    fun getPublicAndPrivateRepositories(username: String,token: String): LiveData<Resource<List<GitRepository>>>

    fun searchUser(userName: String): LiveData<Resource<List<GitUser>>>
    fun getCurrentUsers(): LiveData<Resource<List<GitUser>>>
    fun insertUser(user: GitUser): LiveData<Resource<GitUser>>
    fun deleteUser(user: GitUser): LiveData<Resource<GitUser>>
    fun getCommits(gitRepository: GitRepository, token:String? = null): LiveData<Resource<List<Commit>>>
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
                return true
            }

            override fun loadFromDb(): LiveData<List<GitRepository>> =
                Transformations.map(database.gitRepoDao.getRepositoriesByUser(userName)) { listDBRepo -> listDBRepo.asListDomainModel() }


            override fun createCall(): LiveData<ApiResponse<List<GitRepositoryTransfer>>> {
                return gitRepoService.getPublicRepositoriesByUser(
                    userName,
                    GET_REPOSITORIES_PARAM_TYPE
                )
            }

        }.asLiveData()
    }

    override fun getPublicAndPrivateRepositories(userName: String,token: String): LiveData<Resource<List<GitRepository>>> {
        return object :
            NetworkBoundResource<List<GitRepository>, List<GitRepositoryTransfer>>(appExecutors = AppExecutors.instance!!) {
            override fun saveCallResult(item: List<GitRepositoryTransfer>) {
                val user = item.asDatabaseModel(userName)

                //running in a thread...
                database.gitRepoDao.insertRespositories(user)
            }

            override fun shouldFetch(data: List<GitRepository>?): Boolean {
                //TODO  24 H make a request
                return true
            }

            override fun loadFromDb(): LiveData<List<GitRepository>> =
                Transformations.map(database.gitRepoDao.getRepositories()) {
                        listDBRepo ->
                        listDBRepo.asListDomainModel()

                }


            override fun createCall(): LiveData<ApiResponse<List<GitRepositoryTransfer>>> {
                return gitRepoService.getPublicAndPrivateRepositories("token " + token)
            }

        }.asLiveData()
    }


    override fun getCommits(gitRepository: GitRepository,token:String?): LiveData<Resource<List<Commit>>> {
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


                return token?.let { gitRepoService.getCommit(gitRepository.full_name, "token $token")} ?:
                gitRepoService.getCommit(gitRepository.full_name)

            }
        }.asLiveData()
    }


}

