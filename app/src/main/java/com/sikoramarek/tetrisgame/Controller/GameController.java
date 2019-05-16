package com.sikoramarek.tetrisgame.Controller;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.sikoramarek.tetrisgame.Common.SharedResources;
import com.sikoramarek.tetrisgame.model.PlayField;
import com.sikoramarek.tetrisgame.view.GameView;

public class GameController extends Thread{

    private PlayField playField;
    private final GameView gameView;

    private long updateTime;
    private int speed = 800;

    private boolean running;
    private final SurfaceHolder surfaceHolder;

    public GameController(GameView gameView){

        this.gameView = gameView;
        this.surfaceHolder = gameView.getHolder();
        this.playField = new PlayField();

        InputHandler.attachController(this);

        running = true;

        updateTime = System.currentTimeMillis();

    }

    @Override
    public void run() {
        while (running){
            int score = playField.getScore();
            if (score > 2000){
                speed = 400;
            }
            if (score > 10000){
                speed = 250;
            }
            this.gameView.setScore(score);
            if (!playField.isRunning()){
                gameView.endGame();
                if (SharedResources.getSharedPreferences().getInt("HighestScore", 0) < score){
                    SharedPreferences.Editor editor = SharedResources.getSharedPreferences().edit();
                    editor.putInt("HighestScore", score);
                    editor.apply();
                }
//                this.running = false;
            }
            Canvas canvas = null;
            int retry = 0;
            while (canvas == null) {
                try {
                    synchronized (surfaceHolder) {
                        retry++;
                        canvas = this.surfaceHolder.lockCanvas();
                        if (canvas != null) {
                            this.gameView.updateView(
                                    playField.getActiveBlock(),
                                    playField.getInactiveCells(), canvas);
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                } catch (IllegalStateException e) {
                    if (retry > 10){
                        running = false;
                        break;
                    }
                }
            }
            if (System.currentTimeMillis() - updateTime > speed){
                this.playField.update();
                updateTime = System.currentTimeMillis();
            }
        }
    }


    void move(Inputs input) {
        switch (input) {
            case CLICK:
                playField.click();
                break;
            case LEFT:
                playField.left();
                break;
            case RIGHT:
                playField.right();
                break;
            case DOWN:
                playField.update();
                break;
        }
    }


    public void stopGame(){
        running = false;
        try {
            this.join();
        } catch (InterruptedException e) {
            Log.d("GameController", e.getMessage());
        }
    }
}
