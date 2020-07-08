package com.javabobo.supergit.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitPermissions (

    val admin : Boolean,
    val push : Boolean,
    val pull : Boolean
)