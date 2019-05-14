package com.sikoramarek.tetrisgame.Controller;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.sikoramarek.tetrisgame.model.PlayField;
import com.sikoramarek.tetrisgame.view.GameView;

public class GameController extends Thread{

    private PlayField playField;
    private final GameView gameView;

    private long updateTime;
    private int speed = 800;

    private boolean running;
    private SurfaceHolder surfaceHolder;

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
            Canvas canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    int score = playField.getScore();
                    if (score > 2000){
                        speed = 400;
                    }
                    if (score > 10000){
                        speed = 250;
                    }
                    this.gameView.setScore(score);
                    if (playField.isNotRunning()){
                        gameView.endGame();
                    }
                    this.gameView.updateView(playField.getActiveBlock(), playField.getInactiveCells(), canvas);
                }
            }catch (Exception e){
                //TODO
//                e.printStackTrace();
            }
            finally {
                try {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            if (System.currentTimeMillis() - updateTime > speed){
                this.playField.update();
                updateTime = System.currentTimeMillis();
            }
            if (playField.isNotRunning()){
                this.running = false;
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

    public int getScore() {
        return playField.getScore();
    }
}
