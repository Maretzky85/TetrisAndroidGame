package com.sikoramarek.tetrisgame.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

import com.sikoramarek.tetrisgame.controller.InputHandlerHelper;
import com.sikoramarek.tetrisgame.controller.Inputs;
import com.sikoramarek.tetrisgame.model.Block;
import com.sikoramarek.tetrisgame.model.BlockB;
import com.sikoramarek.tetrisgame.model.Cell;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.sikoramarek.tetrisgame.controller.InputHandlerHelper.currentAction;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final float WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final float HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    private final Paint fontPaint = new Paint();
    private final Paint endFontPaint = new Paint();
    private final Paint borderPaint = new Paint();

    int score = 0;

    private final ViewResources viewResources;

    private final float screenHeight = HEIGHT;
    private final float screenWidth = WIDTH;

    private float cellWidth = screenWidth/10;
    private final float cellHeight = screenHeight/20;
    private final float widthToHeightRatio = WIDTH / HEIGHT;

    private final int SENSITIVITY = 80;

    private long touchTime;
    private int touchXPos;
    private int touchYPos;

    private Block activeBlocks;
    private ArrayList<Cell> inactiveCells;

    private boolean takingBlockDown = false;
    private boolean endGame = false;

    private final SharedPreferences sharedPreferences = getContext()
            .getSharedPreferences("Score", MODE_PRIVATE);
    private int widthOffset = 0;

    public GameView(Context context){

        super(context);
        getHolder().addCallback(this);

        fontPaint.setColor(Color.WHITE);
        endFontPaint.setColor(Color.WHITE);
        fontPaint.setTextSize(50);
        endFontPaint.setTextSize(250);

        borderPaint.setColor(Color.WHITE);
        viewResources = new ViewResources(getResources());
        viewResources.resizeImages(WIDTH, HEIGHT);
        if (widthToHeightRatio > 1){
            cellWidth = Math.abs((int) (cellWidth * (widthToHeightRatio -2)));
            widthOffset = Math.abs((int) (WIDTH * (widthToHeightRatio -2)));
        }

        setTouchListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int alteredSensitivity = SENSITIVITY;
                if (takingBlockDown){
                    alteredSensitivity = alteredSensitivity*2;
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

                            if (deltaX > alteredSensitivity){
                                touchXPos = x;
                                InputHandlerHelper.move(Inputs.LEFT);
                                currentAction = MotionEvent.ACTION_MOVE;
                                break;
                            } else
                            if (deltaX < -alteredSensitivity){
                                touchXPos = x;
                                InputHandlerHelper.move(Inputs.RIGHT);
                                break;
                            }else
                            if (deltaY < -SENSITIVITY){
                                touchYPos = y;
                                InputHandlerHelper.move(Inputs.DOWN);
                                currentAction = MotionEvent.ACTION_MOVE;
                                takingBlockDown = true;
                                break;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - touchTime < 100 && currentAction == MotionEvent.ACTION_DOWN){
                            InputHandlerHelper.move(Inputs.CLICK);
                        }
                        currentAction = -1;
                        takingBlockDown = false;
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    public void endGame(){
        if (sharedPreferences.getInt("HighestScore", 0) < score){
            sharedPreferences.edit().putInt("HighestScore", score).apply();
        }
        endGame = true;
    }

    @Override
    public void draw(Canvas canvas){
        if (canvas != null){
            super.draw(canvas);
            if (widthToHeightRatio > 1) {
                drawOutline(canvas);
            }
            if (activeBlocks != null){
                Cell[] positions = activeBlocks.getCells();
                for (Cell cell: positions
                ) {
                        canvas.drawBitmap(viewResources.getActiveBlock(cell.getColor()),
                                cell.getPoint().x*cellWidth+widthOffset,
                                cell.getPoint().y*cellHeight,
                                null);
                }
            }
            if (inactiveCells != null){
                for (int i = 0; i < inactiveCells.size(); i++) {
                    canvas.drawBitmap(viewResources.getInactiveBlock(inactiveCells.get(i).getColor()),
                            inactiveCells.get(i).getPoint().x*cellWidth+widthOffset,
                            inactiveCells.get(i).getPoint().y*cellHeight,
                            null);
                }
            }

            Cell[] positions = BlockB.getNextPreview().getCells();
            for (Cell cell: positions
            ) {
                canvas.drawBitmap(viewResources.getThumbnail(cell.getColor()),
                        cell.getPoint().x*cellWidth+widthOffset,
                        cell.getPoint().y*cellHeight,
                        null);
            }

            canvas.drawText("Score: "+ score, 50,50, fontPaint);
            if (endGame){
                canvas.drawText(""+score, 0, 800, endFontPaint);
            }
        }
    }


    private void drawOutline(Canvas canvas) {
        if (canvas != null){
            //left border
            canvas.drawLine(widthOffset, HEIGHT-20*cellHeight, widthOffset, HEIGHT, borderPaint);

            //right border
            canvas.drawLine(widthOffset+10*cellWidth, HEIGHT-20*cellHeight, widthOffset+10*cellWidth, HEIGHT, borderPaint);
        }
    }


    public boolean updateView(Block activeBlock, ArrayList<Cell> inactiveCells){
        this.activeBlocks = activeBlock;
        this.inactiveCells = inactiveCells;

        SurfaceHolder surfaceHolder = getHolder();
        synchronized (surfaceHolder){
            try {
                Canvas canvas = surfaceHolder.lockCanvas();
                draw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }catch (IllegalStateException | IllegalArgumentException ignored){
                return false;
            }
        }
        return true;
    }

    public void setScore(int score){
        this.score = score;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //TODO
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //TODO
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //TODO
    }
}
