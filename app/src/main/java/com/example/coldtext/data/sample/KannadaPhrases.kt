package com.example.coldtext.data.sample

import com.example.coldtext.data.model.Difficulty
import com.example.coldtext.data.model.Phrase
import com.example.coldtext.data.model.Register
import com.example.coldtext.data.model.ReviewStatus
import com.example.coldtext.data.model.Topic
import com.example.coldtext.data.model.WordGloss

val KANNADA_SAMPLE_PHRASES = listOf(
    // Greetings & Casual
    Phrase(
        id = "kn_greet_1",
        targetLanguageId = "kn",
        nativeScript = "ಸ್ವಲ್ಪ chill ಮಾಡಿ",
        latinTransliteration = "Swalpa chill māḍi",
        englishMeaning = "Let's chill a little.",
        wordGloss = listOf(
            WordGloss("Swalpa", "ಸ್ವಲ್ಪ", "A little / slightly", "Adverb"),
            WordGloss("chill", "chill", "Relax / chill out", "Verb"),
            WordGloss("māḍi", "ಮಾಡಿ", "Do / make (polite form of maadu)", "Verb")
        ),
        pronunciationNotes = "Swal-pa (a little), chill (relax), maa-di (do - polite)",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Extremely popular in Bengaluru urban casual talk",
        reviewStatus = ReviewStatus.REVIEWED
    ),
    Phrase(
        id = "kn_greet_2",
        targetLanguageId = "kn",
        nativeScript = "Guru, late āgutte!",
        latinTransliteration = "Guru, late āgutte!",
        englishMeaning = "Dude, we're gonna be late!",
        wordGloss = listOf(
            WordGloss("Guru", "ಗುರು", "Dude / buddy / friend", "Noun (Slang)"),
            WordGloss("late", "late", "Delay", "Adjective"),
            WordGloss("āgutte", "ಆಗುತ್ತೆ", "Will happen / becomes", "Verb")
        ),
        pronunciationNotes = "Gu-ru (friend), late, aa-gut-te (happens)",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.SLANG,
        regionNotes = "Common friendly greeting among youth in Karnataka",
        reviewStatus = ReviewStatus.REVIEWED
    ),
    Phrase(
        id = "kn_greet_3",
        targetLanguageId = "kn",
        nativeScript = "Namaskāra, hegiddīrā?",
        latinTransliteration = "Namaskāra, hegiddīrā?",
        englishMeaning = "Hello, how are you?",
        wordGloss = listOf(
            WordGloss("Namaskāra", "ನಮಸ್ಕಾರ", "Greetings / Hello", "Interjection"),
            WordGloss("hegiddīrā", "ಹೇಗಿದ್ದೀರಾ", "How are you? (Respectful)", "Question")
        ),
        pronunciationNotes = "Na-mas-kaa-ra, he-gid-dee-raa",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Standard polite greeting across all Kannada regions",
        reviewStatus = ReviewStatus.REVIEWED
    ),

    // Everyday Conversation
    Phrase(
        id = "kn_everyday_1",
        targetLanguageId = "kn",
        nativeScript = "Nāle meet māḍōṇava?",
        latinTransliteration = "Nāle meet māḍōṇava?",
        englishMeaning = "Shall we meet tomorrow?",
        wordGloss = listOf(
            WordGloss("Nāle", "ನಾಳೆ", "Tomorrow", "Noun"),
            WordGloss("meet", "meet", "Meet up", "Verb"),
            WordGloss("māḍōṇava", "ಮಾಡೋಣವ", "Shall we do?", "Verb (Question)")
        ),
        pronunciationNotes = "Naa-le (tomorrow), meet, maa-doo-na-va (shall we do)",
        topic = Topic.EVERYDAY,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Standard code-switching phrase for making plans",
        reviewStatus = ReviewStatus.REVIEWED
    ),
    Phrase(
        id = "kn_everyday_2",
        targetLanguageId = "kn",
        nativeScript = "Nange full tension āgide",
        latinTransliteration = "Nange full tension āgide",
        englishMeaning = "I am feeling super stressed.",
        wordGloss = listOf(
            WordGloss("Nange", "ನನಗೆ", "To me / I", "Pronoun"),
            WordGloss("full", "full", "Complete / very", "Adverb"),
            WordGloss("tension", "tension", "Stress", "Noun"),
            WordGloss("āgide", "ಆಗಿದೆ", "Has happened", "Verb")
        ),
        pronunciationNotes = "Nan-ge (to me), full, ten-sion, aa-gi-de (has become)",
        topic = Topic.EVERYDAY,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Used daily in college and office environments",
        reviewStatus = ReviewStatus.REVIEWED
    ),

    // Food & Dining
    Phrase(
        id = "kn_food_1",
        targetLanguageId = "kn",
        nativeScript = "Full bisi ide, coffee cool ādanantara drink māḍi",
        latinTransliteration = "Full bisi ide, coffee cool ādanantara drink māḍi",
        englishMeaning = "It's super hot, drink the coffee after it cools down.",
        wordGloss = listOf(
            WordGloss("Full", "Full", "Very", "Adverb"),
            WordGloss("bisi", "ಬಿಸಿ", "Hot", "Adjective"),
            WordGloss("ide", "ಇದೆ", "Is / exists", "Verb"),
            WordGloss("coffee", "coffee", "Coffee", "Noun"),
            WordGloss("cool", "cool", "Cool", "Adjective"),
            WordGloss("ādanantara", "ಆದನಂತರ", "After becoming", "Preposition"),
            WordGloss("drink māḍi", "drink ಮಾಡಿ", "Drink (polite)", "Verb")
        ),
        pronunciationNotes = "Full bi-si i-de, cof-fee cool aa-da-nan-ta-ra drink maa-di",
        topic = Topic.FOOD,
        difficulty = Difficulty.INTERMEDIATE,
        register = Register.POLITE,
        regionNotes = "Classic filter coffee shop culture in South India",
        reviewStatus = ReviewStatus.REVIEWED
    ),
    Phrase(
        id = "kn_food_2",
        targetLanguageId = "kn",
        nativeScript = "Oota aithā? Lunch aithā?",
        latinTransliteration = "Oota aithā? Lunch aithā?",
        englishMeaning = "Did you have food / lunch?",
        wordGloss = listOf(
            WordGloss("Oota", "ಊಟ", "Meal / food", "Noun"),
            WordGloss("aithā", "ಆಯ್ತಾ", "Is it done?", "Verb (Question)"),
            WordGloss("Lunch", "Lunch", "Lunch meal", "Noun")
        ),
        pronunciationNotes = "Oo-ta ai-thaa? Lunch ai-thaa?",
        topic = Topic.FOOD,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "The ultimate caring greeting in Kannada culture",
        reviewStatus = ReviewStatus.REVIEWED
    ),

    // Travel & Commute
    Phrase(
        id = "kn_travel_1",
        targetLanguageId = "kn",
        nativeScript = "Auto fare eṣṭu āgutte?",
        latinTransliteration = "Auto fare eṣṭu āgutte?",
        englishMeaning = "How much will the auto fare be?",
        wordGloss = listOf(
            WordGloss("Auto fare", "Auto fare", "Auto rickshaw fare", "Noun"),
            WordGloss("eṣṭu", "ಎಷ್ಟು", "How much?", "Question Word"),
            WordGloss("āgutte", "ಆಗುತ್ತೆ", "Will it cost/become?", "Verb")
        ),
        pronunciationNotes = "Au-to fare esh-tu aa-gut-te?",
        topic = Topic.TRAVEL,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Essential phrase for navigating auto rickshaws",
        reviewStatus = ReviewStatus.REVIEWED
    ),
    Phrase(
        id = "kn_travel_2",
        targetLanguageId = "kn",
        nativeScript = "Drive slowly, road full traffic ide!",
        latinTransliteration = "Drive slowly, road full traffic ide!",
        englishMeaning = "Drive slowly, the road is full of traffic!",
        wordGloss = listOf(
            WordGloss("Drive slowly", "Drive slowly", "Drive with care", "Phrase"),
            WordGloss("road", "road", "Road / street", "Noun"),
            WordGloss("full", "full", "Completely", "Adverb"),
            WordGloss("traffic", "traffic", "Vehicular congestion", "Noun"),
            WordGloss("ide", "ಇದೆ", "Is present", "Verb")
        ),
        pronunciationNotes = "Drive slow-ly, road full traf-fic i-de!",
        topic = Topic.TRAVEL,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Everyday commuting phrase in city transit",
        reviewStatus = ReviewStatus.REVIEWED
    ),

    // Shopping & Bargaining
    Phrase(
        id = "kn_shopping_1",
        targetLanguageId = "kn",
        nativeScript = "Discount koḍi, full costly ide!",
        latinTransliteration = "Discount koḍi, full costly ide!",
        englishMeaning = "Give some discount, it is very expensive!",
        wordGloss = listOf(
            WordGloss("Discount", "Discount", "Price reduction", "Noun"),
            WordGloss("koḍi", "ಕೊಡಿ", "Give (polite)", "Verb"),
            WordGloss("full", "full", "Very", "Adverb"),
            WordGloss("costly", "costly", "Expensive", "Adjective"),
            WordGloss("ide", "ಇದೆ", "Is", "Verb")
        ),
        pronunciationNotes = "Dis-count ko-di, full cost-ly i-de!",
        topic = Topic.SHOPPING,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Bargaining classic at local markets like Commercial Street",
        reviewStatus = ReviewStatus.REVIEWED
    ),

    // Work & College
    Phrase(
        id = "kn_work_1",
        targetLanguageId = "kn",
        nativeScript = "Coffee break ge togoḷḷa?",
        latinTransliteration = "Coffee break ge togoḷḷa?",
        englishMeaning = "Shall we take a coffee break?",
        wordGloss = listOf(
            WordGloss("Coffee break", "Coffee break", "Short rest", "Noun"),
            WordGloss("ge", "ಗೆ", "For / to", "Postposition"),
            WordGloss("togoḷḷa", "ತಗೊಳೊಲ್ಲ", "Shall we take?", "Verb (Question)")
        ),
        pronunciationNotes = "Cof-fee break-ge to-gol-la?",
        topic = Topic.WORK,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Universal office / tech park phrase",
        reviewStatus = ReviewStatus.REVIEWED
    ),
    Phrase(
        id = "kn_work_2",
        targetLanguageId = "kn",
        nativeScript = "Sari boss, nānu ready iddīni",
        latinTransliteration = "Sari boss, nānu ready iddīni",
        englishMeaning = "Okay boss, I am ready.",
        wordGloss = listOf(
            WordGloss("Sari", "ಸರಿ", "Okay / Right", "Adjective"),
            WordGloss("boss", "boss", "Manager / Lead / Buddy", "Noun"),
            WordGloss("nānu", "ನಾನು", "I / Myself", "Pronoun"),
            WordGloss("ready", "ready", "Prepared", "Adjective"),
            WordGloss("iddīni", "ಇದ್ದೀನಿ", "Am present", "Verb")
        ),
        pronunciationNotes = "Sa-ri boss, naa-nu rea-dy id-dee-ni",
        topic = Topic.WORK,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Common workplace affirmative expression",
        reviewStatus = ReviewStatus.REVIEWED
    )
)
