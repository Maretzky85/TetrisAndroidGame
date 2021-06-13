package com.sikoramarek.tetrisgame.controller;

import android.os.Bundle;
import android.view.SurfaceHolder;

import com.sikoramarek.tetrisgame.model.PlayField;
import com.sikoramarek.tetrisgame.view.GameView;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.sikoramarek.tetrisgame.controller.InputHandlerHelper.pause;
import static com.sikoramarek.tetrisgame.controller.InputHandlerHelper.speed;

public class GameController extends Thread{

    private final PlayField playField;
    private final GameView gameView;

    private final BlockingQueue<Inputs> cachedInputs;

    private long updateTime;

    private boolean running;
    private final SurfaceHolder surfaceHolder;

    private int lastSpeedUpdateScore = 0;

    public GameController(final GameView gameView){

        this.gameView = gameView;
        this.surfaceHolder = gameView.getHolder();
        this.playField = new PlayField();
        this.cachedInputs = new ArrayBlockingQueue<>(5);
        speed = 800;

        InputHandlerHelper.attachController(this);
        running = true;

        updateTime = System.currentTimeMillis();

        playField.setRefreshView(new Runnable() {
            @Override
            public void run() {
                boolean drawed = false;
                while (!drawed) {
                    drawed = gameView.updateView(
                            playField.getActiveBlock(),
                            playField.getInactiveCells());
                }
            }
        });
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

            if (System.currentTimeMillis() - updateTime > speed) {
                if (pause) {
                    pause = false;
                } else {
                    this.playField.update();
                    updateTime = System.currentTimeMillis();
                }
            }
            synchronized (surfaceHolder) {
                while (!cachedInputs.isEmpty()){
                    moveExecute(cachedInputs.remove());
                }
                boolean drawed = false;
                while (!drawed) {
                    drawed = this.gameView.updateView(
                            playField.getActiveBlock(),
                            playField.getInactiveCells());
                }
            }
        }
    }

    private void adjustSpeedViaScore(int score) {
        if (speed < 50 || score == 0) {
            return;
        }
        if (score - lastSpeedUpdateScore > 1000){
            if (speed > 200){
                speed -= 100;
            } else {
                speed -= 10;
            }
            lastSpeedUpdateScore = score;
        }
    }


    private void moveExecute(Inputs input) {
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

    void move(Inputs input){
        if (!cachedInputs.contains(input)){
            cachedInputs.add(input);
        }
    }

    public void saveStateTo(Bundle outState) {
        playField.saveStateTo(outState);
    }

    public void loadStateFrom(Bundle savedInstanceState) {
        playField.loadStateFrom(savedInstanceState);
    }
}
