package com.example.moodtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * Represents one mood log entry saved to the database.
 * @Entity tells Room to create a table called "mood_entries".
 */
@Entity(tableName = "mood_entries")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Mood score: 1 (terrible) to 5 (amazing)
    val moodScore: Int,

    // Optional journal note from the user
    val note: String,

    // Sleep hours (e.g., 7.5)
    val sleepHours: Float,

    // Energy level: 1 (exhausted) to 5 (energised)
    val energyLevel: Int,

    // When this entry was created, stored as a string (ISO-8601)
    val timestamp: String = LocalDateTime.now().toString()
)
