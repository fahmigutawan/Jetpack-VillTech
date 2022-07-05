package com.ub.villtech.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ub.villtech.ui.theme.BlueDark
import com.ub.villtech.ui.theme.GreenLight
import com.ub.villtech.ui.theme.Typography

@Composable
fun GreenSnackbar(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState
) {
    SnackbarHost(hostState = hostState) {
        Snackbar(
            modifier = Modifier.padding(32.dp),
            shape = RoundedCornerShape(CornerSize(14.dp)),
            backgroundColor = GreenLight,
            elevation = 4.dp
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