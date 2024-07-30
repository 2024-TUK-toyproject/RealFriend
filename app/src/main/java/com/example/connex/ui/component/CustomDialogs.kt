package com.example.connex.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun PictureChoiceDialog(modifier: Modifier = Modifier, onClose: () -> Unit) {
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        PictureChoiceDialogContents(modifier = modifier)
    }
}

@Composable
fun PictureChoiceDialogContents(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(13.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = modifier.padding(vertical = 16.dp)) {
            PictureChoiceDialogContent(icon = Icons.Default.CameraAlt, text = "카메라로 촬영하기") {

            }
            PictureChoiceDialogContent(icon = Icons.Default.AddPhotoAlternate, text = "사진 선택하기") {

            }
        }

    }
}

@Composable
fun PictureChoiceDialogContent(icon: ImageVector, text: String, onClick: () -> Unit) {
    val contentColor = Color(0xFFC1C1C1)
    val textStyle = TextStyle(fontSize = 16.sp, lineHeight = 16.sp, color = contentColor)

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(start = 40.dp, top = 16.dp, bottom = 16.dp)) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp), tint = contentColor)
        Spacer(modifier = Modifier.width(24.dp))
        Text(text = text, style = textStyle)
    }
}
