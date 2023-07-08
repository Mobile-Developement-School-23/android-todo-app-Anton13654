package com.aeincprojects.todoapp.data.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor (
    private val token: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val updatedRequest = request.newBuilder()
            .header(AUTHORIZATION_HEADER_NAME, token)
            .build()
        return chain.proceed(updatedRequest)
    }

    companion object {
        private const val AUTHORIZATION_HEADER_NAME = "Authorization"
    }
}