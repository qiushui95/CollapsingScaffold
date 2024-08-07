package nbe.someone.code.collapsing.toolbar

import androidx.compose.runtime.FloatState
import androidx.compose.runtime.IntState
import androidx.compose.runtime.asFloatState
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf

public class CollapsingToolbarState internal constructor(
    internal val scrollEndDeltaPxState: IntState,
) {
    internal val offsetYState = mutableFloatStateOf(0f)

    internal val toolbarHeightState = mutableIntStateOf(0)

    internal val toolbarYState = derivedStateOf {
        offsetYState.floatValue.coerceIn(maxScrollPxState.intValue * -1f, 0f)
    }.asFloatState()

    public val maxScrollPxState: IntState = derivedStateOf {
        val maxScroll = toolbarHeightState.intValue - scrollEndDeltaPxState.intValue

        maxScroll.coerceAtLeast(0)
    }.asIntState()

    public val scrollPxState: IntState = derivedStateOf {
        (offsetYState.floatValue.toInt() * -1).coerceIn(0, maxScrollPxState.intValue)
    }.asIntState()

    public val progressState: FloatState = derivedStateOf {
        val scrollPx = scrollPxState.intValue
        val maxScrollPx = maxScrollPxState.intValue

        if (maxScrollPx <= 0) 0f else scrollPx * 1f / maxScrollPx
    }.asFloatState()
}
