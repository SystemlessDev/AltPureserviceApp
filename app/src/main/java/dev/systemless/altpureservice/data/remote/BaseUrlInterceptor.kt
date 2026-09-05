/*
* TODO: GPL code
* Sourced from https://github.com/oliexdev/openCook/blob/129efe0b1efb855d44720dde67b50023b9f84fd8/app/src/main/java/com/food/opencook/data/remote/BaseUrlInterceptor.kt#L4
* */

package dev.systemless.altpureservice.data.remote

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseUrlInterceptor @Inject constructor() : Interceptor {
    @Volatile
    private var baseUrl: HttpUrl? = null

    /** Update from the persisted setting. Invalid/blank URLs clear the override. */
    fun setBaseUrl(url: String?) {
        baseUrl = url?.trim()?.takeIf { it.isNotEmpty() }?.toHttpUrlOrNull()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val base = baseUrl
            ?: throw IOException("Server URL not configured")
        val newUrl = chain.request().url.newBuilder()
            .scheme(base.scheme)
            .host(base.host)
            .port(base.port)
            .build()
        val request = chain.request().newBuilder().url(newUrl).build()
        return chain.proceed(request)
    }
}
