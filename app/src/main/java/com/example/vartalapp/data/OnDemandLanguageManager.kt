package com.example.vartalapp.data

import android.content.Context
import com.example.vartalapp.data.db.PhraseEntity
import com.example.vartalapp.data.db.VartalDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DownloadProgressState(
    val languageId: String,
    val isDownloading: Boolean = false,
    val progressFraction: Float = 0f,
    val isDownloaded: Boolean = false,
    val error: String? = null
)

class OnDemandLanguageManager(
    private val context: Context,
    private val database: VartalDatabase
) {
    private val _downloadStates = MutableStateFlow<Map<String, DownloadProgressState>>(
        mapOf("kannada" to DownloadProgressState("kannada", isDownloaded = true, progressFraction = 1f))
    )
    val downloadStates: Flow<Map<String, DownloadProgressState>> = _downloadStates.asStateFlow()

    fun isLanguageInstalled(languageId: String): Boolean {
        return _downloadStates.value[languageId]?.isDownloaded ?: (languageId == "kannada")
    }

    suspend fun downloadLanguagePack(languageInfo: LanguageInfo, onProgress: (Float) -> Unit) {
        val id = languageInfo.id
        if (isLanguageInstalled(id)) return

        val currentMap = _downloadStates.value.toMutableMap()
        currentMap[id] = DownloadProgressState(id, isDownloading = true, progressFraction = 0.1f)
        _downloadStates.value = currentMap

        // Simulate on-demand dynamic feature pack download stream
        for (step in 1..10) {
            delay(120)
            val progress = step / 10f
            onProgress(progress)
            currentMap[id] = DownloadProgressState(id, isDownloading = true, progressFraction = progress)
            _downloadStates.value = currentMap.toMap()
        }

        // Install preview phrases into Room database
        if (languageInfo.previewPhrases.isNotEmpty()) {
            val entities = languageInfo.previewPhrases.map { p ->
                PhraseEntity(
                    id = p.id,
                    english = p.english,
                    kannada = p.kannada,
                    transliteration = p.transliteration,
                    meaning = p.meaning,
                    explanation = p.explanation,
                    category = p.category,
                    level = p.level,
                    formality = p.formality,
                    alternative = p.alternative,
                    verificationStatus = "On-Demand Downloaded",
                    packId = "pack_${languageInfo.id}"
                )
            }
            database.phraseDao().insertPhrases(entities)
        }

        currentMap[id] = DownloadProgressState(id, isDownloading = false, isDownloaded = true, progressFraction = 1f)
        _downloadStates.value = currentMap.toMap()
    }
}
