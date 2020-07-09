package com.theappexperts.supergit.repositories

import androidx.lifecycle.LiveData
import com.theappexperts.supergit.models.GitRepositoryTransfer
import com.theappexperts.supergit.models.User
import com.theappexperts.supergit.network.IGitRepoService
import com.theappexperts.supergit.utils.Resource

interface ISearchGitRepo {
    fun searchRepositories(user: User): LiveData<Resource<List<GitRepositoryTransfer>>>
}

class SearchGitRepoRepository(private val IGitRepoService: IGitRepoService) : ISearchGitRepo {

    override fun searchRepositories(user: User): LiveData<Resource<List<GitRepositoryTransfer>>> {

        TODO("FINISH THIS")

    }

}