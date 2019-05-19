package com.sikoramarek.tetrisgame.model;

import android.graphics.Point;
import android.os.Bundle;

public interface Block {

    public Cell[] getCells();

    public void update();

    public void transform();

    public void moveLeft();

    public void moveRight();

    public Point[] nextTransformPositions();

    public void saveState(Bundle outState);

    public Block loadStateFrom(Bundle savedStateBundle);
}
