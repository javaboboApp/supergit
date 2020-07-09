package com.theappexperts.supergit.network

import androidx.lifecycle.LiveData
import com.theappexperts.supergit.models.GitRepositoryTransfer
import com.theappexperts.supergit.utils.GET_REPOSITORIES_ENDPOINT
import com.javabobo.supergit.utils.SEARCH_USER_ENDPOINT
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IGitRepoService {
//    @GET(GET_REPOSITORIES_ENDPOINT)
//    fun getRepositories(@Path("user_name") userName : String) : LiveData<ApiResponse<List<GitRepositoryTransfer>>>
    @GET(SEARCH_USER_ENDPOINT)
    fun searchUser(@Query("q") userName: String): Deferred<SearchGitUsersContainer>

}