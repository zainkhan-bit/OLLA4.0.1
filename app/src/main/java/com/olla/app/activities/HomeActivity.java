package com.olla.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.olla.app.database.DatabaseHelper;
import com.olla.app.models.Profile;

public class HomeActivity extends BaseActivity {
    private TextView tvWelcome;
    private Button btnVocabulary, btnGrammar, btnQuiz, btnProgress, btnProfile;
    private DatabaseHelper dbHelper;
    private int profileId;
    private String nativeLanguage, targetLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.olla.app.R.layout.activity_home);

        profileId = getIntent().getIntExtra("profile_id", -1);
        nativeLanguage = getIntent().getStringExtra("native_language");
        targetLanguage = getIntent().getStringExtra("target_language");

        dbHelper = new DatabaseHelper(this);
        initViews();
        setupListeners();
        updateWelcomeMessage();
    }

    private void initViews() {
        tvWelcome = findViewById(com.olla.app.R.id.tvWelcome);
        btnVocabulary = findViewById(com.olla.app.R.id.btnVocabulary);
        btnGrammar = findViewById(com.olla.app.R.id.btnGrammar);
        btnQuiz = findViewById(com.olla.app.R.id.btnQuiz);
        btnProgress = findViewById(com.olla.app.R.id.btnProgress);
        btnProfile = findViewById(com.olla.app.R.id.btnProfile);
    }

    private void setupListeners() {
        btnVocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, VocabularyActivity.class);
                intent.putExtra("profile_id", profileId);
                intent.putExtra("native_language", nativeLanguage);
                intent.putExtra("target_language", targetLanguage);
                startActivity(intent);
            }
        });

        btnGrammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, GrammarActivity.class);
                intent.putExtra("profile_id", profileId);
                intent.putExtra("native_language", nativeLanguage);
                intent.putExtra("target_language", targetLanguage);
                startActivity(intent);
            }
        });

        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
                intent.putExtra("profile_id", profileId);
                intent.putExtra("native_language", nativeLanguage);
                intent.putExtra("target_language", targetLanguage);
                startActivity(intent);
            }
        });

        btnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProgressActivity.class);
                intent.putExtra("profile_id", profileId);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("native_language", nativeLanguage);
                intent.putExtra("target_language", targetLanguage);
                startActivity(intent);
            }
        });
    }

    private void updateWelcomeMessage() {
        if (profileId != -1) {
            java.util.List<Profile> profiles = dbHelper.getAllProfiles();
            for (Profile p : profiles) {
                if (p.getId() == profileId) {
                    tvWelcome.setText("Welcome, " + p.getUsername() + "!\nLearning " + targetLanguage);
                    break;
                }
            }
        }
    }
}

