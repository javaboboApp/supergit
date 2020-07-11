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
 class DBGitRepository(
    @PrimaryKey
    @ColumnInfo(name = "id")

    var id: Long,
    @ColumnInfo(name = "full_name")
    var full_name: String = "" ,
    @ColumnInfo(name = "owner_name")
    var owner_name: String = "" ,
    @ColumnInfo(name = "private")
    var private: Boolean =false,
    @ColumnInfo(name = "name")
    var name: String ="",
    @ColumnInfo(name = "description")
    var description: String =""

){
    constructor() : this(0,"","",false,"","")
}


@Entity
data class DBUser(
    @PrimaryKey()
    val username: String = "",
    val avatar_url: String = ""
)


@Entity
data class DBCommit( @ColumnInfo(name = "timestamp") val timestamp: Long)