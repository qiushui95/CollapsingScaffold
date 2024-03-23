package nbe.someone.code.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.IntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember

@Composable
public fun rememberCollapsingToolbarState(
    maxScrollDistancePxDeltaState: IntState = mutableIntStateOf(0),
): CollapsingToolbarState = remember(maxScrollDistancePxDeltaState) {
    CollapsingToolbarState(maxScrollDistancePxDeltaState)
}
