package com.sikoramarek.tetrisgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.sikoramarek.tetrisgame.Common.SharedResources;

public class MainActivity extends Activity {

    Button btnStart;
    TextView tvHighestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        tvHighestScore = findViewById(R.id.tvHighestScore);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedResources sharedResources = new SharedResources(sharedPref);

        int score = sharedPref.getInt("HighestScore", 0);
        if (score > 0){
            tvHighestScore.setText(getString(R.string.hscore)+sharedPref.getInt("HighestScore", 0));
            tvHighestScore.setVisibility(View.VISIBLE);
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.sikoramarek.tetrisgame.GameActivity.class);
                startActivity(intent);
            }
        });

    }
}
