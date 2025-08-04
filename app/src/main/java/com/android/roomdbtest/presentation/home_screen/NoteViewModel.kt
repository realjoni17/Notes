package com.android.roomdbtest.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.roomdbtest.auth.AuthService
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.domain.usecase.DeleteNoteUseCase
import com.android.roomdbtest.domain.usecase.ExportNotesToDriveUseCase
import com.android.roomdbtest.domain.usecase.GetNotesUseCase
import com.android.roomdbtest.domain.usecase.ImportNotesFromDriveUseCase
import com.android.roomdbtest.domain.usecase.SearchNotesUseCase
import com.android.roomdbtest.presentation.auth.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject




import androidx.lifecycle.viewModelScope
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.drive.Drive
import dagger.Provides

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Singleton


@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val searchNotesUseCase: SearchNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val authService: AuthService,
    private val exportNotesToDriveUseCase: ExportNotesToDriveUseCase,
    private val importNotesFromDriveUseCase: ImportNotesFromDriveUseCase,
    private val driveServiceProvider: DriveServiceProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        observeAuthState()
    }

    private fun observeAuthState() {
        viewModelScope.launch {
            authService.currentUser.collect { user ->
                if (user != null) {
                    _uiState.value = _uiState.value.copy(
                        isAuthenticated = true,
                        currentUserId = user.uid
                    )
                    loadNotes(user.uid)
                } else {
                    _uiState.value = _uiState.value.copy(
                        isAuthenticated = false,
                        currentUserId = null,
                        notes = emptyList()
                    )
                }
            }
        }
    }

    private fun loadNotes(userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                getNotesUseCase(userId).collect { notes ->
                    _uiState.value = _uiState.value.copy(
                        notes = notes,
                        isLoading = false,
                        isAuthenticated = true
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to load notes",
                    isLoading = false
                )
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            loadNotes(_uiState.value.currentUserId ?: return)
        } else {
            searchNotes(query)
        }
    }

    private fun searchNotes(query: String) {
        val userId = _uiState.value.currentUserId ?: return
        viewModelScope.launch {
            try {
                searchNotesUseCase(userId, query).collect { notes ->
                    _uiState.value = _uiState.value.copy(notes = notes)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to search notes"
                )
            }
        }
    }

    fun exportNotesToDrive() {
        val userId = _uiState.value.currentUserId ?: return
        val driveService = driveServiceProvider.getDriveService() ?: run {
            _uiState.value = _uiState.value.copy(
                error = "Drive service not initialized"
            )
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            exportNotesToDriveUseCase(userId, driveService).fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        successMessage = "Notes exported successfully"
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to export notes"
                    )
                }
            )
        }
    }

    fun importNotesFromDrive() {
        val userId = _uiState.value.currentUserId ?: return
        val driveService = driveServiceProvider.getDriveService() ?: run {
            _uiState.value = _uiState.value.copy(
                error = "Drive service not initialized"
            )
            return
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            importNotesFromDriveUseCase(userId, driveService).fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        successMessage = "Notes imported successfully"
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to import notes"
                    )
                }
            )
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                deleteNoteUseCase(note)
                // Notes will be automatically updated through Flow
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to delete note"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAuthenticated: Boolean = false,
    val currentUserId: String? = null,
    val successMessage: String? = null
)


interface DriveServiceProvider {
    fun getDriveService(): Drive?
    fun initializeDriveService(credential: GoogleAccountCredential)
}


class DriveServiceProviderImpl @Inject constructor() : DriveServiceProvider {
    private var driveService: Drive? = null

    override fun getDriveService(): Drive? {
        return driveService
    }

    override fun initializeDriveService(credential: GoogleAccountCredential) {
        driveService = Drive.Builder(
            com.google.api.client.googleapis.javanet.GoogleNetHttpTransport.newTrustedTransport(),
            com.google.api.client.json.gson.GsonFactory.getDefaultInstance(),
            credential
        ).setApplicationName("NotesApp").build()
    }
}