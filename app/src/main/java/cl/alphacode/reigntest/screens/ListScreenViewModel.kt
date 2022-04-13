package cl.alphacode.reigntest.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alphacode.reigntest.model.News
import cl.alphacode.reigntest.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {
    private val _news = MutableStateFlow<List<News>>(emptyList())

    fun getNews(): MutableStateFlow<List<News>> {
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getNews("mobile")
            _news.update { news }
        }
        return _news
    }

    fun removeNews(news: News) {
        _news.update {
            it.filter { it.createdAtI != news.createdAtI }
        }
    }
}