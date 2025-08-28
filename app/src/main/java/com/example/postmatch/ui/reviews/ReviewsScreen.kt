package com.example.postmatch.ui.reviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postmatch.R
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalReviewProvider


@Composable
fun ReviewsScreen(
    reviewsViewModel: ReviewsViewModel,
    onReviewClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by reviewsViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.verde_oscuro))
            .padding(16.dp)
    ) {
        // Encabezado
        ReviewHeader()
        Spacer(modifier = Modifier.height(16.dp))
        // Lista de tarjetas
        SectionReviews(
            reviews = state.reviews,
            onReviewClick =  onReviewClick
        )
    }
}

@Composable
fun ReviewHeader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.postmatch),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@Composable
fun AccionButtonHeader(
    imageVector: ImageVector,
    idContentDescription: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = stringResource(idContentDescription),
            tint = Color.White
        )
    }
}

@Composable
fun SectionReviews(
    onReviewClick: (Int) -> Unit,
    reviews: List<ReviewInfo>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(reviews.size) {
            index ->
            ReviewCard(
                reviewInfo = reviews[index],
                onReviewClick = onReviewClick
            )
        }
    }
}

@Composable
fun ReviewCard(
    onReviewClick: (Int) -> Unit,
    reviewInfo: ReviewInfo,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(R.color.verde_oscuro3))
            .clickable { onReviewClick(reviewInfo.idReview) }
            .padding(12.dp)
    ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${reviewInfo.usuarioNombre} - ${reviewInfo.usuarioEmail}",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Text(
                    text = reviewInfo.titulo,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = reviewInfo.descripcion,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${reviewInfo.numLikes}", color = Color.White, fontSize = 12.sp)

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${reviewInfo.numComentarios}", color = Color.White, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(id = R.drawable.estadio_bernabeu),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
}


@Composable
@Preview (showBackground = true)
fun ReviewsScreenPreview(){
    ReviewsScreen(
       reviewsViewModel = viewModel(),
        onReviewClick = {}
    )
}