package com.javabobo.supergit.ui.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.javabobo.supergit.models.GitRepository
import com.javabobo.supergit.repositories.ISearchGitRepo
import com.javabobo.supergit.utils.Resource

private const val TAG = "SearchRepoViewModel"
class SearchRepoViewModel(private val iSearchGitRepo: ISearchGitRepo) : ViewModel() {

    fun getPublicRepositoriesByUser(username: String): LiveData<Resource<List<GitRepository>>> {
        Log.d(TAG, "getPublicRepositoriesByUser: ${username}")
        return iSearchGitRepo.getPublicRepositoriesByUser(username)

    }

    fun getPrivateAndPublicRepositories(userName:String, token: String): LiveData<Resource<List<GitRepository>>> {
        return  iSearchGitRepo.getPublicAndPrivateRepositories(userName,token)
    }
}