package com.example.postmatch.data.datasource.services

import com.example.postmatch.data.dtos.LiveMatchDto
import retrofit2.http.GET
import retrofit2.http.Query


interface LiveMatchRetrofitService {
    data class LiveMatchResponse(val response: List<LiveMatchDto>)
    @GET("fixtures")
    suspend fun getLiveMatches(
        @Query("live") live: String = "all"
    ): LiveMatchResponse
}
