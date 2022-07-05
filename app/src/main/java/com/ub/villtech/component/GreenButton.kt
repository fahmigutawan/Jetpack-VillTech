package com.ub.villtech.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ub.villtech.ui.theme.GreenMint
import com.ub.villtech.ui.theme.Typography

@Composable
fun GreenButton(
    onClick: () -> Unit,
    text: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(CornerSize(14.dp)))
            .background(color = GreenMint)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = Color.Black)
            ), contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp, start = 24.dp, end = 24.dp),
            text = text,
            style = Typography.button,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}