package cl.alphacode.reigntest.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import cl.alphacode.reigntest.R
import cl.alphacode.reigntest.ui.pages.CardList
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import cl.alphacode.reigntest.ui.atoms.CenterTextScreen


@Composable
fun MainScreen(
    navigationToDetails: (String?, String?) -> Unit,
    viewModel: ListScreenViewModel = hiltViewModel()
) {
    val newsListResponse by viewModel.news.collectAsState()
    val errorMessage by viewModel.message.observeAsState()
    var refreshing by remember { mutableStateOf(false) }
    val context = LocalContext.current

    errorMessage?.getContentIfNotHandled()?.let {
        Toast.makeText(
            context,
            it,
            Toast.LENGTH_SHORT
        ).show()
    }

    Log.i("News", newsListResponse.size.toString())
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        onRefresh = {
            refreshing = true
            viewModel.refreshNews()
            refreshing = false
        },
    ) {
        if (newsListResponse.isEmpty()) {
            CenterTextScreen(stringResource(R.string.loading_text))
        } else {
            CardList(
                newsListResponse,
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