package com.ub.villtech.component

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.SizeResolver
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.ub.villtech.R
import com.ub.villtech.model.AdminInfo
import com.ub.villtech.model.ContentImageResponse
import com.ub.villtech.model.ContentItem
import com.ub.villtech.model.ContentVideoResponse
import com.ub.villtech.ui.theme.Dark
import com.ub.villtech.ui.theme.GreenMid
import com.ub.villtech.ui.theme.GreenMint
import com.ub.villtech.ui.theme.Typography
import com.ub.villtech.viewmodel.AdminHomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ProductVideoCard(
    modifier: Modifier = Modifier,
    contentWidth: Dp,
    contentData: ContentItem,
    padding: Dp = 4.dp
) {
    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(context)
        .setMediaSourceFactory(DefaultMediaSourceFactory(context).setLiveTargetOffsetMs(5000))
        .build()

    val mediaItem =
        MediaItem
            .Builder()
            .setUri(Uri.parse(contentData.media_url))
            .setLiveConfiguration(
                MediaItem
                    .LiveConfiguration
                    .Builder()
                    .setMaxPlaybackSpeed(1.02f)
                    .build()
            ).build()

    exoPlayer.setMediaItem(mediaItem)


    Card(
        modifier = modifier
            .clip(RoundedCornerShape(CornerSize(8.dp)))
            .padding(padding)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = Color.Black),
                onClick = {
                    /**GO TO DETAILED PAGE*/
                }
            ),
        elevation = 4.dp,
        shape = RoundedCornerShape(CornerSize(14.dp)),
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            /**Player section*/
            val playerHeight = (LocalConfiguration.current.screenHeightDp / 6).dp
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(CornerSize(14.dp)))
                    .background(color = Color.DarkGray)
            ) {
                AndroidView(
                    modifier = Modifier
                        .width(contentWidth)
                        .height(playerHeight)
                        .clip(RoundedCornerShape(CornerSize(14.dp))),
                    factory = {
                        StyledPlayerView(it).apply {
                            player = exoPlayer
                        }
                    }
                )
            }

            /**Informations section*/
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = contentData.category,
                    style = Typography.body1,
                    color = GreenMid,
                    fontSize = 10.sp
                )
                Text(
                    text = contentData.title,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_comment),
                        contentDescription = "Comment",
                        tint = Color.Black
                    )
                    Text(
                        text = contentData.commentCount,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 8.sp,
                        color = Color.Black
                    )
                }
            }

        }
    }
}

@Composable
fun ProductPreviewCard(
    modifier: Modifier = Modifier,
    contentWidth: Dp,
    contentData: ContentItem,
    padding: Dp = 4.dp,
    onClick: () -> Unit
) {
    Card(modifier = modifier
        .clip(RoundedCornerShape(CornerSize(8.dp)))
        .padding(padding)
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = true, color = Color.Black),
            onClick = {
                onClick()
            }
        ),
        elevation = 4.dp,
        shape = RoundedCornerShape(CornerSize(14.dp)),
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            /**Image section*/
            AsyncImage(
                modifier = Modifier
                    .width(contentWidth)
                    .clip(RoundedCornerShape(CornerSize(14.dp))),
                model = ImageRequest.Builder(LocalContext.current)
                    .placeholder(R.drawable.ic_no_picture)
                    .data(contentData.thumbnail_url)
                    .build(),
                contentDescription = "Image",
                contentScale = ContentScale.FillWidth
            )

            /**Informations section*/
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = contentData.category,
                    style = Typography.body1,
                    color = GreenMid,
                    fontSize = 10.sp
                )
                Text(
                    text = contentData.title,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_comment),
                        contentDescription = "Comment",
                        tint = Color.Black
                    )
                    Text(
                        text = contentData.commentCount,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 8.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun PreviewCardImage(
    modifier: Modifier = Modifier,
    contentWidth: Dp,
    contentData: ContentImageResponse,
    onClick: () -> Unit,
    padding: Dp = 4.dp
) {
    Card(modifier = modifier
        .clip(RoundedCornerShape(CornerSize(8.dp)))
        .padding(padding)
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = true, color = Color.Black),
            onClick = {
                onClick()
            }
        ),
        elevation = 4.dp,
        shape = RoundedCornerShape(CornerSize(14.dp)),
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            /**Image section*/
            AsyncImage(
                modifier = Modifier
                    .width(contentWidth)
                    .clip(RoundedCornerShape(CornerSize(14.dp))),
                model = ImageRequest.Builder(LocalContext.current)
                    .placeholder(R.drawable.ic_no_picture)
                    .data(contentData.thumbnail_url)
                    .build(),
                contentDescription = "Image",
                contentScale = ContentScale.FillWidth
            )

            /**Informations section*/
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = contentData.category,
                    style = Typography.body1,
                    color = GreenMid,
                    fontSize = 10.sp
                )
                Text(
                    text = contentData.title,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_comment),
                        contentDescription = "Comment",
                        tint = Color.Black
                    )
                    Text(
                        text = contentData.commentCount,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 8.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun PreviewCardVideo(
    modifier: Modifier = Modifier,
    contentWidth: Dp,
    contentData: ContentVideoResponse,
    onClick: () -> Unit,
    padding: Dp = 4.dp
) {
    Card(modifier = modifier
        .clip(RoundedCornerShape(CornerSize(8.dp)))
        .padding(padding)
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = true, color = Color.Black),
            onClick = {
                onClick()
            }
        ),
        elevation = 4.dp,
        shape = RoundedCornerShape(CornerSize(14.dp)),
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            /**Image section*/
            AsyncImage(
                modifier = Modifier
                    .width(contentWidth)
                    .clip(RoundedCornerShape(CornerSize(14.dp))),
                model = ImageRequest.Builder(LocalContext.current)
                    .placeholder(R.drawable.ic_no_picture)
                    .data(contentData.thumbnail_url)
                    .build(),
                contentDescription = "Image",
                contentScale = ContentScale.FillWidth
            )

            /**Informations section*/
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = contentData.category,
                    style = Typography.body1,
                    color = GreenMid,
                    fontSize = 10.sp
                )
                Text(
                    text = contentData.title,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_comment),
                        contentDescription = "Comment",
                        tint = Color.Black
                    )
                    Text(
                        text = contentData.commentCount,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 8.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun AdminCard(
    modifier: Modifier = Modifier,
    contentWidth: Dp,
    adminInfo: AdminInfo
) {
    val cardHeight = LocalConfiguration.current.screenHeightDp / 4
    val imgHeight = cardHeight * 5 / 6
    val imageWithProfilePicture: @Composable () -> Unit = {
        AsyncImage(
            modifier = Modifier
                .width(contentWidth)
                .height(imgHeight.dp),
            contentScale = ContentScale.Crop,
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(adminInfo.image_url)
                .build(),
            contentDescription = "Picture"
        )
    }
    val imageWithNoPicture: @Composable () -> Unit = {
        AsyncImage(
            modifier = Modifier
                .width(contentWidth)
                .height(imgHeight.dp),
            contentScale = ContentScale.Crop,
            model = R.drawable.ic_no_picture,
            contentDescription = "Picture"
        )
    }

    Card(
        modifier = modifier
            .width(contentWidth)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = Dark),
                onClick = {}
            ),
        border = BorderStroke(width = 1.dp, color = GreenMid),
        shape = RoundedCornerShape(CornerSize(14.dp))
    ) {
        Column {
            /**Profile Picture*/
            if (adminInfo.image_url == "null") imageWithNoPicture()
            else imageWithProfilePicture()

            /**Name*/
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                text = adminInfo.name,
                color = Color.Black,
                fontSize = 10.sp,
                fontFamily = FontFamily(
                    Font(R.font.poppins_medium)
                )
            )
        }
    }
}

@Composable
fun AdminPostingMediaPreviewVideo(uri: Uri, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val videoPlayerHeight = LocalConfiguration.current.screenHeightDp / 2
    val adminHomeViewModel = getViewModel<AdminHomeViewModel>()
    val vidPagerHeight = LocalConfiguration.current.screenHeightDp / 2

    val exoPlayer = ExoPlayer.Builder(context)
        .setMediaSourceFactory(
            DefaultMediaSourceFactory(context).setLiveTargetOffsetMs(
                5000
            )
        )
        .build()

    val mediaItem =
        MediaItem
            .Builder()
            .setUri(uri)
            .setLiveConfiguration(
                MediaItem
                    .LiveConfiguration
                    .Builder()
                    .setMaxPlaybackSpeed(1.02f)
                    .build()
            ).build()

    exoPlayer.setMediaItem(mediaItem)

    //Player
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        //Content
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(vidPagerHeight.dp)
                .clip(RoundedCornerShape(CornerSize(14.dp)))
                .background(color = Color.DarkGray),
            contentAlignment = Alignment.TopEnd
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(videoPlayerHeight.dp)
                    .clip(RoundedCornerShape(CornerSize(14.dp)))
                    .background(color = Color.DarkGray), contentAlignment = Alignment.Center
            )
            {
                AndroidView(
                    modifier = Modifier
                        .height(videoPlayerHeight.dp)
                        .clip(RoundedCornerShape(CornerSize(14.dp))),
                    factory = {
                        StyledPlayerView(it).apply {
                            player = exoPlayer
                        }
                    }
                )
            }

            Box(
                modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White),
                    onClick = {
                        adminHomeViewModel.videoUri = null
                        if (adminHomeViewModel.videoUri != null) {
                            adminHomeViewModel.uploadVideoEnabled = false
                            adminHomeViewModel.uploadPhotoEnabled = false
                            adminHomeViewModel.uploadPhotoColor = Color.Gray
                            adminHomeViewModel.uploadVideoColor = Color.Gray
                        } else {
                            adminHomeViewModel.uploadVideoEnabled = true
                            adminHomeViewModel.uploadPhotoEnabled = true
                            adminHomeViewModel.uploadPhotoColor = Dark
                            adminHomeViewModel.uploadVideoColor = Dark
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Black
                    )
                }
            }
        }

        //Thumbnail
        if (adminHomeViewModel.mp4Thumbnail != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "THUMBNAIL:",
                    style = Typography.body1
                )
                AsyncImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(CornerSize(14.dp)))
                        .padding(start = 16.dp, end = 16.dp),
                    model = adminHomeViewModel.mp4Thumbnail,
                    contentDescription = "THUMBNAIL"
                )
            }
        }

        //Pick thumbnail
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = {
                if (it != null) adminHomeViewModel.mp4Thumbnail = it
            })
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(CornerSize(8.dp)))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true, color = Dark),
                    onClick = {
                        launcher.launch("image/jpeg")
                    }
                )
                .background(GreenMint),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "TAMBAHKAN THUMBNAIL",
                fontFamily = FontFamily((Font(R.font.poppins_semibold))),
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun AdminPostingMediaPreviewImage(uri: Uri, modifier: Modifier = Modifier) {
    val imgPagerHeight = LocalConfiguration.current.screenHeightDp / 2
    val adminHomeViewModel = getViewModel<AdminHomeViewModel>()
    val scrWidth = LocalConfiguration.current.screenWidthDp - 32

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(CornerSize(14.dp)))
            .background(Color.Gray)
            .height(imgPagerHeight.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(imgPagerHeight.dp)
                .clip(RoundedCornerShape(CornerSize(14.dp)))
                .background(color = Color.DarkGray), contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier.width(scrWidth.dp),
                contentScale = ContentScale.Fit,
                model = uri,
                contentDescription = "Image"
            )
        }
        Box(modifier = Modifier.padding(8.dp), contentAlignment = Alignment.Center) {
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White),
                onClick = {
                    adminHomeViewModel.photoUri.remove(uri)
                    if (adminHomeViewModel.photoUri.isNotEmpty()) {
                        adminHomeViewModel.uploadVideoEnabled = false
                        adminHomeViewModel.uploadVideoColor = Color.Gray
                    } else {
                        adminHomeViewModel.uploadVideoEnabled = true
                        adminHomeViewModel.uploadPhotoEnabled = true
                        adminHomeViewModel.uploadPhotoColor = Dark
                        adminHomeViewModel.uploadVideoColor = Dark
                    }
                }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun DetailPostVideo(uri: Uri, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val videoPlayerHeight = LocalConfiguration.current.screenWidthDp / 16 * 9

    val exoPlayer = ExoPlayer.Builder(context)
        .setMediaSourceFactory(
            DefaultMediaSourceFactory(context).setLiveTargetOffsetMs(
                5000
            )
        )
        .build()

    val mediaItem =
        MediaItem
            .Builder()
            .setUri(uri)
            .setLiveConfiguration(
                MediaItem
                    .LiveConfiguration
                    .Builder()
                    .setMaxPlaybackSpeed(1.02f)
                    .build()
            ).build()

    exoPlayer.setMediaItem(mediaItem)

    //Content
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(videoPlayerHeight.dp)
            .clip(RoundedCornerShape(CornerSize(14.dp)))
            .background(color = Color.Black), contentAlignment = Alignment.Center
    )
    {
        AndroidView(
            modifier = Modifier
                .height(videoPlayerHeight.dp)
                .clip(RoundedCornerShape(CornerSize(14.dp))),
            factory = {
                StyledPlayerView(it).apply {
                    player = exoPlayer
                }
            }
        )
    }
}