# File Report – OLLA Android Project

## Root
- `README.md` – Product overview, feature list, tech stack, setup instructions, validation rules, learning flow, checklist, and license note.
- `FILE_REPORT.md` – This document summarizing the purpose of every project file.
- `build.gradle.kts` – Top-level Gradle configuration enabling the Android application plugin catalogs.
- `settings.gradle.kts` – Declares repositories and includes the `app` module.
- `gradlew`, `gradlew.bat`, `gradle/` – Gradle wrapper scripts and supporting files.
- `local.properties`, `gradle.properties` – Local SDK path and global Gradle settings.

## Module `app/`
- `build.gradle.kts` – Module-level Gradle config targeting SDK 36, setting min SDK 26, enabling Java 11, and declaring dependencies (AppCompat, Material, RecyclerView, etc.).
- `proguard-rules.pro` – Empty placeholder for release obfuscation rules.

### `app/src/main/AndroidManifest.xml`
Registers application metadata, permissions, and every activity (Splash, LanguageSelection, Profile, Home, Vocabulary, Grammar, Quiz, QuizResult, Progress) with portrait orientation and light theme.

### Java Sources `app/src/main/java/com/olla/app/`

#### activities/
- `BaseActivity.java` – Common ancestor handling theme preference (light/dark) via SharedPreferences and AppCompatDelegate.
- `SplashActivity.java` – Shows animated splash text for two seconds before launching language selection.
- `LanguageSelectionActivity.java` – Lets users pick native/target languages, validates supported pairs, then opens Profile screen.
- `ProfileActivity.java` – Manages profile creation, login, switching, deletion, and theme toggle with strict username/password validation.
- `HomeActivity.java` – Dashboard linking to vocabulary, grammar, quiz, progress, and profile screens, greeting the active user.
- `VocabularyActivity.java` – Loads vocabulary rows for the chosen language pair and supports text-to-speech playback.
- `GrammarActivity.java` – Displays grammar topics for the learner’s target language using a RecyclerView.
- `QuizActivity.java` – Lists available quizzes for the language pair and starts the quiz runner when selected.
- `QuizResultActivity.java` – Conducts quiz questions, tracks answers, color-codes correct/wrong selections, records progress, and summarizes results.
- `ProgressActivity.java` – Shows quiz attempt history with statistics like average score, total attempts, and last attempt info.

#### adapters/
- `VocabularyAdapter.java` – Binds bilingual word pairs with a speak button to trigger TTS.
- `GrammarAdapter.java` – Presents grammar topic cards with title and content.
- `QuizAdapter.java` – Displays quiz name cards and notifies the selection listener.
- `ProgressAdapter.java` – Renders progress entries (quiz name, score, date, percentage) in cards.
- `ProfileAdapter.java` – (Prepared for potential list use) shows profiles with username and language pair.

#### database/
- `DatabaseHelper.java` – Extends `SQLiteOpenHelper`, creates all tables (profiles, vocabulary, grammar, quiz, progress), preloads sample data (50+ words per direction, grammar topics, 9 quizzes), and exposes CRUD/query helpers for profiles, vocabulary, grammar, quizzes, and progress.

#### models/
- `Profile.java` – POJO for profile data.
- `Vocabulary.java` – POJO for vocabulary entries.
- `Grammar.java` – POJO for grammar topics.
- `QuizQuestion.java` – POJO representing quiz questions/options.
- `Progress.java` – POJO representing stored quiz attempts.

### Resources `app/src/main/res/`
- `layout/activity_splash.xml` – Full-screen splash layout with centered title/subtitle.
- `layout/activity_language_selection.xml` – Language selectors with spinners and continue button.
- `layout/activity_profile.xml` – Scrollable profile management UI with TextInputLayouts, action buttons, and theme switch.
- `layout/activity_home.xml` – Welcome card plus grid of navigation buttons.
- `layout/activity_vocabulary.xml` – Title bar plus vocabulary RecyclerView.
- `layout/activity_grammar.xml` – Title bar plus grammar RecyclerView.
- `layout/activity_quiz.xml` – Quiz selection list and start button.
- `layout/activity_quiz_result.xml` – Quiz runner UI with question, options, navigation, and submit controls.
- `layout/activity_progress.xml` – Progress title, stats text, and RecyclerView list.
- `layout/item_vocabulary.xml` – Card for vocabulary pair plus speak button.
- `layout/item_grammar.xml` – Card for grammar topic and description.
- `layout/item_quiz.xml` – Card for quiz name selection.
- `layout/item_progress.xml` – Card showing quiz score, percentage, and date.
- `layout/item_profile.xml` – Card showing username and language pairing.

- `values/strings.xml` – All localized text strings (app name, labels, button text, messages).
- `values/colors.xml` – Color palette for primary, accent, backgrounds, text, quiz result states.
- `values/themes.xml` & `values-night/themes.xml` – Material Day/Night theme definitions referencing the color resources.
- `drawable/` & `mipmap/` – Generated launcher icons and backgrounds.
- `xml/backup_rules.xml`, `xml/data_extraction_rules.xml` – Default backup/restore configuration.

### Other
- `app/src/test` & `app/src/androidTest` – Standard Gradle directories for future unit/UI tests (currently empty).

This report mirrors the codebase as delivered and explains the purpose of each file for quick onboarding or review.

