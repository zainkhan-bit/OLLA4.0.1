# OLLA – Offline Language Learning App

OLLA (Offline Language Learning App) is a fully offline Android application that helps learners practice vocabulary, grammar, quizzes, and track progress across multiple profiles. The app is built entirely in Java using an activity-based architecture, Material Design UI, and a pre-populated SQLite database so that every module works without an internet connection.

---

## ✨ Key Features

| Module | Highlights |
| --- | --- |
| **User Profiles** | Multiple profiles, unique English-only usernames (≥4 chars), 4-digit numeric passwords, profile switching/deletion, per-profile data separation |
| **Splash & Theming** | Animated splash screen, full light/dark theme support via shared preferences |
| **Language Selection** | Choose native/target languages (English ↔ Urdu ↔ German combinations) |
| **Vocabulary** | 50+ bilingual word pairs per direction (300 total entries), RecyclerView list, text-to-speech pronunciation |
| **Grammar** | 12 core topics per language with bilingual explanations and examples |
| **Quizzes** | 3 quizzes per language pair, 10 MCQs each, color-coded answer feedback, score summary, wrong/correct highlighting |
| **Progress Tracking** | Stores quiz attempts (score, total questions, timestamp), shows average score, total attempts, last attempt info |

---

## 📁 Project Structure

```
OLLA/
├── app/
│   ├── src/main/
│   │   ├── java/com/olla/app/
│   │   │   ├── activities/        # All screens (Splash, Profiles, Vocabulary, Grammar, Quiz, Progress, etc.)
│   │   │   ├── adapters/          # RecyclerView adapters
│   │   │   ├── database/          # DatabaseHelper (SQLite)
│   │   │   └── models/            # Plain model classes
│   │   ├── res/                   # Layouts, drawables, themes, strings, colors
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── ...
├── README.md
└── settings.gradle.kts
```

---

## 🗄️ Database Overview

Database name: `olla.db`

Tables:

| Table | Description |
| --- | --- |
| `profiles` | Stores profile info (username, password, native/target languages) |
| `vocabulary` | 50 word pairs per language direction (English-Urdu, Urdu-English, English-German, etc.) |
| `grammar` | 12 topics per language (present, past, future, tenses, articles, pronouns, etc.) |
| `quiz` | 9 quizzes total (3 per language pair) with 10 MCQs each |
| `progress` | Stores quiz results (quiz id/name, profile id, score, total questions, date) |

All tables are created and seeded inside `DatabaseHelper.java` on first launch so the app works instantly offline.

---

## 🛠️ Tech Stack

- **Language:** Java (Android SDK 26–36)
- **Architecture:** Activity-based (no Compose)
- **UI:** Material Components, RecyclerView, CardView
- **Storage:** SQLite + SharedPreferences
- **Audio:** Android TextToSpeech API

---

## 🚀 Getting Started

1. **Clone**
   ```bash
   git clone <repo-url>
   cd OLLA40
   ```

2. **Open in Android Studio Flamingo+**
   - Let Gradle sync.
   - Plug in a device or launch an emulator (API 26+).

3. **Build & Run**
   - `Build > Make Project`
   - `Run > Run 'app'`

> **Note:** If deploying to a physical device, ensure “Install via USB/ADB” is enabled in developer settings to avoid `INSTALL_FAILED_USER_RESTRICTED`.

---

## 🧑‍💻 Profiles & Validation

- Usernames: English alphabet only, minimum 4 characters
- Passwords: Exactly 4 digits
- Profile switching prompts for password to protect each learner’s data

---

## 📚 Learning Flows

1. **Language Selection** → choose native/target languages  
2. **Profile Creation/Login** → secure access per learner  
3. **Home Dashboard**
   - Vocabulary (with TTS)
   - Grammar topics
   - Quizzes (3 difficulty levels)
   - Progress tracker

---

## ✅ Testing Checklist

- [x] Splash navigation in 2 seconds
- [x] Profile validation enforced
- [x] Vocabulary TTS and scrolling
- [x] Quizzes color-code answers and store results
- [x] Progress screen displays stats correctly
- [x] Dark mode toggle persists across sessions

---

## 📄 License

This project is provided as-is for educational use. Adapt and extend it for your own offline learning projects!

---

Happy learning with **OLLA – Offline Language Learning App**! 🎓✨

