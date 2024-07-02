package nbe.someone.code.collapsing.toolbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.SubcomposeLayout

@Composable
public fun CollapsingToolbarScaffold(
    modifier: Modifier,
    strategy: CollapsingToolbarStrategy,
    toolbar: @Composable () -> Unit,
    bodyTop: @Composable () -> Unit,
    body: @Composable () -> Unit,
    state: CollapsingToolbarState = rememberCollapsingToolbarState(),
) {
    val connection = remember<NestedScrollConnection>(strategy) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                return strategy.onPreScroll(state, available, source)
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                return strategy.onPostScroll(state, consumed, available, source)
            }
        }
    }

    SubcomposeLayout(
        modifier = modifier
            .clipToBounds()
            .nestedScroll(connection),
    ) { constraints ->
        val toolbarConstraints = constraints.copy(
            minHeight = 0,
            maxHeight = constraints.maxHeight * 30,
        )

        val toolbarPlaceableList = subcompose("toolbar", toolbar).map {
            it.measure(toolbarConstraints)
        }

        val toolbarMaxHeight = toolbarPlaceableList.maxOfOrNull { it.height } ?: 0

        val bodyTopConstraints = constraints.copy(minHeight = 0)

        val bodyTopPlaceableList = subcompose("body top", bodyTop).map {
            it.measure(bodyTopConstraints)
        }

        val bodyTopMaxHeight = bodyTopPlaceableList.maxOfOrNull { it.height } ?: 0

        val deltaBodyHeight = state.scrollEndDeltaPxState.intValue.coerceAtLeast(0)

        val bodyMaxHeight = constraints.maxHeight - bodyTopMaxHeight - deltaBodyHeight

        val remainHeight = (constraints.maxHeight - toolbarMaxHeight - bodyTopMaxHeight)
            .takeIf { it > 0 }
            ?: bodyMaxHeight

        val bodyConstraints = constraints.copy(
            minHeight = remainHeight,
            maxHeight = bodyMaxHeight,
        )

        val bodyPlaceableList = subcompose("body") { body() }.map {
            it.measure(bodyConstraints)
        }

        state.toolbarHeightState.intValue = toolbarMaxHeight

        layout(constraints.maxWidth, constraints.maxHeight) {
            val toolbarY = state.toolbarYState.floatValue.toInt()

            for (placeable in toolbarPlaceableList) {
                placeable.place(0, toolbarY)
            }

            val bodyTopY = toolbarMaxHeight + toolbarY

            for (placeable in bodyTopPlaceableList) {
                placeable.place(0, bodyTopY)
            }

            val bodyY = bodyTopMaxHeight + toolbarMaxHeight + toolbarY

            for (placeable in bodyPlaceableList) {
                placeable.place(0, bodyY)
            }
        }
    }
}
