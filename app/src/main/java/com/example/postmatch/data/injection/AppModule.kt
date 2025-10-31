package com.example.postmatch.data.injection

import com.example.postmatch.data.datasource.services.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ✅ 1. Proveedor del OkHttpClient (debe ir primero)
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().build()

    // ✅ 2. Retrofit principal (para tu backend local)
    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .client(client) // usa el mismo cliente
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    // ✅ 3. Servicios locales (usan el mismo Retrofit)
    @Singleton
    @Provides
    fun providesReviewRetrofitService(retrofit: Retrofit): ReviewRetrofitService =
        retrofit.create(ReviewRetrofitService::class.java)

    @Singleton
    @Provides
    fun providesPartidoRetrofitService(retrofit: Retrofit): PartidoRetrofitService =
        retrofit.create(PartidoRetrofitService::class.java)

    @Singleton
    @Provides
    fun providesUsuarioRetrofitService(retrofit: Retrofit): UsuarioRetrofitService =
        retrofit.create(UsuarioRetrofitService::class.java)

    @Singleton
    @Provides
    fun providesHistoriaRetrofitService(retrofit: Retrofit): HistoriaRetrofitService =
        retrofit.create(HistoriaRetrofitService::class.java)

    // ✅ 4. Retrofit del API-FOOTBALL (usa el mismo cliente, pero con header dinámico)
    @Provides
    @Singleton
    fun provideLiveMatchService(client: OkHttpClient): LiveMatchRetrofitService =
        Retrofit.Builder()
            .baseUrl("https://v3.football.api-sports.io/")
            .client(
                client.newBuilder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("x-apisports-key", "a61f91ff0e6cdec91eb77d070f82b8b8")
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LiveMatchRetrofitService::class.java)
}
