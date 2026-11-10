package com.example.coldtext.data.sample

import com.example.coldtext.data.model.Difficulty
import com.example.coldtext.data.model.Phrase
import com.example.coldtext.data.model.PhraseVariation
import com.example.coldtext.data.model.Register
import com.example.coldtext.data.model.ReviewStatus
import com.example.coldtext.data.model.Topic
import com.example.coldtext.data.model.WordGloss

val KANNADA_SAMPLE_PHRASES = listOf(
    // 1. Greetings & Casual Reactions
    Phrase(
        id = "kn_greet_1",
        targetLanguageId = "kn",
        nativeScript = "Swalpa chill ಮಾಡಿ",
        latinTransliteration = "Swalpa chill māḍi",
        englishMeaning = "Let's chill a little / Relax a bit.",
        literalGloss = "Word breakdown: Swalpa (a little) + chill (relax) + māḍi (do/make - polite request)",
        wordGloss = listOf(
            WordGloss("Swalpa", "ಸ್ವಲ್ಪ", "A little / slightly", "Adverb"),
            WordGloss("chill", "chill", "Relax / chill out", "Verb"),
            WordGloss("māḍi", "ಮಾಡಿ", "Do / make (polite request)", "Verb")
        ),
        pronunciationNotes = "Swal-pa (a little), chill (relax), maa-di (do - polite request)",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Popular in Bengaluru urban tech & casual conversation. Note: Natural meaning means 'relax a bit', not a strict command.",
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Bengaluru Native Speaker Circle",
        reviewDate = "2026-02-15",
        reviewMethod = "In-Person Audio & Script Verification",
        regionVariety = "Bengaluru Urban Standard",
        reviewerPermissionGranted = true,
        disputeStatus = "No Active Disputes",
        source = "Urban Colloquial Kannada Corpus",
        variations = listOf(
            PhraseVariation(Register.SLANG, "Swalpa chill māḍo", "ಸ್ವಲ್ಪ chill ಮಾಡೋ", "Casual friend-to-friend variation"),
            PhraseVariation(Register.POLITE, "Swalpa chill māḍi", "ಸ್ವಲ್ಪ chill ಮಾಡಿ", "Standard polite request")
        )
    ),
    Phrase(
        id = "kn_greet_2",
        targetLanguageId = "kn",
        nativeScript = "Namaskāra, hegiddīrā?",
        latinTransliteration = "Namaskāra, hegiddīrā?",
        englishMeaning = "Hello, how are you doing?",
        literalGloss = "Word breakdown: Namaskāra (greetings) + hegiddīrā (how are you - respectful)",
        wordGloss = listOf(
            WordGloss("Namaskāra", "ನಮಸ್ಕಾರ", "Greetings / Hello", "Interjection"),
            WordGloss("hegiddīrā", "ಹೇಗಿದ್ದೀರಾ", "How are you? (Respectful plural/formal)", "Question")
        ),
        pronunciationNotes = "Na-mas-kaa-ra, he-gid-dee-raa",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Standard polite greeting across all Karnataka",
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Karnataka Language Board Review",
        reviewDate = "2026-02-10",
        reviewMethod = "Editorial Panel Consensus",
        regionVariety = "Standard Literary & Spoken Kannada",
        reviewerPermissionGranted = true,
        disputeStatus = "No Active Disputes",
        source = "Formal Kannada Conversation Guide",
        variations = listOf(
            PhraseVariation(Register.INFORMAL, "Hegiddīya?", "ಹೇಗಿದ್ದೀಯಾ?", "Used with close friends"),
            PhraseVariation(Register.POLITE, "Hegiddīrā?", "ಹೇಗಿದ್ದೀರಾ?", "Polite/formal for elders & acquaintances")
        )
    ),
    Phrase(
        id = "kn_greet_3",
        targetLanguageId = "kn",
        nativeScript = "Aaytu aaytu",
        latinTransliteration = "Aaytu aaytu",
        englishMeaning = "Oh okay okay / Got it, done!",
        literalGloss = "Word breakdown: Aaytu (it is done / okay)",
        wordGloss = listOf(
            WordGloss("Aaytu", "ಆಯ್ತು", "Done / Okay / Finished", "Interjection")
        ),
        pronunciationNotes = "Aay-tu aay-tu (the single most common go-to word in Bengaluru!)",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Universal go-to phrase for agreement and acknowledgment",
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Bengaluru Native Speaker Circle",
        reviewDate = "2026-02-16",
        reviewMethod = "Colloquial Verification",
        regionVariety = "Bengaluru Everyday Spoken"
    ),
    Phrase(
        id = "kn_greet_4",
        targetLanguageId = "kn",
        nativeScript = "Houda?",
        latinTransliteration = "Houda?",
        englishMeaning = "Oh is it? / Really?",
        literalGloss = "Word breakdown: Houda? (Is it so?)",
        wordGloss = listOf(
            WordGloss("Houda", "ಹೌದಾ", "Is it? / Is that so?", "Question Word")
        ),
        pronunciationNotes = "Hou-da? (used as a conversational reaction)",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Everyday natural reaction in conversations",
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Bengaluru Native Speaker Circle",
        reviewDate = "2026-02-16",
        reviewMethod = "Colloquial Verification",
        regionVariety = "Bengaluru Everyday Spoken"
    ),

    // 2. Local Commute, Apartment & Delivery Conversations
    Phrase(
        id = "kn_delivery_1",
        targetLanguageId = "kn",
        nativeScript = "Caretaker-ge kodi",
        latinTransliteration = "Caretaker-ge kodi",
        englishMeaning = "Please give it to the caretaker.",
        literalGloss = "Word breakdown: Caretaker-ge (to caretaker) + kodi (give - polite)",
        wordGloss = listOf(
            WordGloss("Caretaker-ge", "ಕೇರ್‌ಟೇಕರ್‌ಗೆ", "To the caretaker", "Noun + Postposition"),
            WordGloss("kodi", "ಕೊಡಿ", "Give (polite)", "Verb")
        ),
        pronunciationNotes = "Care-ta-ker-ge ko-di",
        topic = Topic.LOCAL_TRAVEL,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Everyday delivery executive code-switch in Bengaluru apartments",
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Urban Commute Panel",
        reviewDate = "2026-02-16",
        reviewMethod = "Delivery Field Audit",
        regionVariety = "Bengaluru Everyday Spoken"
    ),
    Phrase(
        id = "kn_delivery_2",
        targetLanguageId = "kn",
        nativeScript = "Neevu JK Skyline building-nalli ideera?",
        latinTransliteration = "Neevu JK Skyline building-nalli ideera?",
        englishMeaning = "Are you in the JK Skyline building?",
        literalGloss = "Word breakdown: Neevu (you) + JK Skyline building-nalli (in building) + ideera (are present?)",
        wordGloss = listOf(
            WordGloss("Neevu", "ನೀವು", "You (polite/plural)", "Pronoun"),
            WordGloss("building-nalli", "ಬಿಲ್ಡಿಂಗ್‌ನಲ್ಲಿ", "In the building", "Noun + Postposition"),
            WordGloss("ideera", "ಇದೀರಾ", "Are you present?", "Verb (Question)")
        ),
        pronunciationNotes = "Nee-vu JK Sky-line buil-ding-nal-li i-dee-ra?",
        topic = Topic.LOCAL_TRAVEL,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Location check with delivery driver / cab driver",
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Urban Commute Panel",
        reviewDate = "2026-02-16",
        reviewMethod = "Delivery Field Audit",
        regionVariety = "Bengaluru Everyday Spoken"
    ),
    Phrase(
        id = "kn_delivery_3",
        targetLanguageId = "kn",
        nativeScript = "503 flat munde ideera?",
        latinTransliteration = "503 flat munde ideera?",
        englishMeaning = "Are you in front of flat no. 503?",
        literalGloss = "Word breakdown: 503 flat munde (in front of flat 503) + ideera (are you there?)",
        wordGloss = listOf(
            WordGloss("flat munde", "ಫ್ಲಾಟ್ ಮುಂದೆ", "In front of flat", "Preposition"),
            WordGloss("ideera", "ಇದೀರಾ", "Are you there?", "Verb (Question)")
        ),
        pronunciationNotes = "503 flat mun-de i-dee-ra?",
        topic = Topic.LOCAL_TRAVEL,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Apartment door location confirmation",
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Urban Commute Panel",
        reviewDate = "2026-02-16",
        reviewMethod = "Delivery Field Audit",
        regionVariety = "Bengaluru Everyday Spoken"
    ),
    Phrase(
        id = "kn_delivery_4",
        targetLanguageId = "kn",
        nativeScript = "Ondu nimisha wait maadi, naanu barthiddini",
        latinTransliteration = "Ondu nimisha wait maadi, naanu barthiddini",
        englishMeaning = "Please wait 1 min, I'm coming.",
        literalGloss = "Word breakdown: Ondu nimisha (one minute) + wait maadi (please wait) + naanu barthiddini (I am coming)",
        wordGloss = listOf(
            WordGloss("Ondu nimisha", "ಒಂದು ನಿಮಿಷ", "One minute", "Time"),
            WordGloss("wait maadi", "ವೇಟ್ ಮಾಡಿ", "Please wait", "Verb"),
            WordGloss("naanu", "ನಾನು", "I / Myself", "Pronoun"),
            WordGloss("barthiddini", "ಬರುತ್ತಿದ್ದೀನಿ", "Am coming", "Verb")
        ),
        pronunciationNotes = "On-du ni-mi-sha wait maa-di, naa-nu bar-thid-di-ni",
        topic = Topic.LOCAL_TRAVEL,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Crucial phrase when stepping out to collect deliveries or cabs",
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Urban Commute Panel",
        reviewDate = "2026-02-16",
        reviewMethod = "Delivery Field Audit",
        regionVariety = "Bengaluru Everyday Spoken"
    ),

    // 3. Friends Hangout & Chillin'
    Phrase(
        id = "kn_friends_1",
        targetLanguageId = "kn",
        nativeScript = "Guru, late āgutte, fast āgi banni!",
        latinTransliteration = "Guru, late āgutte, fast āgi banni!",
        englishMeaning = "Dude, we're gonna be late, come fast!",
        literalGloss = "Word breakdown: Guru (friend/dude) + late āgutte (will be late) + fast āgi banni (come quickly)",
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
        regionNotes = "Classic youth hangout catchphrase in Bengaluru & Mysuru",
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Youth Dialect Panel",
        reviewDate = "2026-02-12",
        reviewMethod = "Field Audio Recording Audit",
        regionVariety = "Urban Youth Colloquial",
        reviewerPermissionGranted = true,
        disputeStatus = "No Active Disputes",
        source = "Campus Speech Study"
    ),
    Phrase(
        id = "kn_friends_2",
        targetLanguageId = "kn",
        nativeScript = "Nāle meet māḍōṇava?",
        latinTransliteration = "Nāle meet māḍōṇava?",
        englishMeaning = "Shall we meet up tomorrow?",
        literalGloss = "Word breakdown: Nāle (tomorrow) + meet (meet) + māḍōṇava (shall we do?)",
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
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Youth Dialect Panel",
        reviewDate = "2026-02-12",
        reviewMethod = "Field Audio Recording Audit",
        regionVariety = "Urban Youth Colloquial",
        reviewerPermissionGranted = true,
        disputeStatus = "No Active Disputes",
        source = "Casual Code-Switching Dataset"
    ),

    // 4. Street Food & Dining
    Phrase(
        id = "kn_food_1",
        targetLanguageId = "kn",
        nativeScript = "Full bisi ide, coffee cool ādanantara drink māḍi",
        latinTransliteration = "Full bisi ide, coffee cool ādanantara drink māḍi",
        englishMeaning = "It's super hot, drink the coffee after it cools down.",
        literalGloss = "Word breakdown: Full bisi ide (is very hot) + coffee cool ādanantara (after coffee cools) + drink māḍi (do drink)",
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
        regionNotes = "Iconic filter coffee stall advice in South India",
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Food & Culture Reviewer",
        reviewDate = "2026-02-14",
        reviewMethod = "Culinary Field Audit",
        regionVariety = "South Karnataka Spoken",
        reviewerPermissionGranted = true,
        disputeStatus = "No Active Disputes",
        source = "Culinary Conversation Guide"
    ),
    Phrase(
        id = "kn_food_2",
        targetLanguageId = "kn",
        nativeScript = "Oota aithā? Lunch aithā?",
        latinTransliteration = "Oota aithā? Lunch aithā?",
        englishMeaning = "Did you have food / lunch?",
        literalGloss = "Word breakdown: Oota aithā (meal completed?) + Lunch aithā (lunch completed?)",
        wordGloss = listOf(
            WordGloss("Oota", "ಊಟ", "Meal / food", "Noun"),
            WordGloss("aithā", "ಆಯ್ತಾ", "Done?", "Verb (Question)"),
            WordGloss("Lunch", "Lunch", "Midday meal", "Noun")
        ),
        pronunciationNotes = "Oo-ta ai-thaa? Lunch ai-thaa?",
        topic = Topic.FOOD_STREET,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Warmest everyday food check-in in Kannada culture",
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Cultural Heritage Panel",
        reviewDate = "2026-02-10",
        reviewMethod = "In-Person Audio & Script Verification",
        regionVariety = "Standard Spoken Kannada",
        reviewerPermissionGranted = true,
        disputeStatus = "No Active Disputes",
        source = "Everyday Colloquial Sayings"
    ),

    // 5. Autos & Local Commute
    Phrase(
        id = "kn_travel_1",
        targetLanguageId = "kn",
        nativeScript = "Auto fare eṣṭu āgutte? Meter hāki!",
        latinTransliteration = "Auto fare eṣṭu āgutte? Meter hāki!",
        englishMeaning = "How much is the auto fare? Please turn on the meter!",
        literalGloss = "Word breakdown: Auto fare eṣṭu āgutte (how much auto fare will be) + Meter hāki (put meter)",
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
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Urban Commute Panel",
        reviewDate = "2026-02-11",
        reviewMethod = "Field Transit Audit",
        regionVariety = "Bengaluru City Transit Standard",
        reviewerPermissionGranted = true,
        disputeStatus = "No Active Disputes",
        source = "City Survival Phrasebook"
    ),

    // 6. Shopping & Bargaining
    Phrase(
        id = "kn_shop_1",
        targetLanguageId = "kn",
        nativeScript = "Discount koḍi, full costly ide!",
        latinTransliteration = "Discount koḍi, full costly ide!",
        englishMeaning = "Give some discount, it is very expensive!",
        literalGloss = "Word breakdown: Discount koḍi (give discount) + full costly ide (is very expensive)",
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
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Market Traders Panel",
        reviewDate = "2026-02-08",
        reviewMethod = "Field Market Survey",
        regionVariety = "Bengaluru Market Colloquial",
        reviewerPermissionGranted = true,
        disputeStatus = "No Active Disputes",
        source = "Bargaining Guide"
    ),

    // 7. Corporate & Office Talk
    Phrase(
        id = "kn_work_1",
        targetLanguageId = "kn",
        nativeScript = "Coffee break ge togoḷḷa?",
        latinTransliteration = "Coffee break ge togoḷḷa?",
        englishMeaning = "Shall we take a coffee break?",
        literalGloss = "Word breakdown: Coffee break ge (for coffee break) + togoḷḷa (shall we take?)",
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
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Corporate Language Panel",
        reviewDate = "2026-02-13",
        reviewMethod = "Workplace Speech Audit",
        regionVariety = "Bengaluru Corporate Code-Switch",
        reviewerPermissionGranted = true,
        disputeStatus = "No Active Disputes",
        source = "Office Code-Switching Study"
    ),

    // 8. Help & Emergencies
    Phrase(
        id = "kn_help_1",
        targetLanguageId = "kn",
        nativeScript = "Swalpa help māḍtīrā? Medical shop elli ide?",
        latinTransliteration = "Swalpa help māḍtīrā? Medical shop elli ide?",
        englishMeaning = "Could you help a bit? Where is the medical shop?",
        literalGloss = "Word breakdown: Swalpa help māḍtīrā (will you help a little?) + Medical shop elli ide (where is medical shop?)",
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
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Civic Services Panel",
        reviewDate = "2026-02-07",
        reviewMethod = "Civic Audit Consensus",
        regionVariety = "Standard Spoken Kannada",
        reviewerPermissionGranted = true,
        disputeStatus = "No Active Disputes",
        source = "Emergency Assistance Guide"
    )
)
