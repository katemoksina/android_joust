package com.example.kate.game;

import android.graphics.Canvas;

/**
 * Created by kate on 26/05/2017.
 */

public class GameThread extends Thread {
    private GameView mView;
    private boolean running = false;

    public GameThread(GameView mView){
        this.mView = mView;
    }

    public void setRunning(boolean run){
        running = run;
    }
    @Override
    public void run(){
        while(running){
            Canvas canvas = null;
            try {
                canvas = mView.getHolder().lockCanvas();
                synchronized (mView.getHolder()){
                    mView.onDraw(canvas);
                }
            } finally {
                if (canvas!=null){
                    mView.getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
