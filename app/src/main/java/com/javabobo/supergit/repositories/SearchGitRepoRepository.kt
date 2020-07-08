package com.javabobo.supergit.repositories

import androidx.lifecycle.LiveData
import com.javabobo.supergit.models.GitRepositoryTransfer
import com.javabobo.supergit.models.User
import com.javabobo.supergit.network.IGitRepoService
import com.javabobo.supergit.utils.Resource

interface ISearchGitRepo {
    fun searchRepositories(user: User): LiveData<Resource<List<GitRepositoryTransfer>>>
}

class SearchGitRepoRepository(private val IGitRepoService: IGitRepoService) : ISearchGitRepo {

    override fun searchRepositories(user: User): LiveData<Resource<List<GitRepositoryTransfer>>> {

        TODO("FINISH THIS")

    }

}