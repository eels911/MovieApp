package ru.androidschool.intensiv.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.network.logger.CustomHttpLogging

object MovieApiClient {

    private var client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor(CustomHttpLogging()).apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val apiClient: MovieApiInterface by lazy {

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        return@lazy retrofit.create(MovieApiInterface::class.java)
    }
}
