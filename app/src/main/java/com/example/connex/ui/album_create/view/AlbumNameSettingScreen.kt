package com.example.connex.ui.album_create.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.connex.ui.album_create.AlbumCreatingViewModel
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.component.UserNameTextField
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Head2Semibold
import com.example.connex.utils.Constants

@Composable
fun AlbumNameSettingScreen(albumCreatingViewModel: AlbumCreatingViewModel) {

    val focusManager = LocalFocusManager.current

    val albumNameUiState by albumCreatingViewModel.albumName.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 51.dp)
    ) {
        Text(
            text = "공유 앨범 이름을 설정해 주세요.",
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
        ColumnSpacer(height = 28.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            BackgroundCard(image = Constants.DEFAULT_PROFILE)
            RowSpacer(width = 18.dp)
            UserNameTextField(
                modifier = Modifier.width(200.dp),
                text = albumNameUiState,
                updateText = {
                    if (it.length <= 10) {
                        albumCreatingViewModel.updateAlbumName(it)
                    }
                }
            ) {
                focusManager.clearFocus()
            }
        }
    }
}

@Composable
fun BackgroundCard(image: String) {
    Card(
        Modifier
            .size(80.dp),
        shape = RoundedCornerShape((7.5).dp),
        colors = CardDefaults.cardColors(containerColor = Gray100),
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}