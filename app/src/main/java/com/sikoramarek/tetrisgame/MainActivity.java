package com.sikoramarek.tetrisgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.sikoramarek.tetrisgame.Controller.GameController;
import com.sikoramarek.tetrisgame.view.GameView;

public class MainActivity extends Activity {

    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        GameView gameView = new GameView(this);
        gameController = new GameController(gameView);
        setContentView(gameView);
    }
}
