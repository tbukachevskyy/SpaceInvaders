package com.android.cis195.taras.spaceinvaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Taras on 12/5/17.
 */

public class GameView extends SurfaceView implements Runnable {
    Set<GameObj> gameObjects;
    private SurfaceHolder holder;
    volatile boolean playing;
    private Canvas canvas;
    Thread gameThread = null;
    int lives;
    int points;
    private int height;
    private int width;

    public GameView(Context context, int width, int height) {
        super(context);
        gameObjects = Collections.newSetFromMap(new ConcurrentHashMap<GameObj, Boolean>());
        holder = getHolder();
        playing = false;
        this.width = width;
        this.height = height;
        this.lives = 3;
        this.points = 0;
    }

    @Override
    public void run() {
        while (playing) {
            if (holder.getSurface().isValid()) {
                canvas = holder.lockCanvas();
                canvas.drawColor(Color.BLACK);
                for (GameObj o : gameObjects) {
                    if (o != null && canvas != null) o.paint(canvas);
                }
                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setTextSize(50);
                canvas.drawText("Lives: " + String.valueOf(lives), 100, height + 100, paint);
                canvas.drawText("Points: " + String.valueOf(points), width - 300, height + 100, paint);
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void start() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void end() {
        gameThread.interrupt();
    }
}
