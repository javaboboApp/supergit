package com.theappexperts.supergit.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theappexperts.supergit.models.GitUser

class SharedHomeViewModel : ViewModel() {


    private val currentFragment = MutableLiveData<Int>()

    private val _userSelectedLiveData = MutableLiveData<GitUser>()
    val userSelectedLiveData: LiveData<GitUser?>
        get() {
            return _userSelectedLiveData
        }

   fun selectUser(user: GitUser){
      _userSelectedLiveData.value = user
   }
   fun unselectUser(){
      _userSelectedLiveData.value = null
   }


}