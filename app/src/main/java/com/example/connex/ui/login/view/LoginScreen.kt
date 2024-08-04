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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.connex.ui.component.ArrowBackIcon
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.component.MobileCarrierModalBottomSheet
import com.example.connex.ui.component.PhoneOutLineTextField
import com.example.connex.ui.component.util.addFocusCleaner
import com.example.connex.ui.login.LoginViewModel
import com.example.connex.ui.theme.Gray200
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Gray50
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.PrimaryBlue2
import com.example.connex.ui.theme.Typography
import com.example.connex.utils.Constants
import com.example.domain.model.login.MobileCarrier


@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel) {

    val focusManager = LocalFocusManager.current
    val loginPhoneAuthUiState by loginViewModel.loginUiState.collectAsStateWithLifecycle()
//    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

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
            .padding(horizontal = 24.dp, vertical = 40.dp)
            .imePadding()
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
            modifier = Modifier
                .fillMaxSize()
                .addFocusCleaner(focusManager)
                .verticalScroll(
                    rememberScrollState())
        ) {
//            Spacer(modifier = Modifier.height(84.dp - statusBarPadding))
            ArrowBackIcon {
                navController.popBackStack()
            }
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
                    color = PrimaryBlue2
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
                        onDone = { focusManager.clearFocus() },
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
                    onDone = { focusManager.clearFocus() }
                )
            }
        }
        GeneralButton(
            modifier = Modifier
                .height(55.dp)
                .align(Alignment.BottomCenter),
            text = "다음",
            enabled = buttonEnabled
        ) {
            if (!isPhone) {
                loginViewModel.updateMobileCarrier(MobileCarrier.NOT)
                isPhone = true
            } else if (!isMobileCarrier) {
                loginViewModel.fetchRequestCertificateCode{ isMobileCarrier = true }
            } else {
                loginViewModel.fetchCheckCertificateCode { navController.navigate(Constants.SIGNUP_PROFILE_INIT_ROUTE) }
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

    val completedColor = Gray400
    val notCompletedColor = Gray900

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
    val borderColor = if (isCompleted) Gray200 else PrimaryBlue2
    val backgroundColor = if (isCompleted) Gray50 else Color.White

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
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = mobileCarrier,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 14.dp, vertical = 16.dp)
            )
        }

    }
}

