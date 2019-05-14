package com.sikoramarek.tetrisgame.model;

import java.util.ArrayList;
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

    public boolean isNotRunning(){
        return !running;
    }

    public synchronized void update(){
        if (running){
            ArrayList<int[]> positions = activeBlock.getPositions();
            for (int[] position: positions
            ){
                synchronized (inactiveCells){
                    for (Cell cell : inactiveCells
                    ) {
                        if (cell.yPos == position[1]+1 && cell.xPos == position[0]){
                            placeActiveBlock(activeBlock);
                            return;
                        }
                    }
                }
                if (position[1] == 19){
                    placeActiveBlock(activeBlock);
                    return;
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
            if (cell.yPos > 0){
                lines[cell.yPos]++;
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
            if (cell.yPos == 0){
                running = false;
                return;
            }
        }
    }

    private void deleteLine(int y) {
        Iterator<Cell> inactiveIterator = inactiveCells.iterator();
        while (inactiveIterator.hasNext()){
            Cell cell = inactiveIterator.next();
            if (cell.yPos == y){
                inactiveIterator.remove();
            }
        }

        for (Cell cell : inactiveCells
                ) {
            if (cell.yPos < y){
                cell.yPos += 1;
            }
        }
    }

    private void placeActiveBlock(Block block) {
        activeBlock = BlockB.getRandomBlock(5,1);
        ArrayList<int[]> positions = block.getPositions();
        for (int[] position: positions
        ){
            inactiveCells.add(new Cell(position[0], position[1]));
        }
        checkLines();
    }

    public void left() {
        if (activeBlock!=null){
            ArrayList<int[]> positions = activeBlock.getPositions();
            for (int[] position : positions
            ) {
                if (position[0] == 0){
                    return;
                }

                for (Cell cell : inactiveCells
                ) {
                    if (cell.yPos == position[1] && cell.xPos == position[0]-1){
                        return;
                    }
                }

            }
            activeBlock.moveLeft();
        }
    }

    public void right() {
        if (activeBlock!=null){
            ArrayList<int[]> positions = activeBlock.getPositions();
            for (int[] position : positions
            ) {
                if (position[0] == 9) {
                    return;
                }

                for (Cell cell : inactiveCells
                ) {
                    if (cell.yPos == position[1] && cell.xPos == position[0]+1){
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
        ArrayList<int[]> nextPositions = activeBlock.nextTransformPositions();
        for (int[] position : nextPositions
        ) {
            if (position[0] < 0 || position[0] > 9){
                return false;
            }

            for (Cell cell : inactiveCells
            ) {
                if (cell.yPos == position[1] && cell.xPos == position[0]){
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
