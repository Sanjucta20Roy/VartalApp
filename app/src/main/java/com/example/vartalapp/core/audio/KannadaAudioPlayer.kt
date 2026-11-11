package com.example.vartalapp.core.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.Toast
import java.util.Locale

class KannadaAudioPlayer(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var isInitialized = false
    private var isKannadaSupported = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val knLocale = Locale("kn", "IN")
            val result = tts?.setLanguage(knLocale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Fall back to English or notify user
                isKannadaSupported = false
            } else {
                isKannadaSupported = true
            }
            isInitialized = true
        } else {
            isInitialized = false
        }
    }

    fun speak(text: String, speechRate: Float = 1.0f) {
        if (!isInitialized) return
        tts?.setSpeechRate(speechRate)

        val result = if (isKannadaSupported) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "VartalAudioID")
        } else {
            // Fallback for devices without native Kannada TTS voice pack
            Toast.makeText(context, "Kannada TTS voice pack not installed on device. Falling back...", Toast.LENGTH_SHORT).show()
            tts?.setLanguage(Locale.ENGLISH)
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "VartalAudioID")
        }
    }

    fun stop() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
