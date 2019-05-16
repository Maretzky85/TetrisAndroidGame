package com.sikoramarek.tetrisgame.model;

import android.graphics.Point;

public interface Block {

    public Cell[] getCells();

    public void update();

    public void transform();

    public void moveLeft();

    public void moveRight();

    public Point[] nextTransformPositions();


}
