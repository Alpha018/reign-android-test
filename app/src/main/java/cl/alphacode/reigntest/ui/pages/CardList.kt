package cl.alphacode.reigntest.ui.pages

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
import androidx.compose.ui.unit.dp
import cl.alphacode.reigntest.model.News
import cl.alphacode.reigntest.ui.organism.CardComponent
import cl.alphacode.reigntest.ui.viewModel.NewsUi

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardList(
    news: List<NewsUi>,
    onCardClicked: (NewsUi) -> Unit = {},
    onItemRemove: (NewsUi) -> Unit = {}
) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentPadding = PaddingValues(0.dp, 5.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(news, { it.createdAtI }) { item ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        onItemRemove(item)
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
                        CardComponent(news = item)
                    }
                }
            )
        }
    }
}