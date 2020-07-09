package com.appexperts.supergit.di

import com.google.firebase.auth.FirebaseAuth
import com.appexperts.reddit.persistence.DatabaseFactory
import com.appexperts.supergit.network.GitRetrofitInstance
import com.appexperts.supergit.repositories.AuthGitRepository
import com.appexperts.supergit.repositories.IAuthGitRepository
import com.appexperts.supergit.repositories.ISearchGitRepo
import com.appexperts.supergit.repositories.SearchGitRepoRepository
import com.appexperts.supergit.ui.auth.AddUserRepositoryGithubViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val databaseModule = module {
    single { DatabaseFactory.getDBInstance(get()) }
}

val firebaseModule = module{
    single{ FirebaseAuth.getInstance()}
}


val networkModule = module {
    single { GitRetrofitInstance().retrofitPupil() }
}
val repositoriesModule = module {
    single<IAuthGitRepository> { AuthGitRepository() }
    single<ISearchGitRepo> { SearchGitRepoRepository(get()) }
}

val viewModelModule = module {
    viewModel { AddUserRepositoryGithubViewModel(get())  }

}
