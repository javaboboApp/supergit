package com.appexperts.supergit.ui.auth

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.appexperts.supergit.models.User
import com.appexperts.supergit.repositories.IAuthGitRepository
import com.appexperts.supergit.utils.Resource

class AddUserRepositoryGithubViewModel(private val authGitRepository: IAuthGitRepository) :ViewModel() {


    fun login(activity: Activity) : LiveData<Resource<User>>{
        return authGitRepository.login(activity)
    }
}