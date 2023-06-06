@file:Suppress("NOTHING_TO_INLINE")

package com.bagadesh.ui

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Created by bagadesh on 07/06/23.
 */

@Composable
fun Dp.HeightSpacer(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(this)
    )
}
@Composable
fun Dp.SizeSpacer(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .size(this)
    )
}

@Composable
inline fun Dp.toPixel(): Float {
    val density = LocalDensity.current
    return remember(density) {
        with(density) {
            toPx()
        }
    }
}

inline fun Modifier.condition(condition: Boolean, action: () -> Modifier): Modifier {
    return if (condition) {
        this then action()
    } else {
        this
    }
}

inline fun Modifier.ifElse(
    condition: Boolean,
    failure: Modifier.() -> Modifier = { this },
    success: Modifier.() -> Modifier
): Modifier {
    return if (condition) {
        success()
    } else {
        failure()
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun Modifier.swipeToAction(drag: Int = 6, targetState: () -> Float): Modifier = composed {
    val transitionState = remember { MutableTransitionState(false) }
    val transition = updateTransition(transitionState, "offsetUpdateTransition")
    val offsetTransition by transition.animateFloat(
        label = "offsetTransition",
        transitionSpec = { tween(durationMillis = 500) },
        targetValueByState = { if (it) -targetState() else 0f },
    )
    offset {
        IntOffset(x = offsetTransition.roundToInt(), y = 0)
    }.pointerInput(Unit) {
        detectHorizontalDragGestures { _, dragAmount ->
            when {
                dragAmount > drag -> {
                    // Swipe Left to Right increase dragAmount
                    transitionState.targetState = false
                }

                dragAmount < -drag -> {
                    // Swipe Right to Left decreases dragAmount
                    transitionState.targetState = true
                }
            }
        }
    }
}

fun Modifier.countClickable(
    count: Int = 5,
    maxDifference: Int = 3000,
    onClick: () -> Unit,
): Modifier = composed {
    val clickFlow = remember { MutableSharedFlow<Timed>() }
    LaunchedEffect(key1 = Unit, block = {
        clickFlow.countBuffer(count).takeWhile {
            val difference = it.last().time - it.first().time
            difference < maxDifference
        }.collectLatest {
            onClick()
        }
    })
    val scope = rememberCoroutineScope()
    noRippleClickable {
        scope.launch {
            clickFlow.emit(Timed(System.currentTimeMillis()))
        }
    }
}

fun<T> Flow<T>.countBuffer(count: Int): Flow<List<T>> {
    return flow {
        var consumed = 0
        val list = mutableListOf<T>()
        collect {
            if (++consumed == count) {
                consumed = 0
                val copyList = list.toList()
                list.clear()
                emit(copyList)
            } else {
                list.add(it)
            }
        }
    }
}

data class Timed(val time: Long)

fun Modifier.gradiantBackground(): Modifier {
    return this then Modifier.background(
        brush = Brush.linearGradient(
            listOf(
                Color(0xFF303030),
                Color.Black
            ),
            start = Offset(Float.POSITIVE_INFINITY, 0f),
            end = Offset(0f, Float.POSITIVE_INFINITY),
        )
    )
}