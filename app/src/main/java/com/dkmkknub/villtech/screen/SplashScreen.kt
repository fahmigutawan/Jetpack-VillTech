package com.dkmkknub.villtech.screen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import com.ub.villtech.R
import com.dkmkknub.villtech.navigation.NavigationRoute
import com.dkmkknub.villtech.ui.theme.GreenMint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navigateToOnboardScreen:() -> Unit
) {
    val iconHeight = (LocalConfiguration.current.screenHeightDp) / 4

    LaunchedEffect(true){
        delay(2500)
        navigateToOnboardScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.height(iconHeight.dp),
                painter = painterResource(id = R.drawable.ic_logo_splash),
                contentDescription = "Logo",
                tint = Color.Unspecified
            )
            Text(
                text = "VillTech",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.racing_sans_one)),
                    color = GreenMint,
                    fontSize = 54.sp
                )
            )
        }
    }
}