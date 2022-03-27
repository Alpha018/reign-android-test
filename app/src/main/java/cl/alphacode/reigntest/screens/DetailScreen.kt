package cl.alphacode.reigntest.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.alphacode.reigntest.ui.atoms.CenterTextScreen
import cl.alphacode.reigntest.ui.atoms.WebViewPage

@Composable
fun DetailScreen(navController: NavController, url: String?, title: String?) {
    Scaffold(topBar = {
        TopAppBar {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow back",
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            title?.let {
                Text(text = title)
            }
        }
    }) {
        if (url.isNullOrBlank() || url == "undefined") {
            CenterTextScreen(text = "Sin URL")
        } else {
            WebViewPage(url)
        }
    }

}