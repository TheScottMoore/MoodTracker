package com.example.moodtracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.moodtracker.data.MoodDatabase
import com.example.moodtracker.data.MoodEntry
import com.example.moodtracker.data.MoodRepository
import kotlinx.coroutines.launch

/**
 * ViewModel survives screen rotations and holds UI-related data.
 * It talks to the Repository to get/save data.
 *
 * AndroidViewModel gives us access to the Application context (needed to open the DB).
 */
class MoodViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MoodRepository
    val allEntries: LiveData<List<MoodEntry>>

    init {
        val dao = MoodDatabase.getDatabase(application).moodDao()
        repository = MoodRepository(dao)
        allEntries = repository.allEntries
    }

    /**
     * Insert a mood entry. viewModelScope.launch runs this in a background thread
     * so we don't block the UI.
     */
    fun insert(entry: MoodEntry) = viewModelScope.launch {
        repository.insert(entry)
    }

    fun delete(entry: MoodEntry) = viewModelScope.launch {
        repository.delete(entry)
    }
}
