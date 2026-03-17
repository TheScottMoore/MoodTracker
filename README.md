# 📱 Mood Tracker — Android App

A simple, clean Android app for tracking your daily mood, energy level, sleep, and journal notes. Built with Kotlin following modern Android best practices.

---

## ✨ Features

- 😄 **Mood Rating** — rate your mood 1–5 with stars
- ⚡ **Energy Level** — log your energy on a 1–5 scale
- 💤 **Sleep Hours** — enter how many hours you slept
- 📝 **Journal Note** — optional free-text note
- 📋 **History Screen** — scroll through all past entries (newest first)
- 🗑️ **Delete Entries** — long-press any entry to delete it
- 💾 **Offline Storage** — all data is saved locally on your device using Room (SQLite)

---

## 🛠️ Tech Stack

| Layer       | Technology                          |
|-------------|-------------------------------------|
| Language    | Kotlin                              |
| UI          | XML Layouts + Material Components   |
| Database    | Room (SQLite wrapper)               |
| Architecture| MVVM (Model-View-ViewModel)         |
| Reactivity  | LiveData + Coroutines               |

---

## 🚀 Getting Started (Step-by-Step for Beginners)

### Step 1: Install Android Studio
1. Download **Android Studio** from https://developer.android.com/studio
2. Install it with all default options (include the Android SDK)

### Step 2: Open the Project
1. Launch Android Studio
2. Click **"Open"** and navigate to the `MoodTracker` folder
3. Wait for Gradle to sync (this downloads all dependencies — can take a few minutes the first time)

### Step 3: Run the App
**Option A — On a real phone:**
1. On your phone, go to **Settings → About Phone → tap "Build Number" 7 times** to enable Developer Options
2. Go to **Settings → Developer Options → Enable USB Debugging**
3. Connect your phone via USB
4. In Android Studio, select your phone from the device dropdown and click ▶ Run

**Option B — On an emulator:**
1. In Android Studio, click **Device Manager** (right sidebar)
2. Click **"Create Virtual Device"**, pick a phone (e.g. Pixel 6), and download a system image
3. Start the emulator, then click ▶ Run

---

## 📁 Project Structure

```
app/src/main/
├── java/com/example/moodtracker/
│   ├── data/
│   │   ├── MoodEntry.kt        ← The data model (what a mood log contains)
│   │   ├── MoodDao.kt          ← Database queries (insert, get all, delete)
│   │   ├── MoodDatabase.kt     ← The Room database setup
│   │   └── MoodRepository.kt  ← Middleman between DB and ViewModel
│   ├── viewmodel/
│   │   └── MoodViewModel.kt    ← Holds UI data, survives screen rotations
│   └── ui/
│       ├── MainActivity.kt     ← Home screen (log mood)
│       ├── HistoryActivity.kt  ← History screen
│       └── MoodEntryAdapter.kt ← RecyclerView adapter for the list
└── res/
    ├── layout/
    │   ├── activity_main.xml       ← Home screen layout
    │   ├── activity_history.xml    ← History screen layout
    │   └── item_mood_entry.xml     ← One row in the history list
    └── values/
        ├── colors.xml
        ├── strings.xml
        └── themes.xml
```

---

## 💡 How It Works (Architecture Overview)

```
UI (Activity) ←→ ViewModel ←→ Repository ←→ Room Database
```

1. **User taps "Save"** in `MainActivity`
2. `MainActivity` calls `viewModel.insert(entry)`
3. `MoodViewModel` asks the `MoodRepository` to save it
4. `MoodRepository` tells `MoodDao` to run the SQL INSERT
5. **Room saves it to SQLite** on the device
6. `allEntries` LiveData automatically notifies `HistoryActivity` to refresh the list

---

## 🔧 Possible Future Improvements

- 📊 Charts/graphs showing mood trends over time
- 🔔 Daily reminder notifications
- 🏷️ Activity tags (exercise, work, socialising)
- 🌙 Dark mode support
- 📤 Export data to CSV

---

## 📝 License
Free to use and modify for personal projects.
