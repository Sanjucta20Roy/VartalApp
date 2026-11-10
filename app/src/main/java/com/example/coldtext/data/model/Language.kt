package com.example.coldtext.data.model

data class Language(
    val id: String,
    val name: String,
    val nativeName: String,
    val scriptName: String,
    val isSupported: Boolean,
    val localeCode: String,
    val region: String
)

val INDIAN_LANGUAGES = listOf(
    // Major Regional & Official Languages
    Language("kn", "Kannada", "ಕನ್ನಡ", "Kannada Script", true, "kn_IN", "Karnataka"),
    Language("ta", "Tamil", "தமிழ்", "Tamil Script", true, "ta_IN", "Tamil Nadu & Puducherry"),
    Language("te", "Telugu", "తెలుగు", "Telugu Script", true, "te_IN", "Andhra Pradesh & Telangana"),
    Language("hi", "Hindi", "हिन्दी", "Devanagari", true, "hi_IN", "North & Central India"),
    Language("ml", "Malayalam", "മലയാളം", "Malayalam Script", true, "ml_IN", "Kerala & Lakshadweep"),
    Language("mr", "Marathi", "मराठी", "Devanagari", true, "mr_IN", "Maharashtra"),
    Language("bn", "Bengali", "বাংলা", "Bengali Script", true, "bn_IN", "West Bengal & Tripura"),
    Language("gu", "Gujarati", "ગુજરાતી", "Gujarati Script", false, "gu_IN", "Gujarat"),
    Language("pa", "Punjabi", "ਪੰਜਾਬੀ", "Gurmukhi", false, "pa_IN", "Punjab"),
    Language("or", "Odia", "ଓଡ଼ିଆ", "Odia Script", false, "or_IN", "Odisha"),
    Language("as", "Assamese", "অসমীয়া", "Bengali-Assamese", false, "as_IN", "Assam"),
    Language("ur", "Urdu", "اردو", "Perso-Arabic", false, "ur_IN", "Pan-India"),
    Language("kok", "Konkani", "ಕೊಂಕಣಿ / कोंकणी", "Devanagari / Kannada", false, "kok_IN", "Goa & Coastal Karnataka"),

    // Scheduled & Regional Languages (30+ total)
    Language("mai", "Maithili", "मैथिली", "Tirhuta / Devanagari", false, "mai_IN", "Bihar & Jharkhand"),
    Language("sat", "Santali", "ᱥᱟᱱᱛᱟᱲᱤ", "Ol Chiki", false, "sat_IN", "Jharkhand & Odisha"),
    Language("ks", "Kashmiri", "कॉशुर / کٲشُر", "Perso-Arabic / Devanagari", false, "ks_IN", "Jammu & Kashmir"),
    Language("ne", "Nepali", "नेपाली", "Devanagari", false, "ne_IN", "Sikkim & North Bengal"),
    Language("doi", "Dogri", "डोगरी", "Devanagari", false, "doi_IN", "Jammu"),
    Language("mni", "Manipuri (Meitei)", "ꯃꯩꯇꯩꯂꯣꯟ", "Meitei Mayek", false, "mni_IN", "Manipur"),
    Language("brx", "Bodo", "बर'", "Devanagari", false, "brx_IN", "Assam"),
    Language("sa", "Sanskrit", "संस्कृतम्", "Devanagari", false, "sa_IN", "Pan-India"),
    Language("sd", "Sindhi", "سنڌي / सिन्धी", "Arabic / Devanagari", false, "sd_IN", "Gujarat & Rajasthan"),

    // Vibrant Regional Dialects & Languages
    Language("tcy", "Tulu", "ತುಳು", "Tigalari / Kannada", false, "tcy_IN", "Tulunadu (Karnataka)"),
    Language("kdf", "Kodava", "ಕೊಡವ", "Kannada Script", false, "kdf_IN", "Kodagu (Karnataka)"),
    Language("bho", "Bhojpuri", "भोजपुरी", "Devanagari", false, "bho_IN", "Bihar & UP"),
    Language("mag", "Magahi", "मगही", "Devanagari", false, "mag_IN", "Southern Bihar"),
    Language("anp", "Angika", "अंगिका", "Anga Lipi / Devanagari", false, "anp_IN", "Eastern Bihar"),
    Language("raj", "Rajasthani", "राजस्थानी", "Devanagari", false, "raj_IN", "Rajasthan"),
    Language("hne", "Chhattisgarhi", "छत्तीसगढ़ी", "Devanagari", false, "hne_IN", "Chhattisgarh"),
    Language("bgc", "Haryanvi", "हरियाणवी", "Devanagari", false, "bgc_IN", "Haryana"),
    Language("gwr", "Garhwali", "गढ़वाली", "Devanagari", false, "gwr_IN", "Uttarakhand"),
    Language("kfy", "Kumaoni", "कुमाऊँनी", "Devanagari", false, "kfy_IN", "Uttarakhand")
)
