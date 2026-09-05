package dev.systemless.altpureservice.data.remote

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CookieInterceptor @Inject constructor() : Interceptor {

    private var authenticationToken: String? = null

    fun setAuthenticationToken(token: String?) {
        authenticationToken = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = authenticationToken
            ?: throw IOException("Server URL not configured")
        val builder = chain.request().newBuilder()

        builder.addHeader("Cookie", ".Ps.Auth=$token")
        return chain.proceed(builder.build())
    }
}
