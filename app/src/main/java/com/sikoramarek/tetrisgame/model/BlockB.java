package com.sikoramarek.tetrisgame.model;

import android.graphics.Point;
import android.os.Bundle;

import com.sikoramarek.tetrisgame.common.GameOptions;
import com.sikoramarek.tetrisgame.view.BlockColors;

import java.util.Objects;
import java.util.Random;

import static com.sikoramarek.tetrisgame.common.GameOptions.BLOCK_ROTATION_LEFT;
import static com.sikoramarek.tetrisgame.common.GameOptions.BLOCK_ROTATION_RIGHT;

public class BlockB implements Block {

    private static final Random randomGenerator = new Random();
    private static Type nextType = getRandomType();
    private static Block nextBlock = getNext(5, 5);
    private final int currentBlockRotation = new GameOptions().getCurrentBlockRotation();
    private int boardXPosition;
    private int boardYPosition;
    private Type type;
    private Cell[] cells;

    private BlockB() {
    }

    static Block getNext(int x, int y) {
        Block next = getBlock(x, y, nextType);
        nextType = getRandomType();
        nextBlock = getBlock(5, 5, nextType);
        return next;
    }

    public static Block getNextPreview() {
        return nextBlock;
    }

    static Type getRandomType() {
        int random = randomGenerator.nextInt(7000);
        if (random < 1000) {
            return Type.I;
        }
        if (random < 2000) {
            return Type.J;
        }
        if (random < 3000) {
            return Type.L;
        }
        if (random < 4000) {
            return Type.O;
        }
        if (random < 5000) {
            return Type.S;
        }
        if (random < 6000) {
            return Type.T;
        } else {
            return Type.Z;
        }
    }

    private static Block getBlock(int x, int y, Type type) {
        BlockB block = new BlockB();
        Cell[] cells = new Cell[4];

        switch (type) {
            case I:
                cells = new Cell[]{
                        new Cell(x, y - 2, BlockColors.RED),
                        new Cell(x, y - 1, BlockColors.RED),
                        new Cell(x, y, BlockColors.RED),
                        new Cell(x, y + 1, BlockColors.RED)
                };
                break;
            case J:
                cells = new Cell[]{
                        new Cell(x - 1, y - 1, BlockColors.YELLOW),
                        new Cell(x - 1, y, BlockColors.YELLOW),
                        new Cell(x, y, BlockColors.YELLOW),
                        new Cell(x + 1, y, BlockColors.YELLOW)
                };
                break;
            case L:
                cells = new Cell[]{
                        new Cell(x - 1, y, BlockColors.GREEN),
                        new Cell(x, y, BlockColors.GREEN),
                        new Cell(x + 1, y, BlockColors.GREEN),
                        new Cell(x + 1, y - 1, BlockColors.GREEN)
                };
                break;
            case O:
                cells = new Cell[]{
                        new Cell(x - 1, y, BlockColors.BLUE),
                        new Cell(x - 1, y - 1, BlockColors.BLUE),
                        new Cell(x, y, BlockColors.BLUE),
                        new Cell(x, y - 1, BlockColors.BLUE)
                };
                break;
            case S:
                cells = new Cell[]{
                        new Cell(x - 1, y - 1, BlockColors.ORANGE),
                        new Cell(x, y - 1, BlockColors.ORANGE),
                        new Cell(x, y, BlockColors.ORANGE),
                        new Cell(x + 1, y, BlockColors.ORANGE)
                };
                break;
            case T:
                cells = new Cell[]{
                        new Cell(x - 1, y, BlockColors.VIOLET),
                        new Cell(x, y - 1, BlockColors.VIOLET),
                        new Cell(x, y, BlockColors.VIOLET),
                        new Cell(x + 1, y, BlockColors.VIOLET)
                };
                break;

            case Z:
                cells = new Cell[]{
                        new Cell(x - 1, y, BlockColors.ORANGE),
                        new Cell(x, y, BlockColors.ORANGE),
                        new Cell(x, y - 1, BlockColors.ORANGE),
                        new Cell(x + 1, y - 1, BlockColors.ORANGE)
                };
                break;
        }

        return block
                .setBoardYPosition(y)
                .setBoardXPosition(x)
                .setType(type)
                .setCells(cells)
                .build();
    }

    private BlockB setBoardXPosition(int boardXPosition) {
        this.boardXPosition = boardXPosition;
        return this;
    }

    private BlockB setBoardYPosition(int boardYPosition) {
        this.boardYPosition = boardYPosition;
        return this;
    }

    private BlockB setType(Type type) {
        this.type = type;
        return this;
    }

    private Block build() {
        return this;
    }

    @Override
    public Cell[] getCells() {
        return cells;
    }

    private BlockB setCells(Cell[] cells) {
        this.cells = cells;
        return this;
    }

    @Override
    public void update() {
        for (Cell cell : cells) {
            cell.getPoint().set(cell.getPoint().x, cell.getPoint().y + 1);
        }
        boardYPosition += 1;
    }

    @Override
    public void transform() {
        if (type != Type.O) {
            if (currentBlockRotation == BLOCK_ROTATION_LEFT) {
                transformLeft();
            } else {
                transformRight();
            }
        }
    }

    private void transformRight() {
        for (Cell cell : cells
        ) {
            int xPos = cell.getPoint().x - this.boardXPosition;
            int yPos = cell.getPoint().y - this.boardYPosition;
            int newXPos = -yPos + this.boardXPosition;
            int newYPos = xPos + this.boardYPosition;
            cell.getPoint().set(newXPos, newYPos);
        }
    }

    private void transformLeft() {
        for (Cell cell : cells
        ) {
            int xPos = cell.getPoint().x - this.boardXPosition;
            int yPos = cell.getPoint().y - this.boardYPosition;
            int newXPos = yPos + this.boardXPosition;
            int newYPos = -xPos + this.boardYPosition;
            cell.getPoint().set(newXPos, newYPos);
        }
    }

    @Override
    public void moveLeft() {
        for (Cell cell : cells) {
            cell.getPoint().set(cell.getPoint().x - 1, cell.getPoint().y);
        }
        boardXPosition -= 1;
    }

    @Override
    public void moveRight() {
        for (Cell cell : cells) {
            cell.getPoint().set(cell.getPoint().x + 1, cell.getPoint().y);
        }
        boardXPosition += 1;
    }

    @Override
    public Point[] nextTransformPositions() {
        Point[] nextPositions = new Point[cells.length];
        for (int i = 0; i < cells.length; i++) {
            int xPos = cells[i].getPoint().x - this.boardXPosition;
            int yPos = cells[i].getPoint().y - this.boardYPosition;
            int newXPos;
            int newYPos;
            if (currentBlockRotation == BLOCK_ROTATION_RIGHT) {
                newXPos = -yPos + this.boardXPosition;
                newYPos = xPos + this.boardYPosition;
            } else {
                newXPos = yPos + this.boardXPosition;
                newYPos = -xPos + this.boardYPosition;
            }
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
        outState.putInt("XPosition", boardXPosition);
        outState.putInt("YPosition", boardYPosition);
    }

    public Block loadStateFrom(Bundle savedStateBundle) {
        int[] activeXList = savedStateBundle.getIntArray("activeXList");
        int[] activeYList = savedStateBundle.getIntArray("activeYList");
        int[] activeColors = savedStateBundle.getIntArray("activeColors");

        BlockB block = new BlockB();
        Cell[] cells = new Cell[4];

        for (int i = 0; i < Objects.requireNonNull(activeXList).length; i++) {
            assert activeColors != null;
            assert activeYList != null;
            cells[i] = new Cell(
                    activeXList[i],
                    activeYList[i],
                    BlockColors.values()[activeColors[i]]
            );
        }

        return block
                .setBoardXPosition(savedStateBundle.getInt("XPosition"))
                .setBoardYPosition(savedStateBundle.getInt("XPosition"))
                .setCells(cells)
                .build();
    }

    private enum Type {
        T(0), I(1), J(2), L(3), O(4), S(5), Z(6);

        Type(int value) {
        }
    }
}
