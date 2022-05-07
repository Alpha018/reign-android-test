package cl.alphacode.reigntest.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alphacode.reigntest.event.Event
import cl.alphacode.reigntest.repository.NewsRepository
import cl.alphacode.reigntest.ui.viewModel.NewsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val repository: NewsRepository,
    @Named("ListScreenDispatcher") private val dispatcher: CoroutineDispatcher
): ViewModel() {
    private val _news = MutableStateFlow<List<NewsUi>>(emptyList())
    val news: StateFlow<List<NewsUi>> get() = _news.asStateFlow()

    private val _message = MutableLiveData<Event<String>>(null)
    val message : LiveData<Event<String>> = _message

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch(dispatcher) {
            val news = repository.getNews("mobile")
            _news.update { news.map {
                NewsUi(
                    title = it.storyTitle ?: it.title ?: "",
                    it.author,
                    it.createdAt,
                    it.createdAtI,
                    it.storyUrl ?: ""
                )
            } }
        }
    }

    fun refreshNews() {
        getNews()
    }

    fun onNewsClicked(news: NewsUi, navigationToDetails: (String?, String?) -> Unit) {
        if (news.storyUrl.isNotEmpty()) {
            navigationToDetails(news.storyUrl, news.title)
        } else {
            _message.value = Event("Problemas con la RED o la respuesta está vacía")
        }
    }

    fun removeNews(news: NewsUi) {
        _news.update {
            it.filter { it.createdAtI != news.createdAtI }
        }
        _message.value = Event("Se eliminó la noticia")
    }
}