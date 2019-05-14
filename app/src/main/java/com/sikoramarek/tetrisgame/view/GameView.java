package com.sikoramarek.tetrisgame.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    Paint fontPaint = new Paint();
    Paint endFontPaint = new Paint();

    int score = 0;

    private int screenHeight = HEIGHT;
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

    private int currentAction = -1;
    private boolean takingBlockDown = false;
    private boolean endGame = false;


    @SuppressLint("ClickableViewAccessibility")
    public GameView(Context context){

        super(context);
        getHolder().addCallback(this);

        InputHandler.attachView(this);

        fontPaint.setColor(Color.WHITE);
        endFontPaint.setColor(Color.WHITE);
        fontPaint.setTextSize(50);
        endFontPaint.setTextSize(250);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int sensitivity = SENSITIVITY;
                if (takingBlockDown){
                    sensitivity = sensitivity*2;
                }
                int x = (int) event.getX();
                int y = (int) event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchXPos = x;
                        touchYPos = y;
                        touchTime = System.currentTimeMillis();
                        currentAction = MotionEvent.ACTION_DOWN;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (currentAction == MotionEvent.ACTION_DOWN ||
                        currentAction == MotionEvent.ACTION_MOVE){
                            int deltaX = touchXPos - x;
                            int deltaY = touchYPos - y;

                            if (deltaX > sensitivity){
                                touchXPos = x;
                                InputHandler.move(Inputs.LEFT);
                                currentAction = MotionEvent.ACTION_MOVE;
                                break;
                            }else
                            if (deltaX < -sensitivity){
                                touchXPos = x;
                                InputHandler.move(Inputs.RIGHT);
                                break;
                            }else
                            if (deltaY < -SENSITIVITY){
                                touchYPos = y;
                                InputHandler.move(Inputs.DOWN);
                                currentAction = MotionEvent.ACTION_MOVE;
                                takingBlockDown = true;
                                break;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - touchTime < 100 && currentAction == MotionEvent.ACTION_DOWN){
                            InputHandler.move(Inputs.CLICK);
                        }
                        currentAction = -1;
                        takingBlockDown = false;
                        break;
                }
                return true;
            }
        });
    }

    public void endGame(){
        endGame = true;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("GameView", "=============================================================Created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("GameView", "=============================================================Changed\n"+
                "format: "+format+
                "\nwidth: "+width+
                "\nheight: "+height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("GameView", "=============================================================Destroyed");
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
            canvas.drawText("Score: "+ score, 50,50, fontPaint);
        }
        if (endGame){
            canvas.drawText(""+score, 0, 800, endFontPaint);
        }
    }


    public void updateView(Block activeBlock, Vector<Cell> inactiveBlock, Canvas canvas){
        this.activeBlocks = activeBlock;
        this.inactiveBlocks = inactiveBlock;
        draw(canvas);
    }

    public void setScore(int score){
        this.score = score;
    }
}
