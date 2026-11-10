package com.example.coldtext.data.sample

import com.example.coldtext.data.model.Difficulty
import com.example.coldtext.data.model.Phrase
import com.example.coldtext.data.model.Register
import com.example.coldtext.data.model.ReviewStatus
import com.example.coldtext.data.model.Topic
import com.example.coldtext.data.model.WordGloss

val TAMIL_SAMPLE_PHRASES = listOf(
    Phrase(
        id = "ta_greet_1",
        targetLanguageId = "ta",
        nativeScript = "Konjam chill paṇṇunga!",
        latinTransliteration = "Konjam chill paṇṇunga!",
        englishMeaning = "Let's chill a little.",
        wordGloss = listOf(
            WordGloss("Konjam", "கொஞ்சம்", "A little", "Adverb"),
            WordGloss("chill", "chill", "Relax", "Verb"),
            WordGloss("paṇṇunga", "பண்ணுங்க", "Do (polite)", "Verb")
        ),
        pronunciationNotes = "Kon-jam chill pan-nun-ga",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Common in Chennai urban conversation",
        reviewStatus = ReviewStatus.REVIEWED
    ),
    Phrase(
        id = "ta_everyday_1",
        targetLanguageId = "ta",
        nativeScript = "Māppiḷḷai, late āgiḍum!",
        latinTransliteration = "Māppiḷḷai, late āgiḍum!",
        englishMeaning = "Dude, it's gonna be late!",
        wordGloss = listOf(
            WordGloss("Māppiḷḷai", "மாப்பிள்ளை", "Dude / buddy", "Noun (Slang)"),
            WordGloss("late", "late", "Delay", "Adjective"),
            WordGloss("āgiḍum", "ஆகிடும்", "Will become", "Verb")
        ),
        pronunciationNotes = "Maap-pi-lai, late aa-gi-dum",
        topic = Topic.FRIENDS_HANGOUT,
        difficulty = Difficulty.BEGINNER,
        register = Register.SLANG,
        regionNotes = "Popular friendly slang in Tamil Nadu",
        reviewStatus = ReviewStatus.REVIEWED
    )
)

val TELUGU_SAMPLE_PHRASES = listOf(
    Phrase(
        id = "te_greet_1",
        targetLanguageId = "te",
        nativeScript = "Kāsasta chill avvaṇḍi!",
        latinTransliteration = "Kāsasta chill avvaṇḍi!",
        englishMeaning = "Let's chill a little.",
        wordGloss = listOf(
            WordGloss("Kāsasta", "కొంత", "A little bit", "Adverb"),
            WordGloss("chill", "chill", "Relax", "Verb"),
            WordGloss("avvaṇḍi", "అవ్వండి", "Become / do (polite)", "Verb")
        ),
        pronunciationNotes = "Kaa-sas-ta chill av-van-di",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.POLITE,
        regionNotes = "Popular in Hyderabad & Vijayawada casual tech talk",
        reviewStatus = ReviewStatus.REVIEWED
    )
)

val HINDI_SAMPLE_PHRASES = listOf(
    Phrase(
        id = "hi_greet_1",
        targetLanguageId = "hi",
        nativeScript = "Thoḍā chill karo yaār!",
        latinTransliteration = "Thoḍā chill karo yaār!",
        englishMeaning = "Chill a little, buddy!",
        wordGloss = listOf(
            WordGloss("Thoḍā", "थोड़ा", "A little", "Adverb"),
            WordGloss("chill", "chill", "Relax", "Verb"),
            WordGloss("karo", "करो", "Do", "Verb"),
            WordGloss("yaār", "यार", "Dude / friend", "Noun")
        ),
        pronunciationNotes = "Tho-daa chill ka-ro yaar",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Universal Hinglish everyday phrase across North India",
        reviewStatus = ReviewStatus.REVIEWED
    )
)

val MALAYALAM_SAMPLE_PHRASES = listOf(
    Phrase(
        id = "ml_greet_1",
        targetLanguageId = "ml",
        nativeScript = "Korachu chill ākkāṁ!",
        latinTransliteration = "Korachu chill ākkāṁ!",
        englishMeaning = "Let's chill a little.",
        wordGloss = listOf(
            WordGloss("Korachu", "കുറച്ച്", "A little", "Adverb"),
            WordGloss("chill", "chill", "Relax", "Verb"),
            WordGloss("ākkāṁ", "ആക്കാം", "Shall we make/do?", "Verb")
        ),
        pronunciationNotes = "Ko-ra-chu chill aak-kaam",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Kochi & Trivandrum youth casual talk",
        reviewStatus = ReviewStatus.REVIEWED
    )
)

val MARATHI_SAMPLE_PHRASES = listOf(
    Phrase(
        id = "mr_greet_1",
        targetLanguageId = "mr",
        nativeScript = "Thoḍa chill karā re!",
        latinTransliteration = "Thoḍa chill karā re!",
        englishMeaning = "Chill a little, guys!",
        wordGloss = listOf(
            WordGloss("Thoḍa", "थोडा", "A little", "Adverb"),
            WordGloss("chill", "chill", "Relax", "Verb"),
            WordGloss("karā", "करा", "Do (polite)", "Verb"),
            WordGloss("re", "रे", "Buddy", "Interjection")
        ),
        pronunciationNotes = "Tho-da chill ka-raa re",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Pune & Mumbai casual conversation",
        reviewStatus = ReviewStatus.REVIEWED
    )
)
