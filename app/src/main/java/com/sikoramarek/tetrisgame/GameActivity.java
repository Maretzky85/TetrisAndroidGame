package com.sikoramarek.tetrisgame;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.sikoramarek.tetrisgame.controller.GameController;
import com.sikoramarek.tetrisgame.common.GameOptions;
import com.sikoramarek.tetrisgame.view.GameView;

public class GameActivity extends Activity {

    GameController gameController;
    private long lastBackPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameOptions gameOptions = new GameOptions();
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(gameOptions.getCurrentGameOrientation());

        hideSystemUI();

        GameView gameView = new GameView(this);
        setContentView(gameView);
        gameController = new GameController(gameView);
        if (savedInstanceState != null) {
            gameController.loadStateFrom(savedInstanceState);
        }
        new Thread(gameController).start();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        gameController.saveStateTo(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        long timeNow = System.currentTimeMillis();
        long timePressed = timeNow - lastBackPressedTime;
        if (timePressed < 500) {
            super.onBackPressed();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.exitPrompt), Toast.LENGTH_LONG).show();
            lastBackPressedTime = System.currentTimeMillis();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
