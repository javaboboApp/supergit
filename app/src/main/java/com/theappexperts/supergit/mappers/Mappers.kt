package com.theappexperts.supergit.mappers

import android.net.Uri
import com.theappexperts.supergit.models.Commit
import com.theappexperts.supergit.models.GitRepository
import com.theappexperts.supergit.models.GitUser
import com.theappexperts.supergit.network.TransferModel.CommitsContainerTransfer
import com.theappexperts.supergit.network.TransferModel.GitRepositoryTransfer
import com.theappexperts.supergit.network.TransferModel.SearchGitUsersContainer
import com.theappexperts.supergit.network.TransferModel.UserTransfer
import com.theappexperts.supergit.persistence.DBCommit
import com.theappexperts.supergit.persistence.DBGitRepository
import com.theappexperts.supergit.persistence.DBUser
import com.theappexperts.supergit.utils.DateUtils.convertToDate

fun List<DBUser>.asDomainModel(): List<GitUser> {
    return map {
        GitUser(
            name = it.username, photo = Uri.parse(it.avatar_url), token = it.token
        )
    }

}
fun GitUser.asDbMoodel(): DBUser {
    var avatarUrl = ""
    var tokenAux =""
    photo?.let { avatarUrl = photo.toString()}
    token?.let { tokenAux = token }
    return DBUser(name, avatarUrl, tokenAux )
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

fun List<GitRepositoryTransfer>.asDatabaseModel(userName: String): List<DBGitRepository>{
    return map {
        DBGitRepository(
             id= it.id?.toLong()?: 0,
            name = it.name?: "",
            full_name = it.full_name?: "",
            owner_name = userName,
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
            description = it.description,
            owner = GitUser(it.name)
        )
    }
}

fun List<CommitsContainerTransfer>.asListDBCommits(repoId: Long):List<DBCommit>{
    return map {
        DBCommit(timestamp = it.commit.author.date.convertToDate() ?: -1,
        authorName = it.commit.author.name,
        message = it.commit.message,
        repoId = repoId)
    }
}

fun List<DBCommit>.asListCommitDomainModel():List<Commit>{
    return map { Commit(timestamp = it.timestamp,
    repoId = it.repoId,
    message = it.message,
    authorName = it.authorName) }
}
