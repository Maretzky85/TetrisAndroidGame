package com.sikoramarek.tetrisgame.view;

public enum BlockColors {
    BLUE(0), GREEN(1), ORANGE(2), RED(3), VIOLET(4), YELLOW(5);
    private int value;
    BlockColors(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
