package com.dkmkknub.villtech.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dkmkknub.villtech.ui.theme.BlueDark
import com.dkmkknub.villtech.ui.theme.GreenLight
import com.dkmkknub.villtech.ui.theme.GreenMid
import com.dkmkknub.villtech.ui.theme.Typography

@Composable
fun GreenSnackbar(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState,
    onClickAction: (() -> Unit)? = null,
    actionText: String = "Click here"
) {
    SnackbarHost(hostState = hostState) {
        Snackbar(
            modifier = Modifier.padding(32.dp),
            shape = RoundedCornerShape(CornerSize(14.dp)),
            backgroundColor = GreenLight,
            elevation = 4.dp,
            action = {
                if (onClickAction != null) {
                    Text(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true, color = GreenMid),
                            onClick = onClickAction
                        ),
                        text = actionText,
                        color = BlueDark
                    )
                }
            }
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it.message,
                style = Typography.body2,
                color = BlueDark,
                textAlign = TextAlign.Center
            )
        }
    }
}