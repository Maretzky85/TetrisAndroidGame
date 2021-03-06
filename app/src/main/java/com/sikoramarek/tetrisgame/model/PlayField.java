package com.sikoramarek.tetrisgame.model;

import android.graphics.Point;
import android.os.Bundle;

import com.sikoramarek.tetrisgame.controller.InputHandlerHelper;
import com.sikoramarek.tetrisgame.view.BlockColors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PlayField {

    private Block activeBlock;
    private final ArrayList<Cell> inactiveCells;
    private int score = 0;
    private boolean running = true;
    private Runnable refreshView;


    public PlayField(){
        inactiveCells = new ArrayList<>(20);
        activeBlock = BlockB.getNext(5,1);
    }

    public void setRefreshView(Runnable refreshView) {
        this.refreshView = refreshView;
    }

    public boolean isRunning(){
        return running;
    }

    public void update(){
        if (running && !InputHandlerHelper.pause){
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
                final int lineToDelete = i;
                PlayField.this.deleteLine(lineToDelete);
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
        InputHandlerHelper.pause = true;
        Iterator<Cell> inactiveIterator = inactiveCells.iterator();
        while (inactiveIterator.hasNext()){
            Cell cell = inactiveIterator.next();
            if (cell.getPoint().y == y){
                inactiveIterator.remove();
                synchronized (this){
                    try {
                        Thread.sleep(20);
                        refreshView.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
        Cell[] positions = block.getCells();
        inactiveCells.addAll(Arrays.asList(positions));
        InputHandlerHelper.pause = true;
        checkLines();
        activeBlock = BlockB.getNext(5,1);
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

    public ArrayList<Cell> getInactiveCells() {
        return inactiveCells;
    }

    public int getScore() {
        return score;
    }

    public void saveStateTo(Bundle outState) {
        int inactiveSize = inactiveCells.size();
        int[] inactiveXList = new int[inactiveSize];
        int[] inactiveYList = new int[inactiveSize];
        int[] inactiveColors = new int[inactiveSize];


        for (int i = 0; i < inactiveCells.size(); i++) {
            Cell currentCell = inactiveCells.get(i);
            inactiveXList[i] = currentCell.getPoint().x;
            inactiveYList[i] = currentCell.getPoint().y;
            inactiveColors[i] = currentCell.getColor().getValue();
        }

        activeBlock.saveState(outState);

        outState.putIntArray("inactiveXList", inactiveXList);
        outState.putIntArray("inactiveYList", inactiveYList);
        outState.putIntArray("inactiveColors", inactiveColors);

        outState.putInt("score", score);
    }

    public void loadStateFrom(Bundle savedInstanceState) {
        int[] inactiveXList = savedInstanceState.getIntArray("inactiveXList");
        int[] inactiveYList = savedInstanceState.getIntArray("inactiveYList");
        int[] inactiveColors = savedInstanceState.getIntArray("inactiveColors");

        for (int i = 0; i < inactiveXList.length; i++) {
            inactiveCells.add(new Cell(
                    inactiveXList[i],
                    inactiveYList[i],
                    BlockColors.values()[inactiveColors[i]]
            ));
        }

        this.score = savedInstanceState.getInt("score");


        activeBlock = activeBlock.loadStateFrom(savedInstanceState);

    }
}
