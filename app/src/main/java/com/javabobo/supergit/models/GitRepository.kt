package com.javabobo.supergit.models



class GitRepository(
    val name: String,
    val full_name: String,
    val private: Boolean,
    val owner: GitUser
)