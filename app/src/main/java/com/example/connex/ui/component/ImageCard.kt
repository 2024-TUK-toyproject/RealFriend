package com.example.connex.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.connex.R
import com.example.connex.ui.theme.Body1SemiBold
import com.example.connex.ui.theme.Body3Regular
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray300
import com.example.connex.ui.theme.Gray800

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

@Composable
fun ColumnScope.TempImageCard(weight: Float) {
    Box(
        modifier = Modifier
            .weight(weight)
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

// 프로필 사진
// 이름
// 전화번호
@Composable
fun ProfileCard(image: String, name: String, phone: String) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
        TempImageCard(weight = 1f)
        ColumnSpacer(height = 8.dp)
        Text(text = name, style = Body1SemiBold, color = Gray800)
        ColumnSpacer(height = 2.dp)
        Text(text = phone, style = Body3Regular, color = Gray300)
    }
}