package com.javabobo.supergit.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.javabobo.supergit.models.GitRepository
import com.javabobo.supergit.models.GitUser

class SharedHomeViewModel : ViewModel() {



    private val _userSelectedLiveData = MutableLiveData<GitUser>()
    val userSelectedLiveData: LiveData<GitUser?>
        get() {
            return _userSelectedLiveData
        }

   fun selectUser(user: GitUser){
      _userSelectedLiveData.value = user
   }


    private val _gitRepoSelectedLiveData = MutableLiveData<GitRepository>()
    val gitRepoSelectedLiveData: LiveData<GitRepository?>
        get() {
            return _gitRepoSelectedLiveData
        }

    fun selectRepository(repository: GitRepository){
        _gitRepoSelectedLiveData.value = repository
    }



}