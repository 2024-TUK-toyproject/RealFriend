package com.example.connex.ui.album_create.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.SearchTextField
import com.example.connex.ui.home.view.ContactCard
import com.example.connex.ui.home.view.ContactCardCheckBox
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Head2Semibold
import com.example.connex.utils.Constants

@Composable
fun BackgroundSelectScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 51.dp)
    ) {
        Text(
            text = "대표 이미지를\n설정해 주세요",
            style = Head2Semibold,
            color = Gray900,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        ColumnSpacer(height = 16.dp)
        Text(
            text = "앨범 생성 후, 설정에서 변경 가능합니다.",
            style = Body2Medium,
            color = Gray500,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        ColumnSpacer(height = 32.dp)

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(4) {
                BackgroundCard()
            }
        }
    }
}

@Composable
fun BackgroundCard(modifier: Modifier = Modifier) {
    val ratio = 140f / 160f
    Card(
        modifier
            .fillMaxWidth()
            .aspectRatio(ratio),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEAECED))
    ) {

    }
}