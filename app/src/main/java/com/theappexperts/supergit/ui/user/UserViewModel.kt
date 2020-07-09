package com.theappexperts.supergit.ui.user

import com.theappexperts.supergit.models.GitUser
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.theappexperts.supergit.di.repositoriesModule
import com.theappexperts.supergit.network.SearchGitUsersContainer
import com.theappexperts.supergit.repositories.ISearchGitRepo
import com.theappexperts.supergit.utils.Resource
import kotlinx.coroutines.*

private const val TAG = "UserViewModel"

class UserViewModel(private val searchGitRepo: ISearchGitRepo) : ViewModel() {

    fun getCurrentUsers(): LiveData<Resource<List<GitUser>>> {
        return searchGitRepo.getCurrentUsers()
    }


    fun insertUser(user: GitUser): LiveData<Resource<GitUser>> {
        return searchGitRepo.insertUser(user)
    }


}