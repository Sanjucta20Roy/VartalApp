# VartalApp - Learn Indian Regional Languages & Varieties through Code-Switching

**VartalApp** is a friendly, beginner-focused Android application for learning regional Indian languages and varieties through everyday **code-switching** phrases: natural sentences that mix English with words from regional Indian languages.

> **Example:**  
> *“Swalpa chill māḍi”*  
> - **Word Breakdown Gloss**: *Swalpa (a little) + chill (relax) + māḍi (do/make - polite request)*  
> - **Natural English**: *“Let’s chill a little / Relax a bit.”*  
> Displays native script (ಸ್ವಲ್ಪ chill ಮಾಡಿ), readable Latin transliteration, literal vs natural gloss, pronunciation guide, register/politeness flags, verified speaker review audit metadata, and TTS audio.

---

## 🌟 Main Screens & Features

1. **Welcome & Language Selection (Onboarding)**
   - Explains code-switching methodology.
   - Target course selection displaying explicit course status (**Starter Unit 1** vs **Preview Available** vs **In Development** vs **Roadmap**).
   - Learner primary motivation selection (*Casual Hangouts, Travel & Transit, Work & Office, Moving to City*) and preferred script mode (*Both Native & Latin* vs *Latin Only*).

2. **Home Dashboard**
   - Displays current course progress, streak counter (e.g. 🔥 1 Day Streak), and daily goal progress bar.
   - Animated mascot companion (`AnimatedMascotView`).
   - 10 Everyday Situation Cards: Greetings, Friends Hangout, Street Food, Autos & Local Commute, Travel, Shopping, Corporate Talk, Official & Govt, College, Help & Emergencies.

3. **Phrase Lesson**
   - Latin transliteration, native script Unicode, Natural English Meaning, and Literal Word-by-Word Gloss.
   - Register chips (*Informal, Polite, Formal, Slang*) and Situation Register Variations switcher.
   - Verified Speaker Review metadata (*Reviewer Group, Audit Method, Region Variety, Dispute Status*).
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
   - Offline Feedback Reports manager (View, Copy to Clipboard, Delete All).
   - Audio speech rate control slider (0.5x to 1.5x) and text size scaling.
   - Target language switcher and Local Storage & Speech Data disclosures.

---

## 🌐 Language Catalog & Regional Varieties (39 Total)

- **Starter Unit 1 Course (`CourseStatus.AVAILABLE`)**:
  - 🟢 **Kannada**: 12 Verified Phrases (*Kannada Starter Unit 1*)
- **Previews Available (`CourseStatus.PREVIEW`)**:
  - 🟠 **Tamil**: 2 Preview Phrases
  - 🟠 **Telugu**: 1 Preview Phrase
  - 🟠 **Hindi**: 1 Preview Phrase
  - 🟠 **Malayalam**: 1 Preview Phrase
  - 🟠 **Marathi**: 1 Preview Phrase
  - 🟠 **Bengali**: 1 Preview Phrase
  - 🟠 **Gujarati**: 1 Preview Phrase
  - 🟠 **Punjabi**: 1 Preview Phrase
- **In Active Development (`CourseStatus.IN_DEVELOPMENT`)**: Odia, Assamese, Urdu, Konkani.
- **Planned Roadmap (`CourseStatus.ROADMAP`)**: 26 Regional Languages & Local Dialects (Maithili, Santali, Kashmiri, Nepali, Dogri, Manipuri/Meitei, Bodo, Sanskrit, Sindhi, Tulu, Kodava, Bhojpuri, Magahi, Angika, Rajasthani, Chhattisgarhi, Haryanvi, Garhwali, Kumaoni, Marwari, Bundeli, Malvi, Bhili, Gondi, Mizo, Khasi).

---

## 🚀 How to Run the App

1. Open the project folder in **Android Studio**.
2. Connect an Android device or launch an AVD Emulator (API 23+).
3. Build and run via Android Studio or command line:
   ```bash
   ./gradlew assembleDebug
   ```
