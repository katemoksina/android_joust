package com.example.kate.game;

import android.graphics.Bitmap;

/**
 * Created by kate on 26/05/2017.
 */

public class Sprite {
    private int x=10;
    private int y=10;
    private int xSpeed = 25;
    private int ySpeed = 25;
    private GameView mView;
    private Bitmap mBitmap;

    public Sprite(GameView mView, Bitmap mBitmap) {
        this.mView=mView;
        this.mBitmap=mBitmap;
    }
}
