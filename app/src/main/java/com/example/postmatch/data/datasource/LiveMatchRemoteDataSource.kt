package com.example.postmatch.data.datasource

import com.example.postmatch.data.dtos.LiveMatchDto

interface LiveMatchRemoteDataSource {
    suspend fun getLiveMatches(): List<LiveMatchDto>
}
