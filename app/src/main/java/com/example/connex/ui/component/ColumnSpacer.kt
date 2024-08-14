package com.example.connex.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun ColumnSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun ColumnWhiteSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height).background(Color.White))
}

@Composable
fun RowSpacer(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}