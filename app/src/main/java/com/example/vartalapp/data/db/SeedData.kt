package com.example.vartalapp.data.db

object SeedData {
    val initialPhrases: List<PhraseEntity> = listOf(
        // Pack 1: Greetings & Introductions
        PhraseEntity("p1_1", "Hello / Greetings", "ನಮಸ್ಕಾರ", "Namaskara sir", "Hello", "Bare minimum universal greeting in Bengaluru.", "Greetings & Introductions", 1, "Polite Kanglish", "Namaste", "Native-speaker verified", "pack_1"),
        PhraseEntity("p1_2", "How are you?", "ಹೇಗಿದ್ದೀರಾ?", "Hegiddeera?", "How are you?", "Short polite well-being query.", "Greetings & Introductions", 1, "Polite Kanglish", "Hegiddeeya?", "Native-speaker verified", "pack_1"),
        PhraseEntity("p1_3", "I am fine", "ಚೆನ್ನಾಗಿದ್ದೀನಿ.", "Chennagiddini", "I am fine", "Bite-sized answer to Hegiddeera.", "Greetings & Introductions", 1, "Casual Kanglish", "Fine iddini", "Native-speaker verified", "pack_1"),
        PhraseEntity("p1_4", "Thank you very much", "ತುಂಬಾ ಥ್ಯಾಂಕ್ಸ್.", "Thumba thanks", "Thank you so much", "Everyday code-mixed gratitude.", "Greetings & Introductions", 1, "Casual Kanglish", "Thanks sir", "Native-speaker verified", "pack_1"),
        PhraseEntity("p1_5", "What is your name?", "ನಿಮ್ಮ ಹೆಸರು?", "Nimma hesaru?", "Your name?", "Shortened 2-word name query.", "Greetings & Introductions", 1, "Polite Kanglish", "Name enu?", "Native-speaker verified", "pack_1"),
        PhraseEntity("p1_6", "My name is...", "ನನ್ನ ಹೆಸರು...", "Nanna hesaru...", "My name is...", "State name after Nanna hesaru.", "Greetings & Introductions", 1, "Neutral", null, "Native-speaker verified", "pack_1"),
        PhraseEntity("p1_7", "Yes / Okay", "ಹೌದು / ಆಯ್ತು", "Haudu / Aaythu", "Yes / Okay", "Universal confirmation.", "Greetings & Introductions", 1, "Neutral", "Aaythu sir", "Native-speaker verified", "pack_1"),
        PhraseEntity("p1_8", "No / Not there", "ಇಲ್ಲ", "Illa", "No / Not there", "The #1 single word for No or Absence.", "Greetings & Introductions", 1, "Neutral", "Alla", "Native-speaker verified", "pack_1"),

        // Pack 2: Numbers & Money
        PhraseEntity("p2_1", "How much is this?", "ಇದು ಎಷ್ಟು?", "Idu eshtu? / Rate eshtu?", "How much?", "Bare minimum price query.", "Numbers & Money", 1, "Casual Kanglish", "Rate eshtu sir?", "Native-speaker verified", "pack_2"),
        PhraseEntity("p2_2", "Give change", "ಚಿಲ್ಲಿರೆ ಕೊಡಿ.", "Chillare kodi / Change kodi", "Give change", "Request change money.", "Numbers & Money", 1, "Polite Kanglish", "Change idya?", "Native-speaker verified", "pack_2"),
        PhraseEntity("p2_3", "It is very expensive", "ತುಂಬಾ ಜಾಸ್ತಿ.", "Thumba jyaasthi", "Too high", "Shortened bargaining phrase.", "Numbers & Money", 1, "Casual Kanglish", "Thumba rate", "Native-speaker verified", "pack_2"),
        PhraseEntity("p2_4", "Reduce price a little", "ಸ್ವಲ್ಪ ಕಮ್ಮಿ ಮಾಡಿ.", "Swalpa kammi maadi", "Reduce a bit", "Bargaining instruction.", "Numbers & Money", 1, "Polite Kanglish", "Kammi maadi sir", "Native-speaker verified", "pack_2"),
        PhraseEntity("p2_5", "I will pay via GPay", "GPay ಮಾಡುತ್ತೇನೆ.", "GPay / Online pay maadthene", "Pay via GPay", "Crucial Bengaluru digital payment phrase.", "Numbers & Money", 1, "Casual Kanglish", "GPay idya sir?", "Native-speaker verified", "pack_2"),
        PhraseEntity("p2_6", "Give total bill", "ಟೋಟಲ್ ಬಿಲ್ ಕೊಡಿ.", "Total bill kodi", "Total bill", "Asking final bill.", "Numbers & Money", 1, "Polite Kanglish", "Bill eshtu?", "Native-speaker verified", "pack_2"),

        // Pack 3: Transport & Auto Drivers
        PhraseEntity("p3_1", "Will you come to Indiranagar?", "ಇಂದಿರಾನಗರ ಬರುತ್ತೀರಾ?", "Indiranagar barteera sir?", "Go to Indiranagar?", "Shortened auto driver hailing.", "Transport & Auto Drivers", 2, "Polite Kanglish", "Indiranagar-ge barteera?", "Native-speaker verified", "pack_3"),
        PhraseEntity("p3_2", "Please put the meter", "ಮೀಟರ್ ಹಾಕಿ.", "Meter haaki sir", "Put meter", "Auto meter request.", "Transport & Auto Drivers", 2, "Polite Kanglish", "Meter maadi sir", "Native-speaker verified", "pack_3"),
        PhraseEntity("p3_3", "Go straight", "ಸ್ಟ್ರೈಟ್ ಹೋಗಿ.", "Straight hogi", "Go straight", "Code-mixed direction.", "Transport & Auto Drivers", 2, "Casual Kanglish", "Nera hogi", "Native-speaker verified", "pack_3"),
        PhraseEntity("p3_4", "Turn left / right", "ಲೆಫ್ಟ್ / ರೈಟ್ ತಗೊಳ್ಳಿ.", "Left thagoli / Right thagoli", "Take left/right", "Universal direction instruction.", "Transport & Auto Drivers", 2, "Casual Kanglish", null, "Native-speaker verified", "pack_3"),
        PhraseEntity("p3_5", "Stop here", "ಇಲ್ಲಿ ನಿಲ್ಲಿಸಿ.", "Illi stop maadi / Illi nillisi", "Stop here", "Drop-off instruction.", "Transport & Auto Drivers", 2, "Casual Kanglish", "Illi stop maadi sir", "Native-speaker verified", "pack_3"),
        PhraseEntity("p3_6", "Drive slowly", "ಸ್ಲೋ ಆಗಿ ಹೋಗಿ.", "Slow aagi hogi", "Go slow", "Code-mixed safety request.", "Transport & Auto Drivers", 2, "Casual Kanglish", "Swalpa mella hogi", "Native-speaker verified", "pack_3"),

        // Pack 4: Apartment & Security Guard
        PhraseEntity("p4_1", "I am coming down", "ಬರುತ್ತಿದ್ದೀನಿ.", "Barthiddini / 1 min barthini", "Coming", "Bite-sized guard response.", "Apartment & Security Guard", 2, "Casual Kanglish", "Naanu barthiddini", "Native-speaker verified", "pack_4"),
        PhraseEntity("p4_2", "Please wait 1 min", "1 ನಿಮಿಷ ವೈಟ್ ಮಾಡಿ.", "1 min wait maadi", "Wait 1 min", "Ultra-practical short wait phrase.", "Apartment & Security Guard", 2, "Polite Kanglish", "Ondu nimisha kaayiri", "Native-speaker verified", "pack_4"),
        PhraseEntity("p4_3", "Give it to the caretaker", "ಕೇರ್‌ಟೇಕರ್‌ಗೆ ಕೊಡಿ.", "Caretaker-ge kodi", "Give caretaker", "Key/parcel instruction.", "Apartment & Security Guard", 2, "Polite Kanglish", "Guard-ge kodi", "Native-speaker verified", "pack_4"),
        PhraseEntity("p4_4", "I live in flat 503", "ಫ್ಲಾಟ್ 503 ನನ್ನದು.", "Flat 503 nannadhu", "Flat 503", "Shortened flat declaration.", "Apartment & Security Guard", 2, "Casual Kanglish", "503 flat-nalli iddini", "Native-speaker verified", "pack_4"),
        PhraseEntity("p4_5", "Keep parcel near gate", "ಪಾರ್ಸಲ್ ಗೇಟ್ ಹತ್ತಿರ ಇಡಿ.", "Parcel gate hatthira idi", "Leave at gate", "Contactless drop instruction.", "Apartment & Security Guard", 2, "Polite Kanglish", "Gate-nalli idi", "Native-speaker verified", "pack_4"),
        PhraseEntity("p4_6", "Water is not coming", "ನೀರು ಬರುತ್ತಿಲ್ಲ.", "Neeru barthilla", "No water", "Caretaker maintenance call.", "Apartment & Security Guard", 2, "Casual Kanglish", "Water illi", "Native-speaker verified", "pack_4"),
        PhraseEntity("p4_7", "Did the plumber come?", "ಪ್ಲಂಬರ್ ಬಂದ್ರಾ?", "Plumber bandhra?", "Plumber came?", "Maintenance check.", "Apartment & Security Guard", 2, "Polite Kanglish", null, "Native-speaker verified", "pack_4"),

        // Pack 5: Delivery & Courier
        PhraseEntity("p5_1", "Where are you now?", "ಎಲ್ಲಿದ್ದೀರಾ ಸರ್?", "Elli iddeera sir?", "Where are you?", "Delivery tracking call.", "Delivery & Courier", 2, "Polite Kanglish", "Where are you now?", "Native-speaker verified", "pack_5"),
        PhraseEntity("p5_2", "Come to 3rd floor", "3rd ಫ್ಲೋರ್‌ಗೆ ಬನ್ನಿ.", "3rd floor-ge banni", "3rd floor", "Direct floor guidance.", "Delivery & Courier", 2, "Polite Kanglish", null, "Native-speaker verified", "pack_5"),
        PhraseEntity("p5_3", "Leave at the door", "ಡೋರ್ ಹತ್ತಿರ ಇಡಿ.", "Door hatthira idi", "Leave at door", "Drop-off request.", "Delivery & Courier", 2, "Polite Kanglish", "Door front-nalli idi", "Native-speaker verified", "pack_5"),
        PhraseEntity("p5_4", "I did not get OTP", "OTP ಬಂದಿಲ್ಲ.", "OTP bandilla", "No OTP", "Bite-sized OTP issue phrase.", "Delivery & Courier", 2, "Casual Kanglish", "OTP resend maadi", "Native-speaker verified", "pack_5"),
        PhraseEntity("p5_5", "Call after 10 mins", "10 min ಆಮೇಲೆ ಕಾಲ್ ಮಾಡಿ.", "10 mins aamele call maadi", "Call in 10 mins", "Reschedule call.", "Delivery & Courier", 2, "Polite Kanglish", null, "Native-speaker verified", "pack_5"),

        // Pack 6: Housekeeper & Maid
        PhraseEntity("p6_1", "Clean room properly", "ರೂಮ್ ಕ್ಲೀನ್ ಮಾಡಿ.", "Room clean maadi", "Clean room", "Maid direction.", "Housekeeper & Maid", 3, "Polite Kanglish", "Sariyaagi clean maadi", "Native-speaker verified", "pack_6"),
        PhraseEntity("p6_2", "Wash dishes", "ಪಾತ್ರೆ ಕ್ಲೀನ್ ಮಾಡಿ.", "Paathre clean maadi", "Wash dishes", "Kitchen instruction.", "Housekeeper & Maid", 3, "Polite Kanglish", "Paathre tholeri", "Native-speaker verified", "pack_6"),
        PhraseEntity("p6_3", "Come tomorrow at 8 AM", "ನಾಳೆ 8 AM-ಗೆ ಬನ್ನಿ.", "Naale 8 AM-ge banni", "Come tomorrow 8 AM", "Maid timing instruction.", "Housekeeper & Maid", 3, "Polite Kanglish", null, "Native-speaker verified", "pack_6"),
        PhraseEntity("p6_4", "Use less spice", "ಖಾರ ಕಮ್ಮಿ ಹಾಕಿ.", "Kaara kammi haaki", "Less spice", "Essential cooking instruction.", "Housekeeper & Maid", 3, "Polite Kanglish", "Spice kammi maadi", "Native-speaker verified", "pack_6"),
        PhraseEntity("p6_5", "Use less salt", "ಉಪ್ಪು ಕಮ್ಮಿ ಹಾಕಿ.", "Uppu kammi haaki", "Less salt", "Salt instruction.", "Housekeeper & Maid", 3, "Polite Kanglish", null, "Native-speaker verified", "pack_6"),

        // Pack 7: Shopping & Grocery Store
        PhraseEntity("p7_1", "Do you have fresh milk?", "ಫ್ರೆಶ್ ಮಿಲ್ಕ್ ಇದೆಯಾ?", "Fresh milk idya?", "Fresh milk?", "Grocery query.", "Shopping & Grocery Store", 3, "Casual Kanglish", "Nandini milk idya?", "Native-speaker verified", "pack_7"),
        PhraseEntity("p7_2", "Give 1 kg onion", "1 kg ಈರುಳ್ಳಿ ಕೊಡಿ.", "1 kg eerulli kodi", "1 kg onion", "Veggies purchase.", "Shopping & Grocery Store", 3, "Polite Kanglish", null, "Native-speaker verified", "pack_7"),
        PhraseEntity("p7_3", "Give me a carry bag", "ಒಂದು ಕವರ್ ಕೊಡಿ.", "Cover / Bag kodi", "Give bag", "Carry bag request.", "Shopping & Grocery Store", 3, "Polite Kanglish", null, "Native-speaker verified", "pack_7"),
        PhraseEntity("p7_4", "Do you accept GPay?", "GPay ತಗೊಳ್ತೀರಾ?", "GPay thgolteera?", "Accept GPay?", "Payment query.", "Shopping & Grocery Store", 3, "Polite Kanglish", "PhonePe idya?", "Native-speaker verified", "pack_7"),

        // Pack 8: Food & Restaurants
        PhraseEntity("p8_1", "One masala dosa please", "ಒಂದು ಮಸಾಲೆ ದೋಸೆ ಕೊಡಿ.", "Ondu masala dosa kodi", "1 masala dosa", "Classic hotel order.", "Food & Restaurants", 3, "Polite Kanglish", null, "Native-speaker verified", "pack_8"),
        PhraseEntity("p8_2", "Give filter coffee", "ಫಿಲ್ಟರ್ ಕಾಫಿ ಕೊಡಿ.", "Filter coffee kodi", "Filter coffee", "Beverage order.", "Food & Restaurants", 3, "Polite Kanglish", "1 meter coffee kodi", "Native-speaker verified", "pack_8"),
        PhraseEntity("p8_3", "Bring drinking water", "ವಾಟರ್ ತನ್ನಿ ಸರ್.", "Water thanni sir", "Bring water", "Table request.", "Food & Restaurants", 3, "Polite Kanglish", "Kudiyova neeru thanni", "Native-speaker verified", "pack_8"),
        PhraseEntity("p8_4", "Please give the bill", "ಬಿಲ್ ಕೊಡಿ ಸರ್.", "Bill kodi sir", "Give bill", "Finishing meal.", "Food & Restaurants", 3, "Polite Kanglish", "Bill thanni", "Native-speaker verified", "pack_8"),
        PhraseEntity("p8_5", "Make it parcel / takeaway", "ಪಾರ್ಸಲ್ ಮಾಡಿ.", "Parcel maadi", "Make takeaway", "Takeaway request.", "Food & Restaurants", 3, "Polite Kanglish", "Takeaway maadi", "Native-speaker verified", "pack_8"),
        PhraseEntity("p8_6", "Without sugar coffee", "ಕಾಫಿಗೆ ಸಕ್ಕರೆ ಹಾಕ್ಬೇಡಿ.", "Without sugar coffee", "No sugar coffee", "Dietary order.", "Food & Restaurants", 3, "Polite Kanglish", null, "Native-speaker verified", "pack_8"),

        // Pack 9: Office & Work
        PhraseEntity("p9_1", "I am in a meeting", "ಮೀಟಿಂಗ್‌ನಲ್ಲಿದ್ದೇನೆ.", "Meeting-nalli iddini", "In meeting", "Office chat status.", "Office & Work", 4, "Casual Kanglish", "Meeting-nalli iddene", "Native-speaker verified", "pack_9"),
        PhraseEntity("p9_2", "I will call you later", "ಆಮೇಲೆ ಕಾಲ್ ಮಾಡುತ್ತೇನೆ.", "Aamele call maadthene", "Call later", "Deferring call.", "Office & Work", 4, "Polite Kanglish", "Aamele call maadthini", "Native-speaker verified", "pack_9"),
        PhraseEntity("p9_3", "Please send the file", "ಫೈಲ್ ಕಳುಹಿಸಿ.", "File send maadi / kaluhisi", "Send file", "Slack/email request.", "Office & Work", 4, "Polite Kanglish", "File kaluhisi", "Native-speaker verified", "pack_9"),
        PhraseEntity("p9_4", "I completed the work", "ವರ್ಕ್ ಕಂಪ್ಲೀಟ್ ಮಾಡಿದ್ದೇನೆ.", "Work complete maadiddeene", "Work completed", "Status update.", "Office & Work", 4, "Casual Kanglish", null, "Native-speaker verified", "pack_9"),

        // Pack 10: Directions & Asking Help
        PhraseEntity("p10_1", "Where is the bus stop?", "ಬಸ್ ಸ್ಟಾಪ್ ಎಲ್ಲಿದೆ?", "Bus stop elli ide?", "Where's bus stop?", "Commute query.", "Directions & Asking Help", 4, "Neutral", null, "Native-speaker verified", "pack_10"),
        PhraseEntity("p10_2", "Where is the Metro station?", "ಮೆಟ್ರೋ ಸ್ಟೇಷನ್ ಎಲ್ಲಿದೆ?", "Metro station elli ide?", "Where's Metro?", "Namma Metro query.", "Directions & Asking Help", 4, "Neutral", null, "Native-speaker verified", "pack_10"),
        PhraseEntity("p10_3", "Where is nearby ATM?", "ಹತ್ತಿರ ATM ಇದೆಯಾ?", "Hatthira ATM idya?", "ATM nearby?", "Cash query.", "Directions & Asking Help", 4, "Neutral", null, "Native-speaker verified", "pack_10"),
        PhraseEntity("p10_4", "Can you please help me?", "ಸ್ವಲ್ಪ ಹೆಲ್ಪ್ ಮಾಡುತ್ತೀರಾ?", "Swalpa help maadteera?", "Help me?", "Polite assistance query.", "Directions & Asking Help", 4, "Polite Kanglish", "Swalpa sahaya maadi", "Native-speaker verified", "pack_10"),

        // Pack 11: Health & Pharmacy
        PhraseEntity("p11_1", "I have a headache", "ತಲೆ ನೋವು ಇದೆ.", "Thale novu ide", "Headache", "Symptom phrase.", "Health & Pharmacy", 4, "Neutral", null, "Native-speaker verified", "pack_11"),
        PhraseEntity("p11_2", "Give medicine for cold", "ಕೋಲ್ಡ್‌ಗೆ ಮೆಡಿಸಿನ್ ಕೊಡಿ.", "Cold-ge medicine kodi", "Medicine for cold", "Pharmacy purchase.", "Health & Pharmacy", 4, "Polite Kanglish", null, "Native-speaker verified", "pack_11"),
        PhraseEntity("p11_3", "Take after food", "ಊಟದ ನಂತರ ತಗೊಳ್ಳಿ.", "Oota aamele thagoli", "Take after food", "Dose direction.", "Health & Pharmacy", 4, "Polite Kanglish", null, "Native-speaker verified", "pack_11"),

        // Pack 12: Everyday Small Talk
        PhraseEntity("p12_1", "Did you have food?", "ಊಟ ಆಯ್ತಾ?", "Oota aaytha?", "Had food?", "#1 caring daily chat in Karnataka.", "Everyday Small Talk", 5, "Polite Kanglish", "Tiffin aaytha?", "Native-speaker verified", "pack_12"),
        PhraseEntity("p12_2", "Weather is nice today", "ಇವತ್ತು ಹವಾಮಾನ ಸೂಪರ್ ಇದೆ.", "Ivattu climate super ide", "Nice weather", "Bengaluru weather chat.", "Everyday Small Talk", 5, "Casual Kanglish", "Ivattu weather chennagide", "Native-speaker verified", "pack_12"),
        PhraseEntity("p12_3", "It is raining heavily", "ತುಂಬಾ ಮಳೆ ಬರುತ್ತಿದೆ.", "Thumba male barthide", "Heavy rain", "Weather observation.", "Everyday Small Talk", 5, "Neutral", null, "Native-speaker verified", "pack_12"),
        PhraseEntity("p12_4", "No problem / It's okay", "ಏನೂ ತೊಂದರೆ ಇಲ್ಲ.", "Enu thondare illa", "No problem", "Reassuring phrase.", "Everyday Small Talk", 5, "Neutral", "Paravagilla", "Native-speaker verified", "pack_12"),

        // Pack 13: Phone Conversations
        PhraseEntity("p13_1", "Voice is not clear", "ವಾಯ್ಸ್ ಕ್ಲಿಯರ್ ಇಲ್ಲ.", "Voice clear illa / kelisthilla", "Voice unclear", "Phone call issue.", "Phone Conversations", 5, "Casual Kanglish", "Voice sariyaagi kelisthilla", "Native-speaker verified", "pack_13"),
        PhraseEntity("p13_2", "Can you hear me?", "ಕೇಳಿಸುತ್ತಿದೆಯಾ?", "Kelisthidya?", "Can you hear?", "Audibility check.", "Phone Conversations", 5, "Neutral", null, "Native-speaker verified", "pack_13"),
        PhraseEntity("p13_3", "Send message on WhatsApp", "WhatsApp-ನಲ್ಲಿ ಮೆಸೇಜ್ ಮಾಡಿ.", "WhatsApp-nalli message maadi", "Message on WhatsApp", "Code-mixed instruction.", "Phone Conversations", 5, "Polite Kanglish", "WhatsApp maadi", "Native-speaker verified", "pack_13"),

        // Pack 14: Emergencies & Urgent Needs
        PhraseEntity("p14_1", "Please help!", "ಸ್ವಲ್ಪ ಸಹಾಯ ಮಾಡಿ!", "Swalpa help maadi!", "Help me!", "Emergency call.", "Emergencies & Urgent Needs", 5, "Urgent Kanglish", "Help maadi!", "Native-speaker verified", "pack_14"),
        PhraseEntity("p14_2", "Call ambulance", "ಆಂಬ್ಯುಲೆನ್ಸ್ ಕಾಲ್ ಮಾಡಿ.", "Ambulance-na call maadi", "Call ambulance", "Medical emergency.", "Emergencies & Urgent Needs", 5, "Urgent Kanglish", null, "Native-speaker verified", "pack_14"),
        PhraseEntity("p14_3", "I lost my phone", "ಫೋನ್ ಕಳೆದುಹೋಗಿದೆ.", "Phone tholagide / lost aaythu", "Phone lost", "Loss report.", "Emergencies & Urgent Needs", 5, "Casual Kanglish", null, "Native-speaker verified", "pack_14"),

        // Pack 15: Bengaluru City Survival Slang
        PhraseEntity("p15_1", "I don't know Kannada", "ಕನ್ನಡ ಗೊತ್ತಿಲ್ಲ.", "Kannada gothilla", "Don't know Kannada", "#1 survival phrase for Bengaluru newcomers.", "Bengaluru Specific Expressions", 5, "Bare Minimum Survival", "Kannada swalpa gothu", "Native-speaker verified", "pack_15"),
        PhraseEntity("p15_2", "I know a little bit", "ಸ್ವಲ್ಪ ಸ್ವಲ್ಪ ಗೊತ್ತು.", "Swalpa swalpa gothu", "Know a little bit", "Builds instant warmth with local residents.", "Bengaluru Specific Expressions", 5, "Friendly Kanglish", null, "Native-speaker verified", "pack_15"),
        PhraseEntity("p15_3", "Please adjust a little", "ಅಡ್ಜಸ್ಟ್ ಮಾಡಿ ಸರ್.", "Adjust maadi sir", "Please adjust", "Bengaluru's magic word in autos and queues.", "Bengaluru Specific Expressions", 5, "Polite Kanglish", "Swalpa adjust maadi", "Native-speaker verified", "pack_15"),
        PhraseEntity("p15_4", "No scene, chill!", "ಏನೂ ಇಲ್ಲ, ಚಿಲ್ ಮಾಡಿ!", "Enu illa, chill maadi!", "Chill out!", "Bengaluru techie slang.", "Bengaluru Specific Expressions", 5, "Casual Slang", "Chill guru", "Native-speaker verified", "pack_15"),

        // Pack 16: Time, Dates & Schedules
        PhraseEntity("p16_1", "What is the time now?", "ಈಗ ಟೈಮ್ ಎಷ್ಟು?", "Eaga time eshtu?", "What's time?", "Asking time.", "Time, Dates & Schedules", 5, "Casual Kanglish", "Time eshtu?", "Native-speaker verified", "pack_16"),
        PhraseEntity("p16_2", "Come tomorrow", "ನಾಳೆ ಬನ್ನಿ.", "Naale banni", "Come tomorrow", "Scheduling phrase.", "Time, Dates & Schedules", 5, "Polite Kanglish", null, "Native-speaker verified", "pack_16"),
        PhraseEntity("p16_3", "Wait for 10 mins", "10 min ಕಾಯಿರಿ.", "10 min wait maadi / kaayiri", "Wait 10 mins", "Duration phrase.", "Time, Dates & Schedules", 5, "Polite Kanglish", "10 mins wait maadi", "Native-speaker verified", "pack_16")
    )

    val packs: List<Map<String, String>> = listOf(
        mapOf("id" to "pack_1", "title" to "Greetings & Introductions", "situation" to "Bare minimum polite greetings & introductions", "level" to "1"),
        mapOf("id" to "pack_2", "title" to "Numbers & Money", "situation" to "Bargaining, GPay, change & price queries", "level" to "1"),
        mapOf("id" to "pack_3", "title" to "Transport & Auto Drivers", "situation" to "Hailing autos, asking meter, left/right directions", "level" to "2"),
        mapOf("id" to "pack_4", "title" to "Apartment & Security Guard", "situation" to "Guard calls, 1 min wait, caretaker, gate drop", "level" to "2"),
        mapOf("id" to "pack_5", "title" to "Delivery & Courier", "situation" to "Swiggy/Zomato calls, 3rd floor, OTP, door drop", "level" to "2"),
        mapOf("id" to "pack_6", "title" to "Housekeeper & Maid", "situation" to "Room cleaning, washing dishes, less spice, 8 AM timing", "level" to "3"),
        mapOf("id" to "pack_7", "title" to "Shopping & Grocery Store", "situation" to "Fresh milk, 1 kg onions, carry bag, GPay", "level" to "3"),
        mapOf("id" to "pack_8", "title" to "Food & Restaurants", "situation" to "Dosa, Filter coffee, less spice, water, bill, parcel", "level" to "3"),
        mapOf("id" to "pack_9", "title" to "Office & Work", "situation" to "In meeting, call later, file send, work complete", "level" to "4"),
        mapOf("id" to "pack_10", "title" to "Directions & Asking Help", "situation" to "Bus stop, Metro, finding places, lost way", "level" to "4"),
        mapOf("id" to "pack_11", "title" to "Health & Pharmacy", "situation" to "Headache, fever, cold medicine, doctor", "level" to "4"),
        mapOf("id" to "pack_12", "title" to "Everyday Small Talk", "situation" to "Oota aaytha?, Bengaluru climate, male barthide", "level" to "5"),
        mapOf("id" to "pack_13", "title" to "Phone Conversations", "situation" to "Voice not clear, repeat once, WhatsApp message", "level" to "5"),
        mapOf("id" to "pack_14", "title" to "Emergencies & Urgent Needs", "situation" to "Help maadi, ambulance, police station", "level" to "5"),
        mapOf("id" to "pack_15", "title" to "Bengaluru City Survival Slang", "situation" to "Kannada gothilla, Swalpa gothu, Adjust maadi", "level" to "5"),
        mapOf("id" to "pack_16", "title" to "Time, Dates & Schedules", "situation" to "Time eshtu?, Naale banni, 10 min wait", "level" to "5")
    )
}
