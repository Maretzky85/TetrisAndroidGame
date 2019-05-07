package com.sikoramarek.tetrisgame.model;

import android.graphics.Canvas;

public interface Block {

    public void draw(Canvas canvas);

    public void update();

    public void transform();

    public void moveLeft();

    public void moveRight();


}
