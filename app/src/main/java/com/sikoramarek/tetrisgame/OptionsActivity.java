package com.sikoramarek.tetrisgame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sikoramarek.tetrisgame.common.GameOptions;

public class OptionsActivity extends Activity {
    private GameOptions gameOptions;
    TextView tvRotation;
    TextView tvGameOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideUi();
        instantiateVariables();
        setOptionsTexts();
        setListeners();
    }

    private void setListeners() {
        tvRotation.setOnClickListener(v -> {
            gameOptions.changeBlockRotation();
            tvRotation.setText(gameOptions.getCurrentBlockRotationLabel());
        });

        tvGameOrientation.setOnClickListener(v -> {
            gameOptions.changeGameOrientation();
            tvGameOrientation.setText(gameOptions.getCurrentGameOrientationLabel());

        });
    }

    private void setOptionsTexts() {
        tvRotation.setText(gameOptions.getCurrentBlockRotationLabel());
        tvGameOrientation.setText(gameOptions.getCurrentGameOrientationLabel());
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void hideUi() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_options);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void instantiateVariables() {
        gameOptions = new GameOptions();
        tvRotation = findViewById(R.id.tvRotation);
        tvGameOrientation = findViewById(R.id.tvGameOrientation);
    }
}