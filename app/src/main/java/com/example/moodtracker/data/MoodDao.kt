package com.example.moodtracker.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * DAO = Data Access Object.
 * This is where we define the SQL queries for our mood entries.
 * Room auto-generates the implementation for us.
 */
@Dao
interface MoodDao {

    // Insert a new entry. OnConflict.REPLACE updates it if the same id exists.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: MoodEntry)

    // Get all entries, newest first. LiveData means the UI auto-updates when data changes.
    @Query("SELECT * FROM mood_entries ORDER BY timestamp DESC")
    fun getAllEntries(): LiveData<List<MoodEntry>>

    // Delete a specific entry
    @Delete
    suspend fun delete(entry: MoodEntry)
}
