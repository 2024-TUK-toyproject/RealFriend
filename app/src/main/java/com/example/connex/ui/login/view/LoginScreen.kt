package com.example.connex.ui.login.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.connex.ui.component.GenerateButton
import com.example.connex.ui.component.PhoneOutLineTextField
import com.example.connex.ui.login.LoginViewModel
import com.example.domain.model.MobileCarrier

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel) {
    var isPhone by remember {
        mutableStateOf(false)
    }

    val titleStyle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 32.sp
    )


    var text by remember {
        mutableStateOf("010")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(56.dp))
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = "휴대전화 번호를\n입력해 주세요.",
                style = titleStyle,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            if (isPhone) {
                LoginOutLineTextField(label = "통신사") {
                    MobileCarrierBox()
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            LoginOutLineTextField(label = "휴대전화 번호", isCompleted = isPhone) {
                PhoneOutLineTextField(
                    text = text,
                    updatePhone = {
                        if (it.length <= 11) {
                            text = it
                        }
                    },
                    enabled = !isPhone,
                    onDone = {}
                )
            }
        }
        GenerateButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "다음",
            enabled = if (text.length == 11) true else false
        ) {
            isPhone = true
        }
    }

}


@Composable
fun LoginOutLineTextField(label: String, isCompleted: Boolean = false, textField: @Composable () -> Unit) {

    val completedColor = Color(0xFFBFC1C6)
    val notCompletedColor = Color(0xFF333333)

    val labelStyle = TextStyle(
        color = if (isCompleted) completedColor else notCompletedColor,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 14.sp
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, style = labelStyle)
        Spacer(modifier = Modifier.height(8.dp))
        textField()
    }
}

@Composable
fun MobileCarrierBox(modifier: Modifier = Modifier) {
    val borderColor = Color(0xFF5076FD)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = (1.5).dp, color = borderColor),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.LightGray)
    ) {
        Text(text = "통신사 선택", modifier = Modifier.padding(horizontal = 14.dp, vertical = 16.dp))
    }
}