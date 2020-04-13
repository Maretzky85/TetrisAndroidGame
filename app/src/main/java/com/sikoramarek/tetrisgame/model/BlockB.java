package com.sikoramarek.tetrisgame.model;

import android.graphics.Point;
import android.os.Bundle;

import com.sikoramarek.tetrisgame.view.BlockColors;

import java.util.Random;

public class BlockB implements Block{

    private static Random randomGenerator = new Random();
    private static Type nextType = getRandomType();
    private static Block nextBlock = getNext(5 ,5);
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

    static Block getNext(int x, int y) {
        Block next = getBlock(x, y, nextType);
        nextType = getRandomType();
        nextBlock = getBlock(5, 5, nextType);
        return next;
    }

    public static Block getNextPrev() {
        return nextBlock;
    }

    static Type getRandomType(){
        int random = randomGenerator.nextInt(7000);
        if (random < 1000){
            return Type.I;
        }
        if (random < 2000){
            return Type.J;
        }
        if (random < 3000){
            return Type.L;
        }
        if (random < 4000){
            return Type.O;
        }
        if (random < 5000){
            return Type.S;
        }
        if (random < 6000){
            return Type.T;
        }
        else {
            return Type.Z;
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
                .build();
    }

    private Block build() {
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

    @Override
    public void saveState(Bundle outState) {

        int[] activeXList = new int[4];
        int[] activeYList = new int[4];
        int[] activeColors = new int[4];

        for (int i = 0; i < cells.length; i++) {
            Cell currentCell = cells[i];
            activeXList[i] = currentCell.getPoint().x;
            activeYList[i] = currentCell.getPoint().y;
            activeColors[i] = currentCell.getColor().getValue();
        }

        outState.putIntArray("activeXList", activeXList);
        outState.putIntArray("activeYList", activeYList);
        outState.putIntArray("activeColors", activeColors);
        outState.putInt("XPosition", x);
        outState.putInt("YPosition", y);
    }

    public Block loadStateFrom(Bundle savedStateBundle){
        int[] activeXList = savedStateBundle.getIntArray("activeXList");
        int[] activeYList = savedStateBundle.getIntArray("activeYList");
        int[] activeColors = savedStateBundle.getIntArray("activeColors");

        BlockB block = new BlockB();
        Cell[] cells = new Cell[4];

        for (int i = 0; i < activeXList.length; i++) {
            cells[i] = new Cell(
                    activeXList[i],
                    activeYList[i],
                    BlockColors.values()[activeColors[i]]
            );
        }

        return block
                .setX(savedStateBundle.getInt("XPosition"))
                .setY(savedStateBundle.getInt("XPosition"))
                .setCells(cells)
                .build();
    }

    private enum Type{
        T(0), I(1), J(2), L(3), O(4), S(5), Z(6);
        private int value;
        Type(int value){
            this.value = value;
        }
    }
}
