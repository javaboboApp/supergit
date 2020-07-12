package com.theappexperts.supergit.network

import androidx.lifecycle.LiveData
import com.theappexperts.supergit.network.TransferModel.CommitsContainerTransfer
import com.theappexperts.supergit.network.TransferModel.GitRepositoryTransfer
import com.theappexperts.supergit.network.TransferModel.SearchGitUsersContainer
import com.theappexperts.supergit.utils.*
import retrofit2.http.*

interface IGitRepoService {
    @GET(GET_REPOSITORIES_ENDPOINT)
    fun getPublicRepositoriesByUser(
        @Path(PATH_USER_NAME) userName: String,
        @Query("type") typeRequest: String
    ): LiveData<ApiResponse<List<GitRepositoryTransfer>>>

    @GET(SEARCH_USER_ENDPOINT)
    fun searchUser(@Query("q") userName: String): LiveData<ApiResponse<SearchGitUsersContainer>>

    @GET(GET_COMMIT_ENDPOINT)
    fun getCommit(
        @Path(PATH_FULL_NAME_REPO) fullNameRepository: String, @Header("Authorization") token: String
    ): LiveData<ApiResponse<List<CommitsContainerTransfer>>>

    @GET(GET_COMMIT_ENDPOINT)
    fun getCommit(
        @Path(PATH_FULL_NAME_REPO) fullNameRepository: String): LiveData<ApiResponse<List<CommitsContainerTransfer>>>

    @GET(GET_PRIVATE_REPOST)
    fun getPublicAndPrivateRepositories(@Header("Authorization") token: String): LiveData<ApiResponse<List<GitRepositoryTransfer>>>
}