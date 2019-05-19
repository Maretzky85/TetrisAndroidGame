package com.sikoramarek.tetrisgame.Controller;

import android.os.Bundle;
import android.view.SurfaceHolder;

import com.sikoramarek.tetrisgame.model.PlayField;
import com.sikoramarek.tetrisgame.view.GameView;

import static com.sikoramarek.tetrisgame.Controller.InputHandler.pauseUpdate;
import static com.sikoramarek.tetrisgame.Controller.InputHandler.speed;

public class GameController extends Thread{

    private PlayField playField;
    private final GameView gameView;

    private long updateTime;

    private boolean running;
    private final SurfaceHolder surfaceHolder;

    public GameController(GameView gameView){

        this.gameView = gameView;
        this.surfaceHolder = gameView.getHolder();
        this.playField = new PlayField();
        speed = 800;

        InputHandler.attachController(this);

        running = true;

        updateTime = System.currentTimeMillis();

    }

    @Override
    public void run() {
        while (running){
            int score = playField.getScore();
            adjustSpeedViaScore(score);
            this.gameView.setScore(score);
            if (!playField.isRunning()){
                gameView.endGame();
                this.running = false;
            }
            synchronized (surfaceHolder){
                if (System.currentTimeMillis() - updateTime > speed){
                    if (pauseUpdate){
                        pauseUpdate = false;
                        System.out.println("pause update");
                    }else {
                        this.playField.update();
                        updateTime = System.currentTimeMillis();
                    }
                }
                boolean drawed = false;
                while(!drawed){
                    drawed = this.gameView.updateView(
                            playField.getActiveBlock(),
                            playField.getInactiveCells());
                }
            }

        }
    }

    private void adjustSpeedViaScore(int score) {
        if (score > 10000){
            speed = 200;
        }else if (score > 8000){
            speed = 300;
        }else if (speed > 6000){
            speed = 400;
        }else if (score > 4000){
            speed = 500;
        }else if (score > 2000){
            speed = 600;
        }else if (score > 1000){
            speed = 700;
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

    public void saveStateTo(Bundle outState) {
        playField.saveStateTo(outState);
    }

    public void loadStateFrom(Bundle savedInstanceState) {
        playField.loadStateFrom(savedInstanceState);
    }
}
