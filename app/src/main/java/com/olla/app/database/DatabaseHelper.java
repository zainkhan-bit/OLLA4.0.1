package com.olla.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.List;
import com.olla.app.models.*;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "olla.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_PROFILES = "profiles";
    private static final String TABLE_VOCABULARY = "vocabulary";
    private static final String TABLE_GRAMMAR = "grammar";
    private static final String TABLE_QUIZ = "quiz";
    private static final String TABLE_PROGRESS = "progress";

    // Profile table columns
    private static final String COL_PROFILE_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_NATIVE_LANGUAGE = "native_language";
    private static final String COL_TARGET_LANGUAGE = "target_language";

    // Vocabulary table columns
    private static final String COL_VOCAB_ID = "id";
    private static final String COL_NATIVE_WORD = "native_word";
    private static final String COL_TARGET_WORD = "target_word";
    private static final String COL_LANGUAGE_PAIR = "language_pair";

    // Grammar table columns
    private static final String COL_GRAMMAR_ID = "id";
    private static final String COL_TOPIC = "topic";
    private static final String COL_CONTENT = "content";
    private static final String COL_LANGUAGE = "language";

    // Quiz table columns
    private static final String COL_QUIZ_ID = "id";
    private static final String COL_QUESTION = "question";
    private static final String COL_OPTION_A = "optionA";
    private static final String COL_OPTION_B = "optionB";
    private static final String COL_OPTION_C = "optionC";
    private static final String COL_OPTION_D = "optionD";
    private static final String COL_CORRECT_OPTION = "correct_option";
    private static final String COL_QUIZ_NATIVE_LANGUAGE = "native_language";
    private static final String COL_QUIZ_TARGET_LANGUAGE = "target_language";
    private static final String COL_QUIZ_NAME = "quiz_name";
    private static final String COL_QUIZ_LANGUAGE_PAIR = "language_pair";

    // Progress table columns
    private static final String COL_PROGRESS_ID = "id";
    private static final String COL_PROGRESS_QUIZ_ID = "quiz_id";
    private static final String COL_PROGRESS_SCORE = "score";
    private static final String COL_PROGRESS_DATE = "date";
    private static final String COL_PROGRESS_LANGUAGE = "language";
    private static final String COL_PROGRESS_PROFILE_ID = "profile_id";
    private static final String COL_PROGRESS_QUIZ_NAME = "quiz_name";
    private static final String COL_PROGRESS_TOTAL_QUESTIONS = "total_questions";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create profiles table
        String createProfilesTable = "CREATE TABLE " + TABLE_PROFILES + " (" +
                COL_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE NOT NULL, " +
                COL_PASSWORD + " TEXT NOT NULL, " +
                COL_NATIVE_LANGUAGE + " TEXT NOT NULL, " +
                COL_TARGET_LANGUAGE + " TEXT NOT NULL)";
        db.execSQL(createProfilesTable);

        // Create vocabulary table
        String createVocabularyTable = "CREATE TABLE " + TABLE_VOCABULARY + " (" +
                COL_VOCAB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NATIVE_WORD + " TEXT NOT NULL, " +
                COL_TARGET_WORD + " TEXT NOT NULL, " +
                COL_LANGUAGE_PAIR + " TEXT NOT NULL)";
        db.execSQL(createVocabularyTable);

        // Create grammar table
        String createGrammarTable = "CREATE TABLE " + TABLE_GRAMMAR + " (" +
                COL_GRAMMAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TOPIC + " TEXT NOT NULL, " +
                COL_CONTENT + " TEXT NOT NULL, " +
                COL_LANGUAGE + " TEXT NOT NULL)";
        db.execSQL(createGrammarTable);

        // Create quiz table
        String createQuizTable = "CREATE TABLE " + TABLE_QUIZ + " (" +
                COL_QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_QUESTION + " TEXT NOT NULL, " +
                COL_OPTION_A + " TEXT NOT NULL, " +
                COL_OPTION_B + " TEXT NOT NULL, " +
                COL_OPTION_C + " TEXT NOT NULL, " +
                COL_OPTION_D + " TEXT NOT NULL, " +
                COL_CORRECT_OPTION + " TEXT NOT NULL, " +
                COL_QUIZ_NATIVE_LANGUAGE + " TEXT NOT NULL, " +
                COL_QUIZ_TARGET_LANGUAGE + " TEXT NOT NULL, " +
                COL_QUIZ_NAME + " TEXT NOT NULL, " +
                COL_QUIZ_LANGUAGE_PAIR + " TEXT NOT NULL)";
        db.execSQL(createQuizTable);

        // Create progress table
        String createProgressTable = "CREATE TABLE " + TABLE_PROGRESS + " (" +
                COL_PROGRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PROGRESS_QUIZ_ID + " INTEGER, " +
                COL_PROGRESS_SCORE + " INTEGER NOT NULL, " +
                COL_PROGRESS_DATE + " TEXT NOT NULL, " +
                COL_PROGRESS_LANGUAGE + " TEXT NOT NULL, " +
                COL_PROGRESS_PROFILE_ID + " INTEGER NOT NULL, " +
                COL_PROGRESS_QUIZ_NAME + " TEXT NOT NULL, " +
                COL_PROGRESS_TOTAL_QUESTIONS + " INTEGER NOT NULL)";
        db.execSQL(createProgressTable);

        // Insert sample data
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOCABULARY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRAMMAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRESS);
        onCreate(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        insertVocabularyData(db);
        insertGrammarData(db);
        insertQuizData(db);
    }

    private void insertVocabularyData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // English ↔ Urdu (50 words each direction = 100 total)
        String[][] englishUrduWords = {
            {"Hello", "ہیلو"}, {"Goodbye", "الوداع"}, {"Thank you", "شکریہ"}, {"Please", "براہ کرم"},
            {"Yes", "ہاں"}, {"No", "نہیں"}, {"Water", "پانی"}, {"Food", "کھانا"},
            {"House", "گھر"}, {"Book", "کتاب"}, {"Pen", "قلم"}, {"School", "سکول"},
            {"Teacher", "استاد"}, {"Student", "طالب علم"}, {"Friend", "دوست"}, {"Family", "خاندان"},
            {"Mother", "ماں"}, {"Father", "باپ"}, {"Brother", "بھائی"}, {"Sister", "بہن"},
            {"Day", "دن"}, {"Night", "رات"}, {"Morning", "صبح"}, {"Evening", "شام"},
            {"Sun", "سورج"}, {"Moon", "چاند"}, {"Star", "ستارہ"}, {"Sky", "آسمان"},
            {"Tree", "درخت"}, {"Flower", "پھول"}, {"Bird", "پرندہ"}, {"Dog", "کتا"},
            {"Cat", "بلی"}, {"Car", "گاڑی"}, {"Road", "سڑک"}, {"City", "شہر"},
            {"Country", "ملک"}, {"Time", "وقت"}, {"Hour", "گھنٹہ"}, {"Minute", "منٹ"},
            {"Year", "سال"}, {"Month", "مہینہ"}, {"Week", "ہفتہ"}, {"Today", "آج"},
            {"Tomorrow", "کل"}, {"Yesterday", "کل"}, {"Big", "بڑا"}, {"Small", "چھوٹا"},
            {"Good", "اچھا"}, {"Bad", "برا"}, {"Happy", "خوش"}, {"Sad", "اداس"},
            {"Beautiful", "خوبصورت"}, {"Ugly", "بدصورت"}, {"Hot", "گرم"}, {"Cold", "ٹھنڈا"}
        };

        for (String[] word : englishUrduWords) {
            values.clear();
            values.put(COL_NATIVE_WORD, word[0]);
            values.put(COL_TARGET_WORD, word[1]);
            values.put(COL_LANGUAGE_PAIR, "English-Urdu");
            db.insert(TABLE_VOCABULARY, null, values);

            // Reverse direction
            values.clear();
            values.put(COL_NATIVE_WORD, word[1]);
            values.put(COL_TARGET_WORD, word[0]);
            values.put(COL_LANGUAGE_PAIR, "Urdu-English");
            db.insert(TABLE_VOCABULARY, null, values);
        }

        // English ↔ German (50 words)
        String[][] englishGermanWords = {
            {"Hello", "Hallo"}, {"Goodbye", "Auf Wiedersehen"}, {"Thank you", "Danke"}, {"Please", "Bitte"},
            {"Yes", "Ja"}, {"No", "Nein"}, {"Water", "Wasser"}, {"Food", "Essen"},
            {"House", "Haus"}, {"Book", "Buch"}, {"Pen", "Stift"}, {"School", "Schule"},
            {"Teacher", "Lehrer"}, {"Student", "Student"}, {"Friend", "Freund"}, {"Family", "Familie"},
            {"Mother", "Mutter"}, {"Father", "Vater"}, {"Brother", "Bruder"}, {"Sister", "Schwester"},
            {"Day", "Tag"}, {"Night", "Nacht"}, {"Morning", "Morgen"}, {"Evening", "Abend"},
            {"Sun", "Sonne"}, {"Moon", "Mond"}, {"Star", "Stern"}, {"Sky", "Himmel"},
            {"Tree", "Baum"}, {"Flower", "Blume"}, {"Bird", "Vogel"}, {"Dog", "Hund"},
            {"Cat", "Katze"}, {"Car", "Auto"}, {"Road", "Straße"}, {"City", "Stadt"},
            {"Country", "Land"}, {"Time", "Zeit"}, {"Hour", "Stunde"}, {"Minute", "Minute"},
            {"Year", "Jahr"}, {"Month", "Monat"}, {"Week", "Woche"}, {"Today", "Heute"},
            {"Tomorrow", "Morgen"}, {"Yesterday", "Gestern"}, {"Big", "Groß"}, {"Small", "Klein"},
            {"Good", "Gut"}, {"Bad", "Schlecht"}, {"Happy", "Glücklich"}, {"Sad", "Traurig"},
            {"Beautiful", "Schön"}, {"Ugly", "Hässlich"}, {"Hot", "Heiß"}, {"Cold", "Kalt"}
        };

        for (String[] word : englishGermanWords) {
            values.clear();
            values.put(COL_NATIVE_WORD, word[0]);
            values.put(COL_TARGET_WORD, word[1]);
            values.put(COL_LANGUAGE_PAIR, "English-German");
            db.insert(TABLE_VOCABULARY, null, values);

            // Reverse direction
            values.clear();
            values.put(COL_NATIVE_WORD, word[1]);
            values.put(COL_TARGET_WORD, word[0]);
            values.put(COL_LANGUAGE_PAIR, "German-English");
            db.insert(TABLE_VOCABULARY, null, values);
        }

        // Urdu ↔ German (50 words)
        String[][] urduGermanWords = {
            {"ہیلو", "Hallo"}, {"الوداع", "Auf Wiedersehen"}, {"شکریہ", "Danke"}, {"براہ کرم", "Bitte"},
            {"ہاں", "Ja"}, {"نہیں", "Nein"}, {"پانی", "Wasser"}, {"کھانا", "Essen"},
            {"گھر", "Haus"}, {"کتاب", "Buch"}, {"قلم", "Stift"}, {"سکول", "Schule"},
            {"استاد", "Lehrer"}, {"طالب علم", "Student"}, {"دوست", "Freund"}, {"خاندان", "Familie"},
            {"ماں", "Mutter"}, {"باپ", "Vater"}, {"بھائی", "Bruder"}, {"بہن", "Schwester"},
            {"دن", "Tag"}, {"رات", "Nacht"}, {"صبح", "Morgen"}, {"شام", "Abend"},
            {"سورج", "Sonne"}, {"چاند", "Mond"}, {"ستارہ", "Stern"}, {"آسمان", "Himmel"},
            {"درخت", "Baum"}, {"پھول", "Blume"}, {"پرندہ", "Vogel"}, {"کتا", "Hund"},
            {"بلی", "Katze"}, {"گاڑی", "Auto"}, {"سڑک", "Straße"}, {"شہر", "Stadt"},
            {"ملک", "Land"}, {"وقت", "Zeit"}, {"گھنٹہ", "Stunde"}, {"منٹ", "Minute"},
            {"سال", "Jahr"}, {"مہینہ", "Monat"}, {"ہفتہ", "Woche"}, {"آج", "Heute"},
            {"کل", "Morgen"}, {"کل", "Gestern"}, {"بڑا", "Groß"}, {"چھوٹا", "Klein"},
            {"اچھا", "Gut"}, {"برا", "Schlecht"}, {"خوش", "Glücklich"}, {"اداس", "Traurig"},
            {"خوبصورت", "Schön"}, {"بدصورت", "Hässlich"}, {"گرم", "Heiß"}, {"ٹھنڈا", "Kalt"}
        };

        for (String[] word : urduGermanWords) {
            values.clear();
            values.put(COL_NATIVE_WORD, word[0]);
            values.put(COL_TARGET_WORD, word[1]);
            values.put(COL_LANGUAGE_PAIR, "Urdu-German");
            db.insert(TABLE_VOCABULARY, null, values);

            // Reverse direction
            values.clear();
            values.put(COL_NATIVE_WORD, word[1]);
            values.put(COL_TARGET_WORD, word[0]);
            values.put(COL_LANGUAGE_PAIR, "German-Urdu");
            db.insert(TABLE_VOCABULARY, null, values);
        }
    }

    private void insertGrammarData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Grammar topics for English
        String[] topics = {
            "Present Tense", "Past Tense", "Future Tense", "Continuous Tenses",
            "Perfect Tenses", "Parts of Speech", "Sentence Structure", "Common Grammar Mistakes",
            "Articles", "Prepositions", "Pronouns", "Conjunctions"
        };

        String[] englishContent = {
            "Present Tense: Used to describe current actions or states. Example: 'I eat breakfast every day.'",
            "Past Tense: Used to describe actions that happened in the past. Example: 'I ate breakfast yesterday.'",
            "Future Tense: Used to describe actions that will happen. Example: 'I will eat breakfast tomorrow.'",
            "Continuous Tenses: Show ongoing actions. Example: 'I am eating breakfast now.'",
            "Perfect Tenses: Show completed actions. Example: 'I have eaten breakfast already.'",
            "Parts of Speech: Noun, Verb, Adjective, Adverb, Pronoun, Preposition, Conjunction, Interjection.",
            "Sentence Structure: Subject + Verb + Object. Example: 'The cat (subject) sat (verb) on the mat (object).'",
            "Common Mistakes: Using 'your' instead of 'you're', 'its' vs 'it's', 'there' vs 'their' vs 'they're'.",
            "Articles: 'a' (indefinite), 'an' (indefinite before vowel), 'the' (definite). Example: 'A cat, an apple, the sun.'",
            "Prepositions: Words showing position or time. Examples: in, on, at, by, for, with, from, to.",
            "Pronouns: Replace nouns. Examples: I, you, he, she, it, we, they, me, him, her, us, them.",
            "Conjunctions: Connect words or sentences. Examples: and, but, or, so, because, although, if."
        };

        for (int i = 0; i < topics.length; i++) {
            values.clear();
            values.put(COL_TOPIC, topics[i]);
            values.put(COL_CONTENT, englishContent[i]);
            values.put(COL_LANGUAGE, "English");
            db.insert(TABLE_GRAMMAR, null, values);
        }

        // Grammar topics for Urdu
        String[] urduContent = {
            "حال (Present Tense): موجودہ وقت کی کارروائیوں کو بیان کرنے کے لیے استعمال ہوتا ہے۔ مثال: 'میں روزانہ ناشتہ کرتا ہوں۔'",
            "ماضی (Past Tense): ماضی میں ہونے والی کارروائیوں کو بیان کرنے کے لیے استعمال ہوتا ہے۔ مثال: 'میں نے کل ناشتہ کیا۔'",
            "مستقبل (Future Tense): مستقبل میں ہونے والی کارروائیوں کو بیان کرنے کے لیے استعمال ہوتا ہے۔ مثال: 'میں کل ناشتہ کروں گا۔'",
            "جاری زمانہ (Continuous Tenses): جاری کارروائیوں کو ظاہر کرتا ہے۔ مثال: 'میں اب ناشتہ کر رہا ہوں۔'",
            "مکمل زمانہ (Perfect Tenses): مکمل کارروائیوں کو ظاہر کرتا ہے۔ مثال: 'میں نے ناشتہ کر لیا ہے۔'",
            "الفاظ کی اقسام: اسم، فعل، صفت، متعلق فعل، ضمیر، حرف جار، حرف عطف، حرف ندا۔",
            "جملہ کی ساخت: فاعل + فعل + مفعول۔ مثال: 'بلی (فاعل) چٹائی (مفعول) پر بیٹھی (فعل)۔'",
            "عام غلطیاں: 'آپ' اور 'آپ کا' کا غلط استعمال، 'اس کا' اور 'یہ' کا فرق۔",
            "حروف تعریف: 'ایک' (غیر معین)، 'وہ' (معین)۔ مثال: 'ایک بلی، وہ سورج۔'",
            "حروف جار: الفاظ جو جگہ یا وقت ظاہر کرتے ہیں۔ مثال: میں، پر، سے، تک، کے لیے۔",
            "ضمائر: اسم کی جگہ استعمال ہوتے ہیں۔ مثال: میں، تم، وہ، ہم، آپ، یہ۔",
            "حروف عطف: الفاظ یا جملوں کو جوڑتے ہیں۔ مثال: اور، لیکن، یا، اس لیے، کیونکہ۔"
        };

        for (int i = 0; i < topics.length; i++) {
            values.clear();
            values.put(COL_TOPIC, topics[i]);
            values.put(COL_CONTENT, urduContent[i]);
            values.put(COL_LANGUAGE, "Urdu");
            db.insert(TABLE_GRAMMAR, null, values);
        }

        // Grammar topics for German
        String[] germanContent = {
            "Präsens (Present Tense): Beschreibt aktuelle Handlungen. Beispiel: 'Ich esse jeden Tag Frühstück.'",
            "Präteritum (Past Tense): Beschreibt vergangene Handlungen. Beispiel: 'Ich aß gestern Frühstück.'",
            "Futur (Future Tense): Beschreibt zukünftige Handlungen. Beispiel: 'Ich werde morgen Frühstück essen.'",
            "Verlaufsform (Continuous Tenses): Zeigt laufende Handlungen. Beispiel: 'Ich esse gerade Frühstück.'",
            "Perfekt (Perfect Tenses): Zeigt abgeschlossene Handlungen. Beispiel: 'Ich habe bereits gefrühstückt.'",
            "Wortarten: Nomen, Verb, Adjektiv, Adverb, Pronomen, Präposition, Konjunktion, Interjektion.",
            "Satzstruktur: Subjekt + Verb + Objekt. Beispiel: 'Die Katze (Subjekt) saß (Verb) auf der Matte (Objekt).'",
            "Häufige Fehler: 'du' vs 'Sie', 'sein' vs 'ihr', 'der/die/das' Artikelverwendung.",
            "Artikel: 'der' (maskulin), 'die' (feminin), 'das' (neutral), 'ein/eine' (unbestimmt).",
            "Präpositionen: Wörter, die Position oder Zeit zeigen. Beispiele: in, auf, an, bei, für, mit, von, zu.",
            "Pronomen: Ersetzen Nomen. Beispiele: ich, du, er, sie, es, wir, ihr, sie, mich, dich, ihn.",
            "Konjunktionen: Verbinden Wörter oder Sätze. Beispiele: und, aber, oder, so, weil, obwohl, wenn."
        };

        for (int i = 0; i < topics.length; i++) {
            values.clear();
            values.put(COL_TOPIC, topics[i]);
            values.put(COL_CONTENT, germanContent[i]);
            values.put(COL_LANGUAGE, "German");
            db.insert(TABLE_GRAMMAR, null, values);
        }
    }

    private void insertQuizData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Quiz 1: English-Urdu Basic Words
        String[][] quiz1Questions = {
            {"Hello", "ہیلو", "الوداع", "شکریہ", "براہ کرم", "ہیلو"},
            {"Thank you", "ہیلو", "الوداع", "شکریہ", "براہ کرم", "شکریہ"},
            {"Water", "پانی", "کھانا", "گھر", "کتاب", "پانی"},
            {"Book", "قلم", "کتاب", "سکول", "استاد", "کتاب"},
            {"Friend", "دوست", "خاندان", "ماں", "باپ", "دوست"},
            {"Good", "اچھا", "برا", "خوش", "اداس", "اچھا"},
            {"House", "گھر", "سکول", "شہر", "ملک", "گھر"},
            {"Day", "دن", "رات", "صبح", "شام", "دن"},
            {"Big", "بڑا", "چھوٹا", "اچھا", "برا", "بڑا"},
            {"Happy", "خوش", "اداس", "اچھا", "برا", "خوش"}
        };

        for (String[] q : quiz1Questions) {
            values.clear();
            values.put(COL_QUESTION, q[0]);
            values.put(COL_OPTION_A, q[1]);
            values.put(COL_OPTION_B, q[2]);
            values.put(COL_OPTION_C, q[3]);
            values.put(COL_OPTION_D, q[4]);
            values.put(COL_CORRECT_OPTION, q[5]);
            values.put(COL_QUIZ_NATIVE_LANGUAGE, "English");
            values.put(COL_QUIZ_TARGET_LANGUAGE, "Urdu");
            values.put(COL_QUIZ_NAME, "Basic Words Quiz 1");
            values.put(COL_QUIZ_LANGUAGE_PAIR, "English-Urdu");
            db.insert(TABLE_QUIZ, null, values);
        }

        // Quiz 2: English-Urdu Intermediate
        String[][] quiz2Questions = {
            {"Teacher", "استاد", "طالب علم", "دوست", "خاندان", "استاد"},
            {"Family", "دوست", "خاندان", "ماں", "باپ", "خاندان"},
            {"Morning", "صبح", "شام", "دن", "رات", "صبح"},
            {"Sun", "سورج", "چاند", "ستارہ", "آسمان", "سورج"},
            {"Tree", "درخت", "پھول", "پرندہ", "کتا", "درخت"},
            {"City", "شہر", "ملک", "گھر", "سکول", "شہر"},
            {"Time", "وقت", "گھنٹہ", "منٹ", "سال", "وقت"},
            {"Beautiful", "خوبصورت", "بدصورت", "اچھا", "برا", "خوبصورت"},
            {"Hot", "گرم", "ٹھنڈا", "اچھا", "برا", "گرم"},
            {"Today", "آج", "کل", "صبح", "شام", "آج"}
        };

        for (String[] q : quiz2Questions) {
            values.clear();
            values.put(COL_QUESTION, q[0]);
            values.put(COL_OPTION_A, q[1]);
            values.put(COL_OPTION_B, q[2]);
            values.put(COL_OPTION_C, q[3]);
            values.put(COL_OPTION_D, q[4]);
            values.put(COL_CORRECT_OPTION, q[5]);
            values.put(COL_QUIZ_NATIVE_LANGUAGE, "English");
            values.put(COL_QUIZ_TARGET_LANGUAGE, "Urdu");
            values.put(COL_QUIZ_NAME, "Intermediate Words Quiz 2");
            values.put(COL_QUIZ_LANGUAGE_PAIR, "English-Urdu");
            db.insert(TABLE_QUIZ, null, values);
        }

        // Quiz 3: English-Urdu Advanced
        String[][] quiz3Questions = {
            {"Please", "براہ کرم", "شکریہ", "ہیلو", "الوداع", "براہ کرم"},
            {"Student", "استاد", "طالب علم", "دوست", "خاندان", "طالب علم"},
            {"Brother", "بھائی", "بہن", "ماں", "باپ", "بھائی"},
            {"Evening", "صبح", "شام", "دن", "رات", "شام"},
            {"Moon", "سورج", "چاند", "ستارہ", "آسمان", "چاند"},
            {"Flower", "درخت", "پھول", "پرندہ", "کتا", "پھول"},
            {"Car", "گاڑی", "سڑک", "شہر", "ملک", "گاڑی"},
            {"Week", "سال", "مہینہ", "ہفتہ", "گھنٹہ", "ہفتہ"},
            {"Small", "بڑا", "چھوٹا", "اچھا", "برا", "چھوٹا"},
            {"Cold", "گرم", "ٹھنڈا", "اچھا", "برا", "ٹھنڈا"}
        };

        for (String[] q : quiz3Questions) {
            values.clear();
            values.put(COL_QUESTION, q[0]);
            values.put(COL_OPTION_A, q[1]);
            values.put(COL_OPTION_B, q[2]);
            values.put(COL_OPTION_C, q[3]);
            values.put(COL_OPTION_D, q[4]);
            values.put(COL_CORRECT_OPTION, q[5]);
            values.put(COL_QUIZ_NATIVE_LANGUAGE, "English");
            values.put(COL_QUIZ_TARGET_LANGUAGE, "Urdu");
            values.put(COL_QUIZ_NAME, "Advanced Words Quiz 3");
            values.put(COL_QUIZ_LANGUAGE_PAIR, "English-Urdu");
            db.insert(TABLE_QUIZ, null, values);
        }

        // Quiz 1: English-German Basic Words
        String[][] quiz4Questions = {
            {"Hello", "Hallo", "Auf Wiedersehen", "Danke", "Bitte", "Hallo"},
            {"Thank you", "Hallo", "Auf Wiedersehen", "Danke", "Bitte", "Danke"},
            {"Water", "Wasser", "Essen", "Haus", "Buch", "Wasser"},
            {"Book", "Stift", "Buch", "Schule", "Lehrer", "Buch"},
            {"Friend", "Freund", "Familie", "Mutter", "Vater", "Freund"},
            {"Good", "Gut", "Schlecht", "Glücklich", "Traurig", "Gut"},
            {"House", "Haus", "Schule", "Stadt", "Land", "Haus"},
            {"Day", "Tag", "Nacht", "Morgen", "Abend", "Tag"},
            {"Big", "Groß", "Klein", "Gut", "Schlecht", "Groß"},
            {"Happy", "Glücklich", "Traurig", "Gut", "Schlecht", "Glücklich"}
        };

        for (String[] q : quiz4Questions) {
            values.clear();
            values.put(COL_QUESTION, q[0]);
            values.put(COL_OPTION_A, q[1]);
            values.put(COL_OPTION_B, q[2]);
            values.put(COL_OPTION_C, q[3]);
            values.put(COL_OPTION_D, q[4]);
            values.put(COL_CORRECT_OPTION, q[5]);
            values.put(COL_QUIZ_NATIVE_LANGUAGE, "English");
            values.put(COL_QUIZ_TARGET_LANGUAGE, "German");
            values.put(COL_QUIZ_NAME, "Basic Words Quiz 1");
            values.put(COL_QUIZ_LANGUAGE_PAIR, "English-German");
            db.insert(TABLE_QUIZ, null, values);
        }

        // Quiz 2: English-German Intermediate
        String[][] quiz5Questions = {
            {"Teacher", "Lehrer", "Student", "Freund", "Familie", "Lehrer"},
            {"Family", "Freund", "Familie", "Mutter", "Vater", "Familie"},
            {"Morning", "Morgen", "Abend", "Tag", "Nacht", "Morgen"},
            {"Sun", "Sonne", "Mond", "Stern", "Himmel", "Sonne"},
            {"Tree", "Baum", "Blume", "Vogel", "Hund", "Baum"},
            {"City", "Stadt", "Land", "Haus", "Schule", "Stadt"},
            {"Time", "Zeit", "Stunde", "Minute", "Jahr", "Zeit"},
            {"Beautiful", "Schön", "Hässlich", "Gut", "Schlecht", "Schön"},
            {"Hot", "Heiß", "Kalt", "Gut", "Schlecht", "Heiß"},
            {"Today", "Heute", "Morgen", "Morgen", "Abend", "Heute"}
        };

        for (String[] q : quiz5Questions) {
            values.clear();
            values.put(COL_QUESTION, q[0]);
            values.put(COL_OPTION_A, q[1]);
            values.put(COL_OPTION_B, q[2]);
            values.put(COL_OPTION_C, q[3]);
            values.put(COL_OPTION_D, q[4]);
            values.put(COL_CORRECT_OPTION, q[5]);
            values.put(COL_QUIZ_NATIVE_LANGUAGE, "English");
            values.put(COL_QUIZ_TARGET_LANGUAGE, "German");
            values.put(COL_QUIZ_NAME, "Intermediate Words Quiz 2");
            values.put(COL_QUIZ_LANGUAGE_PAIR, "English-German");
            db.insert(TABLE_QUIZ, null, values);
        }

        // Quiz 3: English-German Advanced
        String[][] quiz6Questions = {
            {"Please", "Bitte", "Danke", "Hallo", "Auf Wiedersehen", "Bitte"},
            {"Student", "Lehrer", "Student", "Freund", "Familie", "Student"},
            {"Brother", "Bruder", "Schwester", "Mutter", "Vater", "Bruder"},
            {"Evening", "Morgen", "Abend", "Tag", "Nacht", "Abend"},
            {"Moon", "Sonne", "Mond", "Stern", "Himmel", "Mond"},
            {"Flower", "Baum", "Blume", "Vogel", "Hund", "Blume"},
            {"Car", "Auto", "Straße", "Stadt", "Land", "Auto"},
            {"Week", "Jahr", "Monat", "Woche", "Stunde", "Woche"},
            {"Small", "Groß", "Klein", "Gut", "Schlecht", "Klein"},
            {"Cold", "Heiß", "Kalt", "Gut", "Schlecht", "Kalt"}
        };

        for (String[] q : quiz6Questions) {
            values.clear();
            values.put(COL_QUESTION, q[0]);
            values.put(COL_OPTION_A, q[1]);
            values.put(COL_OPTION_B, q[2]);
            values.put(COL_OPTION_C, q[3]);
            values.put(COL_OPTION_D, q[4]);
            values.put(COL_CORRECT_OPTION, q[5]);
            values.put(COL_QUIZ_NATIVE_LANGUAGE, "English");
            values.put(COL_QUIZ_TARGET_LANGUAGE, "German");
            values.put(COL_QUIZ_NAME, "Advanced Words Quiz 3");
            values.put(COL_QUIZ_LANGUAGE_PAIR, "English-German");
            db.insert(TABLE_QUIZ, null, values);
        }

        // Quiz 1: Urdu-German Basic Words
        String[][] quiz7Questions = {
            {"ہیلو", "Hallo", "Auf Wiedersehen", "Danke", "Bitte", "Hallo"},
            {"شکریہ", "Hallo", "Auf Wiedersehen", "Danke", "Bitte", "Danke"},
            {"پانی", "Wasser", "Essen", "Haus", "Buch", "Wasser"},
            {"کتاب", "Stift", "Buch", "Schule", "Lehrer", "Buch"},
            {"دوست", "Freund", "Familie", "Mutter", "Vater", "Freund"},
            {"اچھا", "Gut", "Schlecht", "Glücklich", "Traurig", "Gut"},
            {"گھر", "Haus", "Schule", "Stadt", "Land", "Haus"},
            {"دن", "Tag", "Nacht", "Morgen", "Abend", "Tag"},
            {"بڑا", "Groß", "Klein", "Gut", "Schlecht", "Groß"},
            {"خوش", "Glücklich", "Traurig", "Gut", "Schlecht", "Glücklich"}
        };

        for (String[] q : quiz7Questions) {
            values.clear();
            values.put(COL_QUESTION, q[0]);
            values.put(COL_OPTION_A, q[1]);
            values.put(COL_OPTION_B, q[2]);
            values.put(COL_OPTION_C, q[3]);
            values.put(COL_OPTION_D, q[4]);
            values.put(COL_CORRECT_OPTION, q[5]);
            values.put(COL_QUIZ_NATIVE_LANGUAGE, "Urdu");
            values.put(COL_QUIZ_TARGET_LANGUAGE, "German");
            values.put(COL_QUIZ_NAME, "Basic Words Quiz 1");
            values.put(COL_QUIZ_LANGUAGE_PAIR, "Urdu-German");
            db.insert(TABLE_QUIZ, null, values);
        }

        // Quiz 2: Urdu-German Intermediate
        String[][] quiz8Questions = {
            {"استاد", "Lehrer", "Student", "Freund", "Familie", "Lehrer"},
            {"خاندان", "Freund", "Familie", "Mutter", "Vater", "Familie"},
            {"صبح", "Morgen", "Abend", "Tag", "Nacht", "Morgen"},
            {"سورج", "Sonne", "Mond", "Stern", "Himmel", "Sonne"},
            {"درخت", "Baum", "Blume", "Vogel", "Hund", "Baum"},
            {"شہر", "Stadt", "Land", "Haus", "Schule", "Stadt"},
            {"وقت", "Zeit", "Stunde", "Minute", "Jahr", "Zeit"},
            {"خوبصورت", "Schön", "Hässlich", "Gut", "Schlecht", "Schön"},
            {"گرم", "Heiß", "Kalt", "Gut", "Schlecht", "Heiß"},
            {"آج", "Heute", "Morgen", "Morgen", "Abend", "Heute"}
        };

        for (String[] q : quiz8Questions) {
            values.clear();
            values.put(COL_QUESTION, q[0]);
            values.put(COL_OPTION_A, q[1]);
            values.put(COL_OPTION_B, q[2]);
            values.put(COL_OPTION_C, q[3]);
            values.put(COL_OPTION_D, q[4]);
            values.put(COL_CORRECT_OPTION, q[5]);
            values.put(COL_QUIZ_NATIVE_LANGUAGE, "Urdu");
            values.put(COL_QUIZ_TARGET_LANGUAGE, "German");
            values.put(COL_QUIZ_NAME, "Intermediate Words Quiz 2");
            values.put(COL_QUIZ_LANGUAGE_PAIR, "Urdu-German");
            db.insert(TABLE_QUIZ, null, values);
        }

        // Quiz 3: Urdu-German Advanced
        String[][] quiz9Questions = {
            {"براہ کرم", "Bitte", "Danke", "Hallo", "Auf Wiedersehen", "Bitte"},
            {"طالب علم", "Lehrer", "Student", "Freund", "Familie", "Student"},
            {"بھائی", "Bruder", "Schwester", "Mutter", "Vater", "Bruder"},
            {"شام", "Morgen", "Abend", "Tag", "Nacht", "Abend"},
            {"چاند", "Sonne", "Mond", "Stern", "Himmel", "Mond"},
            {"پھول", "Baum", "Blume", "Vogel", "Hund", "Blume"},
            {"گاڑی", "Auto", "Straße", "Stadt", "Land", "Auto"},
            {"ہفتہ", "Jahr", "Monat", "Woche", "Stunde", "Woche"},
            {"چھوٹا", "Groß", "Klein", "Gut", "Schlecht", "Klein"},
            {"ٹھنڈا", "Heiß", "Kalt", "Gut", "Schlecht", "Kalt"}
        };

        for (String[] q : quiz9Questions) {
            values.clear();
            values.put(COL_QUESTION, q[0]);
            values.put(COL_OPTION_A, q[1]);
            values.put(COL_OPTION_B, q[2]);
            values.put(COL_OPTION_C, q[3]);
            values.put(COL_OPTION_D, q[4]);
            values.put(COL_CORRECT_OPTION, q[5]);
            values.put(COL_QUIZ_NATIVE_LANGUAGE, "Urdu");
            values.put(COL_QUIZ_TARGET_LANGUAGE, "German");
            values.put(COL_QUIZ_NAME, "Advanced Words Quiz 3");
            values.put(COL_QUIZ_LANGUAGE_PAIR, "Urdu-German");
            db.insert(TABLE_QUIZ, null, values);
        }
    }

    // Profile methods
    public long insertProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, profile.getUsername());
        values.put(COL_PASSWORD, profile.getPassword());
        values.put(COL_NATIVE_LANGUAGE, profile.getNativeLanguage());
        values.put(COL_TARGET_LANGUAGE, profile.getTargetLanguage());
        long id = db.insert(TABLE_PROFILES, null, values);
        db.close();
        return id;
    }

    public Profile getProfile(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.query(TABLE_PROFILES,
                new String[]{COL_PROFILE_ID, COL_USERNAME, COL_PASSWORD, COL_NATIVE_LANGUAGE, COL_TARGET_LANGUAGE},
                COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        Profile profile = null;
        if (cursor != null && cursor.moveToFirst()) {
            profile = new Profile(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            cursor.close();
        }
        db.close();
        return profile;
    }

    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PROFILES, null);
        if (cursor.moveToFirst()) {
            do {
                Profile profile = new Profile(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                profiles.add(profile);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return profiles;
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.query(TABLE_PROFILES,
                new String[]{COL_PROFILE_ID},
                COL_USERNAME + "=?",
                new String[]{username}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public void deleteProfile(int profileId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROFILES, COL_PROFILE_ID + "=?", new String[]{String.valueOf(profileId)});
        db.delete(TABLE_PROGRESS, COL_PROGRESS_PROFILE_ID + "=?", new String[]{String.valueOf(profileId)});
        db.close();
    }

    // Vocabulary methods
    public List<Vocabulary> getVocabularyByLanguagePair(String languagePair) {
        List<Vocabulary> vocabList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.query(TABLE_VOCABULARY,
                new String[]{COL_VOCAB_ID, COL_NATIVE_WORD, COL_TARGET_WORD, COL_LANGUAGE_PAIR},
                COL_LANGUAGE_PAIR + "=?",
                new String[]{languagePair}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Vocabulary vocab = new Vocabulary(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                vocabList.add(vocab);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return vocabList;
    }

    // Grammar methods
    public List<Grammar> getGrammarByLanguage(String language) {
        List<Grammar> grammarList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.query(TABLE_GRAMMAR,
                new String[]{COL_GRAMMAR_ID, COL_TOPIC, COL_CONTENT, COL_LANGUAGE},
                COL_LANGUAGE + "=?",
                new String[]{language}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Grammar grammar = new Grammar(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                grammarList.add(grammar);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return grammarList;
    }

    // Quiz methods
    public List<QuizQuestion> getQuizQuestions(String quizName, String languagePair) {
        List<QuizQuestion> questions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.query(TABLE_QUIZ,
                null,
                COL_QUIZ_NAME + "=? AND " + COL_QUIZ_LANGUAGE_PAIR + "=?",
                new String[]{quizName, languagePair}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                QuizQuestion question = new QuizQuestion(
                        cursor.getString(1), // question
                        cursor.getString(2), // optionA
                        cursor.getString(3), // optionB
                        cursor.getString(4), // optionC
                        cursor.getString(5), // optionD
                        cursor.getString(6), // correctOption
                        cursor.getString(7), // nativeLanguage
                        cursor.getString(8), // targetLanguage
                        cursor.getString(9), // quizName
                        cursor.getString(10) // languagePair
                );
                question.setId(cursor.getInt(0));
                questions.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return questions;
    }

    public List<String> getQuizNames(String languagePair) {
        List<String> quizNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery(
                "SELECT DISTINCT " + COL_QUIZ_NAME + " FROM " + TABLE_QUIZ + " WHERE " + COL_QUIZ_LANGUAGE_PAIR + "=?",
                new String[]{languagePair});
        if (cursor.moveToFirst()) {
            do {
                quizNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return quizNames;
    }

    // Progress methods
    public long insertProgress(Progress progress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PROGRESS_QUIZ_ID, progress.getQuizId());
        values.put(COL_PROGRESS_SCORE, progress.getScore());
        values.put(COL_PROGRESS_DATE, progress.getDate());
        values.put(COL_PROGRESS_LANGUAGE, progress.getLanguage());
        values.put(COL_PROGRESS_PROFILE_ID, progress.getProfileId());
        values.put(COL_PROGRESS_QUIZ_NAME, progress.getQuizName());
        values.put(COL_PROGRESS_TOTAL_QUESTIONS, progress.getTotalQuestions());
        long id = db.insert(TABLE_PROGRESS, null, values);
        db.close();
        return id;
    }

    public List<Progress> getProgressByProfile(int profileId) {
        List<Progress> progressList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.query(TABLE_PROGRESS,
                null,
                COL_PROGRESS_PROFILE_ID + "=?",
                new String[]{String.valueOf(profileId)}, null, null, COL_PROGRESS_DATE + " DESC");
        if (cursor.moveToFirst()) {
            do {
                Progress progress = new Progress(
                        cursor.getInt(1), // quizId
                        cursor.getInt(2), // score
                        cursor.getString(3), // date
                        cursor.getString(4), // language
                        cursor.getInt(5), // profileId
                        cursor.getString(6), // quizName
                        cursor.getInt(7) // totalQuestions
                );
                progress.setId(cursor.getInt(0));
                progressList.add(progress);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return progressList;
    }
}

