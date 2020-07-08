package com.javabobo.supergit.repositories

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.javabobo.supergit.models.User
import com.javabobo.supergit.utils.Resource


interface IAuthGitRepository {
  fun login() : LiveData<Resource<User>>
}

class AuthGitRepository(val firebaseAuth: FirebaseAuth) : IAuthGitRepository {
  override fun login(): LiveData<Resource<User>> {
    val provider = OAuthProvider.newBuilder("github.com")

    TODO("Not yet implemented")
  }


}