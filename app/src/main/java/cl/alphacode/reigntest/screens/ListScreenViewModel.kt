package cl.alphacode.reigntest.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alphacode.reigntest.domain.*
import cl.alphacode.reigntest.entity.News
import cl.alphacode.reigntest.event.Event
import cl.alphacode.reigntest.qualifier.CoroutineDispatcherApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase,
    @CoroutineDispatcherApi private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> get() = _news.asStateFlow()

    private val _message = MutableLiveData<Event<String>>(null)
    val message: LiveData<Event<String>> = _message

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch(dispatcher) {
            getNewsUseCase.updateInternetNews()
            getNewsUseCase.invoke().collect {
                _news.value = it
            }
        }
    }

    fun refreshNews() {
        getNews()
    }

    fun onNewsClicked(news: News, navigationToDetails: (String?, String?) -> Unit) {
        if (news.storyUrl.isNotEmpty()) {
            navigationToDetails(news.storyUrl, news.title)
        } else {
            _message.postValue(Event("Problemas con la RED o la respuesta está vacía"))
        }
    }

    fun removeNews(news: News) {
        viewModelScope.launch(dispatcher) {
            deleteNewsUseCase.invoke(news)
            _message.postValue(Event("Se eliminó la noticia"))
        }
    }
}