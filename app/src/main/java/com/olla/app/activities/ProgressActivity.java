package com.olla.app.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.olla.app.adapters.ProgressAdapter;
import com.olla.app.database.DatabaseHelper;
import com.olla.app.models.Progress;
import java.util.List;

public class ProgressActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private ProgressAdapter adapter;
    private DatabaseHelper dbHelper;
    private TextView tvStats;
    private int profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.olla.app.R.layout.activity_progress);

        profileId = getIntent().getIntExtra("profile_id", -1);
        dbHelper = new DatabaseHelper(this);
        initViews();
        loadProgress();
    }

    private void initViews() {
        recyclerView = findViewById(com.olla.app.R.id.recyclerViewProgress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvStats = findViewById(com.olla.app.R.id.tvStats);
    }

    private void loadProgress() {
        List<Progress> progressList = dbHelper.getProgressByProfile(profileId);
        adapter = new ProgressAdapter(progressList);
        recyclerView.setAdapter(adapter);

        // Calculate statistics
        if (progressList.isEmpty()) {
            tvStats.setText("No quiz attempts yet");
            return;
        }

        int totalAttempts = progressList.size();
        int totalScore = 0;
        int totalQuestions = 0;
        for (Progress p : progressList) {
            totalScore += p.getScore();
            totalQuestions += p.getTotalQuestions();
        }

        double averageScore = totalQuestions > 0 ? (double) totalScore / totalQuestions * 100 : 0;
        Progress lastAttempt = progressList.get(0);

        String stats = "Total Attempts: " + totalAttempts + "\n";
        stats += "Average Score: " + String.format("%.1f", averageScore) + "%\n";
        stats += "Last Attempt: " + lastAttempt.getDate() + "\n";
        stats += "Last Score: " + lastAttempt.getScore() + " / " + lastAttempt.getTotalQuestions();

        tvStats.setText(stats);
    }
}

