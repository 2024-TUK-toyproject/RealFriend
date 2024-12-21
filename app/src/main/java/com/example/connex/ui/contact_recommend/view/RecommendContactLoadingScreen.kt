package com.example.connex.ui.contact_recommend.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.connex.R
import com.example.connex.ui.component.ColumnSpacerWithWeight
import com.example.connex.ui.theme.Heading1

@Composable
fun RecommendContactLoadingScreen() {
    val lottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lottie_loading))
    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .padding(16.dp)) {
        ColumnSpacerWithWeight(0.1f)
        Text(text = "연락할 친구를\n랜덤으로 가져오고 있어요...", style = Heading1, modifier = Modifier.weight(0.1f))
        LottieAnimation(
            modifier = Modifier.weight(0.6f),
            composition = lottieComposition,
            iterations = Int.MAX_VALUE
        )
        ColumnSpacerWithWeight(0.2f)
    }
}