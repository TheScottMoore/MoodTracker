package com.example.moodtracker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.moodtracker.data.MoodEntry
import com.example.moodtracker.databinding.ActivityMainBinding
import com.example.moodtracker.viewmodel.MoodViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MoodViewModel by viewModels()

    // Tracks which mood button is currently selected (1=Angry, 2=Sad, 3=Meh, 4=Content, 5=Happy)
    private var selectedMood: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMoodButtons()

        binding.btnSave.setOnClickListener { saveMoodEntry() }
        binding.btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    /**
     * Sets up the 5 emoji mood buttons so tapping one highlights it
     * and deselects the others.
     */
    private fun setupMoodButtons() {
        val moodButtons = listOf(
            binding.btnMood1, // 😡 Angry
            binding.btnMood2, // 😢 Sad
            binding.btnMood3, // 😐 Meh
            binding.btnMood4, // 😊 Content
            binding.btnMood5  // 😄 Happy
        )

        moodButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                selectedMood = index + 1
                // Highlight selected, dim the rest
                moodButtons.forEach { it.alpha = 0.35f }
                button.alpha = 1.0f
            }
        }
    }

    private fun saveMoodEntry() {
        if (selectedMood == 0) {
            Toast.makeText(this, "Please select a mood!", Toast.LENGTH_SHORT).show()
            return
        }

        val energyLevel = binding.ratingBarEnergy.rating.toInt()
        val sleepHours = binding.etSleepHours.text.toString().toFloatOrNull() ?: 0f
        val note = binding.etNote.text.toString().trim()

        val entry = MoodEntry(
            moodScore = selectedMood,
            energyLevel = energyLevel,
            sleepHours = sleepHours,
            note = note
        )
        viewModel.insert(entry)

        Toast.makeText(this, "Mood saved! ${moodEmoji(selectedMood)}", Toast.LENGTH_SHORT).show()
        resetForm()
    }

    private fun resetForm() {
        // Reset mood buttons
        selectedMood = 0
        listOf(binding.btnMood1, binding.btnMood2, binding.btnMood3,
               binding.btnMood4, binding.btnMood5).forEach { it.alpha = 1.0f }
        binding.ratingBarEnergy.rating = 0f
        binding.etSleepHours.setText("")
        binding.etNote.setText("")
    }

    fun moodEmoji(score: Int): String = when (score) {
        1 -> "😡"; 2 -> "😢"; 3 -> "😐"; 4 -> "😊"; 5 -> "😄"; else -> ""
    }

    fun moodLabel(score: Int): String = when (score) {
        1 -> "Angry"; 2 -> "Sad"; 3 -> "Meh"; 4 -> "Content"; 5 -> "Happy"; else -> ""
    }
}
