package com.theappexperts.supergit.ui.commits

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.theappexperts.supergit.models.Commit
import com.theappexperts.supergit.models.GitRepository
import com.theappexperts.supergit.repositories.ISearchGitRepo
import com.theappexperts.supergit.utils.Resource

private const val TAG = "CommitViewModel"
class CommitsViewModel(private val iSearchGitRepo: ISearchGitRepo) : ViewModel() {


    fun getCommit(userName: String, gitRepository: GitRepository, token:String? = null): LiveData<Resource<List<Commit>>> {
        Log.d(TAG, "getCommit: ${gitRepository.name}")
        return iSearchGitRepo.getCommits(userName,gitRepository,token)
    }
}