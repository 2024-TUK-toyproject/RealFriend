package com.example.connex.ui.album_create.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.SearchTextField
import com.example.connex.ui.home.view.ContactCard
import com.example.connex.ui.home.view.ContactCardCheckBox
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Head2Semibold
import com.example.connex.utils.Constants

@Composable
fun FriendSelectScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(bottom = 51.dp)) {
        Text(
            text = "함께할 친구를 \n선택해 주세요",
            style = Head2Semibold,
            color = Gray900,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        ColumnSpacer(height = 36.dp)
        SearchTextField(
            modifier = Modifier
                .height(40.dp)
                .padding(horizontal = 24.dp),
            padding = Pair(20.dp, 12.dp),
            backgroundColor = Color(0xFFF2F4F8),
            text = "",
            placeholder = "친구의 이름이나 연락처로 검색해 보세요.",
            updateText = {}) {}

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item { ColumnSpacer(height = 12.dp) }
            items(10) {
                ContactCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {}
                        .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp),
                    size = 56.dp,
                    image = Constants.DEFAULT_PROFILE,
                    name = "누구게",
                    phone = "010-0000-0000"
                ) {
                    ContactCardCheckBox(isCheck = false)
                }
            }
        }

    }
}