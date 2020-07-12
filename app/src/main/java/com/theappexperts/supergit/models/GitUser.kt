package com.theappexperts.supergit.models

import android.net.Uri
import com.theappexperts.supergit.persistence.DBUser

class GitUser(val name: String, var photo: Uri? =null, val token: String? = null) {

}

