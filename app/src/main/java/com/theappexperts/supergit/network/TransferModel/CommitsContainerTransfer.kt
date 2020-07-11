package com.theappexperts.supergit.network.TransferModel

data class CommitsContainerTransfer (

    val sha : String,
    val node_id : String,
    val commit : CommitTransfer,
    val url : String,
    val html_url : String,
    val comments_url : String,
    val author : String,
    val committer : String
    )
