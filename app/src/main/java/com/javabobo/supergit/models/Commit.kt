package com.javabobo.supergit.models

data class Commit(
    val timestamp: Long,
    var isHeader: Boolean = false,
    val repoId: Long = -1,
    val authorName: String = "",
    val message: String = ""
){

}