package com.example.connex.ui.album_create.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.connex.ui.component.CheckButton
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Head2Semibold
import com.example.connex.ui.theme.PrimaryBlue1
import com.example.connex.ui.theme.PrimaryBlue2
import com.example.connex.ui.theme.White
import com.example.connex.utils.Constants

class BackgroundItem(
    val id: Long,
    val image: String,
    initialChecked: Boolean,
) {
    var isSelected by mutableStateOf(initialChecked)
}

@Composable
fun BackgroundSelectScreen(modifier: Modifier = Modifier) {


    var dummy by remember {
        mutableStateOf(
            listOf(
                BackgroundItem(1, Constants.DEFAULT_PROFILE, false),
                BackgroundItem(2, Constants.DEFAULT_PROFILE, false),
                BackgroundItem(3, Constants.DEFAULT_PROFILE, false),
                BackgroundItem(4, Constants.DEFAULT_PROFILE, false),
            )
        )
    }

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
            items(items = dummy, key = { it.id }) { item ->
                BackgroundCard(modifier = Modifier.fillMaxWidth(), shape = 15.dp, background = item) {id -> dummy.map { it.isSelected = it.id == id } }
            }
        }
    }
}

@Composable
fun BackgroundCard(modifier: Modifier = Modifier, shape: Dp, background: BackgroundItem, onClick: (Long) -> Unit) {
//    val ratio = 140f / 160f

    Box(modifier = modifier) {
        Card(
            onClick = {onClick(background.id)},
            modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            shape = RoundedCornerShape(shape),
            colors = CardDefaults.cardColors(containerColor = Gray100),
        ) {}
        CheckButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 12.dp, y = 12.dp), isChecked = background.isSelected
        )
    }
}

