package com.example.connex.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connex.ui.component.util.noRippleClickable
import com.example.connex.ui.home.view.BottomSheetAlbumCard
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.IcBlackWarning
import com.example.connex.ui.svg.iconpack.IcConnexUsedUser
import com.example.connex.ui.svg.iconpack.IcRedWarning
import com.example.connex.ui.theme.Gray50
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.Gray600
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Head3Semibold
import com.example.connex.ui.theme.PrimaryBlue2
import com.example.connex.ui.theme.White
import com.example.domain.model.login.MobileCarrier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MobileCarrierModalBottomSheet(
    currentCarrier: MobileCarrier,
    onClose: () -> Unit,
    onClick: (MobileCarrier) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val titleStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 18.sp,
        color = Gray900
    )
    val contentStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        color = Color(0xFFB3B5B6)
    )

    val carriers = listOf(
        MobileCarrier.SKT,
        MobileCarrier.KT,
        MobileCarrier.LG,
        MobileCarrier.FRUGAL_SKT,
        MobileCarrier.FRUGAL_LG,
        MobileCarrier.FRUGAL_KT
    )

    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState,
        shape = RoundedCornerShape(topEnd = 13.dp, topStart = 13.dp),
        containerColor = Color.White,
        dragHandle = null
    ) {
        Column(
//            Spacer(modifier = Modifier.height(43.dp))
            modifier = Modifier.navigationBarsPadding().padding(
                bottom = 44.dp,
                top = 44.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "통신사 선택", style = titleStyle)
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Gray900,
                    modifier = Modifier
                        .size(24.dp)
                        .noRippleClickable { onClose() })

            }
            Spacer(modifier = Modifier.height(43.dp))

            carriers.forEachIndexed { index, mobileCarrier ->
                Text(
                    text = mobileCarrier.getName(),
                    style = contentStyle,
                    color = if (currentCarrier != mobileCarrier) Color(0xFFB3B5B6) else PrimaryBlue2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable { onClick(mobileCarrier) }
                        .padding(start = 32.dp, end = 32.dp))
                if (index != carriers.lastIndex) {
                    Spacer(modifier = Modifier.height(36.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendRemoveModalBottomSheet(
    names: List<String>,
    onClose: () -> Unit,
    onDelete: () -> Unit,
) {
    val name = if (names.size == 1) {
        "${names[0]}님"
    } else {
        "${names[0]} 외 ${names.size - 1}명"
    }

    val sheetState = rememberModalBottomSheetState()
//    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val titleStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight(600),
        color = Gray900
    )
    val contentStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.4.sp,
        fontWeight = FontWeight(400),
        color = Gray900,
        textAlign = TextAlign.Center,
    )


    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState,
        shape = RoundedCornerShape(topEnd = 13.dp, topStart = 13.dp),
        windowInsets = WindowInsets.displayCutout,
        containerColor = Color.White,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 24.dp,
                    top = 36.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(imageVector = IconPack.IcRedWarning, contentDescription = "warning_icon")
            ColumnSpacer(height = 20.dp)
            Text(text = "정말 삭제하시겠어요?", style = titleStyle)
            ColumnSpacer(height = 12.dp)
            Text(text = "${name}은 회원님의 친구 목록에서\n삭제된 사실을 알 수 없습니다.", style = contentStyle)
            ColumnSpacer(height = 28.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                HalfRoundButton(
                    modifier = Modifier.weight(1f),
                    containerColor = Gray50,
                    contentColor = Gray500,
                    text = "뒤로가기"
                ) {
                    onClose()
                }
                RowSpacer(width = 16.dp)
                HalfRoundButton(
                    modifier = Modifier.weight(1f),
                    containerColor = Gray900,
                    contentColor = White,
                    text = "삭제하기"
                ) {
                    onDelete()
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareAlbumModalBottomSheet(
    albums: List<TempleAlbumData>,
    onClose: () -> Unit,
    navigate: () -> Unit,
) {

    val sheetState = rememberModalBottomSheetState()
    var current = albums.size

    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState,
        shape = RoundedCornerShape(topEnd = 13.dp, topStart = 13.dp),
        windowInsets = WindowInsets.displayCutout,
        containerColor = Color.White,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 28.dp,
                    top = 28.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "공유 앨범", style = Head3Semibold, color = Gray900)
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "forward_icon",
                    modifier = Modifier
                        .size(24.dp)
                        .noRippleClickable { navigate() },
                    tint = Gray600
                )
            }
            ColumnSpacer(height = 16.dp)
            Row(modifier = Modifier.fillMaxWidth()) {
                albums.forEach {
                    BottomSheetAlbumCard(
                        modifier = Modifier.weight(1f),
                        onClick = { /*TODO*/ },
                        image = it.image,
                        name = it.name
                    )
                }
                while (current < 3) {
                    Box(modifier = Modifier.weight(1f))
                    current++
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendAddModalBottomSheet(
    onClose: () -> Unit,
) {

    val sheetState = rememberModalBottomSheetState()
    val titleStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight(600),
        color = Gray900
    )
    val contentStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.4.sp,
        fontWeight = FontWeight(400),
        color = Gray900,
        textAlign = TextAlign.Center,
    )


    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState,
        shape = RoundedCornerShape(topEnd = 13.dp, topStart = 13.dp),
        windowInsets = WindowInsets.displayCutout,
        containerColor = Color.White,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 40.dp,
                    top = 36.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(imageVector = IconPack.IcBlackWarning, contentDescription = "warning_icon")
            ColumnSpacer(height = 20.dp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "이름 옆, ", style = titleStyle)
                Image(imageVector = IconPack.IcConnexUsedUser, contentDescription = "ic_connex_used_user", modifier = Modifier.size(24.dp))
                Text(text = "는 무엇인가요?", style = titleStyle)
            }
            ColumnSpacer(height = 12.dp)
            Text(text = "이미 Connex의 회원인\n친구임을 나타내는 표시입니다.", style = contentStyle)
        }
    }
}

data class TempleAlbumData(
    val image: String,
    val name: String,
)