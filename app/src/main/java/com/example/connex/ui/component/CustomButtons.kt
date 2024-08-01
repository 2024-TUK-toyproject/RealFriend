package com.example.connex.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connex.ui.theme.MainBlue
import com.example.connex.ui.theme.MainBlue2

@Composable
fun GeneralButton(modifier: Modifier, text: String, enabled: Boolean, onClick: () -> Unit) {
    val color = MainBlue
    val textStyle =
        TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, lineHeight = 19.sp)


    Button(
        onClick = { onClick() },
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = color,
        )
    ) {
        Text(text = text, style = textStyle)
    }
}

@Composable
fun General2Button(modifier: Modifier, text: String, enabled: Boolean, onClick: () -> Unit) {
    val color = MainBlue2
    val textStyle =
        TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold, lineHeight = (16.7).sp)

    Button(
        onClick = { onClick() },
        modifier = modifier.fillMaxWidth().height(51.dp),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = color,
        )
    ) {
        Text(text = text, style = textStyle)
    }
}