package com.example.postmatch.ui.Screens.reviewsFollow


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.postmatch.R
import com.example.postmatch.data.ReviewInfo
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ReviewsFollowScreen(
    reviewsFollowViewModel: ReviewsFollowViewModel,
    onReviewClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        reviewsFollowViewModel.getFollowedReviews()
    }

    val state by reviewsFollowViewModel.uiState.collectAsState()

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        state.errorMessage != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = state.errorMessage ?: "Error desconocido",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        else -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                ReviewFollowHeader()
                Spacer(modifier = Modifier.height(12.dp))
                SectionFollowReviews(
                    reviews = state.reviews,
                    onReviewClick = onReviewClick,
                    onLikeClick = { reviewId ->
                        reviewsFollowViewModel.sendOrDeleteLike(reviewId)
                    }
                )
            }
        }
    }
}

@Composable
fun ReviewFollowHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.resenha),
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            letterSpacing = 0.3.sp
        )
    }
}

@Composable
fun SectionFollowReviews(
    onReviewClick: (String) -> Unit,
    reviews: List<ReviewInfo>,
    onLikeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .fillMaxSize()
    ) {
        items(reviews.size) { index ->
            ReviewFollowCard(
                reviewInfo = reviews[index],
                onReviewClick = onReviewClick,
                onLikeClick = onLikeClick
            )
        }
    }
}

@Composable
fun ReviewFollowCard(
    onReviewClick: (String) -> Unit,
    reviewInfo: ReviewInfo,
    onLikeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onReviewClick(reviewInfo.idReview) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Encabezado usuario
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = reviewInfo.usuarioNombre,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = reviewInfo.usuarioEmail,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Imagen del partido
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(reviewInfo.partidoFotoUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.estadio_bernabeu),
                placeholder = painterResource(R.drawable.estadio_bernabeu),
                contentDescription = stringResource(R.string.foto_de_perfil),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Título y descripción
            Text(
                text = reviewInfo.titulo,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = reviewInfo.descripcion,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                ),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Interacciones
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (reviewInfo.likedByUser)
                        Icons.Filled.Favorite
                    else
                        Icons.Outlined.FavoriteBorder,
                    contentDescription = if (reviewInfo.likedByUser) "Quitar like" else "Dar like",
                    tint = if (reviewInfo.likedByUser)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable { onLikeClick(reviewInfo.idReview) }
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${reviewInfo.numLikes}",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.width(20.dp))


                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${reviewInfo.numComentarios}",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 13.sp
                )
            }
        }
    }
}

