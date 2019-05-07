package com.sikoramarek.tetrisgame.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class PlayField {

    private Bitmap activeCellImage;
    private Bitmap inactiveCellImage;

    private int screenWidth;
    private int screenHeight;

    private Block activeBlock;

    private ArrayList<Cell> inactiveCells;

    private int cellWidth;
    private int cellHeight;

    public PlayField(Bitmap active, Bitmap inactive, int width, int height){
        inactiveCells = new ArrayList<>(20);

        activeCellImage = active;
        inactiveCellImage = inactive;

        screenHeight = height;
        screenWidth = width;

        cellWidth = screenWidth/11;
        cellHeight = screenHeight/22*;

//        activeCellImage.setWidth(cellWidth);
//        activeCellImage.setHeight(cellHeight);
//        inactiveCellImage.setWidth(cellWidth);
//        inactiveCellImage.setHeight(cellHeight);

        activeBlock = new LineBlock(5,0);
    }

    public void update(){
        ArrayList<int[]> positions = activeBlock.getPositions();
        for (int[] position: positions
        ){
            for (Cell cell : inactiveCells
                    ) {
                if (cell.yPos == position[1]+1 && cell.xPos == position[0]){
                    placeActiveBlock();
                    activeBlock = new LineBlock(5,0);
                }
            }
            if (position[1] == 20){
                placeActiveBlock();
                activeBlock = new LineBlock(5,0);
                Log.i("new", "block");
                return;
            }
        }
        activeBlock.update();
    }

    private void placeActiveBlock() {
        ArrayList<int[]> positions = activeBlock.getPositions();
        for (int[] position: positions
        ){
            inactiveCells.add(new Cell(position[0], position[1]));
        }
        return;
    }

    public void draw(Canvas canvas){
        ArrayList<int[]> positions = activeBlock.getPositions();
        for (int[] position : positions
        ) {
            canvas.drawBitmap(activeCellImage, position[0]*cellWidth, position[1]*cellHeight, null);
        }
        for (Cell cell : inactiveCells
                ) {
            canvas.drawBitmap(inactiveCellImage, cell.xPos*cellWidth, cell.yPos*cellHeight, null);
        }

    }

    public void left() {
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

    public void right() {
        ArrayList<int[]> positions = activeBlock.getPositions();
        for (int[] position : positions
        ) {
            if (position[0] == 10) {
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

    public void click() {
        activeBlock.transform();
    }
}
