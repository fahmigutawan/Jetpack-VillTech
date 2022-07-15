package com.ub.villtech.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ub.villtech.R
import com.ub.villtech.rootViewModel
import com.ub.villtech.ui.theme.*
import com.ub.villtech.utils.LoginChecker
import com.ub.villtech.viewmodel.AdminHomeViewModel
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@Composable
fun TextInputField(
    modifier: Modifier = Modifier,
    placeHolderText: String = "",
    valueState: MutableState<String>,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(CornerSize(32.dp)),
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = Dark,
        placeholderColor = Color.Gray,
        textColor = Dark,
        backgroundColor = Light,
        focusedBorderColor = GreenMid,
        unfocusedBorderColor = BlueDark
    ),
    textStyle: TextStyle = Typography.body1,
    maxLines: Int = 1
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier,
        value = valueState.value,
        onValueChange = { valueState.value = it },
        shape = shape,
        colors = colors,
        textStyle = textStyle,
        maxLines = maxLines,
        leadingIcon = leadingContent,
        trailingIcon = trailingContent,
        placeholder = {
            Text(
                text = placeHolderText,
                style = Typography.body1,
                color = Color.Gray
            )
        },
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(true) })
    )
}

@Composable
fun SearchInputField(
    modifier: Modifier = Modifier,
    placeHolderText: String = "",
    valueState: MutableState<String>,
    onSearch: (String) -> Unit,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(CornerSize(32.dp)),
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = Dark,
        placeholderColor = Color.Gray,
        textColor = Dark,
        backgroundColor = Light,
        focusedBorderColor = GreenMid,
        unfocusedBorderColor = BlueDark
    ),
    textStyle: TextStyle = Typography.body1,
    maxLines: Int = 1
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier,
        value = valueState.value,
        onValueChange = {
            valueState.value = it
            onSearch(it)
        },
        shape = shape,
        colors = colors,
        textStyle = textStyle,
        maxLines = maxLines,
        leadingIcon = leadingContent,
        trailingIcon = trailingContent,
        placeholder = {
            Text(
                text = placeHolderText,
                style = Typography.body1,
                color = Color.Gray
            )
        },
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(true) })
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TextPasswordField(
    modifier: Modifier = Modifier,
    placeHolderText: String = "",
    valueState: MutableState<String>,
    leadingContent: @Composable (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(CornerSize(32.dp)),
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        placeholderColor = Color.Gray,
        textColor = Dark,
        backgroundColor = Light,
        focusedBorderColor = GreenMid,
        unfocusedBorderColor = BlueDark
    ),
    textStyle: TextStyle = Typography.body1,
    maxLines: Int = 1
) {
    val focusManager = LocalFocusManager.current
    var isExposed by remember { mutableStateOf(false) }
    val trailingIconId = remember { mutableStateOf(R.drawable.ic_password_view) }

    if (isExposed) {
        trailingIconId.value = R.drawable.ic_password_hide
    } else {
        trailingIconId.value = R.drawable.ic_password_view
    }

    OutlinedTextField(
        modifier = modifier,
        value = valueState.value,
        onValueChange = { valueState.value = it },
        shape = shape,
        colors = colors,
        textStyle = textStyle,
        maxLines = maxLines,
        leadingIcon = leadingContent,
        trailingIcon = {
            AnimatedContent(targetState = trailingIconId.value) { targetState ->
                IconButton(onClick = { isExposed = !isExposed }) {
                    Icon(
                        painter = painterResource(id = targetState),
                        contentDescription = "Password State",
                        tint = BlueDark
                    )
                }
            }
        },
        placeholder = {
            Text(
                text = placeHolderText,
                style = Typography.body1,
                color = Color.Gray
            )
        },
        visualTransformation = if (isExposed) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(true) })
    )
}