package com.javabobo.supergit.network
import android.net.Uri
import com.javabobo.supergit.models.GitUser
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserTransfer (

    val login : String,
    val id : Int,
    val node_id : String,
    val avatar_url : String,
    val gravatar_id : String,
    val url : String,
    val html_url : String,
    val followers_url : String,
    val following_url : String,
    val gists_url : String,
    val starred_url : String,
    val subscriptions_url : String,
    val organizations_url : String,
    val repos_url : String,
    val events_url : String,
    val received_events_url : String,
    val type : String,
    val site_admin : Boolean,
    val score : Int?
)

fun UserTransfer.asDomainModel(): GitUser {
    return GitUser(login, Uri.parse(avatar_url))
}

fun List<UserTransfer>.asDomainModel(): List<GitUser> {
    return map { it.asDomainModel() }
}