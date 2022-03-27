package cl.alphacode.reigntest.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.alphacode.reigntest.screens.DetailScreen
import cl.alphacode.reigntest.screens.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
        composable(route = AppScreens.MainScreen.route) {
            MainScreen(navController)
        }
        composable(
            route = "${AppScreens.DetailScreen.route}?url={url},title={title}",
            arguments = listOf(
                navArgument(name = "url") {
                    type = NavType.StringType
                    defaultValue = "undefined"
                }, navArgument(name = "title") {
                    type = NavType.StringType
                    defaultValue = "undefined"
                })
        ) {
            DetailScreen(
                navController,
                it.arguments?.getString("url"),
                it.arguments?.getString("title")
            )
        }
    }
}