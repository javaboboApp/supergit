package com.javabobo.supergit.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.javabobo.supergit.models.GitUser
import com.javabobo.supergit.network.*
import com.javabobo.supergit.repositories.ISearchGitRepo
import kotlinx.coroutines.*

private const val TAG = "UserViewModel"
class UserViewModel(private val searchGitRepo: ISearchGitRepo) : ViewModel() {

    val currentUsersLiveData: LiveData<List<GitUser>> = searchGitRepo.getCurrentUsersLiveData()

    private val viewJob: Job = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + Job())

   val searchUserAPIResponseLiveData: LiveData<SearchGitUsersContainer> =
        searchGitRepo.getSearchUserLiveData()


    fun searchUser(userName: String) {
        Log.i(TAG, "searchUser: ")
        uiScope.launch {
            searchGitRepo.searchUser(userName)
        }
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