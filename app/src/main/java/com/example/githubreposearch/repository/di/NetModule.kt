package com.example.githubreposearch.repository.di

import com.apollographql.apollo.ApolloClient
import com.example.githubreposearch.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class NetModule {

    @Provides
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder().method(
                original.method(),
                original.body()
            )
            builder.addHeader(
                "Authorization"
                , "Bearer " + BuildConfig.AUTH_TOKEN
            )
            chain.proceed(builder.build())
        }.build()

    @Provides
    fun providesApolloClient(okHttpClient: OkHttpClient): ApolloClient = ApolloClient.builder()
        .serverUrl(BuildConfig.BASE_URL)
        .okHttpClient(okHttpClient)
        .build()

}