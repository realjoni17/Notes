package com.android.roomdbtest.di

import android.app.Application
import com.android.roomdbtest.data.local.NoteDatabase
import com.android.roomdbtest.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import androidx.room.Room
import com.android.roomdbtest.auth.AuthService
import com.android.roomdbtest.auth.FirebaseAuthService
import com.android.roomdbtest.data.repository.NoteRepositoryImpl
import com.google.firebase.auth.FirebaseAuth

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideNoteDatabase(app: Application) = Room.databaseBuilder(
        app, NoteDatabase::class.java, NoteDatabase.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideNoteRepository(database: NoteDatabase): NoteRepository = NoteRepositoryImpl(
        database.noteDao()
    )
    @Singleton
    @Provides
    fun provideFirebaseAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    fun provideAuthService(firebaseAuthService : FirebaseAuthService) : AuthService{
        return firebaseAuthService

    }

}