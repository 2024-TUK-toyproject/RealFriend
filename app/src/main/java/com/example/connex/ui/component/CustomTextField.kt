package com.example.connex.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PhoneOutLineTextField(text: String, updatePhone: (String) -> Unit, enabled: Boolean, onDone: () -> Unit) {
    val borderColor = if(enabled) Color(0xFF5076FD) else Color(0xFFBFC1C6)
    val backgroundColor = if(enabled) Color.White else Color(0xFFF3F5F7)
    val shape = RoundedCornerShape(8.dp)

    BasicTextField(
        value = text,
        onValueChange = updatePhone,
        enabled = enabled,
        modifier = Modifier
//            .clip(shape)
            .border(width = (1.5).dp, color = borderColor, shape)
            .background(color = backgroundColor, shape = shape)
            .fillMaxWidth()
            .height(48.dp),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 12.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {onDone()})
    ) { innerTextField ->
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp)){
            innerTextField()
        }
    }
}