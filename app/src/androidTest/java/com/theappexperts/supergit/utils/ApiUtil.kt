package com.theappexperts.supergit.utils


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.theappexperts.supergit.network.ApiResponse
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response


 object ApiUtil {
    fun <T> successCall(data: T): LiveData<ApiResponse<T>> {
        return createCall(Response.success(data))
    }
    fun <T> errorCall(data: T): LiveData<ApiResponse<T>> {
        return createCall(Response.error(500, ResponseBody.create(
                MediaType.parse("application/json"),
                ""
        )))
    }

    fun <T> createCall(response: Response<T>?): LiveData<ApiResponse<T>> {
        val data = MutableLiveData<ApiResponse<T>>()
        data.postValue(ApiResponse.create(response!!))
        return data
    }




 }