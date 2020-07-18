package com.javabobo.supergit.utils

const val GIT_BASE_URL = "https://api.github.com/"

const val PATH_USER_NAME = "user_name"
const val PATH_REPO = "repo"
const val GET_REPOSITORIES_ENDPOINT ="users/{"+PATH_USER_NAME+"}/repos"
const val GET_COMMIT_ENDPOINT = "/repos/{"+PATH_USER_NAME+"}/{"+ PATH_REPO+"}/commits"

const val GET_PRIVATE_REPOST = "user/repos"

const val GET_REPOSITORIES_PARAM_TYPE ="owner"

const val SEARCH_USER_ENDPOINT = "search/users"

const val ERROR_INSERTING = "ERROR INSERTING"

const val MAX_HOURS_UPDATED = 24



