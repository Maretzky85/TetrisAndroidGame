package com.sikoramarek.tetrisgame.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class PlayField {
    Random randomGenerator;

    private Block activeBlock;
    private Vector<Cell> inactiveCells;


    public PlayField(){
        inactiveCells = new Vector<>(20);

        randomGenerator = new Random();
        activeBlock = getRandomBlock();

    }



    private Block getRandomBlock() {
        int random = randomGenerator.nextInt(7000);
        if (random < 1000){
            return new OBlock(5,1);
        }
        if (random < 2000){
            return new TBlock(5,1);
        }
        if (random < 3000){
            return new IBlock(5,2);
        }
        if (random < 4000){
            return new LBlock(5,2);
        }
        if (random < 5000){
            return new SBlock(5,2);
        }
        if (random < 6000){
            return new ZBlock(5,2);
        }
        else {
            return new JBlock(5,2);
        }

    }

    public synchronized void update(){
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

    private void checkLines() {
        int[] lines = new int[21];
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
        activeBlock = getRandomBlock();
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
        activeBlock.transform();
    }

    public Block getActiveBlock() {
        return activeBlock;
    }

    public Vector<Cell> getInactiveCells() {
        return inactiveCells;
    }
}
