package com.sikoramarek.tetrisgame.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

import com.sikoramarek.tetrisgame.Controller.InputHandler;
import com.sikoramarek.tetrisgame.Controller.Inputs;
import com.sikoramarek.tetrisgame.R;
import com.sikoramarek.tetrisgame.model.Block;
import com.sikoramarek.tetrisgame.model.Cell;

import java.util.ArrayList;
import java.util.Vector;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    Bitmap activeBlock = BitmapFactory.decodeResource(getResources(), R.drawable.redblock);
    Bitmap inactiveBlock = BitmapFactory.decodeResource(getResources(), R.drawable.inactive);

    int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    private int screenHeight = HEIGHT -100;
    private int screenWidth = WIDTH;

    private int cellWidth = screenWidth/10;
    private int cellHeight = screenHeight/20;

    private Bitmap activeCellImage = Bitmap.createScaledBitmap(activeBlock, cellWidth, cellHeight, false);
    private Bitmap inactiveCellImage = Bitmap.createScaledBitmap(inactiveBlock, cellWidth, cellHeight, false);
    private int SENSITIVITY = 80;

    private long touchTime;
    private int touchXPos;
    private int touchYPos;
    private Block activeBlocks;
    private Vector<Cell> inactiveBlocks;


    @SuppressLint("ClickableViewAccessibility")
    public GameView(Context context){

        super(context);
        getHolder().addCallback(this);

        InputHandler.attachView(this);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchXPos = x;
                        touchYPos = y;
                        touchTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int deltaX = touchXPos - x;
                        int deltaY = touchYPos - y;

                        if (deltaX > SENSITIVITY){
                            touchXPos = x;
                            InputHandler.move(Inputs.RIGHT);
                            break;
                        }else
                        if (deltaX < -SENSITIVITY){
                            touchXPos = x;
                            InputHandler.move(Inputs.LEFT);
                            break;
                        }else
                            if (deltaY < -SENSITIVITY){
                                touchYPos = y;

                                InputHandler.move(Inputs.DOWN);

                                break;
                            }

                        break;
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - touchTime < 100){
                            InputHandler.move(Inputs.CLICK);
                        }
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (canvas != null){
            if (activeBlocks != null){
                ArrayList<int[]> positions = activeBlocks.getPositions();
                for (int[] position : positions
                ) {
                    canvas.drawBitmap(activeCellImage, position[0]*cellWidth, position[1]*cellHeight, null);
                }
            }
            if (inactiveBlocks != null){
                for (Cell cell : inactiveBlocks
                ) {
                    canvas.drawBitmap(inactiveCellImage, cell.xPos*cellWidth, cell.yPos*cellHeight, null);
                }
            }

        }
    }

    public void updateView(Block activeBlock, Vector<Cell> inactiveBlock){
        this.activeBlocks = activeBlock;
        this.inactiveBlocks = inactiveBlock;
//        Canvas canvas = getHolder().lockCanvas();
//        draw(canvas);
//        getHolder().unlockCanvasAndPost(canvas);
    }
}
