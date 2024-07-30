package com.example.connex.ui.login.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.component.MobileCarrierModalBottomSheet
import com.example.connex.ui.component.PhoneOutLineTextField
import com.example.connex.ui.login.LoginViewModel
import com.example.connex.ui.theme.DisableBackground
import com.example.connex.ui.theme.DisableBorder
import com.example.connex.ui.theme.MainBlue
import com.example.connex.ui.theme.Typography
import com.example.domain.model.MobileCarrier


@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel) {

    val loginPhoneAuthUiState by loginViewModel.loginPhoneAuthUiState.collectAsStateWithLifecycle()
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    var isPhone by remember {
        mutableStateOf(false)
    }

    var isMobileCarrier by remember {
        mutableStateOf(false)
    }

    var isMobileCarrierBottomSheetOpen by remember {
        mutableStateOf(false)
    }

    val buttonEnabled by remember {
        derivedStateOf {
            loginPhoneAuthUiState.phoneNumber.checkValidation() && loginPhoneAuthUiState.phoneNumber.mobileCarrier!= MobileCarrier.NOT && loginPhoneAuthUiState.verificationCode.isNotEmpty()
        }
    }





    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        if (isMobileCarrierBottomSheetOpen) {
            MobileCarrierModalBottomSheet(
                currentCarrier = loginPhoneAuthUiState.phoneNumber.mobileCarrier,
                onClose = { isMobileCarrierBottomSheetOpen = false }) {
                loginViewModel.updateMobileCarrier(it)
                isMobileCarrierBottomSheetOpen = false
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(84.dp - statusBarPadding))
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = "휴대전화 번호를\n입력해 주세요.",
                style = Typography.titleMedium,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            if (isMobileCarrier) {
                val verificationCodeStyle = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 12.sp,
                    color = MainBlue
                )
                LoginOutLineTextField(label = "인증번호") {
                    PhoneOutLineTextField(
                        text = loginPhoneAuthUiState.verificationCode,
                        updatePhone = {
                            if (it.length <= 6) {
                                loginViewModel.updateVerificationCode(it)
                            }
                        },
                        enabled = true,
                        onDone = {},
                        trailingIcon = { Text(text = "02:58", style = verificationCodeStyle) }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            if (isPhone) {
                LoginOutLineTextField(label = "통신사", isCompleted = isMobileCarrier) {
                    MobileCarrierBox(
                        isCompleted = isMobileCarrier,
                        mobileCarrier = loginPhoneAuthUiState.phoneNumber.mobileCarrier.getName()
                    ) {
                        isMobileCarrierBottomSheetOpen = true
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            LoginOutLineTextField(label = "휴대전화 번호", isCompleted = isPhone) {
                PhoneOutLineTextField(
                    text = loginPhoneAuthUiState.phoneNumber.number,
                    updatePhone = {
                        if (it.length <= 11) {
                            loginViewModel.updatePhone(it)
                        }
                    },
                    enabled = !isPhone,
                    onDone = {}
                )
            }
        }
        GeneralButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "다음",
            enabled = buttonEnabled
        ) {
            if (!isPhone) {
                loginViewModel.updateMobileCarrier(MobileCarrier.NOT)
                isPhone = true
            } else if (!isMobileCarrier) {
                isMobileCarrier = true
            } else {
                loginViewModel.updateVerificationCode("")
            }
        }
    }

}


@Composable
fun LoginOutLineTextField(
    label: String,
    isCompleted: Boolean = false,
    textField: @Composable () -> Unit
) {

    val completedColor = DisableBorder
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
fun MobileCarrierBox(
    modifier: Modifier = Modifier,
    isCompleted: Boolean = false,
    mobileCarrier: String,
    onClick: () -> Unit
) {
    val borderColor = if (isCompleted) DisableBorder else MainBlue
    val backgroundColor = if (isCompleted) DisableBackground else Color.White

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = (1.5).dp, color = borderColor),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = Color.LightGray
        )
    ) {
        Text(
            text = mobileCarrier,
            fontSize = 12.sp,
            lineHeight = 12.sp,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 16.dp)
        )
    }
}

