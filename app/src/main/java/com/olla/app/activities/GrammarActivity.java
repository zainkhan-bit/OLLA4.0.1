package com.olla.app.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.olla.app.adapters.GrammarAdapter;
import com.olla.app.database.DatabaseHelper;
import com.olla.app.models.Grammar;
import java.util.List;

public class GrammarActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private GrammarAdapter adapter;
    private DatabaseHelper dbHelper;
    private String targetLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.olla.app.R.layout.activity_grammar);

        targetLanguage = getIntent().getStringExtra("target_language");
        dbHelper = new DatabaseHelper(this);
        initViews();
        loadGrammar();
    }

    private void initViews() {
        recyclerView = findViewById(com.olla.app.R.id.recyclerViewGrammar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadGrammar() {
        List<Grammar> grammarList = dbHelper.getGrammarByLanguage(targetLanguage);
        adapter = new GrammarAdapter(grammarList);
        recyclerView.setAdapter(adapter);
    }
}

