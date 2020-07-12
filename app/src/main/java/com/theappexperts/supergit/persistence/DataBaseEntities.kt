package com.theappexperts.supergit.persistence

import com.theappexperts.supergit.models.GitUser
import android.net.Uri
import androidx.room.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = DBUser::class,
        parentColumns = arrayOf("username"),
        childColumns = arrayOf("owner_name"),
        onDelete = ForeignKey.CASCADE
    )], indices = [Index(value = ["owner_name"])]
)
class DBGitRepository {
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "full_name")
    var full_name: String = ""

    @ColumnInfo(name = "owner_name")
    var owner_name: String = ""

    @ColumnInfo(name = "private_repo")
    var private_repo: String = ""

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "description")
    var description: String = ""


    constructor() {
      //do nothing
    }

    constructor(
        id: Long,
        full_name: String,
        owner_name: String,
        private: String,
        name: String,
        description: String
    )  {
        this.id = id
        this.full_name = full_name
        this.owner_name = owner_name
        this.private_repo = private
        this.name = name
        this.description = description
    }
}


@Entity
data class DBUser(
    @PrimaryKey()
    val username: String = "",
    val avatar_url: String = "",
    val token: String = ""
)


@Entity(
    primaryKeys = ["timestamp", "repo_id"],
    foreignKeys = [ForeignKey(
        entity = DBGitRepository::class,
        parentColumns = ["id"],
        childColumns = ["repo_id"],
        onDelete = ForeignKey.CASCADE
    )], indices = [Index(value = ["repo_id"])]
)
data class DBCommit(
    @ColumnInfo(name = "timestamp") val timestamp: Long = -1,
    @ColumnInfo(name = "repo_id") val repoId: Long = -1,
    @ColumnInfo(name = "author_name") val authorName: String = "",
    @ColumnInfo(name = "message") val message: String = ""
)