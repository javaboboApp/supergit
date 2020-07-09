package com.theappexperts.supergit.repositories

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.theappexperts.supergit.models.User
import com.theappexperts.supergit.utils.Resource


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