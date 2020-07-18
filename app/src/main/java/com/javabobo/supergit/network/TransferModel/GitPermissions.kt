package com.javabobo.supergit.network.TransferModel

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitPermissions (

    val admin : Boolean?,
    val push : Boolean?,
    val pull : Boolean?
)