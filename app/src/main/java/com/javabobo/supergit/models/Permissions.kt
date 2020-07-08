package com.javabobo.supergit.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Permissions (

    val admin : Boolean,
    val push : Boolean,
    val pull : Boolean
)