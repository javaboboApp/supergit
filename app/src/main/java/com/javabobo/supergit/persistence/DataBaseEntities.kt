package com.javabobo.supergit.persistence

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.javabobo.supergit.models.GitUser

@Entity(foreignKeys =  arrayOf(ForeignKey(entity = DBGitRepository::class,
    parentColumns = arrayOf("username"),
    childColumns = arrayOf("owner_name"),
    onDelete = ForeignKey.CASCADE)))
data class DBGitRepository (
    @PrimaryKey(autoGenerate = true)
    val id : Long?,
    val full_name : String = "",
    @ColumnInfo(name = "owner_name")
    val ownerName : String,
    val node_id : String = "",
    val name : String = ""

)


@Entity
data class DBUser (
    @PrimaryKey()
    val username : String,
    val avatar_url: String = ""
)


fun List<DBUser>.asDomainModel() : List<GitUser>{
    return map {
        GitUser(
        name = it.username, photo = Uri.parse(it.avatar_url)
        )
    }
}