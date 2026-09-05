package dev.systemless.altpureservice.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import dev.systemless.altpureservice.data.remote.BaseUrlInterceptor
import dev.systemless.altpureservice.data.remote.CookieInterceptor
import dev.systemless.altpureservice.data.remote.LoggingInterceptor
import dev.systemless.altpureservice.data.remote.tickets.TicketApi
import dev.systemless.altpureservice.repository.PureserviceRepository
import dev.systemless.altpureservice.ui.FilterList
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providePureserviceRepository(api: TicketApi) = PureserviceRepository(api)

    @Singleton
    @Provides
    fun provideOkHttpClient(baseUrlInterceptor: BaseUrlInterceptor, cookieInterceptor: CookieInterceptor, loggingInterceptor: LoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(baseUrlInterceptor)
            .addInterceptor(cookieInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providePureserviceApi(client: OkHttpClient): TicketApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://localhost/")
            .client(client)
            .build()
            .create(TicketApi::class.java)
    }

}
