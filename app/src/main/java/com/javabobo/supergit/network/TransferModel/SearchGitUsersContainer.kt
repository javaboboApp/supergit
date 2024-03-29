package com.javabobo.supergit.network.TransferModel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchGitUsersContainer (

    val total_count : Int,
    val incomplete_results : Boolean,
    @Json(name = "items")
    val users : List<UserTransfer>
)



