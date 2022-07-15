package com.ub.villtech.screen

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ub.villtech.R
import com.ub.villtech.ui.theme.BlueDark
import com.ub.villtech.ui.theme.GreenMid
import com.ub.villtech.ui.theme.Light
import com.ub.villtech.ui.theme.Typography

@Composable
fun AboutScreen() {
    LazyColumn(modifier = Modifier.padding(start = 32.dp, end = 32.dp)) {
        /**Banner*/
        item {
            val imgHeight = LocalConfiguration.current.screenHeightDp / 2.5
            Box(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier.height(imgHeight.dp),
                    model = R.drawable.ic_about_banner,
                    contentDescription = "Banner",
                    contentScale = ContentScale.FillHeight
                )
            }
        }

        /**Title*/
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Integrasi Aplikasi", style = Typography.h2, color = BlueDark)
        }

        /**Caption*/
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Integrasi aplikasi dan data merupakan salah satu kunci utama pekerjaan yang efektif dan efisien. Berbagai informasi tentang desa Sidomulyo telah terintegrasi dengan baik serta juga dapat terintegrasi dengan aplikasi luar sebagai pendukung kemajuan desa Sidomulyo",
                style = Typography.body1,
                color = Color.Gray
            )
        }

        /**Checklist*/
        item {
            val checklist: @Composable (String) -> Unit = { text ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = "Circle",
                            tint = GreenMid
                        )
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Check",
                            tint = Light
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = text, style = Typography.body1, color = BlueDark)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            checklist("Integrasi data dari berbagai unit/ kepala desa/ cara, dll")
            checklist("Integrasi berbagai informasi mengenai Desa Sidomulyo")
            checklist("Integrasi dengan masyarakat setempat")
        }
        
        item { 
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}