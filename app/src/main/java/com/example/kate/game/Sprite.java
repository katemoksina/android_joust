package com.example.kate.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by kate on 26/05/2017.
 */

public class Sprite {
    static float acceleration = 1;
    private int xPosition;
    private int yPosition;
    float xSpeed = 0;
    float ySpeed = 0;
    static int radius;
    
    
    public Sprite(int xPosition, int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public static int getRadius(){
        return Sprite.radius;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void increaseLeft() {
        this.xSpeed -= acceleration;
    }
    public void decreaseLeft() {
        if(this.xSpeed<0){
            this.xSpeed = Math.min(0, this.xSpeed+acceleration);
        }
    }
    public void increaseRight() {
        this.xSpeed += acceleration;
    }
    public void decreaseRight() {
        if(this.xSpeed>0){
            this.xSpeed = Math.max(0, this.xSpeed-acceleration);
        }
    }
    public void jump() {
        if(this.yPosition == MainActivity.BARSTARTY-radius) {
            this.ySpeed = -15;
        }
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawCircle(this.getXPosition(), this.getYPosition(), Sprite.radius, paint);
    }

    public void updatePositions() {
        if (this.xSpeed > 0) {
            this.xSpeed = Math.min(this.xSpeed, 20);
        }else{
            this.xSpeed = Math.max(this.xSpeed, -20);
        }
//        this.yPosition +=10;
        this.xPosition += Math.round(this.xSpeed);
        this.yPosition += Math.round(this.ySpeed);


        if (this.yPosition>MainActivity.BARSTARTY-Sprite.radius && this.yPosition<=MainActivity.BARSTARTY+2*Sprite.radius &&
                this.xPosition>=MainActivity.BARSTARTX && this.xPosition<=MainActivity.BARSTARTX+MainActivity.BARLENGTH) {
            this.ySpeed = 0;
            this.yPosition = MainActivity.BARSTARTY-Sprite.radius;
        }else{
            this.ySpeed+=acceleration;
        }
    }
}

//}
