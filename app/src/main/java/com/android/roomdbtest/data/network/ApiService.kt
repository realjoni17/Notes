package com.android.roomdbtest.data.network

import com.android.roomdbtest.data.network.dto.NotesDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("notes")
    suspend fun getNotes() : Response<NotesDto>
}