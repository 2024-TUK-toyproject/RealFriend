package com.example.connex.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.connex.ui.theme.Gray100

@Composable
fun HorizontalGrayDivider(modifier: Modifier = Modifier) =
    HorizontalDivider(modifier = modifier.fillMaxWidth(), color = Gray100, thickness = 0.5.dp)
