package com.appexperts.supergit.repositories

import androidx.lifecycle.LiveData
import com.appexperts.supergit.models.GitRepositoryTransfer
import com.appexperts.supergit.models.User
import com.appexperts.supergit.network.IGitRepoService
import com.appexperts.supergit.utils.Resource

interface ISearchGitRepo {
    fun searchRepositories(user: User): LiveData<Resource<List<GitRepositoryTransfer>>>
}

class SearchGitRepoRepository(private val IGitRepoService: IGitRepoService) : ISearchGitRepo {

    override fun searchRepositories(user: User): LiveData<Resource<List<GitRepositoryTransfer>>> {

        TODO("FINISH THIS")

    }

}