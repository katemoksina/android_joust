package com.example.kate.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity{

    private SurfaceView surfaceView;
    Button buttonLeftP1, buttonRightP1, buttonUpP1, buttonLeftP2, buttonRightP2, buttonUpP2;
    boolean p1left = false, p1right = false, p2left = false, p2right = false, p1jump = false, p2jump = false;
    public static Sprite sprite1, sprite2;
    Paint grey = new Paint();
    Canvas canvas;
    int WIDTH, HEIGHT;
    boolean endedCondition = false;
    SurfaceHolder holder;
    public static int BARSTARTX, BARSTARTY, BARLENGTH, BARHEIGHT;
    public int p1score = 0;
    public int p2score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        WIDTH = size.x;
        HEIGHT = size.y;

        Sprite.radius = (int)Math.round(WIDTH * 0.05);

        BARSTARTX = (int)Math.round(WIDTH * 0.1);
        BARSTARTY = (int)Math.round(HEIGHT * 0.8);
        BARLENGTH = (int)Math.round(WIDTH * 0.8);
        BARHEIGHT = (int)Math.round(HEIGHT * 0.2);

        buttonLeftP1 = (Button)findViewById(R.id.buttonLeftP1);
        buttonLeftP1.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                            MainActivity.this.p1left = true;
                        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                            MainActivity.this.p1left = false;
                        }
                        return true;
                    }
                }
        );

        buttonRightP1 = (Button)findViewById(R.id.buttonRightP1);
        buttonRightP1.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                            MainActivity.this.p1right = true;
                        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                            MainActivity.this.p1right = false;
                        }
                        return true;
                    }
                }
        );

        buttonLeftP2 = (Button)findViewById(R.id.buttonLeftP2);
        buttonLeftP2.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                            MainActivity.this.p2left = true;
                        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                            MainActivity.this.p2left = false;
                        }
                        return true;
                    }
                }
        );

        buttonRightP2 = (Button)findViewById(R.id.buttonRightP2);
        buttonRightP2.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                            MainActivity.this.p2right = true;
                        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                            MainActivity.this.p2right = false;
                        }
                        return true;
                    }
                }
        );

        buttonUpP1 = (Button)findViewById(R.id.buttonUpP1);
        buttonUpP1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        p1jump = true;
                    }
                });

        buttonUpP2 = (Button)findViewById(R.id.buttonUpP2);
        buttonUpP2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        p2jump = true;
                    }
                });

        grey.setColor(Color.GRAY);
        grey.setTextSize(100);

        surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // Do some drawing when surface is ready
                MainActivity.this.holder = holder;

                GameThread.mainActivity = MainActivity.this;
                Thread t = new GameThread();
                t.start();
            }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });
    }

    public void updateVelocities(){
        if(p1left){
            sprite1.increaseLeft();
        }else{
            sprite1.decreaseLeft();
        }
        if(p1right){
            sprite1.increaseRight();
        }else{
            sprite1.decreaseRight();
        }
        if(p1jump){
            sprite1.jump();
            p1jump = false;
        }
        if(p2left){
            sprite2.increaseLeft();
        }else{
            sprite2.decreaseLeft();
        }
        if(p2right){
            sprite2.increaseRight();
        }else{
            sprite2.decreaseRight();
        }
        if(p2jump){
            sprite2.jump();
            p2jump = false;
        }
        if (detectCollisions()) {
            float tempx = sprite1.xSpeed;
            float tempy = sprite1.ySpeed;
            sprite1.xSpeed = sprite2.xSpeed;
            sprite1.ySpeed = sprite2.ySpeed;
            sprite2.xSpeed = tempx;
            sprite2.ySpeed = tempy;
        }
    }

    private boolean detectCollisions(){
        return Math.pow(sprite1.getXPosition()-sprite2.getXPosition(), 2) +
                Math.pow(sprite1.getYPosition()-sprite2.getYPosition(), 2) <
                Math.pow(2*Sprite.radius,2);
    }

}
