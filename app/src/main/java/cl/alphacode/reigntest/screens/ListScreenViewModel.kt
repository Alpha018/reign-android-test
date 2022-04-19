package cl.alphacode.reigntest.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alphacode.reigntest.model.News
import cl.alphacode.reigntest.repository.NewsRepository
import cl.alphacode.reigntest.ui.NewsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {
    private val _news = MutableStateFlow<List<NewsUi>>(emptyList())

    val news: StateFlow<List<NewsUi>> get() = _news.asStateFlow()

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch(dispatcher) {
            val news = repository.getNews("mobile")
            _news.update { news.map {
                NewsUi(title = it.storyTitle ?: it.title ?: "", it.author, it.createdAt, it
                    .createdAtI, it.storyUrl ?: "")
            } }
        }
    }

    fun removeNews(news: News) {
        _news.update {
            it.filter { it.createdAtI != news.createdAtI }
        }
    }

    fun onNewsClicked(news: NewsUi) {
        if (!news.storyUrl.isEmpty()) {
            // send action to navigate
        } else {
            // send action to show error
        }
    }
}