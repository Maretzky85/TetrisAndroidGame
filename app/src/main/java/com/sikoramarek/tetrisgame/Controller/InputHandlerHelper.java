package com.sikoramarek.tetrisgame.controller;

//TODO review if even needed ?
public class InputHandlerHelper {

    public static boolean pause = false;
    public static int currentAction = -1;
    public static int speed;
    private static GameController gameController;

    private InputHandlerHelper() {
    }

    public static void attachController(GameController gc) {
        gameController = gc;
    }

    public static void move(Inputs input) {
        if (gameController != null) {
            gameController.move(input);
        }
    }
}
