package com.dkmkknub.villtech.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.ub.villtech.R
import com.dkmkknub.villtech.component.GreenButton
import com.dkmkknub.villtech.component.TextInputField
import com.dkmkknub.villtech.component.TextPasswordField
import com.dkmkknub.villtech.navigation.NavigationRoute
import com.dkmkknub.villtech.ui.theme.BlueDark
import com.dkmkknub.villtech.ui.theme.Dark
import com.dkmkknub.villtech.ui.theme.Typography
import com.dkmkknub.villtech.utils.LoginChecker
import com.dkmkknub.villtech.viewmodel.AdminLoginViewModel
import com.dkmkknub.villtech.viewmodel.RootViewModel
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val adminLoginViewModel = getViewModel<AdminLoginViewModel>()
    val authInstance = get<FirebaseAuth>()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBanner()

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
            TextFieldSection(viewModel = adminLoginViewModel)
            ButtonSection(navController = navController, viewModel = adminLoginViewModel)
        }
    }
}

@Composable
private fun TopBanner() {
    val bannerHeight = (LocalConfiguration.current.screenHeightDp) / 3

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(bannerHeight.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            modifier = Modifier.height(bannerHeight.dp),
            model = R.drawable.ic_admin_login_banner,
            contentDescription = "Banner"
        )

        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Sebagai Admin",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    color = Color.Black,
                    fontSize = 32.sp,
                )
            )
            Text(
                text = "Masuk untuk admin aplikasi",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_reguler)),
                    color = Color.Black,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
private fun TextFieldSection(viewModel: AdminLoginViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        /**Email*/
        Text(text = "Email", style = Typography.body2, color = Dark)
        TextInputField(
            modifier = Modifier.fillMaxWidth(),
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_admin_login_email),
                    contentDescription = "Email",
                    tint = BlueDark
                )
            },
            valueState = viewModel.emailState,
            placeHolderText = "Masukkan email anda"
        )

        Spacer(modifier = Modifier.height(16.dp))

        /**Password*/
        Text(text = "Password", style = Typography.body2, color = Dark)
        TextPasswordField(
            modifier = Modifier.fillMaxWidth(),
            leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_admin_login_password),
                    contentDescription = "Password",
                    tint = BlueDark
                )
            },
            valueState = viewModel.passwordState,
            placeHolderText = "Masukkan sandi anda"
        )
    }
}

@Composable
private fun ButtonSection(navController: NavController, viewModel: AdminLoginViewModel) {
    val rootViewModel = getViewModel<RootViewModel>()
    val loginChecker = get<LoginChecker>()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GreenButton(
            onClick = {
                viewModel.loginWithEmailPassword(
                    viewModel.emailState.value,
                    viewModel.passwordState.value,
                    onSuccess = {
                        loginChecker.isLoginWithAdmin = true
                        loginChecker.loadAdminInfo()
                        navController.navigate(route = NavigationRoute.HomeScreen.name)
                        rootViewModel.showSnackbar("Berhasil")
                    },
                    onFailed = {
                        rootViewModel.showSnackbar("Gagal\nCoba lagi nanti")
                    }
                )
            },
            text = "Masuk sebagai Admin"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(text = "Masuk sebagai ", color = BlueDark, style = Typography.body2)
            Text(
                modifier = Modifier.clickable(
                    onClick = {
                        navController.popBackStack(
                            route = NavigationRoute.AdminLoginScreen.name,
                            inclusive = true
                        )
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true, color = Color.Black)
                ),
                text = "Pengunjung",
                color = BlueDark,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                fontSize = 12.sp
            )
        }
    }
}