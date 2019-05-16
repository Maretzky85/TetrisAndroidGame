package com.sikoramarek.tetrisgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button btnStart;
    TextView tvHighestScore;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        tvHighestScore = findViewById(R.id.tvHighestScore);
        sharedPreferences = getApplicationContext().getSharedPreferences("Score", MODE_PRIVATE);

        updateScore();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.sikoramarek.tetrisgame.GameActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateScore();
    }

    private void updateScore(){
        int score = sharedPreferences.getInt("HighestScore", 0);
        if (score > 0){
            String scoreText = getString(R.string.hscore)+sharedPreferences.getInt("HighestScore", 0);
            tvHighestScore.setText(scoreText);
            tvHighestScore.setVisibility(View.VISIBLE);
        }
    }
}
