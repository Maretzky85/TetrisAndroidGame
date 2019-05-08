package com.sikoramarek.tetrisgame.model;

import java.util.ArrayList;

public class ZBlock implements Block {

    private int x, y;

    private ArrayList<int[]> positions;
    private ArrayList<int[]> xTransforms;
    private ArrayList<int[]> yTransforms;
    private int currentTransform;


    public ZBlock(int x, int y){
        this.x = x;
        this.y = y;

        currentTransform = 0;

        xTransforms = new ArrayList<>(2);
        xTransforms.add(new int[]{-1,0,0,1});
        xTransforms.add(new int[]{0,0,-1,-1});


        yTransforms = new ArrayList<>(2);
        yTransforms.add(new int[]{0,0,-1,-1});
        yTransforms.add(new int[]{1,0,0,-1});


        positions = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            positions.add(i, null);
        }
        positions.set(0, null);
    }

    @Override
    public ArrayList<int[]> getPositions() {
        int[] xTransform = xTransforms.get(currentTransform);
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
        if (currentTransform < xTransforms.size()-1){
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
