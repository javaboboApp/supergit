package com.javabobo.supergit.ui.users

import androidx.lifecycle.*
import com.javabobo.supergit.models.GitUser
import com.javabobo.supergit.repositories.ISearchGitRepo
import com.javabobo.supergit.utils.OpenForTesting
import com.javabobo.supergit.utils.Resource
import kotlinx.coroutines.flow.onStart

private const val TAG = "UserViewModel"
@OpenForTesting
class UserViewModel(private val searchGitRepo: ISearchGitRepo) : ViewModel() {



    fun getCurrentUsers(): LiveData<Resource<List<GitUser>>> =
         searchGitRepo.getCurrentUsers().onStart {    emit(Resource.loading(null))  }.asLiveData()




    fun removeUser(user: GitUser): LiveData<Resource<GitUser>> {
        return searchGitRepo.deleteUser(user)
    }







}