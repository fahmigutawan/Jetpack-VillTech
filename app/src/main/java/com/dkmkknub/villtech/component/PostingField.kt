package com.dkmkknub.villtech.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ub.villtech.R
import com.dkmkknub.villtech.rootViewModel
import com.dkmkknub.villtech.ui.theme.Dark
import com.dkmkknub.villtech.ui.theme.GreenMint
import com.dkmkknub.villtech.ui.theme.Light
import com.dkmkknub.villtech.ui.theme.Typography
import com.dkmkknub.villtech.utils.LoginChecker
import com.dkmkknub.villtech.viewmodel.AdminHomeViewModel
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@Composable
fun PostingTextField(
    modifier: Modifier = Modifier,
    titleValueState: MutableState<String>,
    descriptionValueState: MutableState<String>
) {
    val viewModel = getViewModel<AdminHomeViewModel>()
    val context = LocalContext.current
    val loginChecker = get<LoginChecker>()

    val vidPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            if (it != null) {
                if (viewModel.isVideoIsFit(context, it)) {
                    viewModel.videoUri = it
                    if (viewModel.videoUri != null) {
                        viewModel.uploadVideoEnabled = false
                        viewModel.uploadPhotoEnabled = false
                        viewModel.uploadPhotoColor = Color.Gray
                        viewModel.uploadVideoColor = Color.Gray
                    } else {
                        viewModel.uploadVideoEnabled = true
                        viewModel.uploadPhotoEnabled = true
                        viewModel.uploadPhotoColor = Dark
                        viewModel.uploadVideoColor = Dark
                    }
                } else {
                    rootViewModel.showSnackbar("Gagal. Pastikan Ukuran File Di bawah 250MB")
                }
            }
        }
    )

    val imgPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = {
            it.forEach { uri ->
                viewModel.photoUri.add(uri)
            }
            if (viewModel.photoUri.isNotEmpty()) {
                viewModel.uploadVideoEnabled = false
                viewModel.uploadVideoColor = Color.Gray
            } else {
                viewModel.uploadVideoEnabled = true
                viewModel.uploadPhotoEnabled = true
                viewModel.uploadPhotoColor = Dark
                viewModel.uploadVideoColor = Dark
            }
        })

    //Composable
    val profilePictureWidth = LocalConfiguration.current.screenWidthDp / 7
    val imageWithProfilePicture: @Composable () -> Unit = {
        AsyncImage(
            modifier = Modifier
                .size(profilePictureWidth.dp)
                .clip(CircleShape),
            model = loginChecker.adminInfo.image_url,
            contentDescription = "PROFILE PICTURE"
        )
    }
    val imageWithNoPicture: @Composable () -> Unit = {
        AsyncImage(
            modifier = Modifier
                .size(profilePictureWidth.dp)
                .clip(CircleShape),
            model = R.drawable.ic_no_picture,
            contentDescription = "PROFILE PICTURE"
        )
    }

    /**CONTENT*/
    LazyColumn(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        /**PREVIEW*/
        item {
            //IF VIDEO
            if (viewModel.videoUri != null)
                AdminPostingMediaPreviewVideo(
                    uri = viewModel.videoUri!!,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            else if (viewModel.photoUri.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    LazyRow(horizontalArrangement = Arrangement.Center) {
                        items(viewModel.photoUri) { uri ->
                            AdminPostingMediaPreviewImage(
                                modifier = Modifier.padding(end = 8.dp),
                                uri = uri
                            )
                        }
                    }
                }
            } else {
                /**DO SOMETHING WHEN NO MEDIA PICKED*/
                /**DO SOMETHING WHEN NO MEDIA PICKED*/
            }
        }

        /**TITLE*/
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                //Profile picture
                if (loginChecker.adminInfo.image_url != "null") imageWithProfilePicture()
                else imageWithNoPicture()

                //Space 16dp
                Spacer(modifier = Modifier.width(16.dp))

                //Title field
                OutlinedTextField(
                    value = titleValueState.value,
                    onValueChange = { titleValueState.value = it },
                    shape = RoundedCornerShape(CornerSize(14.dp)),
                    colors = TextFieldDefaults
                        .outlinedTextFieldColors(
                            textColor = Dark,
                            backgroundColor = Light,
                            focusedBorderColor = Dark,
                            unfocusedBorderColor = Dark
                        ),
                    placeholder = { Text(text = "Tulis judul di sini...", color = Color.Gray) }
                )
            }
        }

        /**DESCRIPTION*/
        item {
            Card(
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(CornerSize(14.dp)),
                border = BorderStroke(width = 1.dp, color = Color.DarkGray)
            ) {
                Column(
                    modifier = modifier
                        .padding(8.dp)
                ) {
                    /**Select media buttons*/
                    /**Select media buttons*/

                    /**Select media buttons*/
                    /**Select media buttons*/
                    /**Select media buttons*/
                    /**Select media buttons*/
                    /**Select media buttons*/

                    /**Select media buttons*/
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        //Image
                        IconButton(
                            onClick = { imgPicker.launch("image/jpeg") },
                            enabled = viewModel.uploadPhotoEnabled
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_image),
                                contentDescription = "Photo",
                                tint = viewModel.uploadPhotoColor
                            )
                        }

                        //Video
                        IconButton(
                            onClick = { vidPicker.launch("video/mp4") },
                            enabled = viewModel.uploadVideoEnabled
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_video),
                                contentDescription = "Video",
                                tint = viewModel.uploadVideoColor
                            )
                        }
                    }

                    /**TextField*/

                    /**TextField*/

                    /**TextField*/

                    /**TextField*/

                    /**TextField*/

                    /**TextField*/

                    /**TextField*/

                    /**TextField*/
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        value = descriptionValueState.value,
                        onValueChange = { descriptionValueState.value = it },
                        decorationBox = { innerTextField ->
                            if (descriptionValueState.value == "") {
                                Text(text = "Tulis deskripsi di sini...", color = Color.Gray)
                            }
                            innerTextField()
                        })
                }
            }
        }

        /**FILE CONSTRAINT*/
        item {
            Column(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "* Hanya gunakan file .JPEG atau .MP4",
                    color = Color.Black,
                    style = Typography.body2,
                    textAlign = TextAlign.Start
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "* Untuk .MP4 maksimal sebesar 250 MB",
                    color = Color.Black,
                    style = Typography.body2,
                    textAlign = TextAlign.Start
                )
            }
        }

        /**CATEGORY*/
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Start,
                text = "Pilih Kategori",
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                color = Dark
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = viewModel.isCategoryInfografisSelected,
                    onClick = {
                        if (!viewModel.category.contains("infografis")) {
                            viewModel.category += "infografis,"
                        }
                        viewModel.isCategoryInfografisSelected =
                            !viewModel.isCategoryInfografisSelected
                    }
                )
                Text(text = "Infografis", style = Typography.body2)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = viewModel.isCategoryVideoSelected,
                    onClick = {
                        if (!viewModel.category.contains("video")) {
                            viewModel.category += "video,"
                        }
                        viewModel.isCategoryVideoSelected =
                            !viewModel.isCategoryVideoSelected
                    }
                )
                Text(text = "Video", style = Typography.body2)
            }
        }

        /**UPLOAD PROGRESS*/
        item {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(5.dp),
                progress = viewModel.uploadProgress.toFloat(),
                color = GreenMint
            )
        }

        /**BUTTON*/
        item {
            val loginChecker = get<LoginChecker>()
            Spacer(modifier = Modifier.height(8.dp))
            GreenButton(
                onClick = { uploadContent(viewModel, loginChecker) },
                text = "Upload",
                enabled = viewModel.isUploadButtonEnabled
            )
        }
    }
}

private fun uploadContent(viewModel: AdminHomeViewModel, loginChecker: LoginChecker) {
    val isVideoUriNotNull = viewModel.videoUri != null
    val isPhotoUriNotEmpty = viewModel.photoUri.isNotEmpty()
    val onFailed: () -> Unit = { rootViewModel.showSnackbar("Terjadi kesalahan coba lagi nanti") }
    val onMediaJpegUploaded: (Int) -> Unit = { contentId ->
        viewModel.uploadPostWithImage(
            contentId = contentId,
            loginChecker = loginChecker,
            onSuccess = {
                rootViewModel.showSnackbar("Postingan berhasil diupload")
                viewModel.resetPostData()
                viewModel.updateCount(contentId)
            },
            onFailed = onFailed
        )
    }
    val onMediaVideoUploaded: (Int) -> Unit = { contentId ->
        viewModel.uploadPostWithVideo(
            contentId = contentId,
            loginChecker = loginChecker,
            onSuccess = {
                rootViewModel.showSnackbar("Postingan berhasil diupload")
                viewModel.resetPostData()
                viewModel.updateCount(contentId)
            },
            onFailed = onFailed
        )
    }
    val onCountRetrieved: (Int) -> Unit = { contentId ->
        if(viewModel.isDataNotFulfilled()) {
            rootViewModel.showSnackbar("Harap isi semua data!")
        }else{
            if (isVideoUriNotNull) {
                viewModel.uploadMp4(
                    contentId = contentId,
                    onSuccess = { onMediaVideoUploaded(contentId) },
                    onFailed = onFailed
                )
            }
            else if (isPhotoUriNotEmpty) {
                viewModel.uploadJpeg(
                    contentId = contentId,
                    onSuccess = { onMediaJpegUploaded(contentId) },
                    onFailed = onFailed
                )
            }
            else {
                rootViewModel.showSnackbar("Harap pilih Gambar atau Video terlebih dahulu")
            }
        }

    }

    /**UPLOAD LOGIC START HERE*/
    viewModel
        .getContentCount()
        .addOnSuccessListener {
            val count = if(it.value.toString()=="null") 0 else Integer.parseInt(it.value.toString())
            onCountRetrieved(count + 1)
        }.addOnFailureListener {
            onFailed()
        }
}