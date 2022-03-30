package cl.alphacode.reigntest.screens

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cl.alphacode.reigntest.navigations.AppScreens
import cl.alphacode.reigntest.service.services.TodoViewModel
import cl.alphacode.reigntest.ui.atoms.CenterTextScreen
import cl.alphacode.reigntest.ui.pages.CardList
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MainScreen(navController: NavController, vm: TodoViewModel = viewModel()) {
    val response = vm.response.collectAsState()
    SideEffect {
        vm.getResultList()
        /*
    *  val encodedUrl = URLEncoder.encode(
        it.storyUrl ?: "", StandardCharsets.UTF_8
            .toString()
    )
    navController.navigate(
        route = "${AppScreens.DetailScreen.route}?url=$encodedUrl,title=${it.title ?: it.storyTitle ?: ""}"
    )
    * */
    }
    var refreshing by remember { mutableStateOf(false) }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        onRefresh = {
            refreshing = true
            vm.getResultList()
            refreshing = false
        },
    ) {
        if (response.value.isEmpty()) {
            CenterTextScreen("Cargando...")
        } else {
            CardList(response.value, onCardClicked = {
                vm.onItemClicked(it)
            },
            onItemRemoved = {
                vm.onRemoveClicked(it)
            })
        }
    }
}