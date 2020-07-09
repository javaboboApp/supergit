package com.theappexperts.supergit.ui.auth

import com.theappexperts.supergit.models.GitUser
import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.theappexperts.supergit.repositories.IAuthGitRepository
import com.theappexperts.supergit.repositories.ISearchGitRepo
import com.theappexperts.supergit.utils.Resource

private const val TAG = "AddUserRepositoryGithub"
class AddUserRepositoryGithubViewModel(private val authGitRepository: IAuthGitRepository, private val searchGitRepo: ISearchGitRepo) :ViewModel() {


    fun login(activity: Activity) : LiveData<Resource<GitUser>>{
        return authGitRepository.login(activity)
    }
    fun insertUser(user: GitUser): LiveData<Resource<GitUser>> {
        return searchGitRepo.insertUser(user)
    }


    private val _searchUser = MutableLiveData<String>()
    val searchUser = Transformations.switchMap(_searchUser) { userName ->
        searchGitRepo.searchUser(userName)
    }

    fun searchUser(userName: String) {
        Log.i(TAG, "searchUser: ")
        _searchUser.postValue( userName)
    }

}