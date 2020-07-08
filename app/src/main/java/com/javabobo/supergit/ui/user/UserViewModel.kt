package com.javabobo.supergit.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.javabobo.supergit.models.GitUser
import com.javabobo.supergit.network.*
import com.javabobo.supergit.repositories.ISearchGitRepo
import kotlinx.coroutines.*

class UserViewModel(private val searchGitRepo: ISearchGitRepo) : ViewModel() {

    val currentUsersLiveData: LiveData<List<GitUser>> = searchGitRepo.getCurrentUsersLiveData()

    private val viewJob: Job = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + Job())

    private var searchAPIResponseLiveData: LiveData<ApiResponse<SearchGitUsersContainer>> =
        MutableLiveData()

    val errorDuringSearchingLiveData: LiveData<Boolean> =
        Transformations.map(searchAPIResponseLiveData) { it is ApiErrorResponse  }

 val userSearchResponseLiveData: LiveData<List<GitUser>> =
  Transformations.map(searchAPIResponseLiveData) {
   if(  it is ApiSuccessResponse){
    it.body.users.map { userTransfer -> userTransfer.asDomainModel()  }
   } else  {
    arrayListOf<GitUser>()
   }
  }


    fun searchUser(userName: String) {
        searchAPIResponseLiveData = searchGitRepo.searchUser(userName)
    }


    fun insertUser(user: GitUser) {
        uiScope.launch {
            insert(user)
        }
    }

    private suspend fun insert(user: GitUser) {
        withContext(Dispatchers.IO) {
            searchGitRepo.insertUser(user)
        }
    }


    override fun onCleared() {
        viewJob.cancel()
        super.onCleared()
    }
}