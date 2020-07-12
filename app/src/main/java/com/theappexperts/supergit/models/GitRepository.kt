package com.theappexperts.supergit.models


class GitRepository(
    val id: Long,
    val name: String,
    val full_name: String,
    val description: String,
    val private: Boolean,
    val date: Long?,
    val owner: GitUser? = null
)