package com.sikoramarek.tetrisgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.sikoramarek.tetrisgame.view.GameView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new GameView(this));
    }
}
