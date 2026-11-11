package com.example.vartalapp.data

import com.example.vartalapp.core.model.Phrase

enum class LanguageAvailability {
    AVAILABLE,
    PREVIEW,
    ON_DEMAND
}

data class LanguageInfo(
    val id: String,
    val name: String,
    val nativeName: String,
    val region: String,
    val downloadSizeMb: String = "1.2 MB",
    val availability: LanguageAvailability,
    val totalPhrasesCount: Int,
    val previewPhrases: List<Phrase> = emptyList()
)

object LanguageCatalog {
    val languages: List<LanguageInfo> = listOf(
        // 1. Fully Available Primary Course (Kannada - Bengaluru Style)
        LanguageInfo("kannada", "Kannada (Bengaluru)", "ಕನ್ನಡ", "Karnataka, India", "2.4 MB", LanguageAvailability.AVAILABLE, 160),

        // TOP 20 GLOBAL INTERNATIONAL LANGUAGES (On-Demand Code-Mixed City Mover)
        LanguageInfo("mandarin", "Mandarin Chinese", "中文", "China, Taiwan, Singapore", "1.6 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("zh_1", "Please wait 1 min", "请等一下.", "1 min qǐng děng yīxià", "Wait 1 min", "Bite-sized city mover phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("zh_2", "How much is this?", "这个多少钱？", "Zhège duōshao qián?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("zh_3", "I don't know Mandarin", "我不会中文.", "Bú huì zhōngwén", "Don't know Mandarin", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("spanish", "Spanish (Spanglish)", "Español", "Spain & Latin America", "1.4 MB", LanguageAvailability.PREVIEW, 12, listOf(
            Phrase("es_1", "Please wait 1 min", "1 min por favor.", "1 min por favor", "Wait 1 min", "Bite-sized Spanglish phrase", "Apartment", 1, "Spanglish"),
            Phrase("es_2", "How much is this?", "¿Cuánto cuesta esto?", "Cuanto cuesta esto?", "How much?", "Shopping query", "Shopping", 1, "Spanglish"),
            Phrase("es_3", "I don't know Spanish", "No hablo español.", "No hablo espanol", "Don't know Spanish", "#1 newcomer survival phrase", "City Survival", 1, "Spanglish")
        )),

        LanguageInfo("arabic", "Arabic", "العربية", "Middle East & North Africa", "1.5 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("ar_1", "Please wait 1 min", "انتظر دقيقة من فضلك.", "1 min wait fadlik", "Wait 1 min", "Bite-sized Arabic phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("ar_2", "How much is this?", "كم هذا؟", "Kam haatha?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("ar_3", "I don't know Arabic", "لا أعرف العربية.", "La a'rif 'arabi", "Don't know Arabic", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("french", "French", "Français", "France, Canada, Africa", "1.3 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("fr_1", "Please wait 1 min", "1 min s'il vous plaît.", "1 min s'il vous plait", "Wait 1 min", "Bite-sized French phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("fr_2", "How much is this?", "C'est combien ?", "C'est combien?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("fr_3", "I don't know French", "Je ne parle pas français.", "Je ne parle pas francais", "Don't know French", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("portuguese", "Portuguese", "Português", "Brazil & Portugal", "1.3 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("pt_1", "Please wait 1 min", "1 min por favor.", "1 min por favor", "Wait 1 min", "Bite-sized Portuguese phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("pt_2", "How much is this?", "Quanto custa isto?", "Quanto custa?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("pt_3", "I don't know Portuguese", "Não falo português.", "Nao falo portugues", "Don't know Portuguese", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("russian", "Russian", "Русский", "Russia & Eastern Europe", "1.4 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("ru_1", "Please wait 1 min", "1 минуту, пожалуйста.", "1 min minutku pozhaluysta", "Wait 1 min", "Bite-sized Russian phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("ru_2", "How much is this?", "Сколько это стоит?", "Skol'ko eto stoit?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("ru_3", "I don't know Russian", "Я не говорю по-русски.", "Ya ne govoryu po-russki", "Don't know Russian", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("indonesian", "Indonesian (Bahasa)", "Bahasa Indonesia", "Indonesia & Malaysia", "1.1 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("id_1", "Please wait 1 min", "Tunggu 1 min ya.", "Tunggu 1 min ya", "Wait 1 min", "Bite-sized Bahasa phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("id_2", "How much is this?", "Berapa harganya?", "Berapa harganya?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("id_3", "I don't know Bahasa", "Saya tidak bisa Bahasa.", "Saya tidak bisa Bahasa", "Don't know Bahasa", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("german", "German", "Deutsch", "Germany, Austria, Switzerland", "1.3 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("de_1", "Please wait 1 min", "1 min bitte.", "1 min bitte", "Wait 1 min", "Bite-sized German phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("de_2", "How much is this?", "Wie viel kostet das?", "Wie viel kostet das?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("de_3", "I don't know German", "Ich spreche kein Deutsch.", "Ich spreche kein Deutsch", "Don't know German", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("japanese", "Japanese", "日本語", "Japan", "1.5 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("ja_1", "Please wait 1 min", "1 min ちょっと待ってください.", "1 min chotto matte kudasai", "Wait 1 min", "Bite-sized Japanese phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("ja_2", "How much is this?", "いくらですか？", "Ikura desu ka?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("ja_3", "I don't know Japanese", "日本語がわかりません.", "Nihongo ga wakarimasen", "Don't know Japanese", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("swahili", "Swahili", "Kiswahili", "East Africa (Kenya, Tanzania)", "1.0 MB", LanguageAvailability.PREVIEW, 8, listOf(
            Phrase("sw_1", "Please wait 1 min", "Ngoja 1 min kidogo.", "Ngoja 1 min kidogo", "Wait 1 min", "Bite-sized Swahili phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("sw_2", "How much is this?", "Ni kiasi gani?", "Ni kiasi gani?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("sw_3", "I don't know Swahili", "Sijui Kiswahili.", "Sijui Kiswahili", "Don't know Swahili", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("turkish", "Turkish", "Türkçe", "Turkey", "1.2 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("tr_1", "Please wait 1 min", "1 min bir dakika lütfen.", "1 min bir dakika lutfen", "Wait 1 min", "Bite-sized Turkish phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("tr_2", "How much is this?", "Bu ne kadar?", "Bu ne kadar?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("tr_3", "I don't know Turkish", "Türkçe bilmiyorum.", "Turkce bilmiyorum", "Don't know Turkish", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("korean", "Korean", "한국어", "South Korea", "1.4 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("ko_1", "Please wait 1 min", "1 min 잠시만 기다려 주세요.", "1 min jamsiman gidaryeo juseyo", "Wait 1 min", "Bite-sized Korean phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("ko_2", "How much is this?", "얼마예요?", "Eolmayeyo?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("ko_3", "I don't know Korean", "한국어 못해요.", "Hangug-eo moshaeyo", "Don't know Korean", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("vietnamese", "Vietnamese", "Tiếng Việt", "Vietnam", "1.1 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("vi_1", "Please wait 1 min", "1 min chờ chút.", "1 min cho chut", "Wait 1 min", "Bite-sized Vietnamese phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("vi_2", "How much is this?", "Bao nhiêu tiền?", "Bao nhieu tien?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("vi_3", "I don't know Vietnamese", "Tôi không biết tiếng Việt.", "Toi khong biet tieng Viet", "Don't know Vietnamese", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("italian", "Italian", "Italiano", "Italy", "1.2 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("it_1", "Please wait 1 min", "Un momento per favore.", "1 min un momento per favore", "Wait 1 min", "Bite-sized Italian phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("it_2", "How much is this?", "Quanto costa?", "Quanto costa?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("it_3", "I don't know Italian", "Non parlo italiano.", "Non parlo italiano", "Don't know Italian", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("thai", "Thai", "ภาษาไทย", "Thailand", "1.3 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("th_1", "Please wait 1 min", "1 min รอสักครู่.", "1 min ro sak khru", "Wait 1 min", "Bite-sized Thai phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("th_2", "How much is this?", "เท่าไหร่?", "Thao rai?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("th_3", "I don't know Thai", "พูดไทยไม่ได้.", "Phut thai mai dai", "Don't know Thai", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("dutch", "Dutch", "Nederlands", "Netherlands & Belgium", "1.2 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("nl_1", "Please wait 1 min", "1 min momentje a.u.b.", "1 min momentje aub", "Wait 1 min", "Bite-sized Dutch phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("nl_2", "How much is this?", "Hoeveel kost dit?", "Hoeveel kost dit?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("nl_3", "I don't know Dutch", "Ik spreek geen Nederlands.", "Ik spreek geen Nederlands", "Don't know Dutch", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        LanguageInfo("polish", "Polish", "Polski", "Poland", "1.2 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("pl_1", "Please wait 1 min", "1 min chwileczkę proszę.", "1 min chwileczke prosze", "Wait 1 min", "Bite-sized Polish phrase", "Apartment", 1, "Code-Mixed"),
            Phrase("pl_2", "How much is this?", "Ile to kosztuje?", "Ile to kosztuje?", "How much?", "Shopping query", "Shopping", 1, "Code-Mixed"),
            Phrase("pl_3", "I don't know Polish", "Nie mówię po polsku.", "Nie mowie po polsku", "Don't know Polish", "#1 newcomer survival phrase", "City Survival", 1, "Code-Mixed")
        )),

        // INDIAN REGIONAL LANGUAGES (Hinglish, Tanglish, Telglish, Manglish, etc.)
        LanguageInfo("hindi", "Hindi (Hinglish)", "हिन्दी", "North & Central India", "1.1 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("hi_1", "Please wait 1 min", "1 मिनट वेट करो.", "1 min wait karo bhaiya", "Wait 1 min", "Bite-sized guard/delivery phrase", "Apartment", 1, "Hinglish"),
            Phrase("hi_2", "Put the meter", "मीटर लगाओ भैया.", "Meter lagao bhaiya", "Put meter", "Auto driver instruction", "Transport", 1, "Hinglish"),
            Phrase("hi_3", "I don't know Hindi", "हिंदी नहीं आती.", "Hindi nahi aati", "Don't know Hindi", "#1 newcomer survival phrase", "City Survival", 1, "Hinglish")
        )),

        LanguageInfo("tamil", "Tamil (Tanglish)", "தமிழ்", "Tamil Nadu & Puducherry", "1.0 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("ta_1", "Please wait 1 min", "1 நிமிஷம் வெயிட் பண்ணுங்க.", "1 min wait pannunga", "Wait 1 min", "Bite-sized Tanglish phrase", "Apartment", 1, "Tanglish"),
            Phrase("ta_2", "Put the meter", "மீட்டர் போடுங்க அண்ணா.", "Meter podunga anna", "Put meter", "Auto hailing phrase", "Transport", 1, "Tanglish"),
            Phrase("ta_3", "I don't know Tamil", "தமிழ் தெரியாது.", "Tamil theriyadhu", "Don't know Tamil", "#1 newcomer survival phrase", "City Survival", 1, "Tanglish")
        )),

        LanguageInfo("telugu", "Telugu (Telglish)", "తెలుగు", "Andhra Pradesh & Telangana", "1.1 MB", LanguageAvailability.PREVIEW, 10, listOf(
            Phrase("te_1", "Please wait 1 min", "1 నిమిషం వెయిట్ చేయండి.", "1 min wait cheyandi", "Wait 1 min", "Bite-sized Telglish phrase", "Apartment", 1, "Telglish"),
            Phrase("te_2", "Put the meter", "మీటర్ వేయండి భయ్యా.", "Meter veyandi bhayya", "Put meter", "Auto hailing phrase", "Transport", 1, "Telglish"),
            Phrase("te_3", "I don't know Telugu", "తెలుగు రాదు.", "Telugu raadu", "Don't know Telugu", "#1 newcomer survival phrase", "City Survival", 1, "Telglish")
        )),

        LanguageInfo("malayalam", "Malayalam (Manglish)", "മലയാളം", "Kerala & Lakshadweep", "1.0 MB", LanguageAvailability.PREVIEW, 8, listOf(
            Phrase("ml_1", "Please wait 1 min", "1 മിനിറ്റ് വെയിറ്റ് ചെയ്യൂ.", "1 min wait cheyyu", "Wait 1 min", "Bite-sized Manglish phrase", "Apartment", 1, "Manglish"),
            Phrase("ml_2", "I don't know Malayalam", "മലയാളം അറിയില്ല.", "Malayalam aariyilla", "Don't know Malayalam", "#1 newcomer survival phrase", "City Survival", 1, "Manglish")
        )),

        LanguageInfo("marathi", "Marathi (Marathglish)", "मराठी", "Maharashtra", "1.1 MB", LanguageAvailability.PREVIEW, 8, listOf(
            Phrase("mr_1", "Please wait 1 min", "1 मिनिट वेट करा.", "1 min wait kara", "Wait 1 min", "Bite-sized Marathglish phrase", "Apartment", 1, "Marathglish"),
            Phrase("mr_2", "I don't know Marathi", "मराठी येत नाही.", "Marathi yet nahi", "Don't know Marathi", "#1 newcomer survival phrase", "City Survival", 1, "Marathglish")
        )),

        LanguageInfo("bengali", "Bengali (Benglish)", "বাংলা", "West Bengal & Tripura", "1.1 MB", LanguageAvailability.PREVIEW, 8, listOf(
            Phrase("bn_1", "Please wait 1 min", "1 মিনিট ওয়েট করুন.", "1 min wait korun", "Wait 1 min", "Bite-sized Benglish phrase", "Apartment", 1, "Benglish"),
            Phrase("bn_2", "I don't know Bengali", "বাংলা জানি না.", "Bangla jani na", "Don't know Bengali", "#1 newcomer survival phrase", "City Survival", 1, "Benglish")
        )),

        LanguageInfo("gujarati", "Gujarati (Gujglish)", "ગુજરાતી", "Gujarat", "1.0 MB", LanguageAvailability.PREVIEW, 8, listOf(
            Phrase("gu_1", "Please wait 1 min", "1 મિનિટ વેટ કરો.", "1 min wait karo", "Wait 1 min", "Bite-sized Gujglish phrase", "Apartment", 1, "Gujglish"),
            Phrase("gu_2", "I don't know Gujarati", "ગુજરાતી નથી આવડતું.", "Gujarati nathi aavtu", "Don't know Gujarati", "#1 newcomer survival phrase", "City Survival", 1, "Gujglish")
        )),

        LanguageInfo("punjabi", "Punjabi (Punglish)", "ਪੰਜਾਬੀ", "Punjab", "1.0 MB", LanguageAvailability.PREVIEW, 8, listOf(
            Phrase("pa_1", "Please wait 1 min", "1 ਮਿੰਟ ਵੇਟ ਕਰੋ ਵੀਰੇ.", "1 min wait karo veere", "Wait 1 min", "Bite-sized Punglish phrase", "Apartment", 1, "Punglish"),
            Phrase("pa_2", "I don't know Punjabi", "ਪੰਜਾਬੀ ਨਹੀਂ ਆਉਂਦੀ.", "Punjabi nahi aandi", "Don't know Punjabi", "#1 newcomer survival phrase", "City Survival", 1, "Punglish")
        )),

        LanguageInfo("odia", "Odia (Odiglish)", "ଓଡ଼ିଆ", "Odisha", "1.0 MB", LanguageAvailability.PREVIEW, 8, listOf(
            Phrase("or_1", "Please wait 1 min", "1 ମିନିଟ ୱେଟ କରନ୍ତୁ.", "1 min wait karantu", "Wait 1 min", "Bite-sized Odiglish phrase", "Apartment", 1, "Odiglish"),
            Phrase("or_2", "I don't know Odia", "ଓଡ଼ିଆ ଆସୁନି.", "Odia asuni", "Don't know Odia", "#1 newcomer survival phrase", "City Survival", 1, "Odiglish")
        )),

        LanguageInfo("assamese", "Assamese (Asglish)", "অসমীয়া", "Assam", "1.0 MB", LanguageAvailability.PREVIEW, 8, listOf(
            Phrase("as_1", "Please wait 1 min", "1 মিনিট ৱেইট কৰক.", "1 min wait korok", "Wait 1 min", "Bite-sized Asglish phrase", "Apartment", 1, "Asglish"),
            Phrase("as_2", "I don't know Assamese", "অসমীয়া না জানো.", "Axomiya najano", "Don't know Assamese", "#1 newcomer survival phrase", "City Survival", 1, "Asglish")
        )),

        LanguageInfo("konkani", "Konkani (Konglish)", "कोंकणी", "Goa & Coastal Karnataka", "900 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("kk_1", "Please wait 1 min", "1 मिनट वेट कर.", "1 min wait kor", "Wait 1 min", "Konglish phrase", "Apartment", 1, "Konglish"),
            Phrase("kk_2", "I don't know Konkani", "कोंकणी कळना.", "Konkani kalana", "Don't know Konkani", "#1 newcomer survival phrase", "City Survival", 1, "Konglish")
        )),

        LanguageInfo("tulu", "Tulu (Tulglish)", "ತುಳು", "Tulunadu (Mangaluru)", "850 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("tu_1", "Please wait 1 min", "1 ನಿಮಿಷ ವೈಟ್ ಮಲ್ಪಿ.", "1 min wait malpi", "Wait 1 min", "Tulglish phrase", "Apartment", 1, "Tulglish"),
            Phrase("tu_2", "I don't know Tulu", "ತುಳು ಬರ್ಪುಜಿ.", "Tulu barpuji", "Don't know Tulu", "#1 newcomer survival phrase", "City Survival", 1, "Tulglish")
        )),

        LanguageInfo("kodava", "Kodava Takk", "ಕೊಡವ ತಕ್ಕ್", "Kodagu (Coorg)", "850 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("ko_1", "Please wait 1 min", "1 ನಿಮಿಷ ವೈಟ್ ಮಾಡಿ.", "1 min wait maadi", "Wait 1 min", "Kodava phrase", "Apartment", 1, "Kodava"),
            Phrase("ko_2", "I don't know Kodava", "ಕೊಡವ ತಕ್ಕ್ தெரியಾ.", "Kodava takk teriya", "Don't know Kodava", "#1 newcomer survival phrase", "City Survival", 1, "Kodava")
        )),

        LanguageInfo("maithili", "Maithili", "मैथिली", "Bihar & Jharkhand", "900 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("mai_1", "Please wait 1 min", "1 मिनट वेट करू.", "1 min wait karu", "Wait 1 min", "Maithili phrase", "Apartment", 1, "Maithili"),
            Phrase("mai_2", "I don't know Maithili", "मैथिली नहि आवैछ.", "Maithili nahi aavaiya", "Don't know Maithili", "#1 newcomer survival phrase", "City Survival", 1, "Maithili")
        )),

        LanguageInfo("bhojpuri", "Bhojpuri", "भोजपुरी", "Bihar & UP", "950 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("bho_1", "Please wait 1 min", "1 मिनट वेट करा भैया.", "1 min wait karaa bhaiya", "Wait 1 min", "Bhojpuri phrase", "Apartment", 1, "Bhojpuri"),
            Phrase("bho_2", "I don't know Bhojpuri", "भोजपुरी ना आवे.", "Bhojpuri nahi aave", "Don't know Bhojpuri", "#1 newcomer survival phrase", "City Survival", 1, "Bhojpuri")
        )),

        LanguageInfo("santali", "Santali", "ᱥᱟᱱᱛᱟᱲᱤ", "Jharkhand & Odisha", "850 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("sat_1", "Please wait 1 min", "1 min wait me.", "1 min wait me", "Wait 1 min", "Santali phrase", "Apartment", 1, "Santali"),
            Phrase("sat_2", "I don't know Santali", "Santali ban badaya.", "Santali ban badaya", "Don't know Santali", "#1 newcomer survival phrase", "City Survival", 1, "Santali")
        )),

        LanguageInfo("kashmiri", "Kashmiri", "कॉशुर", "Jammu & Kashmir", "900 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("ks_1", "Please wait 1 min", "1 मिनट वेट करिव.", "1 min wait kariv", "Wait 1 min", "Kashmiri phrase", "Apartment", 1, "Kashmiri"),
            Phrase("ks_2", "I don't know Kashmiri", "कश्मीरी छु न यिवान.", "Kashmiri chhu na yivaan", "Don't know Kashmiri", "#1 newcomer survival phrase", "City Survival", 1, "Kashmiri")
        )),

        LanguageInfo("nepali", "Nepali", "नेपाली", "Sikkim & West Bengal", "900 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("ne_1", "Please wait 1 min", "1 मिनट वेट गर्नुहोस्.", "1 min wait garnuhos", "Wait 1 min", "Nepali phrase", "Apartment", 1, "Nepali"),
            Phrase("ne_2", "I don't know Nepali", "नेपाली आउँदैन.", "Nepali aaudaina", "Don't know Nepali", "#1 newcomer survival phrase", "City Survival", 1, "Nepali")
        )),

        LanguageInfo("sindhi", "Sindhi", "સિંધિ", "Gujarat & Maharashtra", "900 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("sd_1", "Please wait 1 min", "1 મિનિટ વેટ કરો.", "1 min wait karo", "Wait 1 min", "Sindhi phrase", "Apartment", 1, "Sindhi"),
            Phrase("sd_2", "I don't know Sindhi", "સિંધી ન આંન્દે.", "Sindhi na aande", "Don't know Sindhi", "#1 newcomer survival phrase", "City Survival", 1, "Sindhi")
        )),

        LanguageInfo("dogri", "Dogri", "डोगरी", "Jammu Region", "850 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("doi_1", "Please wait 1 min", "1 मिनट वेट करो.", "1 min wait karo", "Wait 1 min", "Dogri phrase", "Apartment", 1, "Dogri"),
            Phrase("doi_2", "I don't know Dogri", "डोगरी नि आउंदी.", "Dogri ni aundi", "Don't know Dogri", "#1 newcomer survival phrase", "City Survival", 1, "Dogri")
        )),

        LanguageInfo("manipuri", "Manipuri (Meitei)", "মৈতৈলোન્", "Manipur", "850 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("mni_1", "Please wait 1 min", "1 min wait toubiro.", "1 min wait toubiro", "Wait 1 min", "Manipuri phrase", "Apartment", 1, "Manipuri"),
            Phrase("mni_2", "I don't know Manipuri", "মৈতৈলোન્ খংদে.", "Meiteilon khangde", "Don't know Manipuri", "#1 newcomer survival phrase", "City Survival", 1, "Manipuri")
        )),

        LanguageInfo("bodo", "Bodo", "बर'", "Assam", "800 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("brx_1", "Please wait 1 min", "1 min wait khalam.", "1 min wait khalam", "Wait 1 min", "Bodo phrase", "Apartment", 1, "Bodo"),
            Phrase("brx_2", "I don't know Bodo", "बर' मिथिया.", "Bodo mithiya", "Don't know Bodo", "#1 newcomer survival phrase", "City Survival", 1, "Bodo")
        )),

        LanguageInfo("sanskrit", "Sanskrit", "संस्कृतम्", "Pan-India Classical", "1.0 MB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("sa_1", "Please wait 1 min", "एक निमिषं प्रतीक्षणं करोतु.", "Eka nimisham prateekshanam karotu", "Wait 1 min", "Sanskrit phrase", "Apartment", 1, "Sanskrit"),
            Phrase("sa_2", "I don't know Sanskrit", "संस्कृतं न जानामि.", "Sanskritam na jaanami", "Don't know Sanskrit", "Classical phrase", "City Survival", 1, "Sanskrit")
        )),

        LanguageInfo("urdu", "Urdu", "اُردُو", "Pan-India", "1.1 MB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("ur_1", "Please wait 1 min", "1 منٹ ویٹ کیجیے۔", "1 min wait kijiye", "Wait 1 min", "Urdu phrase", "Apartment", 1, "Urdu"),
            Phrase("ur_2", "I don't know Urdu", "اردو نہیں آتی۔", "Urdu nahi aati", "Don't know Urdu", "#1 newcomer survival phrase", "City Survival", 1, "Urdu")
        )),

        LanguageInfo("marwari", "Marwari", "मारवाड़ी", "Rajasthan", "900 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("mwr_1", "Please wait 1 min", "1 मिनट वेट करो सा.", "1 min wait karo saab", "Wait 1 min", "Marwari phrase", "Apartment", 1, "Marwari"),
            Phrase("mwr_2", "I don't know Marwari", "मारवाड़ी कोनी आवे.", "Marwari koni aave", "Don't know Marwari", "#1 newcomer survival phrase", "City Survival", 1, "Marwari")
        )),

        LanguageInfo("chhattisgarhi", "Chhattisgarhi", "छत्तीसगढ़ी", "Chhattisgarh", "850 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("hne_1", "Please wait 1 min", "1 मिनट वेट करहू.", "1 min wait karahu", "Wait 1 min", "Chhattisgarhi phrase", "Apartment", 1, "Chhattisgarhi"),
            Phrase("hne_2", "I don't know Chhattisgarhi", "छत्तीसगढ़ी नहीं आवे.", "Chhattisgarhi nahi aave", "Don't know Chhattisgarhi", "#1 newcomer survival phrase", "City Survival", 1, "Chhattisgarhi")
        )),

        LanguageInfo("garhwali", "Garhwali", "गढ़वाली", "Uttarakhand", "850 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("gbm_1", "Please wait 1 min", "1 मिनट वेट करां.", "1 min wait karaan", "Wait 1 min", "Garhwali phrase", "Apartment", 1, "Garhwali"),
            Phrase("gbm_2", "I don't know Garhwali", "गढ़वाली नि आउंदी.", "Garhwali ni aundi", "Don't know Garhwali", "#1 newcomer survival phrase", "City Survival", 1, "Garhwali")
        )),

        LanguageInfo("kumaoni", "Kumaoni", "कुमाऊँनी", "Uttarakhand", "850 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("kfy_1", "Please wait 1 min", "1 मिनट वेट करा.", "1 min wait karaa", "Wait 1 min", "Kumaoni phrase", "Apartment", 1, "Kumaoni"),
            Phrase("kfy_2", "I don't know Kumaoni", "कुमाऊँनी नि आउंदी.", "Kumaoni ni aundi", "Don't know Kumaoni", "#1 newcomer survival phrase", "City Survival", 1, "Kumaoni")
        )),

        LanguageInfo("haryanvi", "Haryanvi", "हरियाणवी", "Haryana", "900 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("bgc_1", "Please wait 1 min", "1 मिनट वेट कर ले भाई.", "1 min wait kar le bhai", "Wait 1 min", "Haryanvi phrase", "Apartment", 1, "Haryanvi"),
            Phrase("bgc_2", "I don't know Haryanvi", "हरियाणवी ना आंदी.", "Haryanvi na aandi", "Don't know Haryanvi", "#1 newcomer survival phrase", "City Survival", 1, "Haryanvi")
        )),

        LanguageInfo("mizo", "Mizo", "Mizo ṭawng", "Mizoram", "800 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("lus_1", "Please wait 1 min", "1 min wait rawh.", "1 min wait rawh", "Wait 1 min", "Mizo phrase", "Apartment", 1, "Mizo"),
            Phrase("lus_2", "I don't know Mizo", "Mizo tawng ka thiam lo.", "Mizo tawng ka thiam lo", "Don't know Mizo", "#1 newcomer survival phrase", "City Survival", 1, "Mizo")
        )),

        LanguageInfo("khasi", "Khasi", "Ka Ktien Khasi", "Meghalaya", "800 KB", LanguageAvailability.PREVIEW, 5, listOf(
            Phrase("kha_1", "Please wait 1 min", "1 min ap shu.", "1 min ap shu", "Wait 1 min", "Khasi phrase", "Apartment", 1, "Khasi"),
            Phrase("kha_2", "I don't know Khasi", "Nga khlem tip Khasi.", "Nga khlem tip Khasi", "Don't know Khasi", "#1 newcomer survival phrase", "City Survival", 1, "Khasi")
        ))
    )
}
