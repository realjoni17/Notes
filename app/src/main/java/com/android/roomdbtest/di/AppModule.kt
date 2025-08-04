package com.android.roomdbtest.di

import android.app.Application
import android.content.Context
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
import com.android.roomdbtest.domain.usecase.ExportNotesToDriveUseCase
import com.android.roomdbtest.domain.usecase.ImportNotesFromDriveUseCase
import com.android.roomdbtest.presentation.home_screen.DriveServiceProvider
import com.android.roomdbtest.presentation.home_screen.DriveServiceProviderImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return NoteDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideNoteRepository(database: NoteDatabase, firestore: FirebaseFirestore): NoteRepository = NoteRepositoryImpl(
       firestore = firestore,
        database.noteDao()
    )

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideAuthService(firebaseAuthService: FirebaseAuthService): AuthService {
        return firebaseAuthService

    }

    @Singleton
    @Provides
    fun providesFirebaseFireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
  fun providesContext(@ApplicationContext context: Context): Context {
        return context
    }
    @Singleton
    fun provideExportNotesToDriveUseCase(repository: NoteRepository): ExportNotesToDriveUseCase {
        return ExportNotesToDriveUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideImportNotesFromDriveUseCase(repository: NoteRepository): ImportNotesFromDriveUseCase {
        return ImportNotesFromDriveUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideDriveServiceProvider(): DriveServiceProvider {
        return DriveServiceProviderImpl()
    }
}


