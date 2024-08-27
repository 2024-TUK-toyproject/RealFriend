package com.example.connex.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.connex.ui.theme.Body1Semibold
import com.example.connex.ui.theme.Body3Medium
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.PrimaryBlue3
import com.example.connex.ui.theme.White

@Composable
fun SkyBlueBox(
    modifier: Modifier = Modifier,
    leadingImage: ImageVector,
    leadingImageSize: Dp,
    imageAndTextPadding: Dp = 24.dp,
    title: String,
    body: String,
    enabled: Boolean = false,
    elevation: Boolean = false,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = { onClick() },
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF459AFE).copy(alpha = 0.12f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(leadingImageSize),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = PrimaryBlue3,
                    contentColor = White
                ),
                elevation = if(elevation) CardDefaults.cardElevation(defaultElevation = 5.dp) else CardDefaults.cardElevation()
            ) {
                Icon(
                    imageVector = leadingImage,
                    contentDescription = "ic_...",
                    modifier = Modifier.fillMaxSize()
                )
            }
            RowSpacer(width = imageAndTextPadding)
            Column {
                Text(text = title, style = Body3Medium, color = Gray500)
                ColumnSpacer(height = 4.dp)
                Text(text = body, style = Body1Semibold, color = Gray900)
            }
        }
    }
}