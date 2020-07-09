package com.theappexperts.supergit.ui.auth

import com.theappexperts.supergit.models.GitUser
import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.theappexperts.supergit.repositories.IAuthGitRepository
import com.theappexperts.supergit.utils.Resource

class AddUserRepositoryGithubViewModel(private val authGitRepository: IAuthGitRepository) :ViewModel() {


    fun login(activity: Activity) : LiveData<Resource<GitUser>>{
        return authGitRepository.login(activity)
    }
}