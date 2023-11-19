package com.dicoding.mymusiccompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mymusiccompose.data.MusicRepository
import com.dicoding.mymusiccompose.model.Music
import com.dicoding.mymusiccompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MusicRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Music>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Music>>>
        get() = _uiState


    fun getAllMusicById(id: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val music = repository.getAllMusicById(id)
                _uiState.value = UiState.Success(listOf(music))
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }
}