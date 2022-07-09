package com.ub.villtech.component

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
import com.ub.villtech.model.ContentItem
import com.ub.villtech.ui.theme.Dark
import com.ub.villtech.ui.theme.GreenMid
import com.ub.villtech.ui.theme.Typography

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
    padding: Dp = 4.dp
) {
    Card(modifier = modifier
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
            /**Image section*/
            AsyncImage(
                modifier = Modifier
                    .width(contentWidth)
                    .clip(RoundedCornerShape(CornerSize(14.dp))),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(contentData.thumbnail_url)
                    .build(),
                contentDescription = "Image",
                contentScale = ContentScale.Crop
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