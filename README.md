# VartalApp - Learn Indian Regional Languages through Code-Switching

**VartalApp** is a friendly, beginner-focused Android application for learning regional Indian languages through everyday **code-switching** phrases: natural sentences that mix English with words from regional Indian languages.

> **Example:**  
> *“Swalpa chill māḍi”* → *“Let’s chill a little.”*  
> Displays native script (ಸ್ವಲ್ಪ chill ಮಾಡಿ), readable Latin transliteration, English meaning, word-by-word gloss, pronunciation guide, register/politeness flags, and TTS audio playback.

---

## 🌟 Main Screens & Features

1. **Welcome & Language Selection (Onboarding)**
   - Explains the code-switching learning methodology in one sentence.
   - Learner target language selection supporting **32+ Indian regional languages**.
   - User level selection (Beginner, Casual, Conversational) and daily phrase goal setting.

2. **Home Dashboard**
   - Displays current course status, streak counter (e.g. 🔥 1 Day Streak), and daily goal progress bar.
   - "Continue Learning" quick launch button.
   - Topic category cards: Greetings, Everyday Talk, Food & Dining, Travel & Commute, Shopping, and Work & College.

3. **Phrase Lesson**
   - Shows Latin transliteration, native script Unicode text, and English meaning.
   - Register chips (Informal, Polite, Formal, Slang) and Review Status flags (`REVIEWED` vs `UNREVIEWED_SAMPLE`).
   - Interactive word-by-word breakdown gloss cards (word, native script, English gloss, part of speech).
   - Pronunciation guide and regional/dialect context notes.
   - Audio TTS playback button, save favorite bookmark toggle, and Next/Previous navigation.

4. **Practice Quizzes**
   - **Multiple Choice Meaning**: Test recall of phrase meanings.
   - **Listening Match**: Match native script text to spoken TTS audio.
   - **Phrase Builder**: Unscramble word chips into correct sentence order.
   - Instant feedback, explanations, and retry capability.

5. **Phrasebook & Search**
   - Real-time search across English meanings, native script, and Latin transliteration.
   - Filter by topic and favorites.

6. **Progress & Settings**
   - Summary of completed lessons, streak counter, and saved favorites.
   - Audio speech rate control slider (0.5x to 1.5x) and text size scaling.
   - Course target language switcher and daily goal adjustment.

---

## 🌐 Supported Regional Indian Languages (32 Total)

VartalApp features structured support for 32 Indian regional languages and dialects across all regions of India:

1. **Kannada** (ಕನ್ನಡ) — *Karnataka*
2. **Tamil** (தமிழ்) — *Tamil Nadu & Puducherry*
3. **Telugu** (తెలుగు) — *Andhra Pradesh & Telangana*
4. **Hindi** (हिन्दी) — *North & Central India*
5. **Malayalam** (മലയാളം) — *Kerala & Lakshadweep*
6. **Marathi** (मराठी) — *Maharashtra*
7. **Bengali** (বাংলা) — *West Bengal & Tripura*
8. **Gujarati** (ગુજરાતી) — *Gujarat*
9. **Punjabi** (ਪੰਜਾਬੀ) — *Punjab*
10. **Odia** (ଓଡ଼ିଆ) — *Odisha*
11. **Assamese** (অসমীয়া) — *Assam*
12. **Urdu** (اردو) — *Pan-India*
13. **Konkani** (ಕೊಂಕಣಿ / कोंकणी) — *Goa & Coastal Karnataka*
14. **Maithili** (मैथिली) — *Bihar & Jharkhand*
15. **Santali** (ᱥᱟᱱᱛᱟᱲᱤ) — *Jharkhand & Odisha*
16. **Kashmiri** (कॉशुर / کٲشُر) — *Jammu & Kashmir*
17. **Nepali** (नेपाली) — *Sikkim & North Bengal*
18. **Dogri** (डोगरी) — *Jammu*
19. **Manipuri (Meitei)** (ꯃꯩꯇꯩꯂꯣꯟ) — *Manipur*
20. **Bodo** (बर') — *Assam*
21. **Sanskrit** (संस्कृतम्) — *Pan-India*
22. **Sindhi** (سنڌي / सिन्धी) — *Gujarat & Rajasthan*
23. **Tulu** (ತುಳು) — *Tulunadu (Karnataka)*
24. **Kodava** (ಕೊಡವ) — *Kodagu (Karnataka)*
25. **Bhojpuri** (भोजपुरी) — *Bihar & UP*
26. **Magahi** (मगही) — *Southern Bihar*
27. **Angika** (अंगिका) — *Eastern Bihar*
28. **Rajasthani** (राजस्थानी) — *Rajasthan*
29. **Chhattisgarhi** (छत्तीसगढ़ी) — *Chhattisgarh*
30. **Haryanvi** (हरियाणवी) — *Haryana*
31. **Garhwali** (गढ़वाली) — *Uttarakhand*
32. **Kumaoni** (कुमाऊँनी) — *Uttarakhand*

---

## 🏗️ Project Architecture

```
app/src/main/java/com/example/coldtext/
├── data/
│   ├── audio/
│   │   └── TtsHelper.kt            # Android TextToSpeech manager
│   ├── model/
│   │   ├── Language.kt             # Metadata for 32 Indian regional languages
│   │   ├── Phrase.kt               # Phrase, WordGloss, Topic, Register data models
│   │   └── UserPreferences.kt      # User settings & learning progress state
│   ├── repository/
│   │   └── PhraseRepository.kt     # Central repository & SharedPreferences persistence
│   └── sample/
│       ├── KannadaPhrases.kt       # Authentic Kannada code-switching dataset
│       └── MoreLanguagesPhrases.kt  # Datasets for Tamil, Telugu, Hindi, Malayalam, Marathi
├── ui/
│   ├── navigation/
│   │   └── AppNavigation.kt        # Compose Navigation routes & bottom bar tabs
│   ├── screens/
│   │   ├── HomeScreen.kt
│   │   ├── PhraseLessonScreen.kt
│   │   ├── PhrasebookScreen.kt
│   │   ├── PracticeScreen.kt
│   │   ├── SettingsScreen.kt
│   │   └── WelcomeScreen.kt
│   └── theme/
│       ├── Color.kt                # Saffron/Teal Indian-inspired Material 3 colors
│       └── Theme.kt                # Light & Dark mode Material 3 theme
└── MainActivity.kt                 # Main app entry point with Jetpack Compose scaffolding
```

---

## 🚀 How to Run the App

1. Open the project folder in **Android Studio**.
2. Connect an Android device or launch an Android Virtual Device (Emulator) running API 23+.
3. Build and run the `app` configuration.

Alternatively, build via Gradle command line:
```bash
./gradlew assembleDebug
```
