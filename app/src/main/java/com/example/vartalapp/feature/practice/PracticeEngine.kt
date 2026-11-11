package com.example.vartalapp.feature.practice

import com.example.vartalapp.core.model.PracticeQuestion
import com.example.vartalapp.core.model.QuestionType
import com.example.vartalapp.core.model.Phrase

class PracticeEngine {

    fun generateQuiz(
        allPhrases: List<Phrase>,
        dueReviewPhraseIds: Set<String> = emptySet(),
        count: Int = 5
    ): List<PracticeQuestion> {
        if (allPhrases.isEmpty()) return emptyList()

        val shuffledPhrases = allPhrases.shuffled()
        val questions = mutableListOf<PracticeQuestion>()

        shuffledPhrases.take(count).forEachIndexed { index, phrase ->
            val optionsPool = (allPhrases - phrase).shuffled().take(3)
            val type = when {
                dueReviewPhraseIds.contains(phrase.id) -> QuestionType.REVISION
                index % 5 == 0 -> QuestionType.MULTIPLE_CHOICE
                index % 5 == 1 -> QuestionType.LISTENING
                index % 5 == 2 -> QuestionType.RECALL
                index % 5 == 3 -> QuestionType.TRANSLITERATION_MATCH
                else -> QuestionType.PHRASE_CONSTRUCTION
            }

            val question = when (type) {
                QuestionType.MULTIPLE_CHOICE, QuestionType.LISTENING, QuestionType.REVISION -> {
                    val correct = phrase.english
                    val options = (optionsPool.map { it.english } + correct).shuffled()
                    PracticeQuestion(
                        id = "q_${phrase.id}_$index",
                        type = type,
                        prompt = if (type == QuestionType.LISTENING) "Listen to the Kannada pronunciation and choose the correct English meaning:" else "Select the correct English meaning for:\n${phrase.kannada}",
                        phrase = phrase,
                        options = options,
                        correctAnswer = correct,
                        explanation = "${phrase.kannada} (${phrase.transliteration}) means: ${phrase.meaning}"
                    )
                }

                QuestionType.RECALL -> {
                    val correct = phrase.kannada
                    val options = (optionsPool.map { it.kannada } + correct).shuffled()
                    PracticeQuestion(
                        id = "q_${phrase.id}_$index",
                        type = type,
                        prompt = "How do you say in Kannada:\n\"${phrase.english}\"?",
                        phrase = phrase,
                        options = options,
                        correctAnswer = correct,
                        explanation = "\"${phrase.english}\" in Kannada is ${phrase.kannada} (${phrase.transliteration})."
                    )
                }

                QuestionType.TRANSLITERATION_MATCH -> {
                    val correct = phrase.transliteration
                    val options = (optionsPool.map { it.transliteration } + correct).shuffled()
                    PracticeQuestion(
                        id = "q_${phrase.id}_$index",
                        type = type,
                        prompt = "Which transliteration matches this Kannada script?\n${phrase.kannada}",
                        phrase = phrase,
                        options = options,
                        correctAnswer = correct,
                        explanation = "${phrase.kannada} is pronounced as: ${phrase.transliteration}"
                    )
                }

                QuestionType.PHRASE_CONSTRUCTION -> {
                    val correct = phrase.english
                    val words = phrase.english.split(" ").shuffled()
                    val options = (optionsPool.map { it.english } + correct).shuffled()
                    PracticeQuestion(
                        id = "q_${phrase.id}_$index",
                        type = type,
                        prompt = "Select the full English meaning for Kannada expression:\n${phrase.kannada} (${phrase.transliteration})",
                        phrase = phrase,
                        options = options,
                        correctAnswer = correct,
                        explanation = "Full expression: ${phrase.kannada} -> ${phrase.english}"
                    )
                }
            }
            questions.add(question)
        }

        return questions
    }
}
