package com.dkmkknub.villtech.utils

import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionablePage(
    permissionState: PermissionState,
    contentPermissionDeniedFirstTime: @Composable () -> Unit,
    contentPermissionDeniedForever: @Composable () -> Unit,
    contentPermissionNotRequested: @Composable () -> Unit,
    successContent: @Composable () -> Unit
) {

}