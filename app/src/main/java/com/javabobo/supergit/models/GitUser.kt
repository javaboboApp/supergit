package com.javabobo.supergit.models

import android.net.Uri
import com.javabobo.supergit.persistence.DBUser
import java.net.URI

class GitUser(val name: String, val photo: Uri?)

fun GitUser.asDbMoodel(): DBUser {
    var avatarUrl = ""
    photo?.let { avatarUrl = photo.toString()}

   return DBUser(name, avatarUrl )
}