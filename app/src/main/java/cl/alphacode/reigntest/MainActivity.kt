package cl.alphacode.reigntest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import cl.alphacode.reigntest.navigations.AppNavigation
import cl.alphacode.reigntest.ui.theme.ReignTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ReignTestTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        ReignTestTheme {
            Surface(color = MaterialTheme.colors.background) {
                AppNavigation()
            }
        }
    }
}