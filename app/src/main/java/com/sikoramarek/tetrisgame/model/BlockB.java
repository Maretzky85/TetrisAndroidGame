package com.sikoramarek.tetrisgame.model;

import android.graphics.Color;
import android.graphics.Point;

import com.sikoramarek.tetrisgame.view.BlockColors;

import java.util.Random;

public class BlockB implements Block{

    private static Random randomGenerator = new Random();
    private int x, y;
    private Type type;
    private  Cell[] cells;

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

    private BlockB setCells(Cell[] cells) {
        this.cells = cells;
        return this;
    }

    private BlockB setType(Type type){
        this.type = type;
        return this;
    }

    static Block getRandomBlock(int x, int y){
        int random = randomGenerator.nextInt(7000);
        if (random < 1000){
            return getBlock(x, y+2, Type.I);
        }
        if (random < 2000){
            return getBlock(x, y+1, Type.J);
        }
        if (random < 3000){
            return getBlock(x, y+1, Type.L);
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
        Cell[] cells = new Cell[4];

        switch (type) {
            case I:
                cells = new Cell[]{
                        new Cell(x,y-2, BlockColors.RED),
                        new Cell(x,y-1, BlockColors.RED),
                        new Cell(x,y, BlockColors.RED),
                        new Cell(x,y+1, BlockColors.RED)
                };
                break;
            case J:
                cells = new Cell[]{
                        new Cell(x-1,y-1, BlockColors.YELLOW),
                        new Cell(x-1,y, BlockColors.YELLOW),
                        new Cell(x,y, BlockColors.YELLOW),
                        new Cell(x+1,y, BlockColors.YELLOW)
                };
                break;
            case L:
                cells = new Cell[]{
                        new Cell(x-1,y, BlockColors.GREEN),
                        new Cell(x,y, BlockColors.GREEN),
                        new Cell(x+1,y, BlockColors.GREEN),
                        new Cell(x+1,y-1, BlockColors.GREEN)
                };
                break;
            case O:
                cells = new Cell[]{
                        new Cell(x-1,y, BlockColors.BLUE),
                        new Cell(x-1,y-1, BlockColors.BLUE),
                        new Cell(x,y, BlockColors.BLUE),
                        new Cell(x,y-1, BlockColors.BLUE)
                };
                break;
            case S:
                cells = new Cell[]{
                        new Cell(x-1,y-1, BlockColors.ORANGE),
                        new Cell(x,y-1, BlockColors.ORANGE),
                        new Cell(x,y, BlockColors.ORANGE),
                        new Cell(x+1,y, BlockColors.ORANGE)
                };
                break;
            case T:
                cells = new Cell[]{
                        new Cell(x-1,y, BlockColors.VIOLET),
                        new Cell(x,y-1, BlockColors.VIOLET),
                        new Cell(x,y, BlockColors.VIOLET),
                        new Cell(x+1,y, BlockColors.VIOLET)
                };
                break;

            case Z:
                cells = new Cell[]{
                        new Cell(x-1,y, BlockColors.ORANGE),
                        new Cell(x,y, BlockColors.ORANGE),
                        new Cell(x,y-1, BlockColors.ORANGE),
                        new Cell(x+1,y-1, BlockColors.ORANGE)
                };
                break;
        }

        return block
                .setY(y)
                .setX(x)
                .setType(type)
                .setCells(cells)
                .finish();
    }

    private Block finish() {
        return this;
    }

    @Override
    public Cell[] getCells() {
        return cells;
    }

    @Override
    public void update() {
        for (Cell cell : cells
                ) {
            cell.getPoint().set(cell.getPoint().x, cell.getPoint().y+1);
        }
        y += 1;
    }

    @Override
    public void transform() {
        if (type != Type.O){
            for (Cell cell : cells
            ) {
                int xPos = cell.getPoint().x - this.x;
                int yPos = cell.getPoint().y - this.y;
                int newXPos = -yPos + this.x;
                int newYPos = xPos + this.y;
                cell.getPoint().set(newXPos, newYPos);
            }
        }
    }

    @Override
    public void moveLeft() {
        for (Cell cell : cells
        ) {
            cell.getPoint().set(cell.getPoint().x-1, cell.getPoint().y);
        }
        x -= 1;
    }

    @Override
    public void moveRight() {
        for (Cell cell : cells
        ) {
            cell.getPoint().set(cell.getPoint().x+1, cell.getPoint().y);
        }
        x += 1;
    }

    @Override
    public Point[] nextTransformPositions() {
        Point[] nextPositions = new Point[cells.length];
        for (int i = 0; i < cells.length; i++) {
            int xPos = cells[i].getPoint().x - this.x;
            int yPos = cells[i].getPoint().y - this.y;
            int newXPos = -yPos + this.x;
            int newYPos = xPos + this.y;
            nextPositions[i] = new Point(newXPos, newYPos);
        }
        return nextPositions;
    }

    private enum Type{
        T(0), I(1), J(2), L(3), O(4), S(5), Z(6);
        private int value;
        Type(int value){
            this.value = value;
        }
    }
}
