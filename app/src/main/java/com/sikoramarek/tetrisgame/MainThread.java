package com.sikoramarek.tetrisgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.sikoramarek.tetrisgame.view.GameView;

public class MainThread extends Thread {

    private final SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    private static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView){

        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run(){
        while (running){
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
//                    this.gameView.update();
                    this.gameView.draw(canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }

    public void setRunning(boolean isRunning){
        running = isRunning;
    }

}
