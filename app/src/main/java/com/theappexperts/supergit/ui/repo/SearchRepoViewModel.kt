package com.theappexperts.supergit.ui.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.theappexperts.supergit.models.GitRepository
import com.theappexperts.supergit.repositories.ISearchGitRepo
import com.theappexperts.supergit.utils.Resource

private const val TAG = "SearchRepoViewModel"
class SearchRepoViewModel(private val iSearchGitRepo: ISearchGitRepo) : ViewModel() {


    fun getPublicRepositoriesByUser(username: String): LiveData<Resource<List<GitRepository>>> {
        Log.d(TAG, "getPublicRepositoriesByUser: ${username}")
        return iSearchGitRepo.getPublicRepositoriesByUser(username)
    }

    fun getPrivateAndPublicRepositories(token: String): LiveData<Resource<List<GitRepository>>> {
        return  iSearchGitRepo.getPublicAndPrivateRepositories(token)
    }
}