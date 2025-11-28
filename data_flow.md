# Data Flow & Structure Visualizations

Here are the visual representations of how data flows and is structured within the OLLA application.

## 1. System Architecture & Data Flow
This diagram shows how data moves between the Activities and the Database.

```mermaid
graph TD
    subgraph Database
        DB[(SQLite Database\nolla.db)]
    end

    subgraph "User Entry"
        Splash[SplashActivity]
        LangSel[LanguageSelectionActivity]
        Profile[ProfileActivity]
    end

    subgraph "Main App"
        Home[HomeActivity]
        Vocab[VocabularyActivity]
        Grammar[GrammarActivity]
        Quiz[QuizActivity]
        QuizRes[QuizResultActivity]
        Progress[ProgressActivity]
    end

    Splash --> LangSel
    LangSel -- "Select Native/Target Lang" --> Profile
    
    Profile <-->|"Read/Write Profiles"| DB
    Profile -- "Profile ID + Langs" --> Home
    
    Home --> Vocab
    Home --> Grammar
    Home --> Quiz
    Home --> Progress
    
    Vocab <-->|"Fetch Words (Lang Pair)"| DB
    Grammar <-->|"Fetch Topics (Target Lang)"| DB
    
    Quiz <-->|"Fetch Quiz Names"| DB
    Quiz -- "Quiz Name + Lang Pair" --> QuizRes
    
    QuizRes <-->|"Fetch Questions"| DB
    QuizRes -- "Save Result (Score)" --> DB
    
    Progress <-->|"Fetch History (Profile ID)"| DB
```

## 2. Database Schema (ER Diagram)
This diagram illustrates the structure of the database tables and their relationships.

```mermaid
erDiagram
    PROFILES ||--o{ PROGRESS : "has"
    PROFILES {
        int id PK
        string username
        string password
        string native_language
        string target_language
    }

    VOCABULARY {
        int id PK
        string native_word
        string target_word
        string language_pair
    }

    GRAMMAR {
        int id PK
        string topic
        string content
        string language
    }

    QUIZ {
        int id PK
        string quiz_name
        string question
        string options_A_B_C_D
        string correct_option
        string language_pair
    }

    PROGRESS {
        int id PK
        int profile_id FK
        string quiz_name
        int score
        int total_questions
        string date
    }
```

## 3. Quiz Feature Data Flow
A detailed look at how data is handled during a quiz session.

```mermaid
sequenceDiagram
    participant User
    participant QuizActivity
    participant QuizResultActivity
    participant DatabaseHelper
    participant Database

    User->>QuizActivity: Selects Quiz
    QuizActivity->>DatabaseHelper: getQuizQuestions(quizName, langPair)
    DatabaseHelper->>Database: SELECT * FROM quiz WHERE ...
    Database-->>DatabaseHelper: Cursor (Questions)
    DatabaseHelper-->>QuizResultActivity: List<QuizQuestion>
    
    loop Every Question
        QuizResultActivity->>User: Display Question & Options
        User->>QuizResultActivity: Select Option
    end
    
    User->>QuizResultActivity: Click Submit
    QuizResultActivity->>QuizResultActivity: Calculate Score
    
    QuizResultActivity->>DatabaseHelper: insertProgress(progressObj)
    DatabaseHelper->>Database: INSERT INTO progress ...
    
    QuizResultActivity->>User: Show Result Dialog (Score %)
```
