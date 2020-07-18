package com.javabobo.supergit.di

import com.google.firebase.auth.FirebaseAuth
import com.javabobo.supergit.persistence.DatabaseFactory
import com.javabobo.supergit.network.GitRetrofitInstance
import com.javabobo.supergit.repositories.AuthGitRepository
import com.javabobo.supergit.repositories.IAuthGitRepository
import com.javabobo.supergit.repositories.ISearchGitRepo
import com.javabobo.supergit.repositories.SearchGitRepoRepository
import com.javabobo.supergit.ui.addUser.AddUserRepositoryGithubViewModel
import com.javabobo.supergit.ui.commits.CommitsViewModel
import com.javabobo.supergit.ui.repo.SearchRepoViewModel
import com.javabobo.supergit.ui.users.UserViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val databaseModule = module {
    single { DatabaseFactory.getDBInstance(get()) }
}

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
}


val networkModule = module {
    single { GitRetrofitInstance().retrofitPupil() }
}
val repositoriesModule = module {
    single<IAuthGitRepository> { AuthGitRepository() }
    single<ISearchGitRepo> { SearchGitRepoRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel { AddUserRepositoryGithubViewModel(get(),get()) }
    viewModel { UserViewModel(get()) }
    viewModel { SearchRepoViewModel(get()) }
    viewModel  { CommitsViewModel(get()) }
}
