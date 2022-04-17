package com.assessment.olympicathletes.manager

import com.assessment.olympicathletes.api.APIService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

object ApiManager {
    private const val SERVER_BASE_URL: String = "https://test-ocs.herokuapp.com"
    private lateinit var mAPIService: APIService

    init {
        val retrofit = initRetrofit()
        initServices(retrofit)
    }

    private fun initRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            networkInterceptors().add(Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            })
            addInterceptor(interceptor)
        }

        return Retrofit.Builder().baseUrl(SERVER_BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(createMoshiConverter())
            .client(client.build())
            .build()
    }

    private fun createMoshiConverter(): MoshiConverterFactory = MoshiConverterFactory.create()

    private fun initServices(retrofit: Retrofit) {
        mAPIService = retrofit.create(APIService::class.java)
    }

    fun loadGames() = mAPIService.getGames()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!

    fun loadAthletesByGameId(gameId: String) = mAPIService.getAthletesByGameId(gameId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())!!

    fun loadResultsByAthleteId(athleteId: String) = mAPIService.getResultsByAthleteId(athleteId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())!!

}