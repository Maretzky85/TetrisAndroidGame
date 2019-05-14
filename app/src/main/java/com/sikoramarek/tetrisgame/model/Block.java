package com.sikoramarek.tetrisgame.model;

import java.util.ArrayList;

public interface Block {

    public ArrayList<int[]> getPositions();

    public void update();

    public void transform();

    public void moveLeft();

    public void moveRight();

    public ArrayList<int[]> nextTransformPositions();


}
