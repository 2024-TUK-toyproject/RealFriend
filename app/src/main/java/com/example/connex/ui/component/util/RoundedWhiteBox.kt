package com.example.connex.ui.component.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.unit.dp
import com.example.connex.ui.component.ArrowBackIcon
import com.example.connex.ui.component.ArrowForwardIcon
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Gray300
import com.example.connex.ui.theme.Gray800
import com.example.connex.ui.theme.PrimaryBlue2
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun RoundedWhiteBox(shape: Shape = RoundedCornerShape(12.dp), content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Color.White)
    ) {
        content()
    }
}

@Composable
fun ClickableRowContent(text: String, textColor: Color = Gray800, navIcon: Boolean = false, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, style = Body2Medium, color = textColor)
        if (navIcon) { ArrowForwardIcon() }
    }
}

@Composable
fun SwitchRowContent(text: String) {
    var switchState by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, style = Body2Medium, color = Gray800)
        Switch(
            checked = switchState,
            onCheckedChange = { switchState = it },
            colors = SwitchDefaults.colors(
                checkedTrackColor = PrimaryBlue2,
                checkedThumbColor = Color.White,
                checkedTrackAlpha = 1f,
                uncheckedTrackColor = Gray300,
                uncheckedTrackAlpha = 1f,
            )
        )
    }
}