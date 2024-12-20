package com.example.connex.ui.album_create.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.connex.ui.album_create.AlbumCreatingViewModel
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.FriendSelectedState
import com.example.connex.ui.component.LazyRowFriends
import com.example.connex.ui.component.SearchTextField
import com.example.connex.ui.home.view.ContactCard
import com.example.connex.ui.home.view.ContactCardCheckBox
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Head2Semibold
import com.example.domain.model.ApiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FriendSelectScreen(
    albumCreatingViewModel: AlbumCreatingViewModel = hiltViewModel(),
    showSnackBar: (String) -> Unit,
) {
    val friendSelectUiState by albumCreatingViewModel.friendSelectUiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        albumCreatingViewModel.fetchReadAllFriends { showSnackBar(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 51.dp)
    ) {
        Text(
            text = "함께할 친구를 \n선택해 주세요",
            style = Head2Semibold,
            color = Gray900,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        ColumnSpacer(height = 20.dp)


        LazyColumn(modifier = Modifier.fillMaxSize()) {
            if (friendSelectUiState.friends is ApiState.Success<List<FriendSelectedState>>) {
                val selectedFriends =
                    (friendSelectUiState.friends as ApiState.Success<List<FriendSelectedState>>).data.filter { it.isSelect }
                stickyHeader {
                    LazyRowFriends(modifier = Modifier.fillMaxWidth(), friends = selectedFriends)
                }
                item { ColumnSpacer(height = 24.dp) }
            }
            if (friendSelectUiState.friends is ApiState.Loading) {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else if (friendSelectUiState.friends is ApiState.Success<List<FriendSelectedState>>) {
                item {
                    SearchTextField(
                        modifier = Modifier
                            .height(40.dp)
                            .padding(horizontal = 24.dp),
                        padding = Pair(20.dp, 12.dp),
                        backgroundColor = Color(0xFFF2F4F8),
                        text = friendSelectUiState.search,
                        placeholder = "친구의 이름이나 연락처로 검색해 보세요.",
                        updateText = {
                            albumCreatingViewModel.updateSearch(it)
                            albumCreatingViewModel.realTimeSearching(it)
                        }) {
                        albumCreatingViewModel.realTimeSearching(friendSelectUiState.search)
                        focusManager.clearFocus()
                    }
                }
                item { ColumnSpacer(height = 12.dp) }
                items(
                    items = (friendSelectUiState.friends as ApiState.Success<List<FriendSelectedState>>).data,
                    key = { it.friend.userId }) {
                    ContactCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                albumCreatingViewModel.selectOrUnSelectFriend(
                                    it.friend.userId,
                                    !it.isSelect
                                )
                            }
                            .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp),
                        size = 56.dp,
                        image = it.friend.profileImage,
                        name = it.friend.name,
                        phone = it.friend.phone
                    ) {
                        ContactCardCheckBox(isCheck = it.isSelect)
                    }
                }
            }

        }
    }
}
