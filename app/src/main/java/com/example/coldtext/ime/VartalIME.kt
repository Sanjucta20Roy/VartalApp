package com.example.coldtext.ime

import android.content.Context
import android.graphics.Color
import android.inputmethodservice.InputMethodService
import android.text.InputType
import android.text.TextUtils
import android.view.Gravity
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.coldtext.data.repository.PhraseRepository
import com.example.coldtext.data.repository.PhraseRepositoryImpl
import com.example.coldtext.data.translator.CodeSwitchTranslator

class VartalIME : InputMethodService() {

    private lateinit var repository: PhraseRepository
    private lateinit var translator: CodeSwitchTranslator

    private var keyboardLayout: LinearLayout? = null
    private var suggestionScrollView: HorizontalScrollView? = null
    private var suggestionContainer: LinearLayout? = null
    private var typedTextBuffer = StringBuilder()

    override fun onCreate() {
        super.onCreate()
        repository = PhraseRepositoryImpl(applicationContext)
        translator = CodeSwitchTranslator(repository)
    }

    override fun onCreateInputView(): View {
        val context: Context = this

        // Main Keyboard Outer Container
        val mainLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#121212"))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // 1. Code-Switch Suggestion Bar
        suggestionScrollView = HorizontalScrollView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(context, 46)
            )
            setBackgroundColor(Color.parseColor("#1E1E1E"))
        }

        suggestionContainer = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dpToPx(context, 8), 0, dpToPx(context, 8), 0)
        }

        suggestionScrollView?.addView(suggestionContainer)
        mainLayout.addView(suggestionScrollView)

        // 2. QWERTY & Utility Keys Layout
        val keysLayout = createKeysLayout(context)
        mainLayout.addView(keysLayout)

        keyboardLayout = mainLayout
        updateSuggestions()

        return mainLayout
    }

    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        typedTextBuffer.clear()
        updateSuggestions()
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        typedTextBuffer.clear()
        updateSuggestions()
    }

    private fun createKeysLayout(context: Context): View {
        val keysContainer = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dpToPx(context, 4), dpToPx(context, 6), dpToPx(context, 4), dpToPx(context, 8))
        }

        val rows = listOf(
            listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
            listOf("a", "s", "d", "f", "g", "h", "j", "k", "l"),
            listOf("z", "x", "c", "v", "b", "n", "m", "⌫")
        )

        rows.forEach { rowKeys ->
            val rowLayout = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                weightSum = rowKeys.size.toFloat()
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dpToPx(context, 44)
                ).apply {
                    topMargin = dpToPx(context, 3)
                }
            }

            rowKeys.forEach { keyLabel ->
                val btn = Button(context).apply {
                    text = keyLabel
                    textSize = 15f
                    setTextColor(Color.WHITE)
                    setBackgroundColor(Color.parseColor("#2C2C2C"))
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1f
                    ).apply {
                        setMargins(dpToPx(context, 2), 0, dpToPx(context, 2), 0)
                    }

                    setOnClickListener { v ->
                        if (isHapticsEnabled()) {
                            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        }
                        handleKeyPress(keyLabel)
                    }
                }
                rowLayout.addView(btn)
            }
            keysContainer.addView(rowLayout)
        }

        // Bottom Action Row (Space & Enter)
        val actionRow = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dpToPx(context, 46)
            ).apply {
                topMargin = dpToPx(context, 4)
            }
        }

        val spaceBtn = Button(context).apply {
            text = "Space (VartalApp Code-Switch)"
            textSize = 12f
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#E65100"))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 3f).apply {
                setMargins(dpToPx(context, 2), 0, dpToPx(context, 2), 0)
            }
            setOnClickListener {
                handleKeyPress(" ")
            }
        }

        val enterBtn = Button(context).apply {
            text = "↵ Enter"
            textSize = 13f
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#00796B"))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f).apply {
                setMargins(dpToPx(context, 2), 0, dpToPx(context, 2), 0)
            }
            setOnClickListener {
                currentInputConnection?.performEditorAction(EditorInfo.IME_ACTION_DONE)
            }
        }

        actionRow.addView(spaceBtn)
        actionRow.addView(enterBtn)
        keysContainer.addView(actionRow)

        return keysContainer
    }

    private fun handleKeyPress(key: String) {
        val ic = currentInputConnection ?: return

        when (key) {
            "⌫" -> {
                if (typedTextBuffer.isNotEmpty()) {
                    typedTextBuffer.deleteCharAt(typedTextBuffer.length - 1)
                }
                ic.deleteSurroundingText(1, 0)
            }
            else -> {
                typedTextBuffer.append(key)
                ic.commitText(key, 1)
            }
        }
        updateSuggestions()
    }

    private fun updateSuggestions() {
        val container = suggestionContainer ?: return
        container.removeAllViews()

        val context: Context = this
        val inputStr = typedTextBuffer.toString().trim()

        val suggestions = if (inputStr.isNotEmpty()) {
            val result = translator.translate(inputStr, "kn")
            if (result.codeSwitchLatin.isNotBlank()) {
                listOf(result.codeSwitchLatin, "Caretaker-ge kodi", "Ondu nimisha wait maadi", "503 flat munde ideera", "Aaytu aaytu")
            } else {
                listOf("Caretaker-ge kodi", "Ondu nimisha wait maadi", "503 flat munde ideera", "Aaytu aaytu")
            }
        } else {
            listOf("Caretaker-ge kodi", "Ondu nimisha wait maadi", "503 flat munde ideera", "Auto fare estu agutte", "Aaytu aaytu")
        }

        suggestions.distinct().forEach { phraseText ->
            val tv = TextView(context).apply {
                text = " $phraseText "
                textSize = 13f
                setTextColor(Color.parseColor("#FFB74D"))
                setBackgroundColor(Color.parseColor("#2C2C2C"))
                setPadding(dpToPx(context, 12), dpToPx(context, 6), dpToPx(context, 12), dpToPx(context, 6))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(dpToPx(context, 4), 0, dpToPx(context, 4), 0)
                }

                setOnClickListener {
                    val ic = currentInputConnection ?: return@setOnClickListener
                    if (typedTextBuffer.isNotEmpty()) {
                        ic.deleteSurroundingText(typedTextBuffer.length, 0)
                        typedTextBuffer.clear()
                    }
                    ic.commitText(phraseText, 1)
                    updateSuggestions()
                }
            }
            container.addView(tv)
        }
    }

    private fun isHapticsEnabled(): Boolean = true

    private fun dpToPx(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}
