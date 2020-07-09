package com.javabobo.supergit.repositories

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.javabobo.supergit.models.GitUser
import com.javabobo.supergit.utils.Event
import com.javabobo.supergit.utils.Resource


interface IAuthGitRepository {
    fun login(activity: Activity): LiveData<Resource<GitUser>>
}

private const val TAG = "AuthRepository"

class AuthGitRepository(val firebaseAuth: FirebaseAuth) : IAuthGitRepository {
    override fun login(activity: Activity): LiveData<Resource<GitUser>> {
        val result = MutableLiveData<Resource<GitUser>>()
        val provider = OAuthProvider.newBuilder("github.com")
        result.value = Resource.loading(null)

        // Target specific email with login hint.
        provider.addCustomParameter("login", "ellokodeluis@gmail.com")
        // Request read access to a user's email addresses.
        // This must be preconfigured in the app's API permissions.

        // Request read access to a user's email addresses.
        // This must be preconfigured in the app's API permissions.
        val scopes: List<String> = listOf("user:email")
        provider.scopes = scopes

        firebaseAuth
            .startActivityForSignInWithProvider( /* activity= */activity, provider.build())
            .addOnSuccessListener { authResult ->
                Log.i(TAG, "addOnSuccessListener ${authResult}")
                // User is signed in.
                // IdP data available in
                // authResult.getAdditionalUserInfo().getProfile().
                // The OAuth access token can also be retrieved:
                // authResult.getCredential().getAccessToken().
                authResult.user?.displayName?.let {
                    val user =GitUser(name = it, photo = authResult.user?.photoUrl)
                    result.value = Resource.success(Event(user))
                }

                firebaseAuth.signOut()
            }
            .addOnFailureListener { exception ->
                Log.i(TAG, "login: ${exception.message}")
                result.value = Resource.error(exception.message, null)
            }
        return result

    }


}