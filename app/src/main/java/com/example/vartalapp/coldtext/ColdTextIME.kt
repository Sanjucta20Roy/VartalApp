package com.example.vartalapp.coldtext

import android.inputmethodservice.InputMethodService
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.SpaceBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vartalapp.data.db.SeedData
import com.example.vartalapp.feature.translator.TranslatorEngine
import com.example.vartalapp.ui.theme.VartalAppTheme

class ColdTextIME : InputMethodService() {

    private var typedBuffer = StringBuilder()
    private var isPasswordField = false
    private val translatorEngine by lazy { TranslatorEngine(SeedData.initialPhrases.map { it.toDomain() }) }

    override fun onCreateInputView(): View {
        val composeView = ComposeView(this).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                VartalAppTheme {
                    ColdTextKeyboardView(
                        typedText = typedBuffer.toString(),
                        isPassword = isPasswordField,
                        suggestions = getSuggestions(typedBuffer.toString()),
                        onKeyTap = { key -> handleKeyTap(key) },
                        onBackspace = { handleBackspace() },
                        onSpace = { handleSpace() },
                        onEnter = { handleEnter() },
                        onSuggestionTap = { kannadaText -> handleSuggestionTap(kannadaText) }
                    )
                }
            }
        }
        return composeView
    }

    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        typedBuffer.clear()

        // Privacy Safeguard: Check if input field is password / sensitive
        val inputType = attribute?.inputType ?: 0
        val variation = inputType and InputType.TYPE_MASK_VARIATION
        isPasswordField = (
            variation == InputType.TYPE_TEXT_VARIATION_PASSWORD ||
            variation == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ||
            variation == InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD
        )
    }

    private fun handleKeyTap(key: String) {
        typedBuffer.append(key)
        currentInputConnection?.commitText(key, 1)
    }

    private fun handleBackspace() {
        if (typedBuffer.isNotEmpty()) {
            typedBuffer.deleteCharAt(typedBuffer.length - 1)
        }
        currentInputConnection?.deleteSurroundingText(1, 0)
    }

    private fun handleSpace() {
        typedBuffer.append(" ")
        currentInputConnection?.commitText(" ", 1)
    }

    private fun handleEnter() {
        currentInputConnection?.performEditorAction(EditorInfo.IME_ACTION_DONE)
        typedBuffer.clear()
    }

    private fun handleSuggestionTap(kannadaText: String) {
        if (typedBuffer.isNotEmpty()) {
            currentInputConnection?.deleteSurroundingText(typedBuffer.length, 0)
            typedBuffer.clear()
        }
        currentInputConnection?.commitText("$kannadaText ", 1)
    }

    private fun getSuggestions(input: String): List<Pair<String, String>> {
        if (isPasswordField || input.isBlank()) return emptyList()
        val res = translatorEngine.translate(input)
        if (res.kannadaScript.isNotBlank()) {
            return listOf(Pair(res.kannadaScript, res.transliteration))
        }
        return emptyList()
    }
}

@Composable
fun ColdTextKeyboardView(
    typedText: String,
    isPassword: Boolean,
    suggestions: List<Pair<String, String>>,
    onKeyTap: (String) -> Unit,
    onBackspace: () -> Unit,
    onSpace: () -> Unit,
    onEnter: () -> Unit,
    onSuggestionTap: (String) -> Unit
) {
    val row1 = listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p")
    val row2 = listOf("a", "s", "d", "f", "g", "h", "j", "k", "l")
    val row3 = listOf("z", "x", "c", "v", "b", "n", "m")

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF1E1E2C)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // ColdText Suggestion Strip Header
            if (!isPassword && suggestions.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF2D2D44), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(suggestions) { pair ->
                        Surface(
                            modifier = Modifier.clickable { onSuggestionTap(pair.first) },
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.primary
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = pair.first, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "(${pair.second})", color = Color(0xFFFFD54F), fontSize = 12.sp)
                            }
                        }
                    }
                }
            } else if (isPassword) {
                Text(
                    text = "🔒 Password Field — Suggestions disabled for privacy",
                    color = Color.LightGray,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                )
            } else {
                Text(
                    text = "ColdText Keyboard Companion (Kannada Suggestions)",
                    color = Color.Gray,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                )
            }

            // Keyboard Row 1
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                row1.forEach { char -> KeyButton(text = char, modifier = Modifier.weight(1f)) { onKeyTap(char) } }
            }

            // Keyboard Row 2
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Spacer(modifier = Modifier.weight(0.5f))
                row2.forEach { char -> KeyButton(text = char, modifier = Modifier.weight(1f)) { onKeyTap(char) } }
                Spacer(modifier = Modifier.weight(0.5f))
            }

            // Keyboard Row 3
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                row3.forEach { char -> KeyButton(text = char, modifier = Modifier.weight(1f)) { onKeyTap(char) } }
                KeyButton(
                    icon = { Icon(Icons.Default.Backspace, contentDescription = "Delete", tint = Color.White) },
                    modifier = Modifier.weight(1.5f),
                    color = Color(0xFF3F3F5A),
                    onClick = onBackspace
                )
            }

            // Keyboard Row 4 (Space & Enter)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                KeyButton(
                    icon = { Icon(Icons.Default.SpaceBar, contentDescription = "Space", tint = Color.White) },
                    modifier = Modifier.weight(4f),
                    color = Color(0xFF3F3F5A),
                    onClick = onSpace
                )
                KeyButton(
                    icon = { Icon(Icons.Default.KeyboardReturn, contentDescription = "Enter", tint = Color.White) },
                    modifier = Modifier.weight(2f),
                    color = MaterialTheme.colorScheme.primary,
                    onClick = onEnter
                )
            }
        }
    }
}

@Composable
fun KeyButton(
    modifier: Modifier = Modifier,
    text: String = "",
    icon: (@Composable () -> Unit)? = null,
    color: Color = Color(0xFF2E2E42),
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(42.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(6.dp),
        color = color
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                icon()
            } else {
                Text(text = text, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}
