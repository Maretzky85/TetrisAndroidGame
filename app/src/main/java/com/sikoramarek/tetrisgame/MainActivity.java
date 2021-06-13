package com.sikoramarek.tetrisgame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button btnStart;
    private Button btnOptions;
    private TextView tvHighestScore;
    private SharedPreferences sharedPreferences;
    public static Context contextOfApplication;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contextOfApplication = getApplicationContext();
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnOptions = findViewById(R.id.btnOptions);
        tvHighestScore = findViewById(R.id.tvHighestScore);
        sharedPreferences = getApplicationContext().getSharedPreferences("Score", MODE_PRIVATE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        updateScore();

        btnStart.setOnClickListener(v -> {
            Intent game = new Intent(MainActivity.this, GameActivity.class);
            startActivity(game);
        });

        btnOptions.setOnClickListener(v -> {
            Intent game = new Intent(MainActivity.this, OptionsActivity.class);
            startActivity(game);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateScore();
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    private void updateScore() {
        String scoreText = checkHighScoreAndReturnString();
        tvHighestScore.setText(scoreText);
        tvHighestScore.setVisibility(View.VISIBLE);
    }

    private String checkHighScoreAndReturnString() {
        int score = sharedPreferences.getInt("HighestScore", 0);
        if (score > 0) {
            return getString(R.string.hscore) + score;
        } else {
            return getString(R.string.NoHighScore);
        }
    }
}
