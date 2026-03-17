package com.example.moodtracker.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodtracker.databinding.ActivityHistoryBinding
import com.example.moodtracker.viewmodel.MoodViewModel
import java.time.LocalDateTime

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: MoodViewModel by viewModels()
    private lateinit var adapter: MoodEntryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Mood History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = MoodEntryAdapter { entry -> viewModel.delete(entry) }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.allEntries.observe(this) { entries ->
            adapter.submitList(entries)

            // Filter to rolling 60-day window
            val cutoff = LocalDateTime.now().minusDays(60)
            val recent = entries.filter {
                LocalDateTime.parse(it.timestamp).isAfter(cutoff)
            }
            updateTally(recent)
        }
    }

    /**
     * Counts mood types and energy levels from the last 60 days
     * and updates the tally card at the top of the screen.
     */
    private fun updateTally(entries: List<com.example.moodtracker.data.MoodEntry>) {
        val total = entries.size
        binding.tvTallyPeriod.text = "Last 60 days — $total entries"

        // Mood counts
        binding.tvTallyAngry.text   = "😡 Angry:   ${entries.count { it.moodScore == 1 }}"
        binding.tvTallySad.text     = "😢 Sad:     ${entries.count { it.moodScore == 2 }}"
        binding.tvTallyMeh.text     = "😐 Meh:     ${entries.count { it.moodScore == 3 }}"
        binding.tvTallyContent.text = "😊 Content: ${entries.count { it.moodScore == 4 }}"
        binding.tvTallyHappy.text   = "😄 Happy:   ${entries.count { it.moodScore == 5 }}"

        // Average energy
        val avgEnergy = if (entries.isEmpty()) 0f
                        else entries.map { it.energyLevel }.average().toFloat()
        binding.tvTallyEnergy.text = "⚡ Avg Energy: ${"%.1f".format(avgEnergy)} / 5"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
