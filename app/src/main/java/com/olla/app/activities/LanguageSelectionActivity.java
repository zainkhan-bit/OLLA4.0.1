package com.olla.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class LanguageSelectionActivity extends BaseActivity {
    private Spinner spinnerNative, spinnerTarget;
    private Button btnContinue;
    private String selectedNative = "";
    private String selectedTarget = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.olla.app.R.layout.activity_language_selection);

        spinnerNative = findViewById(com.olla.app.R.id.spinnerNative);
        spinnerTarget = findViewById(com.olla.app.R.id.spinnerTarget);
        btnContinue = findViewById(com.olla.app.R.id.btnContinue);

        String[] languages = {"English", "Urdu", "German"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNative.setAdapter(adapter);
        spinnerTarget.setAdapter(adapter);

        spinnerNative.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNative = languages[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerTarget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTarget = languages[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedNative.isEmpty() || selectedTarget.isEmpty()) {
                    Toast.makeText(LanguageSelectionActivity.this, "Please select both languages", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedNative.equals(selectedTarget)) {
                    Toast.makeText(LanguageSelectionActivity.this, "Native and target languages must be different", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if language pair is supported
                String pair = selectedNative + "-" + selectedTarget;
                if (isValidLanguagePair(pair)) {
                    Intent intent = new Intent(LanguageSelectionActivity.this, ProfileActivity.class);
                    intent.putExtra("native_language", selectedNative);
                    intent.putExtra("target_language", selectedTarget);
                    startActivity(intent);
                } else {
                    Toast.makeText(LanguageSelectionActivity.this, "This language pair is not supported", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidLanguagePair(String pair) {
        return pair.equals("English-Urdu") || pair.equals("Urdu-English") ||
               pair.equals("English-German") || pair.equals("German-English") ||
               pair.equals("Urdu-German") || pair.equals("German-Urdu");
    }
}

