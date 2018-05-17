package com.githang.rankgithubchina.api

import com.githang.rankgithubchina.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2018-05-17
 */
object ApiService {

    private const val TIMEOUT_SECONDS = 30L

    private val RETROFIT: Retrofit

    init {
        val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(SerialLogInterceptor())
        }
        RETROFIT = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.API_URL)
                .client(clientBuilder.build())
                .build()
    }

    fun <T> create(clazz: Class<T>): T {
        return RETROFIT.create(clazz)
    }
}