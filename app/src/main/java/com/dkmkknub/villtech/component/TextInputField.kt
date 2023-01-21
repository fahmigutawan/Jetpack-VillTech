package com.dkmkknub.villtech.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.dkmkknub.villtech.ui.theme.*

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
            IconButton(onClick = { isExposed = !isExposed }) {
                Icon(
                    imageVector = if (isExposed) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = "Password State",
                    tint = BlueDark
                )
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