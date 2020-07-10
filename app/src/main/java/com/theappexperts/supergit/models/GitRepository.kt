package com.theappexperts.supergit.models


class GitRepository(
    val id: Long,
    val name: String,
    val full_name: String,
    val description: String,
    val private: Boolean,
    val owner: GitUser? = null
)