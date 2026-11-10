package com.example.coldtext.data.model

data class WordGloss(
    val word: String,
    val nativeScript: String,
    val englishMeaning: String,
    val partOfSpeech: String
)

data class PhraseVariation(
    val register: Register,
    val latinTransliteration: String,
    val nativeScript: String,
    val note: String
)

enum class Topic(val displayName: String, val description: String, val emoji: String) {
    GREETINGS("Greetings & Casual", "Hello, bye, and friendly check-ins", "👋"),
    FRIENDS_HANGOUT("Friends Hangout & Chillin'", "Chai, cafes, plan making & jokes", "☕"),
    FOOD_STREET("Street Food & Dining", "Dosa, filter coffee, spicy food & ordering", "🍲"),
    LOCAL_TRAVEL("Autos & Local Commute", "Metering, landmark directions & auto drivers", "🛺"),
    TRAVEL_TOURISM("Travel & Sightseeing", "Outstation trips, hotels, cabs & spots", "🧳"),
    SHOPPING_BARGAIN("Shopping & Bargaining", "Street shopping, price negotiation & discounts", "🛍️"),
    PROFESSIONAL_WORK("Corporate & Office Talk", "Standups, coffee breaks, deadlines & emails", "💼"),
    OFFICIAL_GOVT("Official & Administrative", "Govt offices, bank paperwork & formal requests", "🏛️"),
    COLLEGE_CAMPUS("College & Campus Life", "Classrooms, canteen, exams & fest talk", "🎓"),
    EMERGENCY_HELP("Help & Emergencies", "Asking directions, medical shops & lost items", "🆘")
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
    REVIEWED("Verified Native Review"),
    UNREVIEWED_SAMPLE("Sample Content (Awaiting Native Review)")
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
    val literalGloss: String? = null,
    val regionNotes: String? = null,
    val audioRef: String? = null,
    val reviewStatus: ReviewStatus = ReviewStatus.REVIEWED,
    val reviewerName: String? = null,
    val reviewDate: String? = null,
    val source: String? = null,
    val isFlagged: Boolean = false,
    val variations: List<PhraseVariation> = emptyList()
)
