package com.example.postmatch.data.injection

import com.example.postmatch.data.datasource.services.HistoriaRetrofitService
import com.example.postmatch.data.datasource.services.PartidoRetrofitService
import com.example.postmatch.data.datasource.services.ReviewRetrofitService
import com.example.postmatch.data.datasource.services.UsuarioRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRetroFit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesReviewRetrofitService(retrofit: Retrofit): ReviewRetrofitService {
        return retrofit.create(ReviewRetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun providesPartidoRetrofitService(retrofit: Retrofit): PartidoRetrofitService {
        return retrofit.create(PartidoRetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun providesUsuarioRetrofitService(retrofit: Retrofit): UsuarioRetrofitService {
        return retrofit.create(UsuarioRetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun providesHistoriaRetrofitService(retrofit: Retrofit): HistoriaRetrofitService {
        return retrofit.create(HistoriaRetrofitService::class.java)
    }
}