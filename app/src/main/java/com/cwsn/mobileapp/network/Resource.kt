package com.cwsn.mobileapp.network

/**
Created by  on 14,June,2022
 **/
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?,message: String): Resource<T> {
            return Resource(status = Status.SUCCESS, data = data, message = null)
        }

        fun <T> error(data: T?, message: String): Resource<T> {
            return Resource(status = Status.ERROR, data = data, message = message)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(status = Status.LOADING, data = data, message = null)
        }
    }
}
