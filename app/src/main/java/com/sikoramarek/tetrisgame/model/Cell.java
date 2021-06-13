package com.sikoramarek.tetrisgame.model;

import android.graphics.Point;

import com.sikoramarek.tetrisgame.view.BlockColors;

public class Cell {

    private Point point;
    private BlockColors color;

    public Cell(int x, int y, BlockColors color){
        point = new Point();
        point.set(x,y);
        this.color = color;
    }

    public BlockColors getColor() {
        return color;
    }

    public void setColor(BlockColors color) {
        this.color = color;
    }

    public Point getPoint(){
        return point;
    }

}
