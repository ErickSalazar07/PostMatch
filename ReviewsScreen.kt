package com.example.postmatch.ui.Screens.reviews

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.postmatch.R
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.ui.theme.*
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ChatBubbleOutline


@Composable
fun ReviewsScreen(
    reviewsViewModel: ReviewsViewModel,
    onReviewClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) { reviewsViewModel.getAllReviews() }

    val state by reviewsViewModel.uiState.collectAsState()

    when {
        state.isLoading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }

        state.errorMessage != null -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            Text(
                text = state.errorMessage ?: "Error desconocido",
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        else -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                TopHeader()
                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { reviewsViewModel.sortByMostLiked() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Más likeados")
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = { reviewsViewModel.sortByMostRecent() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("Más recientes")
                    }
                }



                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    items(state.reviews.size) { index ->
                        ReviewCard(
                            reviewInfo = state.reviews[index],
                            onReviewClick = onReviewClick,
                            onLikeClick = { id -> reviewsViewModel.sendOrDeleteLike(id) },
                            onClickSort = { reviewsViewModel.sortByMostLiked() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(24.dp))

        Text(
            text = "Reviews",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Icon(
            imageVector = Icons.Filled.Settings,
            contentDescription = "Settings",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ReviewCard(
    reviewInfo: ReviewInfo,
    onReviewClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onClickSort: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(14.dp)
    ) {

        Column(Modifier.weight(1f)) {

            Text(
                text = reviewInfo.usuarioNombre,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = reviewInfo.titulo,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = reviewInfo.descripcion,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                fontSize = 13.sp,
                lineHeight = 18.sp,
                modifier = Modifier.padding(top = 6.dp, bottom = 10.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                LikeChip(
                    liked = reviewInfo.likedByUser,
                    count = reviewInfo.numLikes,
                    onClick = { onLikeClick(reviewInfo.idReview) },
                    onClickSort = { onClickSort() }
                )

                Spacer(Modifier.width(12.dp))

            }
        }

        Spacer(Modifier.width(12.dp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(reviewInfo.partidoFotoUrl)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.estadio_bernabeu),
            placeholder = painterResource(R.drawable.estadio_bernabeu),
            contentDescription = "Foto del partido",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(110.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable { onReviewClick(reviewInfo.idReview) }
        )
    }
}

@Composable
fun LikeChip(liked: Boolean, count: Int, onClick: () -> Unit, onClickSort: () -> Unit) {

    val chipBackground = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    val iconTint = if (liked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(chipBackground)
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .clickable {
                onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (liked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(18.dp)
        )

        Spacer(Modifier.width(6.dp))

        Text(
            text = count.toString(),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 13.sp
        )
    }
}

