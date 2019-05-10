package com.sikoramarek.tetrisgame.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

import com.sikoramarek.tetrisgame.MainThread;
import com.sikoramarek.tetrisgame.R;
import com.sikoramarek.tetrisgame.model.PlayField;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private PlayField playField;

    private long updateTime;
    private int speed = 800;
    private int sensitivity = 80;

    private long touchTime;
    private int touchXPos;
    private int touchYPos;

    @SuppressLint("ClickableViewAccessibility")
    public GameView(Context context){

        super(context);
        getHolder().addCallback(this);

        playField = new PlayField(
                BitmapFactory.decodeResource(getResources(), R.drawable.redblock),
                BitmapFactory.decodeResource(getResources(), R.drawable.inactive),
                Resources.getSystem().getDisplayMetrics().widthPixels,
                Resources.getSystem().getDisplayMetrics().heightPixels
        );
        thread = new MainThread(getHolder(), this);

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

                        if (deltaX > sensitivity){
                            touchXPos = x;
                            playField.left();
                            break;
                        }else
                        if (deltaX < -sensitivity){
                            touchXPos = x;
                            playField.right();
                            break;
                        }else
                            if (deltaY < -sensitivity){
                                touchYPos = y;
                                synchronized (playField){
                                    playField.update();
                                }
                                break;
                            }

                        break;
                    case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - touchTime < 100){
                            playField.click();
                        }
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
        updateTime = System.currentTimeMillis();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update(){
        if (System.currentTimeMillis() - updateTime > speed){
            synchronized (playField){
                playField.update();
            }
            updateTime = System.currentTimeMillis();
        }

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (canvas != null){
            playField.draw(canvas);
        }
    }
}
