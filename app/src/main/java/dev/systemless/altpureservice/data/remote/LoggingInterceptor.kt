package dev.systemless.altpureservice.data.remote

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : Interceptor  {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        Log.i("psand", request.headers["Cookie"]!! + " b " + request.url)
        return chain.proceed(request)
    }
}
