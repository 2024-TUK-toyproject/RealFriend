package com.example.connex.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.connex.ui.component.util.noRippleClickable
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Head3Medium

@Composable
fun BackArrowAppBar(modifier: Modifier = Modifier, text: String = "", onBack: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    color = Gray100,
                    strokeWidth = 0.5f
                )
            }
            .padding(vertical = 17.dp, horizontal = 20.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBackIosNew,
            contentDescription = "back",
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterStart)
                .noRippleClickable { onBack() },
            tint = Gray900
        )
        Text(text = text, modifier = Modifier.align(Alignment.Center), style = Head3Medium)
    }
}


@Composable
fun AppBarIcon(image: ImageVector, onClick: () -> Unit) {
    Icon(
        imageVector = image,
        contentDescription = "ic_..",
        tint = Gray900,
        modifier = Modifier
            .size(24.dp)
            .noRippleClickable { onClick() }
    )
}
