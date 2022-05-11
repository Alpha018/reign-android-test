package cl.alphacode.reigntest.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alphacode.reigntest.repository.NewsRepository
import cl.alphacode.reigntest.ui.viewModel.NewsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

data class UiState(val items: List<NewsUi>, val isLoading: Boolean)

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val repository: NewsRepository,
    @Named("ListScreenDispatcher") private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _news = MutableStateFlow<UiState>(UiState(emptyList(), true))
    val uiState: StateFlow<UiState> get() = _news.asStateFlow()
    private val _message = Channel<Messages>(Channel.CONFLATED)
    val message: Flow<Messages> = _message.consumeAsFlow()

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch(dispatcher) {
            _news.update { it.copy(isLoading = true) }
            val news = repository.getNews("mobile")
            _news.update {
                it.copy(items = news.map {
                    NewsUi(
                        title = it.storyTitle ?: it.title ?: "",
                        it.author,
                        it.createdAt,
                        it.createdAtI,
                        it.storyUrl ?: ""
                    )
                }, isLoading = false)
            }
        }
    }

    fun refreshNews() {
        getNews()
    }

    fun onNewsClicked(news: NewsUi, navigationToDetails: (String?, String?) -> Unit) {
        if (news.storyUrl.isNotEmpty()) {
            navigationToDetails(news.storyUrl, news.title)
        } else {
            _message.trySendBlocking(Messages.NetworkError)
        }
    }

    fun removeNews(news: NewsUi) {
        _news.update {
            it.copy(items = it.items.filter { it.createdAtI != news.createdAtI })
        }
        _message.trySendBlocking(Messages.Deleted)
    }
}