package nbe.someone.code.core

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollSource

public sealed class CollapsingToolbarStrategy {
    private companion object {
        fun handleYScroll(state: CollapsingToolbarState, available: Offset): Offset {
            val oldOffsetY = state.toolbarYState.floatValue
            state.offsetYState.floatValue = state.toolbarYState.floatValue + available.y

            val consumedY = state.toolbarYState.floatValue - oldOffsetY

            return Offset(x = 0f, y = consumedY)
        }
    }

    internal abstract fun onPreScroll(
        state: CollapsingToolbarState,
        available: Offset,
        source: NestedScrollSource,
    ): Offset

    internal abstract fun onPostScroll(
        state: CollapsingToolbarState,
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource,
    ): Offset

    public data object EnterAlways : CollapsingToolbarStrategy() {
        override fun onPreScroll(
            state: CollapsingToolbarState,
            available: Offset,
            source: NestedScrollSource,
        ): Offset {
            return handleYScroll(state, available)
        }

        override fun onPostScroll(
            state: CollapsingToolbarState,
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource,
        ): Offset = Offset.Zero
    }

    public data object ExitUntilCollapsed : CollapsingToolbarStrategy() {
        override fun onPreScroll(
            state: CollapsingToolbarState,
            available: Offset,
            source: NestedScrollSource,
        ): Offset { // y<0 向上滑动
            if (available.y < 0f) {
                return handleYScroll(state, available)
            }

            return Offset.Zero
        }

        override fun onPostScroll(
            state: CollapsingToolbarState,
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource,
        ): Offset {
            return handleYScroll(state, available)
        }
    }
}
