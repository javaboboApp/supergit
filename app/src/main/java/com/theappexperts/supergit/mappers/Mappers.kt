package com.theappexperts.supergit.mappers

import android.net.Uri
import com.theappexperts.supergit.models.GitRepository
import com.theappexperts.supergit.models.GitUser
import com.theappexperts.supergit.network.GitRepositoryTransfer
import com.theappexperts.supergit.network.SearchGitUsersContainer
import com.theappexperts.supergit.network.UserTransfer
import com.theappexperts.supergit.persistence.DBGitRepository
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

fun List<UserTransfer>.asGitUserModel(): List<GitUser> {
    return map { it.asDomainModel() }
}

fun GitRepositoryTransfer.asDomainModel(): GitRepository{
    return GitRepository(
        id = id?.toLong() ?: 0,
        name = name?: "" ,
        full_name = full_name?: "",
        owner = owner?.asDomainModel(),
        private = private?: false,
        description = description?: ""
    )
}

fun List<GitRepositoryTransfer>.asGitRepositoryModel(): List<GitRepository>{
    return  map {it.asDomainModel() }
}

fun List<GitRepositoryTransfer>.asDatabaseModel(): List<DBGitRepository>{
    return map {
        DBGitRepository(
             id= it.id?.toLong()?: 0,
            name = it.name?: "",
            full_name = it.full_name?: "",
            owner_name = it.owner?.asDomainModel()?.name?: "",
            private = it.private?: false,
            description = it.description?: "")
    }
}

fun List<DBGitRepository>.asListDomainModel(): List<GitRepository>{
    return map{
        GitRepository(
            id= it.id,
            name = it.name,
            full_name = it.full_name,
            private = it.private,
            description = it.description
        )
    }
}
