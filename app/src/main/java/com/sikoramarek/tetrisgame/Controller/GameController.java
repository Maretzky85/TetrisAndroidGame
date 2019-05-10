package com.sikoramarek.tetrisgame.Controller;

import com.sikoramarek.tetrisgame.model.PlayField;
import com.sikoramarek.tetrisgame.view.GameView;

public class GameController {

    private PlayField playField;
    private final GameView gameView;

    private long updateTime;
    private int speed = 800;

    private boolean running;

    public GameController(GameView gameView){
        this.gameView = gameView;
        this.playField = new PlayField();
        InputHandler.attachController(this);
        running = true;
        updateTime = System.currentTimeMillis();
        run();
    }

    private void run() {
        while (running){
            update();
            if (System.currentTimeMillis() - updateTime > speed){
                playField.update();
                updateTime = System.currentTimeMillis();
            }else {
                try {
                    Thread.sleep(System.currentTimeMillis() - updateTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update(){
        gameView.updateView(playField.getActiveBlock(), playField.getInactiveCells());
    }

    public void move(Inputs input) {
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
}
