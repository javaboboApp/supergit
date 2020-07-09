package com.theappexperts.supergit.persistence

import com.theappexperts.supergit.models.GitUser
import android.net.Uri
import androidx.room.*

@Entity(foreignKeys = [ForeignKey(entity = DBUser::class,
    parentColumns = arrayOf("username"),
    childColumns = arrayOf("owner_name"),
    onDelete = ForeignKey.CASCADE)], indices = [Index(value = ["owner_name"])]
)
data class DBGitRepository (
    @PrimaryKey(autoGenerate = true)
    val id : Long =0 ,
    val full_name : String = "",
    @ColumnInfo(name = "owner_name")
    val ownerName : String,
    val node_id : String = "",
    val name : String = ""

)


@Entity
data class DBUser (
    @PrimaryKey()
    val username : String="",
    val avatar_url: String = ""
)


