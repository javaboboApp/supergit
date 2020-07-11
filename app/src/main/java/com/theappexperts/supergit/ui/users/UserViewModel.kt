package com.theappexperts.supergit.ui.users

import com.theappexperts.supergit.models.GitUser
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.theappexperts.supergit.repositories.ISearchGitRepo
import com.theappexperts.supergit.utils.Resource

private const val TAG = "UserViewModel"

class UserViewModel(private val searchGitRepo: ISearchGitRepo) : ViewModel() {

    val showNoUserLayout: LiveData<Boolean> = Transformations.map(searchGitRepo.getCurrentUsers()) { resource ->
        when(resource.status){
            Resource.Status.SUCCESS -> resource.data?.peekContent()?.isEmpty() ?: true
            else -> false
        }
    }

    fun getCurrentUsers(): LiveData<Resource<List<GitUser>>> {
        return searchGitRepo.getCurrentUsers()
    }


    fun removeUser(user: GitUser): LiveData<Resource<GitUser>> {
        return searchGitRepo.deleteUser(user)
    }





}