package com.javabobo.supergit.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.javabobo.supergit.models.User
import com.javabobo.supergit.repositories.AuthGitRepository
import com.javabobo.supergit.repositories.IAuthGitRepository
import com.javabobo.supergit.utils.Resource

class LoginGithubViewModel(private val authGitRepository: IAuthGitRepository) :ViewModel() {


    fun login() : LiveData<Resource<User>>{
        return authGitRepository.login()
    }
}