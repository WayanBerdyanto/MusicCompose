package com.dicoding.mymusiccompose.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mymusiccompose.data.MusicRepository
import com.dicoding.mymusiccompose.model.Music
import com.dicoding.mymusiccompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MusicRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Music>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Music>>>
        get() = _uiState

    fun getAllMusic() {
        viewModelScope.launch {
            repository.getAllMusic()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderRewards ->
                    _uiState.value = UiState.Success(orderRewards)
                }
        }
    }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        viewModelScope.launch {
            _query.value = newQuery
            repository.searchMusic(newQuery)
                .catch { error ->
                    _uiState.value = UiState.Error(error.message.toString())
                }
                .collect { musicList ->
                    _uiState.value = UiState.Success(musicList)
                }
        }
    }


}