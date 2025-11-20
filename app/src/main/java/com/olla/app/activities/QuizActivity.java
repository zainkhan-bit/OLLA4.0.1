package com.olla.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.olla.app.adapters.QuizAdapter;
import com.olla.app.database.DatabaseHelper;
import com.olla.app.models.QuizQuestion;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private QuizAdapter adapter;
    private DatabaseHelper dbHelper;
    private String languagePair;
    private List<String> quizNames;
    private Button btnStartQuiz;
    private TextView tvSelectQuiz;
    private String selectedQuizName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.olla.app.R.layout.activity_quiz);

        String nativeLanguage = getIntent().getStringExtra("native_language");
        String targetLanguage = getIntent().getStringExtra("target_language");
        languagePair = nativeLanguage + "-" + targetLanguage;

        dbHelper = new DatabaseHelper(this);
        initViews();
        loadQuizNames();
    }

    private void initViews() {
        recyclerView = findViewById(com.olla.app.R.id.recyclerViewQuiz);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvSelectQuiz = findViewById(com.olla.app.R.id.tvSelectQuiz);
        btnStartQuiz = findViewById(com.olla.app.R.id.btnStartQuiz);
        btnStartQuiz.setEnabled(false);

        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedQuizName.isEmpty()) {
                    startQuiz();
                }
            }
        });
    }

    private void loadQuizNames() {
        quizNames = dbHelper.getQuizNames(languagePair);
        if (quizNames.isEmpty()) {
            tvSelectQuiz.setText("No quizzes available for this language pair");
            return;
        }
        adapter = new QuizAdapter(quizNames, new QuizAdapter.OnQuizSelectedListener() {
            @Override
            public void onQuizSelected(String quizName) {
                selectedQuizName = quizName;
                btnStartQuiz.setEnabled(true);
                btnStartQuiz.setText("Start: " + quizName);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void startQuiz() {
        Intent intent = new Intent(this, QuizResultActivity.class);
        intent.putExtra("quiz_name", selectedQuizName);
        intent.putExtra("language_pair", languagePair);
        intent.putExtra("profile_id", getIntent().getIntExtra("profile_id", -1));
        startActivity(intent);
    }
}

