package com.example.coldtext.data.model

enum class CourseStatus(val label: String) {
    AVAILABLE("Starter Course Available"),
    PREVIEW("Preview Available"),
    IN_DEVELOPMENT("In Development"),
    ROADMAP("Planned Roadmap")
}

data class Language(
    val id: String,
    val name: String,
    val nativeName: String,
    val scriptName: String,
    val courseStatus: CourseStatus,
    val phraseCount: Int,
    val reviewedPhraseCount: Int,
    val localeCode: String,
    val region: String
)

val INDIAN_LANGUAGES = listOf(
    // 1. Starter Course (Complete Lesson Path)
    Language("kn", "Kannada", "ಕನ್ನಡ", "Kannada Script", CourseStatus.AVAILABLE, 12, 12, "kn_IN", "Karnataka"),

    // 2. Previews Available (Early Phrase Samples)
    Language("ta", "Tamil", "தமிழ்", "Tamil Script", CourseStatus.PREVIEW, 2, 2, "ta_IN", "Tamil Nadu & Puducherry"),
    Language("te", "Telugu", "తెలుగు", "Telugu Script", CourseStatus.PREVIEW, 1, 1, "te_IN", "Andhra Pradesh & Telangana"),
    Language("hi", "Hindi", "हिन्दी", "Devanagari", CourseStatus.PREVIEW, 1, 1, "hi_IN", "North & Central India"),
    Language("ml", "Malayalam", "മലയാളം", "Malayalam Script", CourseStatus.PREVIEW, 1, 1, "ml_IN", "Kerala & Lakshadweep"),
    Language("mr", "Marathi", "मराठी", "Devanagari", CourseStatus.PREVIEW, 1, 1, "mr_IN", "Maharashtra"),

    // 3. Courses In Active Development
    Language("bn", "Bengali", "বাংলা", "Bengali Script", CourseStatus.IN_DEVELOPMENT, 0, 0, "bn_IN", "West Bengal & Tripura"),
    Language("gu", "Gujarati", "ગુજરાતી", "Gujarati Script", CourseStatus.IN_DEVELOPMENT, 0, 0, "gu_IN", "Gujarat"),
    Language("pa", "Punjabi", "ਪੰਜਾਬੀ", "Gurmukhi", CourseStatus.IN_DEVELOPMENT, 0, 0, "pa_IN", "Punjab"),
    Language("or", "Odia", "ଓଡ଼ିଆ", "Odia Script", CourseStatus.IN_DEVELOPMENT, 0, 0, "or_IN", "Odisha"),
    Language("as", "Assamese", "অসমীয়া", "Bengali-Assamese", CourseStatus.IN_DEVELOPMENT, 0, 0, "as_IN", "Assam"),
    Language("ur", "Urdu", "اردو", "Perso-Arabic", CourseStatus.IN_DEVELOPMENT, 0, 0, "ur_IN", "Pan-India"),
    Language("kok", "Konkani", "ಕೊಂಕಣಿ / कोंकणी", "Devanagari / Kannada", CourseStatus.IN_DEVELOPMENT, 0, 0, "kok_IN", "Goa & Coastal Karnataka"),

    // 4. Extensible Language Roadmap (19 Regional Languages & Dialects)
    Language("mai", "Maithili", "मैथिली", "Tirhuta / Devanagari", CourseStatus.ROADMAP, 0, 0, "mai_IN", "Bihar & Jharkhand"),
    Language("sat", "Santali", "ᱥᱟᱱᱛᱟᱲᱤ", "Ol Chiki", CourseStatus.ROADMAP, 0, 0, "sat_IN", "Jharkhand & Odisha"),
    Language("ks", "Kashmiri", "कॉशुर / کٲشُر", "Perso-Arabic / Devanagari", CourseStatus.ROADMAP, 0, 0, "ks_IN", "Jammu & Kashmir"),
    Language("ne", "Nepali", "नेपाली", "Devanagari", CourseStatus.ROADMAP, 0, 0, "ne_IN", "Sikkim & North Bengal"),
    Language("doi", "Dogri", "डोगरी", "Devanagari", CourseStatus.ROADMAP, 0, 0, "doi_IN", "Jammu"),
    Language("mni", "Manipuri (Meitei)", "ꯃꯩꯇꯩꯂꯣꯟ", "Meitei Mayek", CourseStatus.ROADMAP, 0, 0, "mni_IN", "Manipur"),
    Language("brx", "Bodo", "बर'", "Devanagari", CourseStatus.ROADMAP, 0, 0, "brx_IN", "Assam"),
    Language("sa", "Sanskrit", "संस्कृतम्", "Devanagari", CourseStatus.ROADMAP, 0, 0, "sa_IN", "Pan-India"),
    Language("sd", "Sindhi", "سنڌي / सिन्धी", "Arabic / Devanagari", CourseStatus.ROADMAP, 0, 0, "sd_IN", "Gujarat & Rajasthan"),
    Language("tcy", "Tulu", "ತುಳು", "Tigalari / Kannada", CourseStatus.ROADMAP, 0, 0, "tcy_IN", "Tulunadu (Karnataka)"),
    Language("kdf", "Kodava", "ಕೊಡವ", "Kannada Script", CourseStatus.ROADMAP, 0, 0, "kdf_IN", "Kodagu (Karnataka)"),
    Language("bho", "Bhojpuri", "भोजपुरी", "Devanagari", CourseStatus.ROADMAP, 0, 0, "bho_IN", "Bihar & UP"),
    Language("mag", "Magahi", "मगही", "Devanagari", CourseStatus.ROADMAP, 0, 0, "mag_IN", "Southern Bihar"),
    Language("anp", "Angika", "अंगिका", "Anga Lipi / Devanagari", CourseStatus.ROADMAP, 0, 0, "anp_IN", "Eastern Bihar"),
    Language("raj", "Rajasthani", "राजस्थानी", "Devanagari", CourseStatus.ROADMAP, 0, 0, "raj_IN", "Rajasthan"),
    Language("hne", "Chhattisgarhi", "छत्तीसगढ़ी", "Devanagari", CourseStatus.ROADMAP, 0, 0, "hne_IN", "Chhattisgarh"),
    Language("bgc", "Haryanvi", "हरियाणवी", "Devanagari", CourseStatus.ROADMAP, 0, 0, "bgc_IN", "Haryana"),
    Language("gwr", "Garhwali", "गढ़वाली", "Devanagari", CourseStatus.ROADMAP, 0, 0, "gwr_IN", "Uttarakhand"),
    Language("kfy", "Kumaoni", "कुमाऊँनी", "Devanagari", CourseStatus.ROADMAP, 0, 0, "kfy_IN", "Uttarakhand")
)
