package com.sikoramarek.tetrisgame.Controller;

public class InputHandler {

    public static boolean pause = false;

    private static GameController gameController;

    private InputHandler(){
    }

    public static int currentAction = -1;

    public static int speed;

    static void attachController(GameController gc){
        gameController = gc;
    }

    public static void move(Inputs input) {
        if (gameController != null){
            gameController.move(input);
        }
    }
}
