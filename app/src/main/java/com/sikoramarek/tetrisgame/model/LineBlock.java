package com.sikoramarek.tetrisgame.model;

import java.util.ArrayList;

public class LineBlock implements Block{

    private int x, y;
    private  ArrayList<int[]> positions;
    private ArrayList<int[]> xTransfrorms;
    private ArrayList<int[]> yTransforms;
    private int currentTransform;

    public LineBlock(int x, int y){
        this.x = x;
        this.y = y;
        currentTransform = 1;

        xTransfrorms = new ArrayList<>(2);
        xTransfrorms.add(new int[]{-2,-1,0,1});
        xTransfrorms.add(new int[]{0,0,0,0});

        yTransforms = new ArrayList<>(2);
        yTransforms.add(new int[]{0,0,0,0});
        yTransforms.add(new int[]{-2,-1,0,1});

        positions = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            positions.add(i, null);
        }
        positions.set(0, null);
    }

    @Override
    public ArrayList<int[]> getPositions() {
        int[] xTransform = xTransfrorms.get(currentTransform);
        int[] yTransform = yTransforms.get(currentTransform);
        for (int i = 0; i < xTransform.length; i++) {
            int[] position = new int[]{x + xTransform[i], y + yTransform[i]};
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
        if (currentTransform < xTransfrorms.size()-1){
            currentTransform++;
        }else {
            currentTransform = 0;
        }
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
