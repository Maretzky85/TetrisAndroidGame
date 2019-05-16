package com.sikoramarek.tetrisgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.sikoramarek.tetrisgame.Controller.GameController;
import com.sikoramarek.tetrisgame.view.GameView;

public class GameActivity extends Activity {

    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GameView gameView = new GameView(this);
        setContentView(gameView);
        gameController = new GameController(gameView);

        new Thread(gameController).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameController.stopGame();
        GameActivity.this.finish();
    }
}
