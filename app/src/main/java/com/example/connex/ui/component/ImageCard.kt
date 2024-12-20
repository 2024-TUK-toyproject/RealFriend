package com.example.connex.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.connex.R
import com.example.connex.ui.theme.Gray100

@Composable
fun PhotoCard(modifier: Modifier = Modifier, picture: String?) {
    Card(
        modifier = modifier,
//        onClick = { navigate() },
        shape = RoundedCornerShape(13.dp),
        colors = CardDefaults.cardColors(
            containerColor = Gray100,
//            contentColor = PrimaryBlue3
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (picture != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = picture),
                    contentDescription = "image_picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun RowScope.ImageCard(image: String, weight: Float) {
    Box(
        modifier = Modifier
            .weight(weight)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = image),
            contentDescription = "image_add_photo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun RowScope.TempImageCard(weight: Float) {
    Box(
        modifier = Modifier
            .weight(weight)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "image_add_photo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}