package com.ub.villtech.screen.user

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ub.villtech.R
import com.ub.villtech.component.GreenButton
import com.ub.villtech.ui.theme.BlueDark
import com.ub.villtech.ui.theme.Typography
import com.ub.villtech.viewmodel.RootViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun OnboardScreen() {
    val bannerHeight = (LocalConfiguration.current.screenHeightDp) / 3

    Column(modifier = Modifier.fillMaxSize()) {
        /**Banner*/
        AsyncImage(
            modifier = Modifier.height(bannerHeight.dp),
            model = R.drawable.ic_onboard_banner,
            contentDescription = "Banner",
            contentScale = ContentScale.Crop
        )

        /**Content*/
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            /**Text*/
            Column {
                //Title
                Text(
                    text = "Mahasiswa Pintar untuk Desa Pintar!",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        fontSize = 40.sp,
                        color = Color.Black
                    )
                )

                //Description
                Text(
                    text = "Desa pintar yang sudah mendapat fasilitas, pelatihan dan pendampingan untuk melakukan transformasi desa digital. Yuk jelajahi!",
                    style = Typography.body1,
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }

            /**Button section*/
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GreenButton(onClick = { /**GO TO HOME*/ }, text = "Masuk sebagai Pengunjung")

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Text(text = "Masuk sebagai ", color = BlueDark, style = Typography.body2)
                    Text(
                        modifier = Modifier.clickable(
                            onClick = { /**GO TO ADMIN LOGIN PAGE*/ },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true, color = Color.Black)
                        ),
                        text = "Admin",
                        color = BlueDark,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}