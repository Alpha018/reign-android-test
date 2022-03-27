package cl.alphacode.reigntest.service.services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.alphacode.reigntest.service.algolia.responses.Hits
import cl.alphacode.reigntest.service.interfaces.RequestService
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    private val _response = mutableStateListOf<Hits>()
    var errorMessage: String by mutableStateOf("")
    val response: MutableList<Hits> get() = _response

    fun getResultList() {
        viewModelScope.launch {
            val apiService = RequestService.getInstance()
            try {
                _response.clear()
                _response.addAll(apiService.getResult().hits)
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}