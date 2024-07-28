package com.example.connex.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
fun GenerateButton(modifier: Modifier, text: String, enabled: Boolean, onClick: () -> Unit) {
    val color = Color(0xFF5076FD)
    val textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)


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
        Text(text = text, modifier = Modifier.padding(vertical = 18.dp), style = textStyle)
    }

}