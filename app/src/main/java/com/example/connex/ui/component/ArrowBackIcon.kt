package com.example.connex.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.connex.ui.component.util.noRippleClickable

@Composable
fun ArrowBackIcon(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Default.ArrowBackIosNew,
        contentDescription = null,
        modifier = modifier.size(24.dp).noRippleClickable { onClick() }
    )
}