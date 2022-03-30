package cl.alphacode.reigntest.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.alphacode.reigntest.screens.DetailScreen
import cl.alphacode.reigntest.screens.MainScreen
import cl.alphacode.reigntest.service.services.TodoViewModel

internal const val URL_ARG = "url"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
        composable(route = AppScreens.MainScreen.route) {
            MainScreen(navController, TodoViewModel())
        }
        composable(
            route = "${AppScreens.DetailScreen.route}?$URL_ARG={$URL_ARG},title={title}",
            arguments = listOf(
                navArgument(name = URL_ARG) {
                    type = NavType.StringType
                    defaultValue = "undefined"
                }, navArgument(name = "title") {
                    type = NavType.StringType
                })
        ) {
            DetailScreen(
                navController,
                it.arguments?.getString(URL_ARG),
                it.arguments?.getString("title")
            )
        }
    }
}