package com.example.coldtext.ime

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.inputmethodservice.InputMethodService
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.HapticFeedbackConstants
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.ScrollView
import android.widget.TextView

class ColdTextIME : InputMethodService() {

    enum class ToneLevel(val label: String, val emoji: String) {
        COLDEST("Coldest", "❄️"),
        NEUTRAL("Neutral", "😐"),
        WARM("Warm", "😊"),
        FRIENDLIEST("Friendly", "❤️")
    }

    enum class LetterSize(val keySp: Float, val tabSp: Float) {
        SMALL(11f, 10f),
        MEDIUM(13f, 12f),
        LARGE(15f, 14f)
    }

    enum class KeyboardSize(val screenHeightRatio: Float) {
        COMPACT(0.35f),
        MEDIUM(0.40f),
        TALL(0.45f)
    }

    enum class AccentTheme(val label: String, val darkBg: Int, val darkText: Int, val lightBg: Int, val lightText: Int) {
        BLUE("Blue", Color.rgb(32, 52, 78), Color.rgb(170, 215, 255), Color.rgb(210, 228, 252), Color.rgb(20, 80, 160)),
        GREEN("Green", Color.rgb(28, 62, 45), Color.rgb(160, 240, 190), Color.rgb(210, 245, 220), Color.rgb(20, 110, 50)),
        PURPLE("Purple", Color.rgb(55, 32, 72), Color.rgb(220, 180, 255), Color.rgb(235, 215, 250), Color.rgb(100, 30, 150)),
        ORANGE("Orange", Color.rgb(75, 42, 22), Color.rgb(255, 200, 160), Color.rgb(255, 230, 210), Color.rgb(180, 70, 20)),
        ROSE("Rose", Color.rgb(72, 28, 48), Color.rgb(255, 180, 210), Color.rgb(255, 215, 230), Color.rgb(160, 20, 80))
    }

    private data class ThemeColors(
        val bg: Int,
        val headerText: Int,
        val cardBg: Int,
        val keyBg: Int,
        val keyText: Int,
        val tabBg: Int,
        val tabSelectedBg: Int,
        val tabText: Int,
        val tabSelectedText: Int,
        val accentBg: Int,
        val accentText: Int,
        val utilityBg: Int,
        val suggestionBg: Int
    )

    private val darkTheme: ThemeColors
        get() = ThemeColors(
            bg = Color.rgb(20, 22, 28),
            headerText = Color.WHITE,
            cardBg = Color.rgb(32, 35, 46),
            keyBg = Color.rgb(34, 38, 50),
            keyText = Color.WHITE,
            tabBg = Color.rgb(28, 30, 40),
            tabSelectedBg = Color.rgb(55, 62, 82),
            tabText = Color.LTGRAY,
            tabSelectedText = Color.WHITE,
            accentBg = accentTheme.darkBg,
            accentText = accentTheme.darkText,
            utilityBg = Color.rgb(40, 44, 58),
            suggestionBg = Color.rgb(35, 42, 60)
        )

    private val lightTheme: ThemeColors
        get() = ThemeColors(
            bg = Color.rgb(242, 244, 248),
            headerText = Color.rgb(20, 20, 25),
            cardBg = Color.rgb(228, 232, 240),
            keyBg = Color.WHITE,
            keyText = Color.rgb(30, 30, 35),
            tabBg = Color.rgb(220, 224, 234),
            tabSelectedBg = Color.rgb(185, 195, 215),
            tabText = Color.rgb(80, 80, 90),
            tabSelectedText = Color.rgb(10, 10, 15),
            accentBg = accentTheme.lightBg,
            accentText = accentTheme.lightText,
            utilityBg = Color.rgb(228, 232, 242),
            suggestionBg = Color.rgb(218, 224, 240)
        )

    private var isDarkMode = true
    private var toneLevel = ToneLevel.NEUTRAL
    private var letterSize = LetterSize.MEDIUM
    private var keyboardSize = KeyboardSize.MEDIUM
    private var accentTheme = AccentTheme.BLUE
    private var isHapticsEnabled = true

    private val currentTheme: ThemeColors
        get() = if (isDarkMode) darkTheme else lightTheme

    private data class PhraseCategory(
        val name: String,
        val defaultPhrases: List<String>
    )

    private val categories = listOf(
        PhraseCategory(
            "Common",
            listOf(
                "K", "hm", "hmm",
                "ok", "sure", "yeah",
                "bye", "ttyl", "lol",
                "ilu", "ikr", "lmao",
                "fine", "alr", "nvm",
                "idk", "wdym", "whatever"
            )
        ),
        PhraseCategory(
            "Abbreviated",
            listOf(
                "tbh", "ngl", "imo",
                "brb", "omg", "btw",
                "afk", "fyi", "idk",
                "rn", "smh", "tbf",
                "np", "yw", "ofc",
                "gtg", "jk", "nvm"
            )
        ),
        PhraseCategory(
            "Friendly",
            listOf(
                "sure!", "yeah of course", "no problem",
                "sounds good", "thanks!", "anytime!",
                "super", "got it", "all good",
                "no worries", "take care", "have fun",
                "sweet", "awesome", "perfect",
                "love it", "nice", "great"
            )
        ),
        PhraseCategory(
            "Farewells",
            listOf(
                "bye", "ttyl", "see ya",
                "talk later", "goodnight", "have a good one",
                "catch ya later", "peace", "cya",
                "later", "take it easy", "until next time",
                "gotta go", "heading out", "so long",
                "cheers", "buh-bye", "bye for now"
            )
        ),
        PhraseCategory(
            "Office",
            listOf(
                "OOO", "WFH", "PTO",
                "AFK", "EOD", "FYI",
                "PFA", "Working on it", "Will check & revert",
                "In a meeting", "Approved", "Please review",
                "Shared on email", "Connect on call", "On it",
                "Reschedule?", "Thanks for update", "Looping team",
                "Looking into it", "Action required"
            )
        )
    )

    private var selectedCategoryIndex = 0
    private val currentCategoryPhrases = mutableListOf<String>()

    private val prefs by lazy {
        getSharedPreferences("coldtext_prefs", Context.MODE_PRIVATE)
    }

    private lateinit var rootLayout: LinearLayout
    private lateinit var titleTextView: TextView
    private lateinit var searchToggleBtn: Button
    private lateinit var refreshToggleBtn: Button
    private lateinit var accentToggleBtn: Button
    private lateinit var fontToggleBtn: Button
    private lateinit var themeToggleBtn: Button
    private lateinit var toneSeekBar: SeekBar
    private lateinit var toneLabelTextView: TextView

    // Real-Time Search Bar Views
    private lateinit var searchContainer: LinearLayout
    private lateinit var searchEditText: EditText
    private lateinit var closeSearchBtn: Button
    private var searchQuery: String = ""

    private lateinit var gridScrollView: ScrollView
    private lateinit var gridLayout: GridLayout
    private lateinit var tabLayout: LinearLayout
    private lateinit var smartSuggestionsLayout: LinearLayout

    // Edit Panel Views
    private lateinit var editPanelContainer: LinearLayout
    private lateinit var editTitleTextView: TextView
    private lateinit var phraseEditText: EditText
    private lateinit var deleteButton: Button
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var resetButton: Button

    // Utility Views
    private lateinit var utilityRow: LinearLayout
    private lateinit var switchKey: Button
    private lateinit var spaceKey: Button
    private lateinit var deleteKey: Button
    private lateinit var emojiRow: LinearLayout
    private val emojiButtons = mutableListOf<Button>()

    private var editingPhraseIndex: Int = -1

    override fun onCreateInputView(): View {
        loadPreferences()

        rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(6), dp(4), dp(6), dp(36))
        }

        rootLayout.setOnApplyWindowInsetsListener { v, insets ->
            val navBottom = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                insets.getInsets(android.view.WindowInsets.Type.navigationBars()).bottom
            } else {
                @Suppress("DEPRECATION")
                insets.systemWindowInsetBottom
            }
            val paddingBottom = maxOf(dp(36), navBottom + dp(8))
            v.setPadding(dp(6), dp(4), dp(6), paddingBottom)
            insets
        }

        // Header Row: [ ColdText ] [ 🔍 ] [ 🎨 ] [ Font ] [ 🌙/☀️ ]
        val headerRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(4), dp(2), dp(4), dp(2))
        }

        titleTextView = TextView(this).apply {
            text = "ColdText"
            textSize = 15f
            typeface = Typeface.DEFAULT_BOLD
        }
        headerRow.addView(
            titleTextView,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        )

        // Search Toggle Button
        searchToggleBtn = Button(this).apply {
            text = "🔍"
            textSize = 12f
            setOnClickListener {
                performHaptic(it)
                if (searchContainer.visibility == View.VISIBLE) {
                    closeSearchMode()
                } else {
                    openSearchMode()
                }
            }
        }
        headerRow.addView(
            searchToggleBtn,
            LinearLayout.LayoutParams(dp(34), dp(32)).apply { setMargins(dp(1), 0, dp(1), 0) }
        )

        // Refresh / Reset Category Button
        refreshToggleBtn = Button(this).apply {
            text = "🔄"
            textSize = 12f
            setOnClickListener {
                performHaptic(it)
                if (searchQuery.isNotEmpty()) {
                    closeSearchMode()
                } else {
                    resetActiveCategoryPhrases()
                    updatePhraseGrid()
                    updateSmartSuggestions()
                }
            }
        }
        headerRow.addView(
            refreshToggleBtn,
            LinearLayout.LayoutParams(dp(34), dp(32)).apply { setMargins(dp(1), 0, dp(1), 0) }
        )

        // Accent Theme Toggle Button
        accentToggleBtn = Button(this).apply {
            text = "🎨"
            textSize = 12f
            setOnClickListener {
                performHaptic(it)
                accentTheme = when (accentTheme) {
                    AccentTheme.BLUE -> AccentTheme.GREEN
                    AccentTheme.GREEN -> AccentTheme.PURPLE
                    AccentTheme.PURPLE -> AccentTheme.ORANGE
                    AccentTheme.ORANGE -> AccentTheme.ROSE
                    AccentTheme.ROSE -> AccentTheme.BLUE
                }
                prefs.edit().putString("accent_theme", accentTheme.name).apply()
                applyTheme()
            }
        }
        headerRow.addView(
            accentToggleBtn,
            LinearLayout.LayoutParams(dp(34), dp(32)).apply { setMargins(dp(1), 0, dp(1), 0) }
        )

        // Font Size Toggle Button
        fontToggleBtn = Button(this).apply {
            text = "Font: ${letterSize.name.lowercase().replaceFirstChar { it.uppercase() }}"
            isAllCaps = false
            textSize = 11f
            setOnClickListener {
                performHaptic(it)
                letterSize = when (letterSize) {
                    LetterSize.SMALL -> LetterSize.MEDIUM
                    LetterSize.MEDIUM -> LetterSize.LARGE
                    LetterSize.LARGE -> LetterSize.SMALL
                }
                prefs.edit().putString("letter_size", letterSize.name).apply()
                text = "Font: ${letterSize.name.lowercase().replaceFirstChar { it.uppercase() }}"
                updatePhraseGrid()
                setupTabs()
            }
        }
        headerRow.addView(
            fontToggleBtn,
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dp(32)).apply {
                setMargins(dp(1), 0, dp(2), 0)
            }
        )

        // Dark/Light Theme Toggle Button
        themeToggleBtn = Button(this).apply {
            text = if (isDarkMode) "🌙" else "☀️"
            textSize = 13f
            setOnClickListener {
                performHaptic(it)
                isDarkMode = !isDarkMode
                prefs.edit().putBoolean("is_dark_mode", isDarkMode).apply()
                text = if (isDarkMode) "🌙" else "☀️"
                applyTheme()
            }
        }
        headerRow.addView(
            themeToggleBtn,
            LinearLayout.LayoutParams(dp(36), dp(32))
        )

        rootLayout.addView(headerRow)

        // Real-Time Search Bar Container (Initially GONE)
        searchContainer = createSearchContainer()
        rootLayout.addView(searchContainer)

        // Tone / Warmth Slider Row
        val toneRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(4), 0, dp(4), dp(2))
        }

        toneLabelTextView = TextView(this).apply {
            text = "Tone: ${toneLevel.emoji} ${toneLevel.label}"
            textSize = 11f
            typeface = Typeface.DEFAULT_BOLD
        }
        toneRow.addView(
            toneLabelTextView,
            LinearLayout.LayoutParams(dp(105), LinearLayout.LayoutParams.WRAP_CONTENT)
        )

        toneSeekBar = SeekBar(this).apply {
            max = ToneLevel.entries.size - 1
            progress = toneLevel.ordinal
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser && progress in ToneLevel.entries.indices) {
                        toneLevel = ToneLevel.entries[progress]
                        prefs.edit().putString("tone_level", toneLevel.name).apply()
                        toneLabelTextView.text = "Tone: ${toneLevel.emoji} ${toneLevel.label}"
                        updatePhraseGrid()
                        updateSmartSuggestions()
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }
        toneRow.addView(
            toneSeekBar,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        )

        rootLayout.addView(toneRow)

        // Smart Suggestions Bar
        val smartScrollView = HorizontalScrollView(this).apply {
            isHorizontalScrollBarEnabled = false
        }
        smartSuggestionsLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        smartScrollView.addView(smartSuggestionsLayout)
        rootLayout.addView(
            smartScrollView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, dp(3))
            }
        )

        // Category Tabs
        val tabScrollView = HorizontalScrollView(this).apply {
            isHorizontalScrollBarEnabled = false
        }
        tabLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }
        tabScrollView.addView(tabLayout)
        rootLayout.addView(
            tabScrollView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, dp(3))
            }
        )

        // Edit/Add Inline Panel
        editPanelContainer = createEditPanel()
        rootLayout.addView(editPanelContainer)

        // Grid ScrollView with Constrained Max Height
        val configOrientation = resources.configuration.orientation
        val columns = if (configOrientation == Configuration.ORIENTATION_LANDSCAPE) 5 else 3

        gridScrollView = ScrollView(this).apply {
            isVerticalScrollBarEnabled = true
        }

        gridLayout = GridLayout(this).apply {
            columnCount = columns
        }
        gridScrollView.addView(
            gridLayout,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )

        val displayMetrics = resources.displayMetrics
        val maxGridHeight = (displayMetrics.heightPixels * keyboardSize.screenHeightRatio * 0.45f).toInt()

        rootLayout.addView(
            gridScrollView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                maxGridHeight
            )
        )

        // Utility Row
        utilityRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, dp(3), 0, dp(3))
        }

        switchKey = Button(this).apply {
            text = "🌐"
            textSize = 16f
            setOnClickListener {
                performHaptic(it)
                val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.showInputMethodPicker()
            }
            setOnLongClickListener {
                performHaptic(it)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    switchToNextInputMethod(false)
                } else {
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                    val token = window?.window?.attributes?.token
                    if (imm != null && token != null) {
                        @Suppress("DEPRECATION")
                        imm.switchToNextInputMethod(token, false)
                    }
                }
                true
            }
        }
        utilityRow.addView(
            switchKey,
            LinearLayout.LayoutParams(0, dp(36), 1f).apply {
                setMargins(dp(2), dp(1), dp(2), dp(1))
            }
        )

        spaceKey = Button(this).apply {
            text = "Space"
            isAllCaps = false
            textSize = 14f
            setOnClickListener {
                performHaptic(it)
                handleTextInput(" ")
            }
        }
        utilityRow.addView(
            spaceKey,
            LinearLayout.LayoutParams(0, dp(36), 3f).apply {
                setMargins(dp(2), dp(1), dp(2), dp(1))
            }
        )

        deleteKey = Button(this).apply {
            text = "⌫"
            textSize = 16f
            setOnClickListener {
                performHaptic(it)
                if (editPanelContainer.visibility == View.VISIBLE) {
                    val text = phraseEditText.text.toString()
                    if (text.isNotEmpty()) {
                        phraseEditText.setText(text.substring(0, text.length - 1))
                        phraseEditText.setSelection(phraseEditText.text.length)
                    }
                } else {
                    sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL)
                }
            }
        }
        utilityRow.addView(
            deleteKey,
            LinearLayout.LayoutParams(0, dp(36), 1f).apply {
                setMargins(dp(2), dp(1), dp(2), dp(1))
            }
        )

        rootLayout.addView(utilityRow)

        // Emoji Quick Bar
        emojiRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }
        emojiButtons.clear()
        listOf("👍", "🙂", "❤️", "🙄", "🔥", "💯").forEach { emoji ->
            val key = Button(this).apply {
                text = emoji
                textSize = 16f
                setOnClickListener {
                    performHaptic(it)
                    handleTextInput(emoji)
                }
            }
            emojiButtons.add(key)
            emojiRow.addView(
                key,
                LinearLayout.LayoutParams(0, dp(34), 1f).apply {
                    setMargins(dp(1), dp(1), dp(1), dp(1))
                }
            )
        }
        rootLayout.addView(emojiRow)

        setupTabs()
        loadActiveCategoryPhrases()
        updatePhraseGrid()
        updateSmartSuggestions()
        applyTheme()

        return rootLayout
    }

    private fun createSearchContainer(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(4), dp(2), dp(4), dp(4))
            visibility = View.GONE

            searchEditText = EditText(this@ColdTextIME).apply {
                hint = "Search phrases..."
                textSize = 13f
                setPadding(dp(8), dp(4), dp(8), dp(4))
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        searchQuery = s?.toString()?.trim() ?: ""
                        updatePhraseGrid()
                    }
                    override fun afterTextChanged(s: Editable?) {}
                })
            }
            addView(
                searchEditText,
                LinearLayout.LayoutParams(0, dp(34), 1f).apply { setMargins(0, 0, dp(4), 0) }
            )

            closeSearchBtn = Button(this@ColdTextIME).apply {
                text = "❌"
                textSize = 11f
                setOnClickListener {
                    performHaptic(it)
                    closeSearchMode()
                }
            }
            addView(
                closeSearchBtn,
                LinearLayout.LayoutParams(dp(36), dp(34))
            )
        }
    }

    private fun openSearchMode() {
        searchContainer.visibility = View.VISIBLE
        searchEditText.setText("")
        searchEditText.requestFocus()
    }

    private fun closeSearchMode() {
        searchQuery = ""
        searchEditText.setText("")
        searchContainer.visibility = View.GONE
        updatePhraseGrid()
    }

    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        loadPreferences()
        updateSmartSuggestions()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (::gridLayout.isInitialized) {
            val columns = if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) 5 else 3
            gridLayout.columnCount = columns
            updatePhraseGrid()
        }
    }

    private fun loadPreferences() {
        isDarkMode = prefs.getBoolean("is_dark_mode", true)
        isHapticsEnabled = prefs.getBoolean("is_haptics_enabled", true)

        val savedTone = prefs.getString("tone_level", ToneLevel.NEUTRAL.name)
        toneLevel = try { ToneLevel.valueOf(savedTone ?: "") } catch (_: Exception) { ToneLevel.NEUTRAL }

        val savedLetter = prefs.getString("letter_size", LetterSize.MEDIUM.name)
        letterSize = try { LetterSize.valueOf(savedLetter ?: "") } catch (_: Exception) { LetterSize.MEDIUM }

        val savedKbdSize = prefs.getString("keyboard_size", KeyboardSize.MEDIUM.name)
        keyboardSize = try { KeyboardSize.valueOf(savedKbdSize ?: "") } catch (_: Exception) { KeyboardSize.MEDIUM }

        val savedAccent = prefs.getString("accent_theme", AccentTheme.BLUE.name)
        accentTheme = try { AccentTheme.valueOf(savedAccent ?: "") } catch (_: Exception) { AccentTheme.BLUE }
    }

    private fun performHaptic(view: View) {
        if (isHapticsEnabled) {
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        }
    }

    private fun handleTextInput(text: String) {
        val adjustedText = adjustPhraseForTone(text)
        if (editPanelContainer.visibility == View.VISIBLE) {
            phraseEditText.append(adjustedText)
        } else {
            currentInputConnection?.commitText(adjustedText, 1)
            updateSmartSuggestions()
        }
    }

    private fun adjustPhraseForTone(phrase: String): String {
        val clean = phrase.trim()
        if (clean.length <= 1 && !clean.all { it.isLetter() }) return phrase

        return when (toneLevel) {
            ToneLevel.COLDEST -> when (clean.lowercase()) {
                "okay", "sure!", "yeah of course", "sounds good" -> "K"
                "no problem", "no worries", "you're welcome!" -> "np"
                "thanks!", "thank you so much!" -> "thx"
                "talk later", "catch ya later", "bye for now", "goodbye!" -> "bye"
                "i don't know", "not sure", "i'm not quite sure about that." -> "idk"
                "what do you mean" -> "wdym"
                "be right back" -> "brb"
                else -> clean.lowercase()
            }
            ToneLevel.NEUTRAL -> clean
            ToneLevel.WARM -> when (clean.lowercase()) {
                "k", "ok" -> "Okay, sounds good!"
                "sure" -> "Sure thing!"
                "yeah" -> "Yeah of course"
                "bye", "ttyl" -> "Talk to you soon!"
                "idk" -> "Not quite sure"
                "thanks!", "thx" -> "Thanks a lot!"
                "no problem", "np" -> "No problem at all!"
                "brb" -> "Be right back!"
                "ooo" -> "Out of office today"
                "wfh" -> "Working from home today"
                "pto" -> "On PTO today"
                "afk" -> "Away from keyboard"
                else -> if (clean.endsWith(".") || clean.endsWith("!")) clean else "$clean!"
            }
            ToneLevel.FRIENDLIEST -> when (clean.lowercase()) {
                "k", "ok", "sure" -> "Aww sure thing! ❤️"
                "yeah" -> "Yeah absolutely!! 😊"
                "bye", "ttyl" -> "Love to talk soon!! 🥰"
                "idk" -> "Hmm not super sure, let me check! 🤔"
                "thanks!", "thx" -> "Thanks so much!! Hugs! ✨"
                "no problem", "np" -> "Anytime!! Happy to help! ❤️"
                "brb" -> "BRB! Back in a second! ✨"
                "ooo" -> "Out of office today! 🌴"
                "wfh" -> "Working from home today! 🏡"
                "pto" -> "On PTO today! Enjoying time off! ☀️"
                "afk" -> "Away from keyboard, back soon! ✨"
                else -> "$clean!! ❤️"
            }
        }
    }

    private fun updateSmartSuggestions() {
        if (!::smartSuggestionsLayout.isInitialized) return
        smartSuggestionsLayout.removeAllViews()

        val textBefore = currentInputConnection?.getTextBeforeCursor(100, 0)?.toString()?.trim() ?: ""
        val lowerText = textBefore.lowercase()

        val rawSuggestions = when {
            lowerText.endsWith("?") || lowerText.contains("where") || lowerText.contains("when") || lowerText.contains("what") || lowerText.contains("who") -> {
                listOf("On my way", "Yes", "No", "5 mins", "Not sure")
            }
            lowerText.contains("hi") || lowerText.contains("hey") || lowerText.contains("hello") || lowerText.contains("yo") -> {
                listOf("Hey!", "What's up?", "How are you?", "Good morning")
            }
            lowerText.contains("thanks") || lowerText.contains("thx") -> {
                listOf("You're welcome!", "Anytime!", "No problem!", "My pleasure")
            }
            else -> {
                listOf("On my way!", "Sounds good", "Let me check", "Call you later", "Thanks!")
            }
        }

        rawSuggestions.forEach { raw ->
            val suggestion = adjustPhraseForTone(raw)
            val btn = Button(this).apply {
                text = suggestion
                isAllCaps = false
                textSize = letterSize.keySp - 1f
                setTextColor(currentTheme.accentText)
                background = createDrawable(currentTheme.suggestionBg, 6)
                setOnClickListener {
                    performHaptic(it)
                    currentInputConnection?.commitText(suggestion, 1)
                    updateSmartSuggestions()
                }
            }
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                dp(30)
            ).apply {
                setMargins(dp(2), 0, dp(2), 0)
            }
            smartSuggestionsLayout.addView(btn, params)
        }
    }

    private fun createEditPanel(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(6), dp(6), dp(6), dp(6))
            visibility = View.GONE

            editTitleTextView = TextView(this@ColdTextIME).apply {
                text = "Edit Phrase"
                textSize = 13f
                typeface = Typeface.DEFAULT_BOLD
            }
            addView(editTitleTextView)

            phraseEditText = EditText(this@ColdTextIME).apply {
                hint = "Enter custom phrase..."
                textSize = 14f
                setPadding(dp(6), dp(6), dp(6), dp(6))
            }
            val editParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, dp(4), 0, dp(4))
            }
            addView(phraseEditText, editParams)

            val buttonsRow = LinearLayout(this@ColdTextIME).apply {
                this.orientation = LinearLayout.HORIZONTAL
            }

            saveButton = Button(this@ColdTextIME).apply {
                text = "Save"
                isAllCaps = false
                textSize = 12f
                setOnClickListener {
                    performHaptic(it)
                    val newPhrase = phraseEditText.text.toString().trim()
                    if (newPhrase.isNotEmpty()) {
                        if (editingPhraseIndex == -1) {
                            currentCategoryPhrases.add(newPhrase)
                        } else if (editingPhraseIndex in currentCategoryPhrases.indices) {
                            currentCategoryPhrases[editingPhraseIndex] = newPhrase
                        }
                        saveActiveCategoryPhrases()
                        updatePhraseGrid()
                    }
                    hideEditPanel()
                }
            }
            buttonsRow.addView(
                saveButton,
                LinearLayout.LayoutParams(0, dp(34), 1f).apply { setMargins(dp(2), 0, dp(2), 0) }
            )

            deleteButton = Button(this@ColdTextIME).apply {
                text = "Delete"
                isAllCaps = false
                textSize = 12f
                setOnClickListener {
                    performHaptic(it)
                    if (editingPhraseIndex in currentCategoryPhrases.indices) {
                        currentCategoryPhrases.removeAt(editingPhraseIndex)
                        saveActiveCategoryPhrases()
                        updatePhraseGrid()
                    }
                    hideEditPanel()
                }
            }
            buttonsRow.addView(
                deleteButton,
                LinearLayout.LayoutParams(0, dp(34), 1f).apply { setMargins(dp(2), 0, dp(2), 0) }
            )

            cancelButton = Button(this@ColdTextIME).apply {
                text = "Cancel"
                isAllCaps = false
                textSize = 12f
                setOnClickListener {
                    performHaptic(it)
                    hideEditPanel()
                }
            }
            buttonsRow.addView(
                cancelButton,
                LinearLayout.LayoutParams(0, dp(34), 1f).apply { setMargins(dp(2), 0, dp(2), 0) }
            )

            resetButton = Button(this@ColdTextIME).apply {
                text = "Reset"
                isAllCaps = false
                textSize = 11f
                setOnClickListener {
                    performHaptic(it)
                    resetActiveCategoryPhrases()
                    updatePhraseGrid()
                    hideEditPanel()
                }
            }
            buttonsRow.addView(
                resetButton,
                LinearLayout.LayoutParams(0, dp(34), 1f).apply { setMargins(dp(2), 0, dp(2), 0) }
            )

            addView(buttonsRow)
        }
    }

    private fun showEditPanel(index: Int) {
        editingPhraseIndex = index
        if (index == -1) {
            editTitleTextView.text = "Add Phrase to '${categories[selectedCategoryIndex].name}'"
            phraseEditText.setText("")
            deleteButton.visibility = View.GONE
        } else {
            editTitleTextView.text = "Edit Phrase (Long-Pressed)"
            phraseEditText.setText(currentCategoryPhrases.getOrNull(index) ?: "")
            deleteButton.visibility = View.VISIBLE
        }
        phraseEditText.setSelection(phraseEditText.text.length)
        editPanelContainer.visibility = View.VISIBLE
    }

    private fun hideEditPanel() {
        editPanelContainer.visibility = View.GONE
        editingPhraseIndex = -1
    }

    private fun setupTabs() {
        tabLayout.removeAllViews()
        categories.forEachIndexed { index, category ->
            val isSelected = index == selectedCategoryIndex
            val tabButton = Button(this).apply {
                text = category.name
                isAllCaps = false
                textSize = letterSize.tabSp
                setTextColor(if (isSelected) currentTheme.tabSelectedText else currentTheme.tabText)
                background = createDrawable(
                    if (isSelected) currentTheme.tabSelectedBg else currentTheme.tabBg,
                    6
                )
                setOnClickListener {
                    performHaptic(it)
                    if (selectedCategoryIndex != index) {
                        selectedCategoryIndex = index
                        hideEditPanel()
                        loadActiveCategoryPhrases()
                        setupTabs()
                        updatePhraseGrid()
                    }
                }
            }
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                dp(32)
            ).apply {
                setMargins(dp(2), 0, dp(2), 0)
            }
            tabLayout.addView(tabButton, params)
        }
    }

    private fun loadActiveCategoryPhrases() {
        currentCategoryPhrases.clear()
        val categoryName = categories[selectedCategoryIndex].name
        currentCategoryPhrases.addAll(getPhrasesForCategory(categoryName))
    }

    private fun getPhrasesForCategory(categoryName: String): List<String> {
        val saved = prefs.getString("cat_$categoryName", null)
        return if (saved != null) {
            saved.split("|||").filter { it.isNotEmpty() }
        } else {
            categories.find { it.name == categoryName }?.defaultPhrases ?: emptyList()
        }
    }

    private fun saveActiveCategoryPhrases() {
        val categoryName = categories[selectedCategoryIndex].name
        val serialized = currentCategoryPhrases.joinToString("|||")
        prefs.edit().putString("cat_$categoryName", serialized).apply()
    }

    private fun resetActiveCategoryPhrases() {
        val categoryName = categories[selectedCategoryIndex].name
        prefs.edit().remove("cat_$categoryName").apply()
        loadActiveCategoryPhrases()
    }

    private fun updatePhraseGrid() {
        gridLayout.removeAllViews()

        val phrasesToDisplay = if (searchQuery.isNotEmpty()) {
            categories.flatMap { cat -> getPhrasesForCategory(cat.name) }
                .filter { it.contains(searchQuery, ignoreCase = true) }
                .distinct()
        } else {
            currentCategoryPhrases
        }

        phrasesToDisplay.forEachIndexed { index, phrase ->
            val displayedText = adjustPhraseForTone(phrase)
            val key = Button(this).apply {
                text = displayedText
                isAllCaps = false
                textSize = letterSize.keySp
                setTextColor(currentTheme.keyText)
                background = createDrawable(currentTheme.keyBg, 6)
                setOnClickListener {
                    performHaptic(it)
                    handleTextInput(phrase)
                }
                setOnLongClickListener {
                    performHaptic(it)
                    if (searchQuery.isEmpty()) {
                        showEditPanel(index)
                    }
                    true
                }
            }

            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = dp(36)
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(dp(2), dp(2), dp(2), dp(2))
            }

            gridLayout.addView(key, params)
        }

        if (searchQuery.isEmpty()) {
            val addKey = Button(this).apply {
                text = "+ Add"
                isAllCaps = false
                textSize = letterSize.keySp - 1f
                setTextColor(currentTheme.accentText)
                background = createDrawable(currentTheme.accentBg, 6)
                setOnClickListener {
                    performHaptic(it)
                    showEditPanel(-1)
                }
            }
            val addParams = GridLayout.LayoutParams().apply {
                width = 0
                height = dp(36)
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(dp(2), dp(2), dp(2), dp(2))
            }
            gridLayout.addView(addKey, addParams)
        }
    }

    private fun applyTheme() {
        val t = currentTheme
        rootLayout.setBackgroundColor(t.bg)
        titleTextView.setTextColor(t.headerText)
        toneLabelTextView.setTextColor(t.headerText)

        searchToggleBtn.setTextColor(t.keyText)
        searchToggleBtn.background = createDrawable(t.utilityBg, 6)

        refreshToggleBtn.setTextColor(t.keyText)
        refreshToggleBtn.background = createDrawable(t.utilityBg, 6)

        accentToggleBtn.setTextColor(t.accentText)
        accentToggleBtn.background = createDrawable(t.accentBg, 6)

        fontToggleBtn.setTextColor(t.keyText)
        fontToggleBtn.background = createDrawable(t.utilityBg, 6)

        themeToggleBtn.setTextColor(t.keyText)
        themeToggleBtn.background = createDrawable(t.utilityBg, 6)

        if (::searchContainer.isInitialized) {
            searchContainer.setBackgroundColor(t.cardBg)
            searchEditText.setTextColor(t.keyText)
            searchEditText.setHintTextColor(if (isDarkMode) Color.GRAY else Color.DKGRAY)
            searchEditText.background = createDrawable(t.keyBg, 6)
            closeSearchBtn.setTextColor(t.keyText)
            closeSearchBtn.background = createDrawable(t.utilityBg, 6)
        }

        editPanelContainer.background = createDrawable(t.cardBg, 8)
        editTitleTextView.setTextColor(t.headerText)
        phraseEditText.setTextColor(t.keyText)
        phraseEditText.setHintTextColor(if (isDarkMode) Color.GRAY else Color.DKGRAY)
        phraseEditText.background = createDrawable(t.keyBg, 6)

        saveButton.background = createDrawable(Color.rgb(40, 110, 60), 6)
        saveButton.setTextColor(Color.WHITE)
        deleteButton.background = createDrawable(Color.rgb(140, 40, 40), 6)
        deleteButton.setTextColor(Color.WHITE)
        cancelButton.background = createDrawable(if (isDarkMode) Color.rgb(60, 60, 70) else Color.rgb(180, 185, 195), 6)
        cancelButton.setTextColor(if (isDarkMode) Color.WHITE else Color.BLACK)
        resetButton.background = createDrawable(if (isDarkMode) Color.rgb(80, 60, 30) else Color.rgb(200, 180, 140), 6)
        resetButton.setTextColor(if (isDarkMode) Color.LTGRAY else Color.BLACK)

        switchKey.setTextColor(t.keyText)
        switchKey.background = createDrawable(t.utilityBg, 6)

        spaceKey.setTextColor(t.keyText)
        spaceKey.background = createDrawable(t.utilityBg, 6)

        deleteKey.setTextColor(t.keyText)
        deleteKey.background = createDrawable(t.utilityBg, 6)

        emojiButtons.forEach { btn ->
            btn.setTextColor(t.keyText)
            btn.background = createDrawable(t.utilityBg, 6)
        }

        setupTabs()
        updatePhraseGrid()
        updateSmartSuggestions()
    }

    private fun createDrawable(bgColor: Int, cornerRadiusDp: Int): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(bgColor)
            cornerRadius = dp(cornerRadiusDp).toFloat()
        }
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
