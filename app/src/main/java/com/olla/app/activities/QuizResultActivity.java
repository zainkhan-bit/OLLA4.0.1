package com.olla.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.olla.app.database.DatabaseHelper;
import com.olla.app.models.Progress;
import com.olla.app.models.QuizQuestion;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuizResultActivity extends BaseActivity {
    private TextView tvQuestion, tvQuestionNumber;
    private RadioGroup radioGroup;
    private RadioButton rbOptionA, rbOptionB, rbOptionC, rbOptionD;
    private Button btnNext, btnPrevious, btnSubmit;
    private DatabaseHelper dbHelper;
    private List<QuizQuestion> questions;
    private List<String> userAnswers;
    private List<Boolean> answerResults;
    private int currentQuestion = 0;
    private int score = 0;
    private String quizName, languagePair;
    private int profileId;
    private boolean isSubmitted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.olla.app.R.layout.activity_quiz_result);

        quizName = getIntent().getStringExtra("quiz_name");
        languagePair = getIntent().getStringExtra("language_pair");
        profileId = getIntent().getIntExtra("profile_id", -1);

        dbHelper = new DatabaseHelper(this);
        initViews();
        loadQuestions();
        setupListeners();
        displayQuestion(0);
    }

    private void initViews() {
        tvQuestionNumber = findViewById(com.olla.app.R.id.tvQuestionNumber);
        tvQuestion = findViewById(com.olla.app.R.id.tvQuestion);
        radioGroup = findViewById(com.olla.app.R.id.radioGroup);
        rbOptionA = findViewById(com.olla.app.R.id.rbOptionA);
        rbOptionB = findViewById(com.olla.app.R.id.rbOptionB);
        rbOptionC = findViewById(com.olla.app.R.id.rbOptionC);
        rbOptionD = findViewById(com.olla.app.R.id.rbOptionD);
        btnNext = findViewById(com.olla.app.R.id.btnNext);
        btnPrevious = findViewById(com.olla.app.R.id.btnPrevious);
        btnSubmit = findViewById(com.olla.app.R.id.btnSubmit);
    }

    private void loadQuestions() {
        questions = dbHelper.getQuizQuestions(quizName, languagePair);
        userAnswers = new ArrayList<>();
        answerResults = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            userAnswers.add("");
            answerResults.add(false);
        }
    }

    private void setupListeners() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCurrentAnswer();
                if (currentQuestion < questions.size() - 1) {
                    currentQuestion++;
                    displayQuestion(currentQuestion);
                }
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCurrentAnswer();
                if (currentQuestion > 0) {
                    currentQuestion--;
                    displayQuestion(currentQuestion);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSubmitted) {
                    saveCurrentAnswer();
                    submitQuiz();
                }
            }
        });
    }

    private void saveCurrentAnswer() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        String answer = "";
        if (selectedId == com.olla.app.R.id.rbOptionA) {
            answer = rbOptionA.getText().toString();
        } else if (selectedId == com.olla.app.R.id.rbOptionB) {
            answer = rbOptionB.getText().toString();
        } else if (selectedId == com.olla.app.R.id.rbOptionC) {
            answer = rbOptionC.getText().toString();
        } else if (selectedId == com.olla.app.R.id.rbOptionD) {
            answer = rbOptionD.getText().toString();
        }
        userAnswers.set(currentQuestion, answer);
    }

    private void displayQuestion(int index) {
        if (index < 0 || index >= questions.size()) return;

        QuizQuestion question = questions.get(index);
        tvQuestionNumber.setText("Question " + (index + 1) + " of " + questions.size());
        tvQuestion.setText(question.getQuestion());
        rbOptionA.setText(question.getOptionA());
        rbOptionB.setText(question.getOptionB());
        rbOptionC.setText(question.getOptionC());
        rbOptionD.setText(question.getOptionD());

        // Restore previous answer
        String previousAnswer = userAnswers.get(index);
        if (!previousAnswer.isEmpty()) {
            if (previousAnswer.equals(rbOptionA.getText().toString())) {
                radioGroup.check(com.olla.app.R.id.rbOptionA);
            } else if (previousAnswer.equals(rbOptionB.getText().toString())) {
                radioGroup.check(com.olla.app.R.id.rbOptionB);
            } else if (previousAnswer.equals(rbOptionC.getText().toString())) {
                radioGroup.check(com.olla.app.R.id.rbOptionC);
            } else if (previousAnswer.equals(rbOptionD.getText().toString())) {
                radioGroup.check(com.olla.app.R.id.rbOptionD);
            }
        } else {
            radioGroup.clearCheck();
        }

        // Color code if submitted
        if (isSubmitted) {
            colorCodeAnswers(question);
        } else {
            resetAnswerColors();
        }

        btnPrevious.setEnabled(index > 0);
        btnNext.setEnabled(index < questions.size() - 1);
        btnSubmit.setVisibility(index == questions.size() - 1 ? View.VISIBLE : View.GONE);
    }

    private void colorCodeAnswers(QuizQuestion question) {
        String correctAnswer = question.getCorrectOption();
        String userAnswer = userAnswers.get(currentQuestion);

        resetAnswerColors();

        // Highlight correct answer in green
        int greenColor = ContextCompat.getColor(this, android.R.color.holo_green_dark);
        if (correctAnswer.equals(rbOptionA.getText().toString())) {
            rbOptionA.setTextColor(greenColor);
        } else if (correctAnswer.equals(rbOptionB.getText().toString())) {
            rbOptionB.setTextColor(greenColor);
        } else if (correctAnswer.equals(rbOptionC.getText().toString())) {
            rbOptionC.setTextColor(greenColor);
        } else if (correctAnswer.equals(rbOptionD.getText().toString())) {
            rbOptionD.setTextColor(greenColor);
        }

        // Highlight wrong answer in red
        int redColor = ContextCompat.getColor(this, android.R.color.holo_red_dark);
        if (!userAnswer.isEmpty() && !userAnswer.equals(correctAnswer)) {
            if (userAnswer.equals(rbOptionA.getText().toString())) {
                rbOptionA.setTextColor(redColor);
            } else if (userAnswer.equals(rbOptionB.getText().toString())) {
                rbOptionB.setTextColor(redColor);
            } else if (userAnswer.equals(rbOptionC.getText().toString())) {
                rbOptionC.setTextColor(redColor);
            } else if (userAnswer.equals(rbOptionD.getText().toString())) {
                rbOptionD.setTextColor(redColor);
            }
        }
    }

    private void resetAnswerColors() {
        int blackColor = ContextCompat.getColor(this, android.R.color.black);
        rbOptionA.setTextColor(blackColor);
        rbOptionB.setTextColor(blackColor);
        rbOptionC.setTextColor(blackColor);
        rbOptionD.setTextColor(blackColor);
    }

    private void submitQuiz() {
        isSubmitted = true;
        score = 0;

        // Calculate score
        for (int i = 0; i < questions.size(); i++) {
            String userAnswer = userAnswers.get(i);
            String correctAnswer = questions.get(i).getCorrectOption();
            if (userAnswer.equals(correctAnswer)) {
                score++;
                answerResults.set(i, true);
            } else {
                answerResults.set(i, false);
            }
        }

        // Save progress
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String date = sdf.format(new Date());
        Progress progress = new Progress(0, score, date, languagePair, profileId, quizName, questions.size());
        dbHelper.insertProgress(progress);

        // Show results
        showResults();
        displayQuestion(currentQuestion);
    }

    private void showResults() {
        String resultText = "Quiz Completed!\n\nScore: " + score + " / " + questions.size() + "\n";
        resultText += "Percentage: " + (score * 100 / questions.size()) + "%\n\n";
        resultText += "Correct answers are highlighted in GREEN\n";
        resultText += "Wrong answers are highlighted in RED";

        new android.app.AlertDialog.Builder(this)
                .setTitle("Quiz Results")
                .setMessage(resultText)
                .setPositiveButton("OK", null)
                .show();

        btnSubmit.setEnabled(false);
        btnNext.setEnabled(false);
        btnPrevious.setEnabled(false);
        radioGroup.setEnabled(false);
    }
}

