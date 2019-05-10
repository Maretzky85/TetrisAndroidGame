package com.sikoramarek.tetrisgame.Controller;

import com.sikoramarek.tetrisgame.view.GameView;

public class InputHandler {

    private static GameController gameController;
    private static GameView gameView;

    private InputHandler(){
    }

    public static void attachView(GameView gv){
        gameView = gv;
    }

    public static void attachController(GameController gc){
        gameController = gc;
    }

    public static void move(Inputs input) {
        gameController.move(input);
    }
}
