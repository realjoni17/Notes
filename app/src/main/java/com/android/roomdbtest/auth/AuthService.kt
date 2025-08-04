package com.android.roomdbtest.auth

import kotlinx.coroutines.flow.Flow

interface AuthService {
    val currentUser : Flow<AuthUser?>
    suspend fun signInWithGoogle(idToken: String): Result<AuthUser>
    suspend fun signOut()
    fun isUserSignedIn() : Boolean
}

data class AuthUser(
    val uid: String,
    val displayName: String,
    val email: String,
    val photoUrl: String
)
