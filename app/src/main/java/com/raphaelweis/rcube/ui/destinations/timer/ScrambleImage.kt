package com.raphaelweis.rcube.ui.destinations.timer

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import coil3.util.DebugLogger
import com.raphaelweis.rcube.R
import com.raphaelweis.rcube.ui.AppViewModelProvider
import java.nio.ByteBuffer

val BASE_SVG_SIZE = 64.dp

@Composable
fun ScrambleImage(
    viewModel: TimerViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current

    val model = ImageRequest.Builder(context)
        .data(ByteBuffer.wrap(viewModel.currentScrambleSvg.value.toByteArray()))
        .decoderFactory(SvgDecoder.Factory()).build()

    val imageLoader = LocalContext.current.imageLoader.newBuilder()
        .logger(DebugLogger())
        .build()

    var isZoomed by remember { mutableStateOf(false) }

    var maxSize by remember { mutableStateOf(BASE_SVG_SIZE) }

    val animatedSize by animateDpAsState(
        targetValue = if (isZoomed) maxSize else BASE_SVG_SIZE,
        animationSpec = tween(durationMillis = 0),
        label = "ImageSizeAnimation"
    )

    val density = LocalContext.current.resources.displayMetrics.density


    AsyncImage(
        model = model,
        imageLoader = imageLoader,
        contentDescription = stringResource(R.string.svg_image_of_scramble),
        modifier = Modifier
            .onGloballyPositioned { layoutCoordinates ->
                val parentSize = layoutCoordinates.parentLayoutCoordinates?.size
                if (parentSize != null) {
                    maxSize =
                        Dp(parentSize.width.coerceAtMost(parentSize.width).toFloat() / density)
                }
            }
            .size(animatedSize)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                isZoomed = !isZoomed
            }
    )
}