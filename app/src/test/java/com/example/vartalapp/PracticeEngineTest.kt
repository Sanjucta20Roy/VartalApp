package com.example.vartalapp

import com.example.vartalapp.core.model.Phrase
import com.example.vartalapp.feature.practice.PracticeEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PracticeEngineTest {

    private val practiceEngine = PracticeEngine()

    private val samplePhrases = listOf(
        Phrase("1", "Hello", "ನಮಸ್ಕಾರ", "Namaskara", "Hello", "Greeting", "Greetings", 1, "Polite"),
        Phrase("2", "Yes", "ಹೌದು", "Haudu", "Yes", "Affirmative", "Greetings", 1, "Neutral"),
        Phrase("3", "No", "ಇಲ್ಲ", "Illa", "No", "Negative", "Greetings", 1, "Neutral"),
        Phrase("4", "Thank you", "ಧನ್ಯವಾದಗಳು", "Dhanyavaadagalu", "Thanks", "Polite", "Greetings", 1, "Polite"),
        Phrase("5", "How much?", "ಎಷ್ಟು?", "Eshtu?", "Price", "Cost", "Money", 1, "Neutral")
    )

    @Test
    fun generateQuiz_returnsRequestedCount() {
        val quiz = practiceEngine.generateQuiz(samplePhrases, count = 5)
        assertEquals(5, quiz.size)
    }

    @Test
    fun generateQuiz_questionsHaveOptionsAndCorrectAnswer() {
        val quiz = practiceEngine.generateQuiz(samplePhrases, count = 3)
        for (q in quiz) {
            assertTrue(q.options.isNotEmpty())
            assertTrue(q.options.contains(q.correctAnswer))
            assertNotNull(q.prompt)
        }
    }
}
