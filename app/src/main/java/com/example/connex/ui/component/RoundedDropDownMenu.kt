package com.example.connex.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.connex.ui.theme.Body3Regular
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.White

@Composable
fun RoundedDropDownMenu(expanded: Boolean = false, onClose: () -> Unit, onClick1: () -> Unit, onClick2: () -> Unit) {
    DropdownMenu(
        modifier = Modifier
            .shadow(
                elevation = 4.dp,
                spotColor = Color(0x14000000),
                ambientColor = Color(0x14000000)
            )
            .background(color = White, shape = RoundedCornerShape(size = 12.dp)),
        expanded = expanded,
        onDismissRequest = { onClose() }) {
        DropdownMenuItem(
            text = { Text(text = "편집", style = Body3Regular, color = Gray500) },
            onClick = {
                onClick1()
                onClose()
            }
        )
        DropdownMenuItem(
            text = { Text(text = "정렬", style = Body3Regular, color = Gray500) },
            onClick = {
                onClick2()
                onClose()
            }
        )
        DropdownMenuItem(
            text = { Text(text = "앨범 설정", style = Body3Regular, color = Gray500) },
            onClick = { /*TODO*/ }
        )
    }
}