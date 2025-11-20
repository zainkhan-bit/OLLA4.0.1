package com.olla.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class SplashActivity extends BaseActivity {
    private static final int SPLASH_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.olla.app.R.layout.activity_splash);

        TextView appTitle = findViewById(com.olla.app.R.id.tvAppTitle);
        TextView appSubtitle = findViewById(com.olla.app.R.id.tvAppSubtitle);

        // Fade-in animation
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(1500);
        fadeIn.setFillAfter(true);

        appTitle.startAnimation(fadeIn);
        appSubtitle.startAnimation(fadeIn);

        // Navigate to LanguageSelectionActivity after delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LanguageSelectionActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DURATION);
    }
}

