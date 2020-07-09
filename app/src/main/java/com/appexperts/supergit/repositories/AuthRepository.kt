package com.appexperts.supergit.repositories

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.appexperts.supergit.models.User
import com.appexperts.supergit.utils.Resource


interface IAuthGitRepository {
    fun login(activity: Activity): LiveData<Resource<User>>
}

private const val TAG = "AuthRepository"

class AuthGitRepository() : IAuthGitRepository {
    override fun login(activity: Activity): LiveData<Resource<User>> {
        val result = MutableLiveData<Resource<User>>()

        return result

    }


}