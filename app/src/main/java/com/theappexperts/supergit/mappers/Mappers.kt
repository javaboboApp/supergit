package com.theappexperts.supergit.mappers

import android.net.Uri
import com.theappexperts.supergit.models.GitUser
import com.theappexperts.supergit.network.SearchGitUsersContainer
import com.theappexperts.supergit.network.UserTransfer
import com.theappexperts.supergit.network.asDomainModel
import com.theappexperts.supergit.persistence.DBUser
import com.theappexperts.supergit.utils.Event
import com.theappexperts.supergit.utils.Resource

fun List<DBUser>.asDomainModel(): List<GitUser> {
    return map {
        GitUser(
            name = it.username, photo = Uri.parse(it.avatar_url)
        )
    }

}
fun GitUser.asDbMoodel(): DBUser {
    var avatarUrl = ""
    photo?.let { avatarUrl = photo.toString()}

    return DBUser(name, avatarUrl )
}

fun SearchGitUsersContainer.asListUserTransfer() : List<GitUser> {
    return   users.map {
         it.asDomainModel()
    }
}

fun UserTransfer.asDomainModel(): GitUser {
    return GitUser(login, Uri.parse(avatar_url))
}

fun List<UserTransfer>.asDomainModel(): List<GitUser> {
    return map { it.asDomainModel() }
}