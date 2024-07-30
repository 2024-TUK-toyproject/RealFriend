package com.example.connex.ui.friendinit.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.component.SearchTextField
import com.example.connex.ui.friendinit.FriendSyncViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FriendSyncScreen(
    navController: NavController,
    friendSyncViewModel: FriendSyncViewModel = hiltViewModel()
) {
    val contactsUiState by friendSyncViewModel.filteredContacts.collectAsStateWithLifecycle()

    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val titleStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp
    )

    var search by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(116.dp-statusBarPadding))
        Text(
            text = "새싹님을 위한\n추천 친구 목록이에요.",
            style = titleStyle,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        Spacer(modifier = Modifier.height(28.dp))
        SearchTextField(
            modifier = Modifier
                .height(44.dp)
                .padding(horizontal = 24.dp),
            padding = Pair(20.dp, 14.dp),
            text = search,
            placeholder = "다른 친구를 추가하고 싶다면 입력해 주세요.",
            updateText = {
                search = it
                friendSyncViewModel.search(search)
            }) {
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            item { Spacer(modifier = Modifier.height(48.dp)) }
            items(items = contactsUiState, key = { it.phone }) {
                ContactCard(name = it.name, phone = it.phone)
            }
        }
        GeneralButton(modifier = Modifier
            .height(55.dp)
            .padding(horizontal = 24.dp), text = "다음", enabled = true) {}
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun ContactCard(name: String, phone: String) {
    val profileColor = Color(0xFFD9D9D9)
    val nameStyle =
        TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            color = Color(0xFF1C1B1F)
        )

    val numberStyle =
        TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 12.sp,
            color = Color(0xFF939393)
        )
    val cardHeight = 60.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .clickable { }
            .padding(start = 32.dp, end = 36.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier
            .weight(1f)
            .fillMaxHeight()) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(profileColor)
            )
            Spacer(modifier = Modifier.width(28.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = name, style = nameStyle)
                Text(text = phone, style = numberStyle)
            }
        }
        Icon(
            imageVector = Icons.Outlined.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color(0xFF939393)
        )

    }
    Spacer(modifier = Modifier.height(32.dp))


}