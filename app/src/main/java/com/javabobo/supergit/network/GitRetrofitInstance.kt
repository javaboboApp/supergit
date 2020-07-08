package com.javabobo.supergit.network

import com.bridge.androidtechnicaltest.utils.LiveDataCallAdapterFactory
import com.javabobo.supergit.utils.GIT_BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class GitRetrofitInstance {
    fun retrofitPupil(): IGitRepoService {


        return Retrofit.Builder()
            .baseUrl(GIT_BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
         //   .addConverterFactory(GsonConverterFactory.create())
            .build().create(IGitRepoService::class.java)
    }
}