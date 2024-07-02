package nbe.someone.code.collapsing.toolbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.IntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember

@Composable
public fun rememberCollapsingToolbarState(
    maxScrollDistancePxDeltaState: IntState = mutableIntStateOf(0),
): CollapsingToolbarState = remember<CollapsingToolbarState>(maxScrollDistancePxDeltaState) {
    CollapsingToolbarState(maxScrollDistancePxDeltaState)
}
