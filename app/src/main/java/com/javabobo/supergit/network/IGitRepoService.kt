package com.javabobo.supergit.network

import androidx.lifecycle.LiveData
import com.javabobo.supergit.models.GitRepositoryTransfer
import com.javabobo.supergit.utils.GET_REPOSITORIES_ENDPOINT
import retrofit2.http.GET
import retrofit2.http.Path

interface IGitRepoService {
    @GET(GET_REPOSITORIES_ENDPOINT)
    fun getRepositories(@Path("user_name") userName : String) : LiveData<ApiResponse<List<GitRepositoryTransfer>>>


}