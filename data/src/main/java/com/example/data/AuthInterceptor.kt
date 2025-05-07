package com.example.data

import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .addHeader("Authorization","391afa64-5c4e-470a-88f1-c01099ad155d")
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}
