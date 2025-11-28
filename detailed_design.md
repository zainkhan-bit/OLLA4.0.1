# Detailed Design

This document provides a deep dive into the application's internal structure and behavior.

## 1. Class Structure Diagram
This diagram illustrates the relationships between the main Java classes, including inheritance and dependencies.

```mermaid
classDiagram
    class BaseActivity {
        +applyTheme()
        +setThemeMode(boolean)
        +isDarkMode() boolean
    }

    class DatabaseHelper {
        +insertProfile(Profile)
        +getProfile(String, String)
        +getVocabularyByLanguagePair(String)
        +getGrammarByLanguage(String)
        +getQuizQuestions(String, String)
        +insertProgress(Progress)
    }

    class SplashActivity
    class LanguageSelectionActivity
    class ProfileActivity
    class HomeActivity
    class VocabularyActivity
    class GrammarActivity
    class QuizActivity
    class QuizResultActivity
    class ProgressActivity

    BaseActivity <|-- SplashActivity
    BaseActivity <|-- LanguageSelectionActivity
    BaseActivity <|-- ProfileActivity
    BaseActivity <|-- HomeActivity
    BaseActivity <|-- VocabularyActivity
    BaseActivity <|-- GrammarActivity
    BaseActivity <|-- QuizActivity
    BaseActivity <|-- QuizResultActivity
    BaseActivity <|-- ProgressActivity

    ProfileActivity ..> DatabaseHelper : uses
    HomeActivity ..> DatabaseHelper : uses
    VocabularyActivity ..> DatabaseHelper : uses
    GrammarActivity ..> DatabaseHelper : uses
    QuizActivity ..> DatabaseHelper : uses
    QuizResultActivity ..> DatabaseHelper : uses
    ProgressActivity ..> DatabaseHelper : uses

    class VocabularyAdapter
    class GrammarAdapter
    class QuizAdapter
    class ProgressAdapter

    VocabularyActivity --> VocabularyAdapter : creates
    GrammarActivity --> GrammarAdapter : creates
    QuizActivity --> QuizAdapter : creates
    ProgressActivity --> ProgressAdapter : creates

    class Profile
    class Vocabulary
    class Grammar
    class QuizQuestion
    class Progress

    DatabaseHelper ..> Profile : manages
    DatabaseHelper ..> Vocabulary : manages
    DatabaseHelper ..> Grammar : manages
    DatabaseHelper ..> QuizQuestion : manages
    DatabaseHelper ..> Progress : manages
```

## 2. UI Navigation Flow (State Diagram)
This diagram details the user's journey through the application screens and the conditions for transitions.

```mermaid
stateDiagram-v2
    [*] --> SplashActivity
    SplashActivity --> LanguageSelectionActivity : After 2s Delay

    state LanguageSelectionActivity {
        [*] --> SelectLanguages
        SelectLanguages --> ValidatePair : Click Continue
        ValidatePair --> SelectLanguages : Invalid Pair
        ValidatePair --> ProfileActivity : Valid Pair
    }

    state ProfileActivity {
        [*] --> LoginOrRegister
        LoginOrRegister --> CreateProfile : New User
        LoginOrRegister --> Login : Existing User
        CreateProfile --> LoginOrRegister : Success
        Login --> HomeActivity : Success
        Login --> LoginOrRegister : Failure
    }

    state HomeActivity {
        [*] --> Dashboard
        Dashboard --> VocabularyActivity : Click Vocabulary
        Dashboard --> GrammarActivity : Click Grammar
        Dashboard --> QuizActivity : Click Quiz
        Dashboard --> ProgressActivity : Click Progress
        Dashboard --> ProfileActivity : Click Profile (Logout/Switch)
    }

    state QuizSection {
        QuizActivity --> QuizResultActivity : Start Quiz
        QuizResultActivity --> QuizResultActivity : Next/Prev Question
        QuizResultActivity --> QuizResultActivity : Submit & Show Result
        QuizResultActivity --> HomeActivity : Back
    }
```

## 3. Vocabulary Component Interaction
A detailed view of how the Vocabulary feature works, including Text-to-Speech (TTS) integration.

```mermaid
sequenceDiagram
    participant User
    participant VocabActivity
    participant VocabAdapter
    participant TTS_Engine
    participant Database

    User->>VocabActivity: Open Vocabulary
    VocabActivity->>Database: getVocabularyByLanguagePair()
    Database-->>VocabActivity: List<Vocabulary>
    
    VocabActivity->>TTS_Engine: Initialize(Target Language)
    TTS_Engine-->>VocabActivity: Ready/Error
    
    VocabActivity->>VocabAdapter: Create(List, TTS)
    VocabActivity->>User: Show List

    User->>VocabAdapter: Click Speak Button
    VocabAdapter->>TTS_Engine: speak(TargetWord)
    TTS_Engine->>User: Audio Output
```
