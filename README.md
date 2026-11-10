# VartalApp - Learn Indian Regional Languages through Code-Switching

**VartalApp** is a friendly, beginner-focused Android application for learning regional Indian languages through everyday **code-switching** phrases: natural sentences that mix English with words from regional Indian languages.

> **Example:**  
> *“Swalpa chill māḍi”*  
> - **Word Breakdown Gloss**: *Swalpa (a little) + chill (relax) + māḍi (do - polite request)*  
> - **Natural English**: *“Let’s chill a little / Relax a bit.”*  
> Displays native script (ಸ್ವಲ್ಪ chill ಮಾಡಿ), readable Latin transliteration, literal vs natural gloss, pronunciation guide, register/politeness flags, verified speaker review audit metadata, and TTS audio.

---

## 🌟 Main Screens & Features

1. **Welcome & Language Selection (Onboarding)**
   - Explains code-switching methodology.
   - Target course selection displaying explicit course status (**Starter Course** vs **Preview Available** vs **In Development** vs **Roadmap**).
   - Learner primary motivation selection (*Casual Hangouts, Travel & Transit, Work & Office, Moving to City*) and preferred script mode (*Both Native & Latin* vs *Latin Only*).

2. **Home Dashboard**
   - Displays current course progress, streak counter (e.g. 🔥 1 Day Streak), and daily goal progress bar.
   - Animated mascot companion (`AnimatedMascotView`).
   - 10 Everyday Situation Cards: Greetings, Friends Hangout, Street Food, Autos & Local Commute, Travel, Shopping, Corporate Talk, Official & Govt, College, Help & Emergencies.

3. **Phrase Lesson**
   - Latin transliteration, native script Unicode, Natural English Meaning, and Literal Word-by-Word Gloss.
   - Register chips (*Informal, Polite, Formal, Slang*) and Situation Register Variations switcher.
   - Verified Speaker Review metadata (*Reviewer Name, Audit Method, Region Variety, Dispute Status*).
   - **Offline Flag / Suggest Correction** dialog with local device saving.
   - TTS audio player with voice readiness status badge, safe intent launcher, and manual settings fallback.

4. **Practice Quizzes & Spaced Repetition**
   - **Spaced Repetition Review Queue**: Automatically queues struggling phrases for review.
   - **Multiple Choice Meaning**: Match phrases to 4 English meaning options.
   - **Listening Match**: Match native script text to spoken TTS audio.
   - **Phrase Builder**: Unscramble word chips into correct sentence order.
   - **Non-Color Accessible Feedback**: Shape-based icons (`✓` / `✗`) and clear text feedback.

5. **Phrasebook & Search**
   - Real-time search across English meanings, literal glosses, native script, and transliteration.
   - Filter by topic and saved favorites.

6. **Progress & Settings**
   - Summary of completed lessons, streak counter, and saved favorites.
   - Audio speech rate control slider (0.5x to 1.5x) and text size scaling.
   - Target language switcher and 100% Offline & Privacy Transparency information.

---

## 🌐 Language Course & Preview Status

- **Starter Course (`CourseStatus.AVAILABLE`)**:
  - 🟢 **Kannada** (ಕನ್ನಡ): 12 Verified Phrases (*Complete Starter Course*)
- **Preview Available (`CourseStatus.PREVIEW`)**:
  - 🟠 **Tamil** (தமிழ்): 2 Phrases (*Early Preview*)
  - 🟠 **Telugu** (తెలుగు): 1 Phrase (*Early Preview*)
  - 🟠 **Hindi** (हिन्दी): 1 Phrase (*Early Preview*)
  - 🟠 **Malayalam** (മലയാളം): 1 Phrase (*Early Preview*)
  - 🟠 **Marathi** (मराठी): 1 Phrase (*Early Preview*)
- **In Active Development (`CourseStatus.IN_DEVELOPMENT`)**: Bengali, Gujarati, Punjabi, Odia, Assamese, Urdu, Konkani.
- **Extensible Language Roadmap (`CourseStatus.ROADMAP`)**: 19 additional regional languages & local dialects.

---

## 🚀 How to Run the App

1. Open the project folder in **Android Studio**.
2. Connect an Android device or launch an AVD Emulator (API 23+).
3. Build and run via Android Studio or command line:
   ```bash
   ./gradlew assembleDebug
   ```
