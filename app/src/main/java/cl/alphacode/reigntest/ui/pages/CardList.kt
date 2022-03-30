package cl.alphacode.reigntest.ui.pages

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.alphacode.reigntest.navigations.AppScreens
import cl.alphacode.reigntest.service.algolia.responses.Hits
import cl.alphacode.reigntest.ui.organism.CardComponent
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardList(
    hits: List<Hits>,
    onCardClicked: (Hits) -> Unit = {},
    onItemRemoved: (Hits) -> Unit = {}
) {
    val context = LocalContext.current

    LazyColumn(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentPadding = PaddingValues(0.dp, 5.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(hits, { it.createdAtI }) { item ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        onItemRemoved(item)
                        Toast.makeText(
                            context,
                            "Noticia eliminada",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    true
                }
            )
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                dismissThresholds = { FractionalThreshold(0.2f) },
                background = {
                    val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                    val color by animateColorAsState(
                        targetValue = when (dismissState.targetValue) {
                            DismissValue.Default -> Color.LightGray
                            DismissValue.DismissedToStart -> Color.Red
                            else -> Color.LightGray
                        }
                    )
                    val icon = when (direction) {
                        DismissDirection.EndToStart -> Icons.Default.Delete
                        else -> Icons.Default.Delete
                    }
                    val scale by animateFloatAsState(
                        targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f
                    )
                    val alignment = when (direction) {
                        DismissDirection.EndToStart -> Alignment.CenterEnd
                        else -> Alignment.CenterStart
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = color)
                            .padding(16.dp),
                        contentAlignment = alignment
                    ) {
                        Icon(
                            icon,
                            contentDescription = "Icon",
                            modifier = Modifier
                                .scale(scale = scale)
                                .fillMaxHeight()
                        )
                    }
                },
                dismissContent = {
                    Card(
                        onClick = {
                           onCardClicked(item)
                        },
                        shape = MaterialTheme.shapes.small,
                        border = BorderStroke(1.dp, Color.LightGray),
                        modifier = Modifier.fillMaxWidth(),
                        elevation = animateDpAsState(
                            targetValue = if (dismissState.dismissDirection != null) 4.dp else 0.dp
                        ).value
                    ) {
                        CardComponent(hits = item)
                    }
                }
            )
        }
    }
}