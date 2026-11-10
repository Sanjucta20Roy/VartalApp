package com.example.coldtext.data.sample

import com.example.coldtext.data.model.Difficulty
import com.example.coldtext.data.model.Phrase
import com.example.coldtext.data.model.Register
import com.example.coldtext.data.model.ReviewStatus
import com.example.coldtext.data.model.Topic
import com.example.coldtext.data.model.WordGloss

val KANNADA_SAMPLE_PHRASES = listOf(
    // 1. Greetings & Casual
    Phrase(
        id = "kn_greet_1",
        targetLanguageId = "kn",
        nativeScript = "Swalpa chill ಮಾಡಿ",
        latinTransliteration = "Swalpa chill māḍi",
        englishMeaning = "Let's chill a little.",
        wordGloss = listOf(
            WordGloss("Swalpa", "ಸ್ವಲ್ಪ", "A little / slightly", "Adverb"),
            WordGloss("chill", "chill", "Relax / chill out", "Verb"),
            WordGloss("māḍi", "ಮಾಡಿ", "Do / make (polite form)", "Verb")
        ),
        pronunciationNotes = "Swal-pa (a little), chill (relax), maa-di (do)",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Extremely popular in Bengaluru urban casual talk",
        reviewStatus = ReviewStatus.REVIEWED
    ),
    Phrase(
        id = "kn_greet_2",
        targetLanguageId = "kn",
        nativeScript = "Namaskāra, hegiddīrā?",
        latinTransliteration = "Namaskāra, hegiddīrā?",
        englishMeaning = "Hello, how are you doing?",
        wordGloss = listOf(
            WordGloss("Namaskāra", "ನಮಸ್ಕಾರ", "Greetings / Hello", "Interjection"),
            WordGloss("hegiddīrā", "ಹೇಗಿದ್ದೀರಾ", "How are you? (Respectful)", "Question")
        ),
        pronunciationNotes = "Na-mas-kaa-ra, he-gid-dee-raa",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Standard polite greeting across all Karnataka",
        reviewStatus = ReviewStatus.REVIEWED
    ),

    // 2. Friends Hangout & Chillin'
    Phrase(
        id = "kn_friends_1",
        targetLanguageId = "kn",
        nativeScript = "Guru, late āgutte, fast āgi banni!",
        latinTransliteration = "Guru, late āgutte, fast āgi banni!",
        englishMeaning = "Dude, we're gonna be late, come fast!",
        wordGloss = listOf(
            WordGloss("Guru", "ಗುರು", "Dude / friend / buddy", "Noun (Slang)"),
            WordGloss("late", "late", "Delay", "Adjective"),
            WordGloss("āgutte", "ಆಗುತ್ತೆ", "Will happen", "Verb"),
            WordGloss("fast āgi", "fast ಆಗಿ", "Quickly", "Adverb"),
            WordGloss("banni", "ಬನ್ನಿ", "Come (polite)", "Verb")
        ),
        pronunciationNotes = "Gu-ru, late aa-gut-te, fast aa-gi ban-ni",
        topic = Topic.FRIENDS_HANGOUT,
        difficulty = Difficulty.BEGINNER,
        register = Register.SLANG,
        regionNotes = "Classic youth hangout catchphrase",
        reviewStatus = ReviewStatus.REVIEWED
    ),
    Phrase(
        id = "kn_friends_2",
        targetLanguageId = "kn",
        nativeScript = "Nāle meet māḍōṇava?",
        latinTransliteration = "Nāle meet māḍōṇava?",
        englishMeaning = "Shall we meet up tomorrow?",
        wordGloss = listOf(
            WordGloss("Nāle", "ನಾಳೆ", "Tomorrow", "Noun"),
            WordGloss("meet", "meet", "Gather / meet", "Verb"),
            WordGloss("māḍōṇava", "ಮಾಡೋಣವ", "Shall we do?", "Verb (Question)")
        ),
        pronunciationNotes = "Naa-le meet maa-doo-na-va?",
        topic = Topic.FRIENDS_HANGOUT,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Weekend planning code-switch",
        reviewStatus = ReviewStatus.REVIEWED
    ),

    // 3. Street Food & Dining
    Phrase(
        id = "kn_food_1",
        targetLanguageId = "kn",
        nativeScript = "Full bisi ide, coffee cool ādanantara drink māḍi",
        latinTransliteration = "Full bisi ide, coffee cool ādanantara drink māḍi",
        englishMeaning = "It's super hot, drink the coffee after it cools down.",
        wordGloss = listOf(
            WordGloss("Full", "Full", "Very / completely", "Adverb"),
            WordGloss("bisi", "ಬಿಸಿ", "Hot", "Adjective"),
            WordGloss("ide", "ಇದೆ", "Is", "Verb"),
            WordGloss("coffee", "coffee", "Filter coffee", "Noun"),
            WordGloss("cool", "cool", "Cool", "Adjective"),
            WordGloss("ādanantara", "ಆದನಂತರ", "After becoming", "Preposition"),
            WordGloss("drink māḍi", "drink ಮಾಡಿ", "Drink (polite)", "Verb")
        ),
        pronunciationNotes = "Full bi-si i-de, cof-fee cool aa-da-nan-ta-ra drink maa-di",
        topic = Topic.FOOD_STREET,
        difficulty = Difficulty.INTERMEDIATE,
        register = Register.POLITE,
        regionNotes = "Iconic filter coffee stall advice",
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
            WordGloss("aithā", "ಆಯ್ತಾ", "Done?", "Verb (Question)"),
            WordGloss("Lunch", "Lunch", "Midday meal", "Noun")
        ),
        pronunciationNotes = "Oo-ta ai-thaa? Lunch ai-thaa?",
        topic = Topic.FOOD_STREET,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Warmest everyday food check-in",
        reviewStatus = ReviewStatus.REVIEWED
    ),

    // 4. Autos & Local Commute
    Phrase(
        id = "kn_travel_1",
        targetLanguageId = "kn",
        nativeScript = "Auto fare eṣṭu āgutte? Meter hāki!",
        latinTransliteration = "Auto fare eṣṭu āgutte? Meter hāki!",
        englishMeaning = "How much is the auto fare? Please turn on the meter!",
        wordGloss = listOf(
            WordGloss("Auto fare", "Auto fare", "Rickshaw fare", "Noun"),
            WordGloss("eṣṭu", "ಎಷ್ಟು", "How much?", "Question Word"),
            WordGloss("āgutte", "ಆಗುತ್ತೆ", "Will it cost?", "Verb"),
            WordGloss("Meter", "Meter", "Auto meter", "Noun"),
            WordGloss("hāki", "ಹಾಕಿ", "Put / turn on", "Verb")
        ),
        pronunciationNotes = "Au-to fare esh-tu aa-gut-te? Me-ter haa-ki!",
        topic = Topic.LOCAL_TRAVEL,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Essential survival phrase for Bengaluru autos",
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
            WordGloss("traffic", "traffic", "Congestion", "Noun"),
            WordGloss("ide", "ಇದೆ", "Is present", "Verb")
        ),
        pronunciationNotes = "Drive slow-ly, road full traf-fic i-de!",
        topic = Topic.LOCAL_TRAVEL,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Peak hour city commute phrase",
        reviewStatus = ReviewStatus.REVIEWED
    ),

    // 5. Travel & Tourism
    Phrase(
        id = "kn_tourism_1",
        targetLanguageId = "kn",
        nativeScript = "Hotel route thumbā easy ide!",
        latinTransliteration = "Hotel route thumbā easy ide!",
        englishMeaning = "The route to the hotel is very easy!",
        wordGloss = listOf(
            WordGloss("Hotel route", "Hotel route", "Directions to hotel", "Noun"),
            WordGloss("thumbā", "ತುಂಬಾ", "Very / much", "Adverb"),
            WordGloss("easy", "easy", "Simple", "Adjective"),
            WordGloss("ide", "ಇದೆ", "Is", "Verb")
        ),
        pronunciationNotes = "Ho-tel route thum-baa ea-sy i-de!",
        topic = Topic.TRAVEL_TOURISM,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Travel navigation code-switch",
        reviewStatus = ReviewStatus.REVIEWED
    ),

    // 6. Shopping & Bargaining
    Phrase(
        id = "kn_shop_1",
        targetLanguageId = "kn",
        nativeScript = "Discount koḍi, full costly ide!",
        latinTransliteration = "Discount koḍi, full costly ide!",
        englishMeaning = "Give some discount, it is very expensive!",
        wordGloss = listOf(
            WordGloss("Discount", "Discount", "Price reduction", "Noun"),
            WordGloss("koḍi", "ಕೊಡಿ", "Give (polite)", "Verb"),
            WordGloss("full", "full", "Very", "Adverb"),
            WordGloss("costly", "costly", "Pricey", "Adjective"),
            WordGloss("ide", "ಇದೆ", "Is", "Verb")
        ),
        pronunciationNotes = "Dis-count ko-di, full cost-ly i-de!",
        topic = Topic.SHOPPING_BARGAIN,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Market bargaining classic at Commercial Street",
        reviewStatus = ReviewStatus.REVIEWED
    ),

    // 7. Corporate & Office Talk
    Phrase(
        id = "kn_work_1",
        targetLanguageId = "kn",
        nativeScript = "Coffee break ge togoḷḷa?",
        latinTransliteration = "Coffee break ge togoḷḷa?",
        englishMeaning = "Shall we take a coffee break?",
        wordGloss = listOf(
            WordGloss("Coffee break", "Coffee break", "Rest break", "Noun"),
            WordGloss("ge", "ಗೆ", "For / to", "Postposition"),
            WordGloss("togoḷḷa", "ತಗೊಳೊಲ್ಲ", "Shall we take?", "Verb")
        ),
        pronunciationNotes = "Cof-fee break-ge to-gol-la?",
        topic = Topic.PROFESSIONAL_WORK,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Tech park office favorite",
        reviewStatus = ReviewStatus.REVIEWED
    ),
    Phrase(
        id = "kn_work_2",
        targetLanguageId = "kn",
        nativeScript = "Sari boss, nānu ready iddīni",
        latinTransliteration = "Sari boss, nānu ready iddīni",
        englishMeaning = "Okay boss, I am ready.",
        wordGloss = listOf(
            WordGloss("Sari", "ಸರಿ", "Okay / right", "Adjective"),
            WordGloss("boss", "boss", "Lead / friend", "Noun"),
            WordGloss("nānu", "ನಾನು", "I / Myself", "Pronoun"),
            WordGloss("ready", "ready", "Prepared", "Adjective"),
            WordGloss("iddīni", "ಇದ್ದೀನಿ", "Am present", "Verb")
        ),
        pronunciationNotes = "Sa-ri boss, naa-nu rea-dy id-dee-ni",
        topic = Topic.PROFESSIONAL_WORK,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Workplace agreement expression",
        reviewStatus = ReviewStatus.REVIEWED
    ),

    // 8. Official & Administrative
    Phrase(
        id = "kn_govt_1",
        targetLanguageId = "kn",
        nativeScript = "Eey paper submit māḍabēku, eṣṭu time āgutte?",
        latinTransliteration = "Eey paper submit māḍabēku, eṣṭu time āgutte?",
        englishMeaning = "Need to submit this document, how much time will it take?",
        wordGloss = listOf(
            WordGloss("Eey paper", "ಈ paper", "This document", "Noun"),
            WordGloss("submit māḍabēku", "submit ಮಾಡಬೇಕು", "Must submit", "Verb"),
            WordGloss("eṣṭu", "ಎಷ್ಟು", "How much?", "Question Word"),
            WordGloss("time", "time", "Duration", "Noun"),
            WordGloss("āgutte", "ಆಗುತ್ತೆ", "Will take", "Verb")
        ),
        pronunciationNotes = "Ee paper sub-mit maa-da-bee-ku, esh-tu time aa-gut-te?",
        topic = Topic.OFFICIAL_GOVT,
        difficulty = Difficulty.INTERMEDIATE,
        register = Register.FORMAL,
        regionNotes = "Government office desk inquiry",
        reviewStatus = ReviewStatus.REVIEWED
    ),

    // 9. College & Campus Life
    Phrase(
        id = "kn_college_1",
        targetLanguageId = "kn",
        nativeScript = "Canteen nalli tea thogoṇōṇava?",
        latinTransliteration = "Canteen nalli tea thogoṇōṇava?",
        englishMeaning = "Shall we grab tea in the canteen?",
        wordGloss = listOf(
            WordGloss("Canteen nalli", "Canteen ನಲ್ಲಿ", "In the canteen", "Noun"),
            WordGloss("tea", "tea", "Tea", "Noun"),
            WordGloss("thogoṇōṇava", "ತಗೊಳೋಣವ", "Shall we take?", "Verb")
        ),
        pronunciationNotes = "Can-teen nal-li tea tho-go-noo-na-va?",
        topic = Topic.COLLEGE_CAMPUS,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Campus hangout phrase",
        reviewStatus = ReviewStatus.REVIEWED
    ),

    // 10. Help & Emergencies
    Phrase(
        id = "kn_help_1",
        targetLanguageId = "kn",
        nativeScript = "Swalpa help māḍtīrā? Medical shop elli ide?",
        latinTransliteration = "Swalpa help māḍtīrā? Medical shop elli ide?",
        englishMeaning = "Could you help a bit? Where is the medical shop?",
        wordGloss = listOf(
            WordGloss("Swalpa", "ಸ್ವಲ್ಪ", "A little", "Adverb"),
            WordGloss("help māḍtīrā", "help ಮಾಡ್ತೀರಾ", "Will you help? (Polite)", "Verb"),
            WordGloss("Medical shop", "Medical shop", "Pharmacy", "Noun"),
            WordGloss("elli", "ಎಲ್ಲಿ", "Where?", "Question Word"),
            WordGloss("ide", "ಇದೆ", "Is located", "Verb")
        ),
        pronunciationNotes = "Swal-pa help maa-dtee-raa? Me-di-cal shop el-li i-de?",
        topic = Topic.EMERGENCY_HELP,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Helpful street inquiry",
        reviewStatus = ReviewStatus.REVIEWED
    )
)
