package com.sikoramarek.tetrisgame.model;

import android.graphics.Point;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

public class PlayField {

    private Block activeBlock;
    private final Vector<Cell> inactiveCells;
    private int score = 0;
    private boolean running = true;


    public PlayField(){
        inactiveCells = new Vector<>(20);
        activeBlock = BlockB.getRandomBlock(5,1);
    }

    public boolean isRunning(){
        return running;
    }

    public synchronized void update(){
        if (running){
            Cell[] positions = activeBlock.getCells();
            for (Cell position : positions) {
                if (position.getPoint().y == 19) {
                    placeActiveBlock(activeBlock);
                    return;
                }
                for (Cell inactiveCell : inactiveCells
                ) {
                    if (inactiveCell.getPoint().y == position.getPoint().y + 1 &&
                            inactiveCell.getPoint().x == position.getPoint().x) {
                        placeActiveBlock(activeBlock);
                        return;
                    }
                }
            }
            activeBlock.update();
        }
    }

    private void checkLines() {
        int[] lines = new int[21];
        int totalLines = 0;
        int scoreToAdd = 0;
        for (int i = 0; i < lines.length; i++) {
            lines[i] = 0;
        }
        for (Cell cell : inactiveCells
                ) {
            if (cell.getPoint().y > 0){
                lines[cell.getPoint().y]++;
            }
        }

        for (int i = 0; i < lines.length; i++) {
            if (lines[i] >= 10){
                deleteLine(i);
                totalLines++;
                scoreToAdd += 100;
            }
        }
        if (totalLines > 0){
            this.score += scoreToAdd * totalLines;
        }

        for (Cell cell : inactiveCells
        ) {
            if (cell.getPoint().y == 0){
                running = false;
                return;
            }
        }
    }

    private void deleteLine(int y) {
        Iterator<Cell> inactiveIterator = inactiveCells.iterator();
        while (inactiveIterator.hasNext()){
            Cell cell = inactiveIterator.next();
            if (cell.getPoint().y == y){
                inactiveIterator.remove();
            }
        }

        for (Cell cell : inactiveCells
                ) {
            if (cell.getPoint().y < y){
                cell.getPoint().offset(0,1);
            }
        }
    }

    private void placeActiveBlock(Block block) {
        activeBlock = BlockB.getRandomBlock(5,1);
        Cell[] positions = block.getCells();
        inactiveCells.addAll(Arrays.asList(positions));
        checkLines();
    }

    public void left() {
        if (activeBlock!=null){
            Cell[] positions = activeBlock.getCells();
            for (Cell cell : positions
            ) {
                if (cell.getPoint().x == 0){
                    return;
                }

                for (Cell inactiveCell : inactiveCells
                ) {
                    if (inactiveCell.getPoint().y == cell.getPoint().y &&
                            inactiveCell.getPoint().x == cell.getPoint().x - 1){
                        return;
                    }
                }

            }
            activeBlock.moveLeft();
        }
    }

    public void right() {
        if (activeBlock!=null){
            Cell[] positions = activeBlock.getCells();
            for (Cell cell : positions
            ) {
                if (cell.getPoint().x == 9) {
                    return;
                }

                for (Cell inactiveCell : inactiveCells
                ) {
                    if (inactiveCell.getPoint().y == cell.getPoint().y &&
                            inactiveCell.getPoint().x == cell.getPoint().x + 1){
                        return;
                    }
                }
            }
            activeBlock.moveRight();
        }
    }

    public void click() {
        if (checkValidTransform()){
            activeBlock.transform();
        }
    }

    private boolean checkValidTransform() {
        Point[] nextPositions = activeBlock.nextTransformPositions();
        for (Point point: nextPositions
        ) {
            if (point.x < 0 || point.x > 9 || point.y > 19){
                return false;
            }

            for (Cell inactiveCell : inactiveCells
            ) {
                if (inactiveCell.getPoint().y == point.y && inactiveCell.getPoint().x == point.x){
                    return false;
                }
            }
        }
        return true;
    }

    public Block getActiveBlock() {
        return activeBlock;
    }

    public Vector<Cell> getInactiveCells() {
        return inactiveCells;
    }

    public int getScore() {
        return score;
    }
}
