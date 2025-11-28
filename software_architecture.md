# Software Architecture

This document illustrates the high-level software architecture of the OLLA application.

## 1. Layered Architecture
The application follows a standard 3-tier architecture typical for Android applications without complex dependency injection frameworks.

```mermaid
graph TD
    subgraph "Presentation Layer (UI)"
        Activities[Activities]
        Adapters[Adapters]
        Layouts[XML Layouts]
    end

    subgraph "Business Logic Layer"
        Models[Data Models (POJOs)]
        Logic[Application Logic]
    end

    subgraph "Data Layer"
        DBHelper[DatabaseHelper]
        SQLite[(SQLite Database)]
        Prefs[SharedPreferences]
    end

    Activities --> Adapters
    Activities --> Layouts
    Activities --> Models
    Activities --> DBHelper
    Activities --> Prefs
    
    Adapters --> Models
    
    DBHelper --> SQLite
    DBHelper --> Models
```

### Layer Descriptions
*   **Presentation Layer**: Handles all UI interactions. It consists of `Activities` (screens), `Adapters` (for lists), and XML resources. It communicates directly with the Data Layer to fetch and display information.
*   **Business Logic Layer**: Contains the core entities (`Profile`, `Vocabulary`, `Grammar`, etc.) that define the data structure. In this simple architecture, much of the business logic is embedded within the Activities and DatabaseHelper.
*   **Data Layer**: Responsible for data persistence. `DatabaseHelper` manages the SQLite database for structured data, while `SharedPreferences` handles simple key-value pairs like theme settings.

## 2. Component Diagram
This diagram shows the main components of the system and their dependencies on Android system services.

```mermaid
componentDiagram
    package "OLLA Application" {
        [UI Components]
        [Database Manager]
        [Data Models]
    }

    package "Android System" {
        [TextToSpeech Service]
        [Shared Preferences]
        [SQLite Engine]
    }

    [UI Components] --> [Database Manager] : Requests Data
    [UI Components] --> [Data Models] : Uses
    [Database Manager] --> [Data Models] : Creates/Reads
    
    [UI Components] --> [TextToSpeech Service] : Audio Playback
    [UI Components] --> [Shared Preferences] : Theme Settings
    [Database Manager] --> [SQLite Engine] : SQL Queries
```
