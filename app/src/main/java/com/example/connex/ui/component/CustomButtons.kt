package com.example.connex.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connex.ui.theme.Body3Regular
import com.example.connex.ui.theme.Gray50
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.PrimaryBlue1
import com.example.connex.ui.theme.PrimaryBlue2
import com.example.connex.ui.theme.White

@Composable
fun GeneralButton(modifier: Modifier, text: String, enabled: Boolean, onClick: () -> Unit) {
    val color = PrimaryBlue2
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
    val color = PrimaryBlue1
    val textStyle =
        TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold, lineHeight = (16.7).sp)

    Button(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .height(51.dp),
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
fun General3Button(modifier: Modifier, text: String, onClick: () -> Unit) {
    val backgroundColor = Color(0xFFEEF2FF)
    val contentColor = PrimaryBlue2
    val textStyle =
        TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold, lineHeight = 18.9.sp)

    Button(
        onClick = { onClick() },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor,
            containerColor = backgroundColor,
        )
    ) {
        Text(text = text, style = textStyle)
    }
}

@Composable
fun HalfRoundButton(
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    text: String,
    onClick: () -> Unit,
) {
    val style = TextStyle(
        fontWeight = FontWeight.SemiBold,
        lineHeight = 18.9.sp,
        fontSize = 14.sp,
        textAlign = TextAlign.Center
    )

    Button(
        onClick = { onClick() },
        modifier = modifier,
        contentPadding = PaddingValues(10.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(text = text, style = style, modifier = Modifier.padding(vertical = 4.dp))
    }
}

@Composable
fun SmallBlueBtn(onClick: () -> Unit, textStyle: TextStyle, text: String) {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(30.dp))
        .clickable { onClick() }
        .background(PrimaryBlue2)
        .padding(horizontal = 13.5.dp, vertical = 5.5.dp)) {
        Text(text = text, style = textStyle, color = White)
    }
}