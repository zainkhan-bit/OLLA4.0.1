package com.olla.app.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;
import com.olla.app.database.DatabaseHelper;
import com.olla.app.models.Profile;
import java.util.List;

public class ProfileActivity extends BaseActivity {
    private EditText etUsername, etPassword;
    private Button btnCreateProfile, btnLogin, btnSwitchProfile, btnDeleteProfile;
    private TextView tvCurrentProfile;
    private Switch switchTheme;
    private DatabaseHelper dbHelper;
    private Profile currentProfile;
    private String nativeLanguage, targetLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.olla.app.R.layout.activity_profile);

        nativeLanguage = getIntent().getStringExtra("native_language");
        targetLanguage = getIntent().getStringExtra("target_language");

        dbHelper = new DatabaseHelper(this);
        initViews();
        setupListeners();
        loadCurrentProfile();
        updateUI();
    }

    private void initViews() {
        etUsername = findViewById(com.olla.app.R.id.etUsername);
        etPassword = findViewById(com.olla.app.R.id.etPassword);
        btnCreateProfile = findViewById(com.olla.app.R.id.btnCreateProfile);
        btnLogin = findViewById(com.olla.app.R.id.btnLogin);
        btnSwitchProfile = findViewById(com.olla.app.R.id.btnSwitchProfile);
        btnDeleteProfile = findViewById(com.olla.app.R.id.btnDeleteProfile);
        tvCurrentProfile = findViewById(com.olla.app.R.id.tvCurrentProfile);
        switchTheme = findViewById(com.olla.app.R.id.switchTheme);
        switchTheme.setChecked(isDarkMode());
    }

    private void setupListeners() {
        btnCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProfile();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProfile();
            }
        });

        btnSwitchProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfileSelection();
            }
        });

        btnDeleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCurrentProfile();
            }
        });

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setThemeMode(isChecked);
            recreate();
        });
    }

    private void createProfile() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidUsername(username)) {
            Toast.makeText(this, "Username must be English letters (4 or more)", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "Password must be exactly 4 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.isUsernameExists(username)) {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        Profile profile = new Profile(username, password, nativeLanguage, targetLanguage);
        long id = dbHelper.insertProfile(profile);
        if (id > 0) {
            Toast.makeText(this, "Profile created successfully", Toast.LENGTH_SHORT).show();
            etUsername.setText("");
            etPassword.setText("");
            loadCurrentProfile();
        } else {
            Toast.makeText(this, "Failed to create profile", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginProfile() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidUsername(username)) {
            Toast.makeText(this, "Username must be English letters (4 or more)", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "Password must be exactly 4 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        Profile profile = dbHelper.getProfile(username, password);
        if (profile != null) {
            currentProfile = profile;
            saveCurrentProfile(profile.getId());
            updateUI();
            navigateToHome();
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void showProfileSelection() {
        List<Profile> profiles = dbHelper.getAllProfiles();
        if (profiles.isEmpty()) {
            Toast.makeText(this, "No profiles found", Toast.LENGTH_SHORT).show();
            return;
        }

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Select Profile");
        String[] usernames = new String[profiles.size()];
        for (int i = 0; i < profiles.size(); i++) {
            usernames[i] = profiles.get(i).getUsername();
        }
        builder.setItems(usernames, (dialog, which) -> {
            Profile selected = profiles.get(which);
            android.app.AlertDialog.Builder passwordDialog = new android.app.AlertDialog.Builder(this);
            final EditText passwordInput = new EditText(this);
            passwordInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            passwordInput.setHint("Enter 4-digit password");
            passwordDialog.setTitle("Enter Password");
            passwordDialog.setView(passwordInput);
            passwordDialog.setPositiveButton("OK", (d, w) -> {
                String pwd = passwordInput.getText().toString();
                if (pwd.equals(selected.getPassword())) {
                    currentProfile = selected;
                    saveCurrentProfile(selected.getId());
                    updateUI();
                    navigateToHome();
                } else {
                    Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
                }
            });
            passwordDialog.setNegativeButton("Cancel", null);
            passwordDialog.show();
        });
        builder.show();
    }

    private void deleteCurrentProfile() {
        if (currentProfile == null) {
            Toast.makeText(this, "No profile selected", Toast.LENGTH_SHORT).show();
            return;
        }

        new android.app.AlertDialog.Builder(this)
                .setTitle("Delete Profile")
                .setMessage("Are you sure you want to delete profile: " + currentProfile.getUsername() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    dbHelper.deleteProfile(currentProfile.getId());
                    currentProfile = null;
                    clearCurrentProfile();
                    updateUI();
                    Toast.makeText(this, "Profile deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void loadCurrentProfile() {
        SharedPreferences prefs = getSharedPreferences("OLLA_PREFS", MODE_PRIVATE);
        int profileId = prefs.getInt("current_profile_id", -1);
        if (profileId != -1) {
            List<Profile> profiles = dbHelper.getAllProfiles();
            for (Profile p : profiles) {
                if (p.getId() == profileId) {
                    currentProfile = p;
                    break;
                }
            }
        }
    }

    private void saveCurrentProfile(int profileId) {
        SharedPreferences prefs = getSharedPreferences("OLLA_PREFS", MODE_PRIVATE);
        prefs.edit().putInt("current_profile_id", profileId).apply();
    }

    private void clearCurrentProfile() {
        SharedPreferences prefs = getSharedPreferences("OLLA_PREFS", MODE_PRIVATE);
        prefs.edit().remove("current_profile_id").apply();
    }

    private void updateUI() {
        if (currentProfile != null) {
            tvCurrentProfile.setText("Current Profile: " + currentProfile.getUsername());
            btnLogin.setEnabled(false);
            btnCreateProfile.setEnabled(false);
        } else {
            tvCurrentProfile.setText("No profile selected");
            btnLogin.setEnabled(true);
            btnCreateProfile.setEnabled(true);
        }
    }

    private void navigateToHome() {
        if (currentProfile != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("profile_id", currentProfile.getId());
            intent.putExtra("native_language", currentProfile.getNativeLanguage());
            intent.putExtra("target_language", currentProfile.getTargetLanguage());
            startActivity(intent);
            finish();
        }
    }

    private boolean isValidUsername(String username) {
        return username.matches("^[A-Za-z]{4,}$");
    }

    private boolean isValidPassword(String password) {
        return password.matches("\\d{4}");
    }
}

