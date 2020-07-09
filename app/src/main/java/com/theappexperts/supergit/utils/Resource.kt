package com.theappexperts.supergit.utils

import com.theappexperts.supergit.utils.Resource.Status.*


data class Resource<out T>(val status: Status, val data: Event<T>?, val message: String?) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }
    companion object {
        fun <T> success(data:Event <T>?): Resource<T> {

            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data:Event <T>?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data:Event <T>?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}