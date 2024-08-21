package com.example.connex.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TabRowDefaults.Divider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connex.ui.component.util.drawColoredShadow
import com.example.connex.ui.theme.Gray200
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Gray50
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.PrimaryBlue2

@Composable
fun PhoneOutLineTextField(
    text: String,
    updatePhone: (String) -> Unit,
    enabled: Boolean,
    onDone: () -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val borderColor = if (enabled) PrimaryBlue2 else Gray200
    val backgroundColor = if (enabled) Color.White else Gray50
    val textColor = if (enabled) Gray900 else Gray400
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
        textStyle = LocalTextStyle.current.copy(color = textColor, fontSize = 14.sp),
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
    backgroundColor: Color = Color.White,
    contentColor: Color = Gray400,
    text: String,
    placeholder: String,
    updateText: (String) -> Unit,
    onSearch: () -> Unit
) {
    val shape = RoundedCornerShape(200.dp)

    BasicTextField(
        value = text,
        onValueChange = updateText,
        modifier = modifier
            .shadow(elevation = 4.dp, shape = shape, spotColor = Color.Black.copy(0.12f))
//            .border(width = 1.dp, color = Gray200, shape)
            .background(color = backgroundColor, shape = shape)
            .fillMaxWidth(),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(
            color = contentColor,
            fontSize = 12.sp,
            lineHeight = 15.sp
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = { onSearch() })
    ) { innerTextField ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = padding.first, vertical = padding.second),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(modifier = Modifier.weight(1f).align(Alignment.CenterVertically)) {
                if (text.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = TextStyle(color = contentColor, fontSize = 12.sp, lineHeight = 0.sp)
                    )
                }
                innerTextField()
            }
        }
    }
}

@Composable
fun UserNameTextField(
    modifier: Modifier = Modifier,
    text: String,
    updateText: (String) -> Unit,
    onDone: () -> Unit
) {

    BasicTextField(
        value = text,
        onValueChange = updateText,
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 12.sp, lineHeight = 12.sp, textAlign = TextAlign.Center,),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { onDone() })
    ) { innerTextField ->
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            innerTextField()
            Spacer(modifier = Modifier.height(2.dp))
            Divider(
                thickness = 1.dp,
                color = Color(0xFFBFC1C6),
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.height(8.5.dp))
            Text(
                text = "${text.length}/10자",
                style = LocalTextStyle.current.copy(
                    fontSize = 14.sp,
                    lineHeight = (18.2).sp,
                    color = Color(0xFFBFC1C6),
                    textAlign = TextAlign.End
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
