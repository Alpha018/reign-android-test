package cl.alphacode.reigntest.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.alphacode.reigntest.screens.DetailScreen
import cl.alphacode.reigntest.screens.MainScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

internal const val URL_ARG = "url"
internal const val TITLE_ARG = "title"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
        buildMainScreen(navController)
        buildDetailScreen(navController)
    }
}

fun NavGraphBuilder.buildMainScreen(navController: NavController) {
    composable(route = AppScreens.MainScreen.route) {
        MainScreen({ storyUrl, title ->
            val encodedUrl = URLEncoder.encode(storyUrl, StandardCharsets.UTF_8.toString())
            navController.navigate(
                route = "${AppScreens.DetailScreen.route}?$URL_ARG=$encodedUrl,$TITLE_ARG=$title"
            )
        })
    }
}

fun NavGraphBuilder.buildDetailScreen(navController: NavController) {
    composable(
        route = "${AppScreens.DetailScreen.route}?$URL_ARG={$URL_ARG},$TITLE_ARG={$TITLE_ARG}",
        arguments = listOf(
            navArgument(name = URL_ARG) {
                type = NavType.StringType
            }, navArgument(name = TITLE_ARG) {
                type = NavType.StringType
            })
    ) {
        DetailScreen(
            navController,
            it.arguments?.getString(URL_ARG)!!,
            it.arguments?.getString(TITLE_ARG)!!
        )
    }
}