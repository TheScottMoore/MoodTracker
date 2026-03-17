package com.example.moodtracker.data

import androidx.lifecycle.LiveData

/**
 * Repository acts as a clean API between the ViewModel and the database.
 * For a beginner: think of it as the "middleman" that handles data operations.
 */
class MoodRepository(private val dao: MoodDao) {

    // Expose all entries to the ViewModel as LiveData
    val allEntries: LiveData<List<MoodEntry>> = dao.getAllEntries()

    // suspend = this must be called from a coroutine (background thread)
    suspend fun insert(entry: MoodEntry) {
        dao.insert(entry)
    }

    suspend fun delete(entry: MoodEntry) {
        dao.delete(entry)
    }
}
