package com.example.postmatch.ui.follow

import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.UsuarioInfo
import com.example.postmatch.data.local.LocalReviewProvider
import com.example.postmatch.data.local.LocalUsuarioProvider

data class FollowState(
    val usuario: UsuarioInfo = LocalUsuarioProvider.usuarios[0],
    val resenhas: List<ReviewInfo> = LocalReviewProvider.reviews
)
