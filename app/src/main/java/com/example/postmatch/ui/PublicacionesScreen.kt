package com.example.postmatch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.postmatch.R
import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.local.LocalReviewProvider


@Composable
fun PublicacionesScreen(
    notificacionesButtonClick: () -> Unit,
    settingsButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val reviews = LocalReviewProvider.reviews
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.verde_oscuro))
            .padding(16.dp)
    ) {
        // Encabezado
        PublicacionesHeader(
            onNotificacionesButtonClick = notificacionesButtonClick,
            onSettingsButtonClick = settingsButtonClick
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Lista de tarjetas
        PublicacionesSection(
            reviews = reviews
        )
    }
}

@Composable
fun PublicacionesHeader(
    onNotificacionesButtonClick: () -> Unit,
    onSettingsButtonClick: () -> Unit,
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

        BotonesAccionHeader(
            onNotificacionesButtonClick = onNotificacionesButtonClick,
            onSettingsButtonClick = onSettingsButtonClick
        )
    }
}

@Composable
fun BotonesAccionHeader(
    onSettingsButtonClick: () -> Unit,
    onNotificacionesButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        AccionButtonHeader(
            imageVector = Icons.Filled.Notifications,
            idContentDescription = R.string.notificaciones,
            onClick = onNotificacionesButtonClick
        )

        AccionButtonHeader(
            imageVector = Icons.Filled.Settings,
            idContentDescription = R.string.settings,
            onClick = onSettingsButtonClick
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
fun PublicacionesSection(
    reviews: List<ReviewInfo>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(reviews.size) {
            index ->
            MatchCard(
                reviewInfo = reviews[index]
            )
        }
    }
}

@Composable
fun MatchCard(
    reviewInfo: ReviewInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1C1F1E))
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

fun PublicacionesScreenPreview(){
    PublicacionesScreen(
        notificacionesButtonClick = {},
        settingsButtonClick = {}
    )
}