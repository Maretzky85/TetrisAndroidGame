package com.sikoramarek.tetrisgame.model;

import java.util.ArrayList;
import java.util.Random;

public class BlockB implements Block{

    private static Random randomGenerator = new Random();
    private int x, y;
    private  ArrayList<int[]> positions;
    private ArrayList<int[]> xTransforms;
    private ArrayList<int[]> yTransforms;
    private int currentTransform = 0;

    private BlockB(){
    }

    private BlockB setX(int x) {
        this.x = x;
        return this;
    }

    private BlockB setY(int y) {
        this.y = y;
        return this;
    }

    private BlockB setPositions(ArrayList<int[]> positions) {
        this.positions = positions;
        return this;
    }

    private BlockB setXTransforms(ArrayList<int[]> xTransforms) {
        this.xTransforms = xTransforms;
        return this;
    }

    private BlockB setYTransforms(ArrayList<int[]> yTransforms) {
        this.yTransforms = yTransforms;
        return this;
    }

    static Block getRandomBlock(int x, int y){
        int random = randomGenerator.nextInt(7000);
        if (random < 1000){
            return getBlock(x, y, Type.I);
        }
        if (random < 2000){
            return getBlock(x, y, Type.J);
        }
        if (random < 3000){
            return getBlock(x, y, Type.L);
        }
        if (random < 4000){
            return getBlock(x, y, Type.O);
        }
        if (random < 5000){
            return getBlock(x, y, Type.S);
        }
        if (random < 6000){
            return getBlock(x, y, Type.T);
        }
        else {
            return getBlock(x, y, Type.Z);
        }
    }

    private static Block getBlock(int x, int y, Type type){
        BlockB block = new BlockB();
        ArrayList<int[]> xTransforms = new ArrayList<>(1);
        ArrayList<int[]> yTransforms = new ArrayList<>(1);
        ArrayList<int[]> positions = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            positions.add(i, null);
        }
        switch (type) {
            case I:
                xTransforms.add(new int[]{0,0,0,0});
                xTransforms.add(new int[]{-2,-1,0,1});

                yTransforms.add(new int[]{-2,-1,0,1});
                yTransforms.add(new int[]{0,0,0,0});
                break;
            case J:
                xTransforms.add(new int[]{-1,-1,0,1});
                xTransforms.add(new int[]{-1,0,0,0});
                xTransforms.add(new int[]{-1,0,1,1});
                xTransforms.add(new int[]{0,-1,-1,-1});

                yTransforms.add(new int[]{-1,0,0,0});
                yTransforms.add(new int[]{0,0,-1,-2});
                yTransforms.add(new int[]{-1,-1,-1,0});
                yTransforms.add(new int[]{-2,-2,-1,0});
                break;
            case L:
                xTransforms.add(new int[]{-1,0,1,1});
                xTransforms.add(new int[]{0,-1,-1,-1});
                xTransforms.add(new int[]{-1,-1,0,1});
                xTransforms.add(new int[]{0,1,1,1});

                yTransforms.add(new int[]{0,0,0,-1});
                yTransforms.add(new int[]{0,0,-1,-2});
                yTransforms.add(new int[]{0,-1,-1,-1});
                yTransforms.add(new int[]{-2,-2,-1,0});
                break;
            case O:
                xTransforms.add(new int[]{-1,-1,0,0});

                yTransforms.add(new int[]{0,-1,0,-1});
                break;
            case S:
                xTransforms.add(new int[]{-1,0,0,1});
                xTransforms.add(new int[]{0,0,-1,-1});

                yTransforms.add(new int[]{-1,-1,0,0});
                yTransforms.add(new int[]{-1,0,0,1});
                break;
            case T:
                xTransforms.add(new int[]{-1,0,0,1});
                xTransforms.add(new int[]{0,0,0,1});
                xTransforms.add(new int[]{-1,0,0,1});
                xTransforms.add(new int[]{0,0,0,-1});

                yTransforms.add(new int[]{0,-1,0,0});
                yTransforms.add(new int[]{-1,0,1,0});
                yTransforms.add(new int[]{0,1,0,0});
                yTransforms.add(new int[]{-1,0,1,0});
                break;
            case Z:
                xTransforms.add(new int[]{-1,0,0,1});
                xTransforms.add(new int[]{0,0,-1,-1});

                yTransforms.add(new int[]{0,0,-1,-1});
                yTransforms.add(new int[]{1,0,0,-1});
                break;
        }

        return block
                .setY(y)
                .setX(x)
                .setXTransforms(xTransforms)
                .setYTransforms(yTransforms)
                .setPositions(positions)
                .finish();
    }

    private Block finish() {
        return this;
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

    @Override
    public ArrayList<int[]> nextTransformPositions() {
        int tempTransform = currentTransform;
        transform();
        ArrayList<int[]> nextPositions =  getPositions();
        currentTransform = tempTransform;
        return nextPositions;
    }

    private enum Type{
        T, I, J, L, O, S, Z
    }
}
