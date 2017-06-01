package com.example.kate.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Created by kate on 01/06/2017.
 */

public class GameThread extends Thread {

    static MainActivity mainActivity;

    public GameThread(){

        super(new Runnable() {
            @Override
            public void run() {
                while (mainActivity.p1score<5 && mainActivity.p2score<5) {

                    mainActivity.sprite1 = new Sprite((int)Math.round(mainActivity.WIDTH*0.2), (int)Math.round(Sprite.radius));
                    mainActivity.sprite2 = new Sprite((int)Math.round(mainActivity.WIDTH*0.8), (int)Math.round(Sprite.radius));

                    while (mainActivity.sprite1.getYPosition() <= mainActivity.BARSTARTY + 2 * Sprite.radius && mainActivity.sprite2.getYPosition() <= mainActivity.BARSTARTY + 2 * Sprite.radius) {
                        Canvas canvas = mainActivity.holder.lockCanvas();
                        if (canvas == null){
                            continue;
                        }

                        mainActivity.updateVelocities();
                        mainActivity.sprite1.updatePositions();
                        mainActivity.sprite2.updatePositions();
                        canvas.drawColor(Color.BLACK);
                        canvas.drawText(Integer.toString(mainActivity.p1score) + ":" + Integer.toString(mainActivity.p2score), mainActivity.WIDTH / 2, mainActivity.HEIGHT / 3, mainActivity.grey);


                        mainActivity.sprite1.draw(canvas, mainActivity.grey);
                        mainActivity.sprite2.draw(canvas, mainActivity.grey);
                        canvas.drawRect(mainActivity.BARSTARTX, mainActivity.BARSTARTY, mainActivity.BARSTARTX + mainActivity.BARLENGTH, mainActivity.BARSTARTY + mainActivity.BARHEIGHT, mainActivity.grey);
                        mainActivity.holder.unlockCanvasAndPost(canvas);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {

                        }
                    }
                    if (mainActivity.sprite1.getYPosition() > mainActivity.BARSTARTY) {
                        mainActivity.p2score += 1;
                    }
                    if (mainActivity.sprite2.getYPosition() > mainActivity.BARSTARTY) {
                        mainActivity.p1score += 1;
                    }
                }
                Canvas canvas = mainActivity.holder.lockCanvas();
                canvas.drawColor(Color.BLACK);
                canvas.drawText(Integer.toString(mainActivity.p1score) + ":" + Integer.toString(mainActivity.p2score), mainActivity.WIDTH / 2, mainActivity.HEIGHT / 3, mainActivity.grey);
                String winner = "Player 1";
                if (mainActivity.p2score>mainActivity.p1score)
                    winner = "Player 2";
                canvas.drawText(winner+" wins!", mainActivity.WIDTH / 2, mainActivity.HEIGHT / 2, mainActivity.grey);
                mainActivity.holder.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException e){

                }
                Intent intent = new Intent(mainActivity, MainActivity.class);
                mainActivity.finish();
                mainActivity.startActivity(intent);
            }
        });

}
}
