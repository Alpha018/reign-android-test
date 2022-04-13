package cl.alphacode.reigntest.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import cl.alphacode.reigntest.R
import cl.alphacode.reigntest.navigations.AppScreens
import cl.alphacode.reigntest.ui.pages.CardList
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import cl.alphacode.reigntest.ui.atoms.CenterTextScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun MainScreen(
    navController: NavController,
    viewModel: ListScreenViewModel = hiltViewModel()
) {
    val newsListResponse = viewModel.getNews().collectAsState()
    var refreshing by remember { mutableStateOf(false) }
    val context = LocalContext.current

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        onRefresh = {
            refreshing = true
            refreshing = false
        },
    ) {
        if (newsListResponse.value.isEmpty()) {
            CenterTextScreen(stringResource(R.string.loading_text))
        } else {
            CardList(
                newsListResponse.value,
                onItemRemove = {
                    viewModel.removeNews(it)
                    Toast.makeText(
                        context,
                        context.getString(R.string.news_deleted),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onCardClicked = {
                    val encodedUrl = URLEncoder.encode(it.storyUrl ?: "", StandardCharsets.UTF_8.toString())
                    val title = it.title ?: it.storyTitle ?: ""
                    if (encodedUrl.isNullOrBlank() || title.isBlank()) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.no_title_or_url),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        navController.navigate(
                            route = "${AppScreens.DetailScreen.route}?url=$encodedUrl,title=$title"
                        )
                    }
                }
            )
        }
    }
}