package com.javabobo.supergit.di

import com.google.firebase.auth.FirebaseAuth
import com.javabobo.reddit.persistence.DatabaseFactory
import com.javabobo.supergit.network.GitRetrofitInstance
import com.javabobo.supergit.network.IGitRepoService
import com.javabobo.supergit.repositories.AuthGitRepository
import com.javabobo.supergit.repositories.IAuthGitRepository
import com.javabobo.supergit.repositories.ISearchGitRepo
import com.javabobo.supergit.repositories.SearchGitRepoRepository
import com.javabobo.supergit.ui.auth.LoginGithubViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val databaseModule = module {
    single { DatabaseFactory.getDBInstance(get()) }


}

val firebaseModule = module {
    single { FirebaseAuth.getInstance()}
}

val networkModule = module {
    single { GitRetrofitInstance().retrofitPupil() }
}
val repositoriesModule = module {
    single<IAuthGitRepository> { AuthGitRepository(get()) }
    single<ISearchGitRepo> { SearchGitRepoRepository(get()) }

}
val viewModelModule = module {
    viewModel { LoginGithubViewModel(get())  }

}
