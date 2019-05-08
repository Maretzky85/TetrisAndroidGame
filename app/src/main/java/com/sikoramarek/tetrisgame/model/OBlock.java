package com.sikoramarek.tetrisgame.model;

import java.util.ArrayList;

public class OBlock implements Block {

    private int x, y;
    private  ArrayList<int[]> positions;
    private int[] xTransforms;
    private int[] yTransforms;
    private int currentTransform;

    public OBlock(int x, int y){
        this.x = x;
        this.y = y;

        currentTransform = 0;

        xTransforms = new int[]{-1,-1,0,0};

        yTransforms = new int[]{0,-1,0,-1};

        positions = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            positions.add(i, null);
        }
        positions.set(0, null);
    }

    @Override
    public ArrayList<int[]> getPositions() {
        for (int i = 0; i < xTransforms.length; i++) {
            int[] position = new int[]{x + xTransforms[i], y + yTransforms[i]};
            positions.set(i, position);
        }
        return positions;
    }

    @Override
    public void update() {
        y += 1;
    }

    @Override
    public void transform() {
        //ignored
    }

    @Override
    public void moveLeft() {
        x -= 1;
    }

    @Override
    public void moveRight() {
        x += 1;
    }
}
