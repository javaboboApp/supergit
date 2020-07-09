package com.theappexperts.supergit.ui.auth

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.theappexperts.supergit.models.User
import com.theappexperts.supergit.repositories.IAuthGitRepository
import com.theappexperts.supergit.utils.Resource

class AddUserRepositoryGithubViewModel(private val authGitRepository: IAuthGitRepository) :ViewModel() {


    fun login(activity: Activity) : LiveData<Resource<User>>{
        return authGitRepository.login(activity)
    }
}