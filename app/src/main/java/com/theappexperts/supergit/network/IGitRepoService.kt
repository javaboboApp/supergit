package com.theappexperts.supergit.network

import androidx.lifecycle.LiveData
import com.theappexperts.supergit.models.GitRepositoryTransfer
import com.theappexperts.supergit.utils.GET_REPOSITORIES_ENDPOINT
import retrofit2.http.GET
import retrofit2.http.Path

interface IGitRepoService {
    @GET(GET_REPOSITORIES_ENDPOINT)
    fun getRepositories(@Path("user_name") userName : String) : LiveData<ApiResponse<List<GitRepositoryTransfer>>>


}