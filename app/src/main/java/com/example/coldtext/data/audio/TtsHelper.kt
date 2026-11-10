package com.example.coldtext.data.audio

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

enum class TtsSupportStatus(val label: String, val isNativeVoiceAvailable: Boolean) {
    NATIVE_VOICE("Native Voice Installed", true),
    ENGLISH_FALLBACK("English Voice Fallback (May Sound Misleading)", true),
    UNAVAILABLE("TTS Engine Unavailable", false)
}

class TtsHelper(context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    var isInitialized = false
        private set

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            val result = tts?.setLanguage(Locale("kn", "IN"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts?.language = Locale.US
            }
        } else {
            Log.e("TtsHelper", "Initialization of TextToSpeech failed")
        }
    }

    fun getLanguageSupportStatus(languageCode: String = "kn_IN"): TtsSupportStatus {
        if (!isInitialized || tts == null) return TtsSupportStatus.UNAVAILABLE
        val parts = languageCode.split("_")
        val locale = if (parts.size == 2) Locale(parts[0], parts[1]) else Locale("kn", "IN")
        val result = tts?.isLanguageAvailable(locale)
        return when (result) {
            TextToSpeech.LANG_AVAILABLE, TextToSpeech.LANG_COUNTRY_AVAILABLE, TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE ->
                TtsSupportStatus.NATIVE_VOICE
            else -> TtsSupportStatus.ENGLISH_FALLBACK
        }
    }

    fun speak(text: String, speechRate: Float = 1.0f, languageCode: String = "kn_IN") {
        if (isInitialized && tts != null) {
            val parts = languageCode.split("_")
            val locale = if (parts.size == 2) Locale(parts[0], parts[1]) else Locale("kn", "IN")
            val langResult = tts?.setLanguage(locale)
            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts?.language = Locale.US
            }
            tts?.setSpeechRate(speechRate)
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "varta_tts_id")
        }
    }

    fun tryOpenTtsSettings(context: Context): Boolean {
        return try {
            val intent = Intent("com.android.settings.TTS_SETTINGS")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("TtsHelper", "Failed to open TTS settings", e)
            false
        }
    }

    fun stop() {
        if (isInitialized) {
            tts?.stop()
        }
    }

    fun shutdown() {
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
            tts = null
        }
    }
}
