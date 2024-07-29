package com.example.connex.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connex.ui.component.util.drawColoredShadow

@Composable
fun PhoneOutLineTextField(
    text: String,
    updatePhone: (String) -> Unit,
    enabled: Boolean,
    onDone: () -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val borderColor = if (enabled) Color(0xFF5076FD) else Color(0xFFBFC1C6)
    val backgroundColor = if (enabled) Color.White else Color(0xFFF3F5F7)
    val shape = RoundedCornerShape(8.dp)

    BasicTextField(
        value = text,
        onValueChange = updatePhone,
        enabled = enabled,
        modifier = Modifier
            .border(width = (1.5).dp, color = borderColor, shape)
            .background(color = backgroundColor, shape = shape)
            .fillMaxWidth()
            .height(48.dp),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 12.sp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { onDone() })
    ) { innerTextField ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            innerTextField()
            if (trailingIcon != null) trailingIcon()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    padding: Pair<Dp, Dp>,
    text: String,
    placeholder: String,
    updateText: (String) -> Unit,
    onDone: () -> Unit
) {
    val borderColor = Color(0xFF000000).copy(alpha = 0.1f)
    val backgroundColor = Color.White
    val shape = RoundedCornerShape(200.dp)
    val iconColor = Color(0xFF939393)

    BasicTextField(
        value = text,
        onValueChange = updateText,
        modifier = modifier
            .shadow(elevation = 4.dp, shape = shape, spotColor = Color.Black.copy(0.12f))
            .border(width = 1.dp, color = borderColor, shape)
            .background(color = backgroundColor, shape = shape)
            .fillMaxWidth(),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 12.sp, lineHeight = 12.sp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onDone = { onDone() })
    ) { innerTextField ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = padding.first, vertical = padding.second),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                if (text.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = TextStyle(color = Color.Gray, fontSize = 12.sp, lineHeight = 12.sp)
                    )
                }
                innerTextField()
            }
        }
    }
}
