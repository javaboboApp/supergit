package com.theappexperts.supergit.di

import com.google.firebase.auth.FirebaseAuth
import com.theappexperts.supergit.persistence.DatabaseFactory
import com.theappexperts.supergit.network.GitRetrofitInstance
import com.theappexperts.supergit.repositories.AuthGitRepository
import com.theappexperts.supergit.repositories.IAuthGitRepository
import com.theappexperts.supergit.repositories.ISearchGitRepo
import com.theappexperts.supergit.repositories.SearchGitRepoRepository
import com.theappexperts.supergit.ui.auth.AddUserRepositoryGithubViewModel
import com.theappexperts.supergit.ui.user.UserViewModel
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
}
