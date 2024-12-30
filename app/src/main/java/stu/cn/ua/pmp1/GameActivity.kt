package stu.cn.ua.pmp1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import model.Question
import model.QuestionGenerator

class GameActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var winnings = 0
    private lateinit var questions: List<Question>
    private var hintUsed = false
    private var totalQuestions = 0
    private var hiddenAnswerIndexes: List<Int> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)

        loadGameSettings()
        setupGame()

        findViewById<Button>(R.id.btn_take_money).setOnClickListener {
            showResultDialog("Вітаємо, ви виграли $winnings$")
        }

        val hintButton = findViewById<Button>(R.id.btn_hint_50_50)
        hintButton.setOnClickListener {
            if (!hintUsed) {
                use50x50Hint()
                hintUsed = true
                hintButton.isEnabled = false
            }
        }
    }

    private fun loadGameSettings() {
        val sharedPreferences = getSharedPreferences("GameSettings", MODE_PRIVATE)
        val questionCount = sharedPreferences.getInt("QuestionCount", 5)
        val isHintEnabled = sharedPreferences.getBoolean("Hint50/50", false)

        questions = QuestionGenerator.generateQuestions(questionCount)
        totalQuestions = questionCount

        if (!isHintEnabled) {
            findViewById<Button>(R.id.btn_hint_50_50).visibility = View.GONE
        }
    }

    private fun setupGame() {
        currentQuestionIndex = 0
        hintUsed = false
        winnings = 0

        displayQuestion()
        setupAnswerButtons()
    }

    private fun displayQuestion() {
        val currentQuestion = questions[currentQuestionIndex]
        findViewById<TextView>(R.id.tv_question).text = currentQuestion.text
        findViewById<Button>(R.id.btn_answer1).text = currentQuestion.answers[0]
        findViewById<Button>(R.id.btn_answer2).text = currentQuestion.answers[1]
        findViewById<Button>(R.id.btn_answer3).text = currentQuestion.answers[2]
        findViewById<Button>(R.id.btn_answer4).text = currentQuestion.answers[3]
        updateWinningsText()
    }

    private fun setupAnswerButtons() {
        findViewById<Button>(R.id.btn_answer1).setOnClickListener {
            checkAnswer(0)
        }
        findViewById<Button>(R.id.btn_answer2).setOnClickListener {
            checkAnswer(1)
        }
        findViewById<Button>(R.id.btn_answer3).setOnClickListener {
            checkAnswer(2)
        }
        findViewById<Button>(R.id.btn_answer4).setOnClickListener {
            checkAnswer(3)
        }
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        val currentQuestion = questions[currentQuestionIndex]
        val correctAnswer = currentQuestion.correctAnswer
        val selectedAnswer = currentQuestion.answers[selectedAnswerIndex]

        if (selectedAnswer == correctAnswer) {
            winnings += calculateWinnings()
            showCorrectAnswerDialog()
        } else {
            showIncorrectAnswerDialog()
        }
    }

    private fun use50x50Hint() {
        val currentQuestion = questions[currentQuestionIndex]
        val correctAnswerIndex = currentQuestion.answers.indexOf(currentQuestion.correctAnswer)
        val wrongAnswerIndexes = (0..3).filter { it != correctAnswerIndex }.shuffled().take(2)
        hideWrongAnswers(wrongAnswerIndexes)

        hiddenAnswerIndexes = wrongAnswerIndexes
        findViewById<Button>(R.id.btn_hint_50_50).isEnabled = false
    }

    private fun hideWrongAnswers(wrongAnswerIndexes: List<Int>) {
        findViewById<Button>(R.id.btn_answer1).apply { visibility = if (wrongAnswerIndexes.contains(0)) View.GONE else View.VISIBLE }
        findViewById<Button>(R.id.btn_answer2).apply { visibility = if (wrongAnswerIndexes.contains(1)) View.GONE else View.VISIBLE }
        findViewById<Button>(R.id.btn_answer3).apply { visibility = if (wrongAnswerIndexes.contains(2)) View.GONE else View.VISIBLE }
        findViewById<Button>(R.id.btn_answer4).apply { visibility = if (wrongAnswerIndexes.contains(3)) View.GONE else View.VISIBLE }
    }

    private fun restoreAnswerButtons() {
        findViewById<Button>(R.id.btn_answer1).visibility = View.VISIBLE
        findViewById<Button>(R.id.btn_answer2).visibility = View.VISIBLE
        findViewById<Button>(R.id.btn_answer3).visibility = View.VISIBLE
        findViewById<Button>(R.id.btn_answer4).visibility = View.VISIBLE
    }

    private fun calculateWinnings(): Int {
        val totalPrize = 1000000
        val totalQuestions = questions.size
        val sumOfProgression = totalQuestions * (totalQuestions + 1) / 2
        val basePrize = totalPrize / sumOfProgression
        var winnings = basePrize * (currentQuestionIndex + 1)
        if (currentQuestionIndex == totalQuestions - 1) {
            val remainder = totalPrize - (basePrize * sumOfProgression)
            winnings += remainder
        }
        return winnings
    }

    private fun showCorrectAnswerDialog() {
        AlertDialog.Builder(this)
            .setMessage("Правильна відповідь! Ваш виграш: $winnings$")
            .setPositiveButton("Ок") { dialog, _ ->
                restoreAnswerButtons()
                currentQuestionIndex++
                if (currentQuestionIndex < questions.size) {
                    displayQuestion()
                } else {
                    showResultDialog("Ви виграли $winnings$")
                }
                dialog.dismiss()
            }
            .show()
    }

    private fun showIncorrectAnswerDialog() {
        AlertDialog.Builder(this)
            .setMessage("Неправильна відповідь! Ви програли.")
            .setPositiveButton("Ок") { _, _ ->
                restoreAnswerButtons()
                finish()
            }
            .show()
    }

    private fun showResultDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                finish()
            }
            .show()
    }

    private fun updateWinningsText() {
        findViewById<TextView>(R.id.tv_winnings).text = "Виграна сума: $winnings$"
    }
}
