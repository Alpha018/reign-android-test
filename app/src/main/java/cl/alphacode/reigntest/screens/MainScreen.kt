package cl.alphacode.reigntest.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import cl.alphacode.reigntest.service.services.TodoViewModel
import cl.alphacode.reigntest.ui.pages.CardList
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import cl.alphacode.reigntest.ui.atoms.CenterTextScreen


@Composable
fun MainScreen(navController: NavController) {
    val vm = TodoViewModel()
    SideEffect {
        vm.getResultList()
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
        if (vm.response.size == 0) {
            CenterTextScreen("Cargando...")
        } else {
            CardList(vm.response, navController)
        }
    }
}