package com.example.connex.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connex.ui.component.util.noRippleClickable
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Body3Regular
import com.example.connex.ui.theme.Gray300
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Gray50
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.PrimaryBlue1
import com.example.connex.ui.theme.PrimaryBlue2
import com.example.connex.ui.theme.PrimaryBlue3
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
fun General4Button(modifier: Modifier, text: String, onClick: () -> Unit) {
    val backgroundColor = Color(0xFFF2F2F4)
    val contentColor = PrimaryBlue2

    Button(
        onClick = { onClick() },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor,
            containerColor = backgroundColor,
        )
    ) {
        Text(text = text, style = Body2Medium, color = Gray500)
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
fun SmallBlueBtn(textStyle: TextStyle, text: String, onClick: () -> Unit) {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(30.dp))
        .clickable { onClick() }
        .background(PrimaryBlue2)
        .padding(horizontal = 13.5.dp, vertical = 5.5.dp)) {
        Text(text = text, style = textStyle, color = White)
    }
}

@Composable
fun CheckButton(modifier: Modifier = Modifier, isChecked: Boolean, color: Color = PrimaryBlue2) {
    if (isChecked) {
        Card(
            shape = CircleShape,
            modifier = modifier.size(24.dp),
            colors = CardDefaults.cardColors(containerColor = color, contentColor = White)
        ) {
            Icon(imageVector = Icons.Rounded.Check, contentDescription = "ic_check", modifier = Modifier.padding(3.dp).fillMaxSize())
        }
    } else {
        Canvas(modifier = modifier.size(24.dp)) {
            drawCircle(color = Gray400)
            drawCircle(color = White, radius = size.width / 2.0f - 4.0f)
            drawCircle(color = Gray400, radius = size.width / 2.0f - 8.0f)
        }
    }
}


@Composable
fun SimpleCheckButton(modifier: Modifier = Modifier, isChecked: Boolean, color: Color, onClickChecked: () -> Unit, onClickUnChecked: () -> Unit) {
    if (isChecked) {
        Card(
            shape = CircleShape,
            modifier = modifier.size(18.dp),
            colors = CardDefaults.cardColors(containerColor = color, contentColor = White),
            onClick = { onClickChecked() }
        ) {
            Icon(imageVector = Icons.Rounded.Check, contentDescription = "ic_check", modifier = Modifier.padding(3.dp).fillMaxSize())
        }
    } else {
        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(CircleShape)
                .border(width = 1.2.dp, color = Gray300, shape = CircleShape)
                .background(color = White)
                .noRippleClickable {
                    onClickUnChecked()
                }
        )
    }
}

@Composable
fun PlusCardButton(size: Dp, shape: Shape, onClick: () -> Unit) {
    val modifier = if (size == 0.dp) Modifier else Modifier.size(size)

    Card(
        modifier = modifier.aspectRatio(1f),
        onClick = { onClick() },
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF2F3F7),
            contentColor = PrimaryBlue3
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "ic_add",
                modifier = Modifier
                    .fillMaxSize(0.25f)
                    .align(Alignment.Center)
            )
        }
    }
}
