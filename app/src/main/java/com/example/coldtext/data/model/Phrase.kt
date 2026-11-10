package com.example.coldtext.data.model

data class WordGloss(
    val word: String,
    val nativeScript: String,
    val englishMeaning: String,
    val partOfSpeech: String
)

enum class Topic(val displayName: String, val description: String) {
    GREETINGS("Greetings", "Hello, bye, and casual check-ins"),
    EVERYDAY("Everyday Talk", "Common daily banter & chats"),
    FOOD("Food & Dining", "Ordering, tea, snacks & meals"),
    TRAVEL("Travel & Commute", "Autos, buses, routes & directions"),
    SHOPPING("Shopping", "Bargaining, prices & buying items"),
    WORK("Work & College", "Office talk, meetings & study breaks")
}

enum class Difficulty(val label: String) {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate")
}

enum class Register(val label: String) {
    INFORMAL("Informal"),
    POLITE("Polite"),
    FORMAL("Formal"),
    SLANG("Slang / Colloquial")
}

enum class ReviewStatus(val label: String) {
    REVIEWED("Reviewed"),
    UNREVIEWED_SAMPLE("Sample Content (Unreviewed)")
}

data class Phrase(
    val id: String,
    val targetLanguageId: String,
    val nativeScript: String,
    val latinTransliteration: String,
    val englishMeaning: String,
    val wordGloss: List<WordGloss>,
    val pronunciationNotes: String,
    val topic: Topic,
    val difficulty: Difficulty,
    val register: Register,
    val regionNotes: String? = null,
    val audioRef: String? = null,
    val reviewStatus: ReviewStatus = ReviewStatus.REVIEWED
)
