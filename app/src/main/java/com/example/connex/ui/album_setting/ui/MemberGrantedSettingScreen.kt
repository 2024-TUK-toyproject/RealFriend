package com.example.connex.ui.album_setting.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.connex.ui.album_setting.GrantedInfo
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.component.RowSpacerWithWeight
import com.example.connex.ui.component.TempImageCard
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.ImageGrantedFounder
import com.example.connex.ui.svg.iconpack.ImageGrantedManager
import com.example.connex.ui.svg.iconpack.ImageGrantedMember
import com.example.connex.ui.theme.BackgroundGray
import com.example.connex.ui.theme.Body1Medium
import com.example.connex.ui.theme.Body1SemiBold
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Body3Medium
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Gray800
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.PrimaryBlue2
import com.example.connex.ui.theme.White
import com.example.domain.entity.album.Granted

@Composable
fun MemberGrantedSettingScreen(applicationState: ApplicationState) {
    var selectedGranted by remember {
        mutableStateOf(Granted.MEMBER)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(bottom = 24.dp)
    ) {
        BackArrowAppBar { applicationState.popBackStack() }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
//                .fillMaxSize()
                .weight(0.9f)
                .background(White)
                .padding(start = 24.dp, end = 24.dp, top = 26.dp)
                .verticalScroll(scrollState)
        ) {
            ProfileArea(profileImage = "", name = "John Doe", granted = "Granted")
            ColumnSpacer(height = 36.dp)
            Text(text = "권한을 선택해 주세요.", style = Body1Medium, color = Gray800)
            ColumnSpacer(height = 14.dp)
            GrantedSelectedButton(
                granted1 = Granted.MEMBER,
                granted2 = Granted.MANAGER,
                selected = selectedGranted
            ) {
                if (it != selectedGranted) selectedGranted = it
            }
            ColumnSpacer(height = 36.dp)
            Text(text = "권한의 종류", style = Body1Medium, color = Gray800)
            ColumnSpacer(height = 14.dp)
            GrantedListBox()
        }
        GeneralButton(modifier = Modifier
            .weight(0.1f)
            .padding(horizontal = 24.dp), text = "권한 변경하기") {}
    }
}

@Composable
fun ProfileArea(profileImage: String, name: String, granted: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        RowSpacerWithWeight(weight = 0.2f)
        TempImageCard(weight = 0.3f)
        RowSpacerWithWeight(weight = 0.2f)
        Column(
            modifier = Modifier.weight(0.3f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = name, style = Body1SemiBold)
            ColumnSpacer(height = 8.dp)
            Text(text = granted, style = Body2Medium, color = Gray800)
        }
        RowSpacerWithWeight(weight = 0.2f)
    }
}

@Composable
fun GrantedSelectedButton(
    granted1: Granted,
    granted2: Granted,
    selected: Granted,
    onSelected: (Granted) -> Unit,
) {
    val (granted1BgColor, granted1TextColor, granted2BgColor, granted2TextColor) = when (selected) {
        Granted.MEMBER -> listOf(PrimaryBlue2, White, BackgroundGray, Gray400)
        Granted.MANAGER -> listOf(BackgroundGray, Gray400, PrimaryBlue2, White)
        Granted.FOUNDER -> TODO()
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp, 0.dp, 0.dp, 12.dp))
                .background(granted1BgColor)
                .clickable { onSelected(Granted.MEMBER) }
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = granted1.value,
                style = Body1SemiBold,
                color = granted1TextColor,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(0.dp, 12.dp, 12.dp, 0.dp))
                .background(granted2BgColor)
                .clickable { onSelected(Granted.MANAGER) }
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = granted2.value,
                style = Body1SemiBold,
                color = granted2TextColor,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun GrantedListBox() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BackgroundGray)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val grantedImages = listOf(IconPack.ImageGrantedFounder, IconPack.ImageGrantedManager, IconPack.ImageGrantedMember)
        val grantedInfo = Granted.entries.zip(grantedImages).map { GrantedInfo(it.first.value, it.second, it.first.description) }
        grantedInfo.forEach {
            GrantedRowContent(it)
            if (it != grantedInfo.last()) HorizontalDivider(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp), color = White, thickness = 0.5.dp)
        }
    }
}

@Composable
fun GrantedRowContent(grantedInfo: GrantedInfo) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.weight(0.3f), verticalAlignment = Alignment.Bottom) {
            Image(imageVector = grantedInfo.image, contentDescription = "image_granted", modifier = Modifier.size(24.dp))
            RowSpacer(width = 4.dp)
            Text(text = "(${grantedInfo.name})", style = Body3Medium, color = Gray900)
        }
        Text(text = grantedInfo.description, style = Body2Medium, color = Gray900, modifier = Modifier.weight(0.7f))
    }
}