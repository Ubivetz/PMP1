package stu.cn.ua.pmp1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        val sharedPreferences = getSharedPreferences("GameSettings", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val questionCountSpinner = findViewById<Spinner>(R.id.spinner_question_count)
        val spinnerItems = listOf("5", "10", "15", "20")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        questionCountSpinner.adapter = adapter

        val savedQuestionCount = sharedPreferences.getInt("QuestionCount", 5)
        val defaultPosition = spinnerItems.indexOf(savedQuestionCount.toString())

        if (defaultPosition != -1) {
            questionCountSpinner.setSelection(defaultPosition)
        }

        val hintCheckBox = findViewById<CheckBox>(R.id.checkbox_hint_50_50)
        hintCheckBox.isChecked = sharedPreferences.getBoolean("Hint50/50", false)
        findViewById<Button>(R.id.btn_save_settings).setOnClickListener {
            val questionCount = questionCountSpinner.selectedItem?.toString()?.toIntOrNull()
            val isHintEnabled = hintCheckBox.isChecked

            if (questionCount != null) {
                editor.putInt("QuestionCount", questionCount)
                editor.putBoolean("Hint50/50", isHintEnabled)
                editor.apply()

                Toast.makeText(this, "Налаштування збережено: Запитань - $questionCount, Підказка - $isHintEnabled", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Будь ласка, виберіть кількість запитань", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
