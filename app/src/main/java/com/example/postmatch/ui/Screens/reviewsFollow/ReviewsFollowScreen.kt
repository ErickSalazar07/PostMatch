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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.postmatch.R
import com.example.postmatch.data.ReviewInfo

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
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.errorMessage != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.errorMessage ?: "Error desconocido")
            }
        }

        else -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                ReviewFollowHeader()
                Spacer(modifier = Modifier.height(16.dp))
                SectionFollowReviews(
                    reviews = state.reviews,
                    onReviewClick = onReviewClick,
                    onLikeClick = { reviewId ->
                        Log.d("LIKES_DEBUG", "SectionFollowReviews -> onLikeClick con reviewId=$reviewId")
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
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.resenha),
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
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
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(reviews.size) { index ->
            ReviewFollowCard(
                reviewInfo = reviews[index],
                onReviewClick = onReviewClick,
                onLikeClick = onLikeClick,
                modifier = Modifier.testTag("reviewFollowItem")
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
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onReviewClick(reviewInfo.idReview) }
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${reviewInfo.usuarioNombre} - ${reviewInfo.usuarioEmail}",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            )
            Text(
                text = reviewInfo.titulo,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.testTag("reviewFollowItemTitulo")
            )
            Text(
                text = reviewInfo.descripcion,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {
                            Log.d("LIKES_DEBUG", "Click en corazón - reviewId=${reviewInfo.idReview}")
                            onLikeClick(reviewInfo.idReview)
                        }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("${reviewInfo.numLikes}", color = MaterialTheme.colorScheme.onPrimary, fontSize = 12.sp)

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("${reviewInfo.numComentarios}", color = MaterialTheme.colorScheme.onPrimary, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

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
                .size(200.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}
