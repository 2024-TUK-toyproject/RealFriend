package com.example.connex.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.connex.ui.theme.Body3SemiBold
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.White
import com.example.domain.entity.user.Friend

class FriendSelectedState(
    val friend: Friend,
    initialChecked: Boolean = false,
) {
    var isSelect by mutableStateOf(initialChecked)

    fun uncheck() {
        isSelect = false
    }

    fun checkOrUncheck() {
        if (isSelect) {
            uncheck()
            return
        }
        isSelect = true
    }
}

@Composable
fun LazyRowFriends(
    modifier: Modifier = Modifier,
    friends: List<FriendSelectedState>,
) {

    AnimatedVisibility(
        visible = friends.isNotEmpty(),
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent),
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(top = 3.dp, start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = friends) {
                SelectedFriendCard(friend = it) {
                    it.uncheck()
                }
            }
        }
    }
}

@Composable
fun SelectedFriendCard(friend: FriendSelectedState, onDelete: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier) {
            Card(shape = CircleShape, modifier = Modifier.size(47.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(model = friend.friend.profileImage),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Card(
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 3.dp, y = (-3).dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Gray500.copy(alpha = 0.5f),
                    contentColor = White
                ),
                onClick = { onDelete() }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxSize()
                )
            }
        }
        ColumnSpacer(height = 3.dp)
        Text(text = friend.friend.name, style = Body3SemiBold, color = Gray500)
    }
}