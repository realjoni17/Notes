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
import com.android.roomdbtest.data.network.ApiService
import com.android.roomdbtest.data.network.AuthInterceptor
import com.android.roomdbtest.data.repository.NoteRepositoryImpl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    fun provideNoteRepository(database: NoteDatabase, apiService: ApiService): NoteRepository =
        NoteRepositoryImpl(
            database.noteDao(), apiService
        )

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor {

        val authToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTcxMzU5MjAsInN1YiI6IiJ9.aInh_CwhaKqLBGdvG1SyBvdgNPy46OaAXjB1B_uXKpE"
        return AuthInterceptor(authToken)
    }


    @Singleton
    @Provides
    fun providesHttpInterceptor(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(provideAuthInterceptor()).build()
    }

   // val client = OkHttpClient.Builder().addInterceptor(AuthInterceptor("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTcxMzU5MjAsInN1YiI6IiJ9.aInh_CwhaKqLBGdvG1SyBvdgNPy46OaAXjB1B_uXKpE")).build()
    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://notes-api-8gy2.onrender.com/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}