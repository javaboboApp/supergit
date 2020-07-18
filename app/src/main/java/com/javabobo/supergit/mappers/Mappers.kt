package com.javabobo.supergit.mappers

import android.net.Uri
import com.javabobo.supergit.models.Commit
import com.javabobo.supergit.models.GitRepository
import com.javabobo.supergit.models.GitUser
import com.javabobo.supergit.network.TransferModel.CommitsContainerTransfer
import com.javabobo.supergit.network.TransferModel.GitRepositoryTransfer
import com.javabobo.supergit.network.TransferModel.SearchGitUsersContainer
import com.javabobo.supergit.network.TransferModel.UserTransfer
import com.javabobo.supergit.persistence.DBCommit
import com.javabobo.supergit.persistence.DBGitRepository
import com.javabobo.supergit.persistence.DBUser
import com.javabobo.supergit.utils.DateUtils.convertToDate

fun List<DBUser>.asDomainModel(): List<GitUser> {
    return map {
        GitUser(
            name = it.username, photo = Uri.parse(it.avatar_url), token = it.token
        )
    }

}

fun GitUser.asDbMoodel(): DBUser {
    var avatarUrl = ""
    var tokenAux = ""
    photo?.let { avatarUrl = photo.toString() }
    token?.let { tokenAux = token }
    return DBUser(name, avatarUrl, tokenAux)
}

fun SearchGitUsersContainer.asListUserTransfer(): List<GitUser> {
    return users.map {
        it.asDomainModel()
    }
}

fun UserTransfer.asDomainModel(): GitUser {
    return GitUser(login, Uri.parse(avatar_url))
}

fun List<UserTransfer>.asGitUserModel(): List<GitUser> {
    return map { it.asDomainModel() }
}

fun GitRepositoryTransfer.asDomainModel(): GitRepository {
    return GitRepository(
        id = id?.toLong() ?: 0,
        name = name ?: "",
        full_name = full_name ?: "",
        owner = owner?.asDomainModel(),
        private = private!!,
        description = description ?: "",
        date = System.currentTimeMillis()
    )
}

fun List<GitRepositoryTransfer>.asGitRepositoryModel(): List<GitRepository> {
    return map { it.asDomainModel() }
}

fun List<GitRepositoryTransfer>.asDatabaseModel(userName: String): List<DBGitRepository> {

    return map {
        val isPrivate: String = if (it.private == false) "public" else "private"

        DBGitRepository(
            it.id?.toLong() ?: 0,
            it.full_name ?: "",
            userName,
            isPrivate,
            it.name ?: "",
            it.description ?: "",
            System.currentTimeMillis()
        )
    }
}

fun List<DBGitRepository>.asListDomainModel(): List<GitRepository> {
    val list = map {
        GitRepository(
            id = it.id,
            name = it.name,
            full_name = it.full_name,
            private = it.private_repo.equals("private"),
            description = it.description,
            owner = GitUser(it.name),
            date = it.date_modif

        )
    }
    return list
}

fun List<CommitsContainerTransfer>.asListDBCommits(repoId: Long): List<DBCommit> {
    return map {
        DBCommit(
            timestamp = it.commit.author.date.convertToDate() ?: -1,
            authorName = it.commit.author.name,
            message = it.commit.message,
            repoId = repoId
        )
    }
}

fun List<DBCommit>.asListCommitDomainModel(): List<Commit> {
    return map {
        Commit(
            timestamp = it.timestamp,
            repoId = it.repoId,
            message = it.message,
            authorName = it.authorName
        )
    }
}
