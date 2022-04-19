package cl.alphacode.reigntest.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alphacode.reigntest.model.News
import cl.alphacode.reigntest.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {


    private val _refreshing = MutableStateFlow(true)
    val refreshing: StateFlow<Boolean> get() = _refreshing.asStateFlow()

    init {
        getNews()
    }
    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> get() = _news.asStateFlow()

    fun getNews(): MutableStateFlow<List<News>> {
        _refreshing.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getNews("mobile")
            _news.update { news }
            _refreshing.value = false
        }
        return _news
    }

    fun removeNews(news: News) {
        _news.update {
            it.filter { it.createdAtI != news.createdAtI }
        }
    }
}