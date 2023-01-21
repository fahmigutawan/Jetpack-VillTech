package com.dkmkknub.villtech.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ub.villtech.R

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_reguler)),
        fontSize = 40.sp,
        fontWeight = FontWeight(700)
    ),
    h2 = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_reguler)),
        fontSize = 24.sp,
        fontWeight = FontWeight(600)
    ),
    body1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_reguler)),
        fontSize = 14.sp,
        fontWeight = FontWeight(200)
    ),
    body2 = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_reguler)),
        fontSize = 12.sp,
        fontWeight = FontWeight(200)
    ),
    button = TextStyle(
        fontFamily = FontFamily(Font(R.font.poppins_medium)),
        fontSize = 14.sp
    )
)