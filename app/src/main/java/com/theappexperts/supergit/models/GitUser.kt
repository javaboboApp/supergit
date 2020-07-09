package com.theappexperts.supergit.models

import android.net.Uri
import com.theappexperts.supergit.persistence.DBUser

class GitUser(val name: String, val photo: Uri?)

fun GitUser.asDbMoodel(): DBUser {
    var avatarUrl = ""
    photo?.let { avatarUrl = photo.toString()}

    return DBUser(name, avatarUrl )
}