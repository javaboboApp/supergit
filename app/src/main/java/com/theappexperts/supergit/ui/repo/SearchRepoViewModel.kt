package com.theappexperts.supergit.ui.repo

import androidx.lifecycle.ViewModel
import com.theappexperts.supergit.models.GitUser
import com.theappexperts.supergit.repositories.SearchGitRepoRepository

class SearchRepoViewModel(private val gitRepoRepository: SearchGitRepoRepository): ViewModel() {


fun getPublicRepositoriesByUser(username: String){
    gitRepoRepository.getPublicRepositoriesByUser(username)
}
}