package cl.alphacode.reigntest.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alphacode.reigntest.domain.*
import cl.alphacode.reigntest.entity.News
import cl.alphacode.reigntest.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val getNewsFromInternetUseCase: GetNewsFromInternetUseCase,
    private val getNewsFromDbUseCase: GetNewsFromDbUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase,
    @Named("ListScreenDispatcher") private val dispatcher: CoroutineDispatcher
): ViewModel() {
    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> get() = _news.asStateFlow()

    private val _message = MutableLiveData<Event<String>>(null)
    val message : LiveData<Event<String>> = _message

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch(dispatcher) {
            val news = getNewsFromInternetUseCase.invoke("mobile")
            news.map {
                saveNewsUseCase.invoke(
                    News(
                        title = it.storyTitle ?: it.title ?: "",
                        author = it.author,
                        createdAt = it.createdAt,
                        createdAtI = it.createdAtI,
                        storyUrl = it.storyUrl ?: ""
                    )
                )
            }
            val newsDb = getNewsFromDbUseCase.invoke()
            _news.update { newsDb }
        }
    }

    fun refreshNews() {
        getNews()
    }

    fun onNewsClicked(news: News, navigationToDetails: (String?, String?) -> Unit) {
        if (news.storyUrl.isNotEmpty()) {
            navigationToDetails(news.storyUrl, news.title)
        } else {
            _message.value = Event("Problemas con la RED o la respuesta está vacía")
        }
    }

    fun removeNews(news: News) {
        viewModelScope.launch(dispatcher) {
            deleteNewsUseCase.invoke(news)
            val newsDb = getNewsFromDbUseCase.invoke()
            _news.update { newsDb }
        }
        _message.value = Event("Se eliminó la noticia")
    }
}