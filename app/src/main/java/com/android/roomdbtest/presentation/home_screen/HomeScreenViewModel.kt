package com.android.roomdbtest.presentation.home_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.domain.use_cases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val noteusecases : NoteUseCases)
    :ViewModel() {
    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    private var recentlyDeletedNote: Note? = null
    private var jop: Job? = null

    private fun getNotes(notes: List<Note>) {
        jop?.cancel()
        jop = noteusecases.getNotesUseCase().onEach { list ->
            _state.value = state.value.copy(
                notes = list
            )
        }.launchIn(viewModelScope)
    }
}