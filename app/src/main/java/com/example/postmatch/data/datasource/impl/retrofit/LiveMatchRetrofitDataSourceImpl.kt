package com.example.postmatch.data.datasource.impl.retrofit

import com.example.postmatch.data.datasource.LiveMatchRemoteDataSource
import com.example.postmatch.data.datasource.services.LiveMatchRetrofitService
import com.example.postmatch.data.dtos.LiveMatchDto
import javax.inject.Inject

class LiveMatchRetrofitDataSourceImpl @Inject constructor(
    private val service: LiveMatchRetrofitService
) {
    suspend fun getLiveMatches(): List<LiveMatchDto> {
        val response = service.getLiveMatches()
        return response.response
    }
}

