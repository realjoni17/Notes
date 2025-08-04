package com.android.roomdbtest.presentation.update_note



import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.roomdbtest.auth.AuthService
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.domain.usecase.AddNoteUseCase
import com.android.roomdbtest.domain.usecase.DeleteNoteUseCase
import com.android.roomdbtest.domain.usecase.UpdateNoteUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

 // private val noteId: Long? = savedStateHandle["noteId"]?.toLongOrNull()

    private val _uiState = MutableStateFlow(NoteEditUiState())
    val uiState: StateFlow<NoteEditUiState> = _uiState.asStateFlow()

    private var currentNote: Note? = null

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateContent(content: String) {
        _uiState.value = _uiState.value.copy(content = content)
    }

    fun loadNote(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)


            try {

                kotlinx.coroutines.delay(500)

                // Mock note data
                currentNote = Note(
                    userId = id,
                    title = "Sample Note",
                    content = "This is a sample note content that was loaded from the database.",
                    timestamp = java.util.Date().time
                )

                _uiState.value = _uiState.value.copy(
                    title = currentNote?.title ?: "",
                    content = currentNote?.content ?: "",
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to load note",
                    isLoading = false
                )
            }
        }
    }

    fun saveNote() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val currentUser = authService.currentUser.first()
                if (currentUser == null) {
                    _uiState.value = _uiState.value.copy(
                        error = "User not authenticated",
                        isLoading = false
                    )
                    return@launch
                }

                val title = _uiState.value.title.trim()
                val content = _uiState.value.content.trim()

                if (title.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        error = "Title cannot be empty",
                        isLoading = false
                    )
                    return@launch
                }

                if (currentNote != null) {
                    // Update existing note
                    val updatedNote = currentNote!!.copy(
                        title = title,
                        content = content,
                        timestamp = java.util.Date().time
                    )
                    updateNoteUseCase(updatedNote)
                } else {
                    // Create new note
                    addNoteUseCase(title, content, currentUser.uid)
                }

                _uiState.value = _uiState.value.copy(
                    isSaved = true,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to save note",
                    isLoading = false
                )
            }
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                currentNote?.let { note ->
                    deleteNoteUseCase(note)
                    _uiState.value = _uiState.value.copy(
                        isSaved = true,
                        isLoading = false
                    )
                } ?: run {
                    _uiState.value = _uiState.value.copy(
                        error = "No note to delete",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to delete note",
                    isLoading = false
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class NoteEditUiState(
    val title: String = "",
    val content: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSaved: Boolean = false
)