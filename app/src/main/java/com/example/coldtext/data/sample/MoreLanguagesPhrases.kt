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
        literalGloss = "Word breakdown: Konjam (a little) + chill (relax) + paṇṇunga (do - polite request)",
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
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Chennai Native Speaker Circle",
        reviewDate = "2026-02-14",
        reviewMethod = "Field Audio Recording Audit",
        regionVariety = "Chennai Urban Colloquial"
    ),
    Phrase(
        id = "ta_everyday_1",
        targetLanguageId = "ta",
        nativeScript = "Māppiḷḷai, late āgiḍum!",
        latinTransliteration = "Māppiḷḷai, late āgiḍum!",
        englishMeaning = "Dude, it's gonna be late!",
        literalGloss = "Word breakdown: Māppiḷḷai (dude) + late āgiḍum (will become late)",
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
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Chennai Native Speaker Circle",
        reviewDate = "2026-02-14",
        reviewMethod = "Field Audio Recording Audit",
        regionVariety = "Chennai Urban Colloquial"
    )
)

val TELUGU_SAMPLE_PHRASES = listOf(
    Phrase(
        id = "te_greet_1",
        targetLanguageId = "te",
        nativeScript = "Kāsasta chill avvaṇḍi!",
        latinTransliteration = "Kāsasta chill avvaṇḍi!",
        englishMeaning = "Let's chill a little.",
        literalGloss = "Word breakdown: Kāsasta (a little bit) + chill (relax) + avvaṇḍi (do/become - polite)",
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
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Hyderabad Colloquial Reviewers",
        reviewDate = "2026-02-13",
        reviewMethod = "Editorial Panel Consensus",
        regionVariety = "Telangana Urban Standard"
    )
)

val HINDI_SAMPLE_PHRASES = listOf(
    Phrase(
        id = "hi_greet_1",
        targetLanguageId = "hi",
        nativeScript = "Thoḍā chill karo yaār!",
        latinTransliteration = "Thoḍā chill karo yaār!",
        englishMeaning = "Chill a little, buddy!",
        literalGloss = "Word breakdown: Thoḍā (a little) + chill (relax) + karo (do) + yaār (dude/friend)",
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
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Delhi & Mumbai Hinglish Panel",
        reviewDate = "2026-02-12",
        reviewMethod = "Urban Speech Audit",
        regionVariety = "Standard Hinglish"
    )
)

val MALAYALAM_SAMPLE_PHRASES = listOf(
    Phrase(
        id = "ml_greet_1",
        targetLanguageId = "ml",
        nativeScript = "Korachu chill ākkāṁ!",
        latinTransliteration = "Korachu chill ākkāṁ!",
        englishMeaning = "Let's chill a little.",
        literalGloss = "Word breakdown: Korachu (a little) + chill (relax) + ākkāṁ (shall we make?)",
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
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Kochi Language Circle",
        reviewDate = "2026-02-11",
        reviewMethod = "Field Speech Audit",
        regionVariety = "Central Kerala Spoken"
    )
)

val MARATHI_SAMPLE_PHRASES = listOf(
    Phrase(
        id = "mr_greet_1",
        targetLanguageId = "mr",
        nativeScript = "Thoḍa chill karā re!",
        latinTransliteration = "Thoḍa chill karā re!",
        englishMeaning = "Chill a little, guys!",
        literalGloss = "Word breakdown: Thoḍa (a little) + chill (relax) + karā (do) + re (buddy)",
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
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Pune Youth Panel",
        reviewDate = "2026-02-10",
        reviewMethod = "In-Person Script Verification",
        regionVariety = "Urban Marathi Code-Switch"
    )
)

val BENGALI_SAMPLE_PHRASES = listOf(
    Phrase(
        id = "bn_greet_1",
        targetLanguageId = "bn",
        nativeScript = "Ektu chill koro yaar!",
        latinTransliteration = "Ektu chill koro yaar!",
        englishMeaning = "Chill a little bit, buddy!",
        literalGloss = "Word breakdown: Ektu (a little) + chill (relax) + koro (do) + yaar (friend)",
        wordGloss = listOf(
            WordGloss("Ektu", "একটু", "A little", "Adverb"),
            WordGloss("chill", "chill", "Relax", "Verb"),
            WordGloss("koro", "করো", "Do", "Verb"),
            WordGloss("yaar", "ইয়ার", "Friend", "Noun")
        ),
        pronunciationNotes = "Ek-tu chill ko-ro yaar",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Kolkata urban youth conversation",
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Kolkata Youth Reviewers",
        reviewDate = "2026-02-14",
        reviewMethod = "In-Person Script Verification",
        regionVariety = "Urban Kolkata Bengali"
    )
)

val GUJARATI_SAMPLE_PHRASES = listOf(
    Phrase(
        id = "gu_greet_1",
        targetLanguageId = "gu",
        nativeScript = "Thoḍum chill karo yaar!",
        latinTransliteration = "Thoḍum chill karo yaar!",
        englishMeaning = "Chill a little, buddy!",
        literalGloss = "Word breakdown: Thoḍum (a little) + chill (relax) + karo (do) + yaar (friend)",
        wordGloss = listOf(
            WordGloss("Thoḍum", "થોડું", "A little", "Adverb"),
            WordGloss("chill", "chill", "Relax", "Verb"),
            WordGloss("karo", "કરો", "Do", "Verb"),
            WordGloss("yaar", "યાર", "Friend", "Noun")
        ),
        pronunciationNotes = "Tho-dum chill ka-ro yaar",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Ahmedabad & Surat casual talk",
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Ahmedabad Circle",
        reviewDate = "2026-02-12",
        reviewMethod = "Editorial Panel Consensus",
        regionVariety = "Urban Gujarati"
    )
)

val PUNJABI_SAMPLE_PHRASES = listOf(
    Phrase(
        id = "pa_greet_1",
        targetLanguageId = "pa",
        nativeScript = "Thoḍā chill karo veer!",
        latinTransliteration = "Thoḍā chill karo veer!",
        englishMeaning = "Chill a little, brother!",
        literalGloss = "Word breakdown: Thoḍā (a little) + chill (relax) + karo (do) + veer (brother)",
        wordGloss = listOf(
            WordGloss("Thoḍā", "ਥੋੜ੍ਹਾ", "A little", "Adverb"),
            WordGloss("chill", "chill", "Relax", "Verb"),
            WordGloss("karo", "ਕਰੋ", "Do", "Verb"),
            WordGloss("veer", "ਵੀਰ", "Brother", "Noun")
        ),
        pronunciationNotes = "Tho-daa chill ka-ro veer",
        topic = Topic.GREETINGS,
        difficulty = Difficulty.BEGINNER,
        register = Register.INFORMAL,
        regionNotes = "Chandigarh & Amritsar casual banter",
        reviewStatus = ReviewStatus.REVIEWED,
        reviewerName = "Chandigarh Youth Panel",
        reviewDate = "2026-02-13",
        reviewMethod = "Field Audio Recording Audit",
        regionVariety = "Urban Punjabi"
    )
)
