package com.example.connex.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 32.sp,
        color = Color(0xFF1C1B1F)
    ),
    headlineLarge = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 32.4.sp,
        letterSpacing = 0.24.sp,
        color = Gray900
    ),
    headlineMedium = TextStyle(
        fontSize = 20.sp,
        lineHeight = 27.sp,
        fontWeight = FontWeight.SemiBold,
        color = Gray900,
        letterSpacing = 0.2.sp,
    ),
    titleLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 27.sp,
        fontWeight = FontWeight.SemiBold,
        color = Gray900,
        letterSpacing = 0.2.sp,
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 27.sp,
        fontWeight = FontWeight.Medium,
        color = Gray900,
        letterSpacing = 0.2.sp,
    ),



    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val Heading1 = TextStyle(
    fontSize = 24.sp,
    fontWeight = FontWeight.SemiBold,
    lineHeight = 32.4.sp,
    letterSpacing = 0.24.sp,
    color = Gray900
)

val Heading2 = TextStyle(
    fontSize = 20.sp,
    lineHeight = 27.sp,
    fontWeight = FontWeight.SemiBold,
    color = Gray900,
    letterSpacing = 0.2.sp,
)

val Subtitle1 = TextStyle(
    fontSize = 16.sp,
    lineHeight = 27.sp,
    fontWeight = FontWeight.SemiBold,
    color = Gray900,
    letterSpacing = 0.2.sp,
)

val text1 = TextStyle(
    fontSize = 12.sp,
    lineHeight = 27.sp,
    fontWeight = FontWeight.Medium,
    color = Gray900,
    letterSpacing = 0.2.sp,
)



