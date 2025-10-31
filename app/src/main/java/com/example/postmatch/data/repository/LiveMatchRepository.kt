package com.example.postmatch.data.repository

import com.example.postmatch.data.datasource.impl.retrofit.LiveMatchRetrofitDataSourceImpl
import javax.inject.Inject

class LiveMatchRepository @Inject constructor(
    private val remote: LiveMatchRetrofitDataSourceImpl
)
 {
    suspend fun getLiveMatches() = runCatching {
        remote.getLiveMatches()
    }
}
