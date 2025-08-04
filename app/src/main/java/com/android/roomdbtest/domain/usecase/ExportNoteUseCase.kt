package com.android.roomdbtest.domain.usecase

import com.android.roomdbtest.domain.repository.NoteRepository
import com.google.api.services.drive.Drive
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.Result


@Singleton
class ExportNotesToDriveUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(userId: String, driveService: Drive): Result<Unit> {
        return repository.exportNotesToDrive(userId, driveService)
    }
}