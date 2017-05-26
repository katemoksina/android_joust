package com.example.kate.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by kate on 26/05/2017.
 */

public class GameView extends SurfaceView {

    private Bitmap mBitmapPlayer1;
    private Bitmap mBitmapPlayer2;
    private SurfaceHolder holder;
    private GameThread mGameThread;

    Paint grey = new Paint();



    private int x = 0;
    private int y = 0;

    public GameView(Context context) {
        super(context);
        mGameThread = new GameThread(this);

        grey.setColor(Color.GRAY);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mGameThread.setRunning(true);
                mGameThread.start();

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                boolean retry = true;
                mGameThread.setRunning(false);
                while(retry){
                    try{
                        mGameThread.join();
                        retry = false;
                    } catch(InterruptedException e){
                    }
                }
            }
        });

        mBitmapPlayer1 = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        mBitmapPlayer2 = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        y=y+10;
        canvas.drawColor(Color.BLACK);
//        canvas.drawBitmap(mBitmapPlayer1, 200, y, null);
//        canvas.drawBitmap(mBitmapPlayer2, 500, y, null);
        canvas.drawCircle(400, y, 100, grey);
        canvas.drawCircle(700, y, 100, grey);
        canvas.drawRect(new RectF(200, 700, 800, 900), grey);
    }

}
