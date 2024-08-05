package com.example.connex.ui.friendinit.view

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.connex.R
import com.example.connex.ui.Screen
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.component.SearchTextField
import com.example.connex.ui.component.util.addFocusCleaner
import com.example.connex.ui.friendinit.FriendSyncViewModel
import com.example.connex.ui.theme.Gray300
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Heading2
import com.example.connex.ui.theme.PrimaryBlue2
import rememberConnexLogo1

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FriendSyncScreen(
    navController: NavController,
    friendSyncViewModel: FriendSyncViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val contactsUiState by friendSyncViewModel.filteredContacts.collectAsStateWithLifecycle()

//    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val countStyle = SpanStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = PrimaryBlue2
    )

    val countTextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = Gray400
    )

    var search by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.24f) /* 원래 0.29dp*/
                .padding(start = 24.dp, end = (35.7).dp, bottom = 20.dp, top = 80.dp)
        ) {
//            Spacer(modifier = Modifier.height(80.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "새싹님을 위한\n추천 친구 목록이에요.",
                    style = Heading2,
                )
                Image(
                    imageVector = rememberConnexLogo1(),
                    contentDescription = null,
                    modifier = Modifier.size(width = 61.3.dp, 52.8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = buildAnnotatedString {
                withStyle(style = countStyle) {
                    append("${friendSyncViewModel.count.value}")
                }
                append("명 추가됨")
            }, style = countTextStyle)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.76f)  /*원래 0.71dp*/
                .background(Color(0xFFf2f4f8))
                .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 40.dp)
        ) {
            SearchTextField(
                modifier = Modifier
                    .height(40.dp),
                padding = Pair(20.dp, 12.dp),
                text = search,
                placeholder = "다른 친구를 추가하고 싶다면 입력해 주세요.",
                updateText = {
                    search = it
                    friendSyncViewModel.search(search)
                }) {
                focusManager.clearFocus()
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                item { Spacer(modifier = Modifier.height(24.dp)) }
                items(items = contactsUiState, key = { it.contact.phone }) {
                    ContactCard(
                        name = it.contact.name,
                        phone = it.contact.phone,
                        isSelected = it.isSelect
                    ) {
                        friendSyncViewModel.selectCard(
                            phone = it.contact.phone,
                            isSelect = !it.isSelect
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            GeneralButton(
                modifier = Modifier
                    .height(55.dp),
                text = "다음", enabled = true
            ) { navController.navigate(Screen.Home.route) }
        }
    }
}

@Composable
fun ContactCard(name: String, phone: String, isSelected: Boolean = false, onClick: () -> Unit) {
    val profileColor = Color(0xFFD9D9D9)
    val checkColor = if (isSelected) PrimaryBlue2 else Gray300
//    val backgroundColor = if (isSelected) Color(0xFFECEFFF) else Color.White
    val backgroundColor = Color.White
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
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .background(backgroundColor)
            .padding(start = 8.dp, end = 16.dp, top = 11.dp, bottom = 11.dp)
            .fillMaxWidth()
            .height(cardHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
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
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = checkColor
        )

    }
    Spacer(modifier = Modifier.height(2.dp))


}