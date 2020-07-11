package com.theappexperts.supergit.network.TransferModel

data class CommitTransfer (

    val author : AuthorTransfer,
    val message : String,
    val url : String,
    val comment_count : Int
)