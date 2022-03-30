package cl.alphacode.reigntest.service.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alphacode.reigntest.service.algolia.responses.Hits
import cl.alphacode.reigntest.service.interfaces.RequestService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoViewModel constructor(private val apiService: RequestService) : ViewModel() {

    private val _response = MutableStateFlow<List<Hits>>(emptyList())
    var errorMessage: String by mutableStateOf("")
    val response: StateFlow<List<Hits>> get() = _response

    fun getResultList() {
        viewModelScope.launch {
            try {
                _response.update {
                    apiService.getResult().hits
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun onItemClicked(hits: Hits) {
        if (!hits.storyUrl.isNullOrEmpty()) {
            // navigateToDetails.value = navigation
        } else // errorMessage = UrlError
    }

    fun onRemoveClicked(hits: Hits) {
        _response.update {
            it.filter { it.createdAtI != hits.createdAtI }
        }
    }
}