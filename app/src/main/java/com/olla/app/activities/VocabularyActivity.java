package com.olla.app.activities;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.olla.app.adapters.VocabularyAdapter;
import com.olla.app.database.DatabaseHelper;
import com.olla.app.models.Vocabulary;
import java.util.List;
import java.util.Locale;

public class VocabularyActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private VocabularyAdapter adapter;
    private DatabaseHelper dbHelper;
    private String languagePair;
    private TextToSpeech tts;
    private Button btnSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.olla.app.R.layout.activity_vocabulary);

        String nativeLanguage = getIntent().getStringExtra("native_language");
        String targetLanguage = getIntent().getStringExtra("target_language");
        languagePair = nativeLanguage + "-" + targetLanguage;

        dbHelper = new DatabaseHelper(this);
        initViews();
        setupTTS();
        loadVocabulary();
    }

    private void initViews() {
        recyclerView = findViewById(com.olla.app.R.id.recyclerViewVocabulary);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupTTS() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    String targetLanguage = getIntent().getStringExtra("target_language");
                    Locale locale = getLocaleForLanguage(targetLanguage);
                    int result = tts.setLanguage(locale);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        tts.setLanguage(Locale.US);
                    }
                }
            }
        });
    }

    private Locale getLocaleForLanguage(String language) {
        switch (language) {
            case "German":
                return Locale.GERMAN;
            case "Urdu":
                return new Locale("ur");
            default:
                return Locale.US;
        }
    }

    private void loadVocabulary() {
        List<Vocabulary> vocabList = dbHelper.getVocabularyByLanguagePair(languagePair);
        adapter = new VocabularyAdapter(vocabList, tts);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}

