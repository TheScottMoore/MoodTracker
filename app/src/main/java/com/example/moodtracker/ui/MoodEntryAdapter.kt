package com.example.moodtracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moodtracker.data.MoodEntry
import com.example.moodtracker.databinding.ItemMoodEntryBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * RecyclerView Adapter displays a list of MoodEntry items.
 *
 * ListAdapter is a smarter version of RecyclerView.Adapter that uses DiffUtil
 * to only redraw items that actually changed (better performance).
 *
 * @param onDelete called when the user long-presses an item to delete it
 */
class MoodEntryAdapter(
    private val onDelete: (MoodEntry) -> Unit
) : ListAdapter<MoodEntry, MoodEntryAdapter.MoodViewHolder>(DiffCallback) {

    // DiffCallback tells RecyclerView how to compare items
    companion object DiffCallback : DiffUtil.ItemCallback<MoodEntry>() {
        override fun areItemsTheSame(old: MoodEntry, new: MoodEntry) = old.id == new.id
        override fun areContentsTheSame(old: MoodEntry, new: MoodEntry) = old == new
    }

    // ViewHolder holds references to the views for one list item
    inner class MoodViewHolder(private val binding: ItemMoodEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: MoodEntry) {
            // Display mood as emoji + label
            binding.tvMood.text = "${moodEmoji(entry.moodScore)} ${moodLabel(entry.moodScore)}"

            // Energy level
            binding.tvEnergy.text = "⚡ Energy: ${entry.energyLevel}/5"

            // Sleep hours
            binding.tvSleep.text = "💤 Sleep: ${entry.sleepHours}h"

            // Show note only if there is one
            if (entry.note.isNotEmpty()) {
                binding.tvNote.text = "📝 ${entry.note}"
                binding.tvNote.visibility = android.view.View.VISIBLE
            } else {
                binding.tvNote.visibility = android.view.View.GONE
            }

            // Format the timestamp nicely
            val dateTime = LocalDateTime.parse(entry.timestamp)
            val formatter = DateTimeFormatter.ofPattern("EEE, MMM d 'at' h:mm a")
            binding.tvTimestamp.text = dateTime.format(formatter)

            // Long press to delete
            binding.root.setOnLongClickListener {
                onDelete(entry)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val binding = ItemMoodEntryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun moodEmoji(score: Int): String = when (score) {
        1 -> "😡"; 2 -> "😢"; 3 -> "😐"; 4 -> "😊"; 5 -> "😄"; else -> "❓"
    }

    private fun moodLabel(score: Int): String = when (score) {
        1 -> "Angry"; 2 -> "Sad"; 3 -> "Meh"; 4 -> "Content"; 5 -> "Happy"; else -> "Unknown"
    }
}
