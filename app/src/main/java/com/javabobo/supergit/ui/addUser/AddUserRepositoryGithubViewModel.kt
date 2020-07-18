package com.javabobo.supergit.ui.addUser

import com.javabobo.supergit.models.GitUser
import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.javabobo.supergit.repositories.IAuthGitRepository
import com.javabobo.supergit.repositories.ISearchGitRepo
import com.javabobo.supergit.utils.Resource

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

    fun searchUser(userName: String): LiveData<Resource<List<GitUser>>> {
        Log.i(TAG, "searchUser: ")
        _searchUser.postValue( userName)
        return searchUser
    }

}