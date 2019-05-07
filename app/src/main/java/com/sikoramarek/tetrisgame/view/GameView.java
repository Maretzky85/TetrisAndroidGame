package com.sikoramarek.tetrisgame.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.sikoramarek.tetrisgame.MainThread;
import com.sikoramarek.tetrisgame.R;
import com.sikoramarek.tetrisgame.model.PlayField;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private PlayField playField;

    private long updateTime;
    private int speed = 200;

    public GameView(Context context){

        super(context);
        getHolder().addCallback(this);

        playField = new PlayField(
                BitmapFactory.decodeResource(getResources(), R.drawable.redblock),
                BitmapFactory.decodeResource(getResources(), R.drawable.inactive),
                Resources.getSystem().getDisplayMetrics().widthPixels,
                Resources.getSystem().getDisplayMetrics().heightPixels
        );
        thread = new MainThread(getHolder(), this, playField);



    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("TAG", "touched down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("TAG", "moving: (" + x + ", " + y + ")");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("TAG", "touched up");
                break;
        }
        return super.onTouchEvent(event);
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
            playField.update();
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
