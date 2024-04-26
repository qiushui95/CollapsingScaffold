package me.ysy.collapsing.scaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nbe.someone.code.core.CollapsingToolbarScaffold
import nbe.someone.code.core.CollapsingToolbarState
import nbe.someone.code.core.CollapsingToolbarStrategy
import nbe.someone.code.core.rememberCollapsingToolbarState
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CPMainPage()
        }
    }
}

@Composable
private fun CPMainPage() {
    val statusBarHeightState = remember {
        mutableIntStateOf(100)
    }
    val strategyState = remember {
        mutableStateOf<CollapsingToolbarStrategy>(CollapsingToolbarStrategy.EnterAlways)
    }

    val state = rememberCollapsingToolbarState(statusBarHeightState)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_main),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
        )

        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            strategy = strategyState.value,
            toolbar = { CPToolBar() },
            bodyTop = { CPBodyTop(strategyState) },
            body = { CPBody(strategyState) },
            state = state,
        )

        CPStatusBar(state, statusBarHeightState)
    }
}

@Composable
private fun CPStatusBar(state: CollapsingToolbarState, heightState: MutableIntState) {
    val percentState = remember(state.progressState) {
        derivedStateOf { (state.progressState.floatValue * 100).toInt() }.asIntState()
    }

    val density = LocalDensity.current

    val heightDPState = remember(key1 = density, key2 = heightState) {
        derivedStateOf { density.run { heightState.intValue.toDp() } }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(heightDPState.value)
            .background(Color.White.copy(alpha = state.progressState.floatValue))
            .clickable { heightState.intValue = Random.nextInt(100, 200) },
    ) {
        Text(
            text = "scroll:${state.scrollPxState.intValue}",
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterStart),
        )
        Text(
            text = "height:${heightState.intValue}",
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Center),
        )
        Text(
            text = "percent:${percentState.intValue}%",
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterEnd),
        )
    }
}

@Composable
private fun CPToolBar() {
    val brush = remember {
        Brush.verticalGradient(
            0f to Color.White.copy(0f),
            0.7f to Color.White.copy(0.5f),
            1f to Color.White,
        )
    }

    Spacer(
        modifier = Modifier
            .background(brush)
            .fillMaxWidth()
            .height(200.dp),
    )
}

@Composable
private fun CPStrategyItem(text: String, isChecked: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clickable(onClick = onClick),
    ) {
        Checkbox(checked = isChecked, onCheckedChange = { onClick() })

        Text(text = text)
    }
}

@Composable
private fun CPBodyTop(strategyState: MutableState<CollapsingToolbarStrategy>) {
    Row(
        modifier = Modifier
            .background(Color.Cyan)
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CPStrategyItem(
            text = "EnterAlways",
            isChecked = strategyState.value is CollapsingToolbarStrategy.EnterAlways,
            onClick = { strategyState.value = CollapsingToolbarStrategy.EnterAlways },
        )
        Spacer(modifier = Modifier.width(16.dp))

        CPStrategyItem(
            text = "ExitUntilCollapsed",
            isChecked = strategyState.value is CollapsingToolbarStrategy.ExitUntilCollapsed,
            onClick = { strategyState.value = CollapsingToolbarStrategy.ExitUntilCollapsed },
        )
    }
}

private fun randomColorList(): List<Color> {
    return listOf(
        Color(0xFFFF0000),
        Color(0xFF00FF00),
        Color(0xFF0000FF),
        Color(0xFFFFFF00),
        Color(0xFF00FFFF),
        Color(0xFFFF00FF),
        Color(0xFFFFFFFF),
        Color(0xFF000000),
        Color(0xFFFFA500),
        Color(0xFF4B0082),
        Color(0xFF8A2BE2),
        Color(0xFF8B0000),
        Color(0xFF008000),
        Color(0xFF000080),
        Color(0xFFFFC0CB),
        Color(0xFFFF4500),
        Color(0xFFFFD700),
        Color(0xFF808000),
        Color(0xFF008B8B),
        Color(0xFFE6E6FA),
    ).shuffled()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CPBody(strategyState: MutableState<CollapsingToolbarStrategy>) {
    val enableRefreshState = remember(strategyState) {
        derivedStateOf { strategyState.value is CollapsingToolbarStrategy.EnterAlways }
    }

    val colorListState = remember {
        mutableStateListOf<Color>()
    }

    LaunchedEffect(key1 = Unit) {
        colorListState.addAll(randomColorList())
    }

    val isRefreshState = remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    val state = rememberPullRefreshState(refreshing = isRefreshState.value, onRefresh = {
        scope.launch {
            isRefreshState.value = true
            delay(1000) // 模拟数据加载
            colorListState.clear()
            colorListState.addAll(randomColorList())
            isRefreshState.value = false
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state = state, enabled = enableRefreshState.value),
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(colorListState.size) { index ->
                CPColorItem(colorListState[index], index)
            }
        }

        PullRefreshIndicator(isRefreshState.value, state, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun CPColorItem(color: Color, index: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Text(
            text = "index:$index",
            color = Color.Black,
            fontSize = 16.sp,
        )
    }
}
