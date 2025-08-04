package com.android.roomdbtest.presentation.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.roomdbtest.auth.AuthService
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class AuthViewModel @Inject constructor(
    internal val authService: AuthService,
   private val auth : FirebaseAuth
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState


    private var authStateJob: Job? = null
    private var _driveService: Drive? = null
    val driveService: Drive?
        get() = _driveService



    init {
        observeAuthState()
    }

    private fun observeAuthState() {
        authStateJob?.cancel()
        authStateJob = viewModelScope.launch {
            authService.currentUser.collect { user ->
                _uiState.update {
                    it.copy(
                        isAuthenticated = user != null,
                        error = null,
                        isLoading = false
                    )
                }

                if (user != null) {
                    FirebaseAuth.getInstance().currentUser?.let { firebaseUser ->
                        delay(5000)
                        Log.d("Joni", "observeAuthState: ${firebaseUser.uid}")
                        initializeDriveService(firebaseUser)
                    } ?: run {
                        _uiState.update {
                            it.copy(error = "Firebase user was null")
                            //Toast.makeText(context, "Firebase user was null", Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    _driveService = null
                }
            }
        }
    }
    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = authService.signInWithGoogle(idToken)

            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, error = null) }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error.localizedMessage ?: "Sign-in failed")
                    }
                }
            )
        }
    }
    fun setError(message: String) {
        _uiState.update { it.copy(isLoading = false, error = message) }
    }

    private fun initializeDriveService(user: FirebaseUser) {
        viewModelScope.launch {
            try {
                val credential = GoogleCredential().apply {
                    setAccessToken(user.getIdToken(false).await().token)
                }
                _driveService = Drive.Builder(
                    NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    credential
                ).setApplicationName("NotesApp").build()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.localizedMessage ?: "Failed to initialize Drive service",
                        isLoading = false
                    )
                }
            }
        }
    }
    fun signOut() {
        viewModelScope.launch {
            authService.signOut()
        }
    }
}



data class AuthUiState(
    val isAuthenticated: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

/*viewModelScope.launch {
    noteRepository.syncNotesFromFirebase(userId)
}*/
