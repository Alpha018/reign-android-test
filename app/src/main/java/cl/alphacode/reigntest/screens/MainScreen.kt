package cl.alphacode.reigntest.screens

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import cl.alphacode.reigntest.R
import cl.alphacode.reigntest.ui.atoms.CenterTextScreen
import cl.alphacode.reigntest.ui.pages.CardList
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collect

@Composable
fun MainScreen(
    navigationToDetails: (String?, String?) -> Unit,
    viewModel: ListScreenViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.message) {
        viewModel.message.collect {
            Toast.makeText(
                context,
                when (it) {
                    Messages.Deleted -> "Se eliminó la noticia"
                    Messages.NetworkError -> "Error"
                },
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    /*
       }*/

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = state.isLoading),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        onRefresh = {
            viewModel.refreshNews()
        },
    ) {
        if (state.isLoading && state.items.isEmpty()) {
            CenterTextScreen(stringResource(R.string.loading_text))
        } else {
            CardList(
                state.items,
                onItemRemove = {
                    viewModel.removeNews(it)
                },
                onCardClicked = {
                    viewModel.onNewsClicked(it, navigationToDetails)
                }
            )
        }
    }
}