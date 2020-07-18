package com.javabobo.supergit.ui.commits

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.javabobo.supergit.models.Commit
import com.javabobo.supergit.models.GitRepository
import com.javabobo.supergit.repositories.ISearchGitRepo
import com.javabobo.supergit.utils.Resource

private const val TAG = "CommitViewModel"
class CommitsViewModel(private val iSearchGitRepo: ISearchGitRepo) : ViewModel() {


    fun getCommit(userName: String, gitRepository: GitRepository, token:String? = null): LiveData<Resource<List<Commit>>> {
        Log.d(TAG, "getCommit: ${gitRepository.name}")
        return iSearchGitRepo.getCommits(userName,gitRepository,token)
    }
}