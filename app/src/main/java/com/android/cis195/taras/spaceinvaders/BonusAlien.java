package com.android.cis195.taras.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.content.res.Resources;

/**
 * Created by Taras on 12/4/17.
 */

public class BonusAlien extends Alien {

    public static double INIT_POS_Y_SCALE = 50.0 / 700;
    public static double WIDTH_SCALE = 48.0 / 700; // width of invader
    public static double HEIGHT_SCALE = 21.0 / 700; // height of invader
    public static int POINT_VAL = 100;
    public static Bitmap BITMAP;

    // standard constructor
    public BonusAlien(int vx, int px, int courtWidth, int courtHeight) {
        super(vx, px, (int)(INIT_POS_Y_SCALE * courtHeight), (int)(WIDTH_SCALE * courtWidth),
                (int)(HEIGHT_SCALE * courtHeight), courtWidth, courtHeight, POINT_VAL);
    }


    //Draws invader based on graphics context
    @Override
    public void paint(Canvas canvas) {
        canvas.drawBitmap(BITMAP,getPx(), getPy(), null);
    }

}
