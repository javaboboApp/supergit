package com.theappexperts.supergit.network

import androidx.lifecycle.LiveData

import com.theappexperts.supergit.utils.SEARCH_USER_ENDPOINT
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IGitRepoService {
//    @GET(GET_REPOSITORIES_ENDPOINT)
//    fun getRepositories(@Path("user_name") userName : String) : LiveData<ApiResponse<List<GitRepositoryTransfer>>>
    @GET(SEARCH_USER_ENDPOINT)
    fun searchUser(@Query("q") userName: String): LiveData<ApiResponse<SearchGitUsersContainer>>

}