package com.appexperts.supergit.network

import com.bridge.androidtechnicaltest.utils.LiveDataCallAdapterFactory
import com.appexperts.supergit.utils.GIT_BASE_URL
import retrofit2.Retrofit

class GitRetrofitInstance {
    fun retrofitPupil(): IGitRepoService {


        return Retrofit.Builder()
            .baseUrl(GIT_BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
         //   .addConverterFactory(GsonConverterFactory.create())
            .build().create(IGitRepoService::class.java)
    }
}