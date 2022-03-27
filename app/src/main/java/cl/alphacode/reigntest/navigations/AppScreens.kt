package cl.alphacode.reigntest.navigations

sealed class AppScreens(val route: String) {
    object MainScreen: AppScreens("MainScreen")
    object DetailScreen: AppScreens("DetailScreen")
}
