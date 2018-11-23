package com.android.cis195.taras.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Taras on 12/4/17.
 */

public class Defender extends GameObj {

    //Constants
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public static final double INIT_X_SCALE = 300.0 / 700;
    public static final double INIT_Y_SCALE = 600.0 / 700;
    public static final double WIDTH_SCALE = 39.0 / 700;
    public static final double HEIGHT_SCALE = 24.0 / 700;
    public static Bitmap BITMAP;

    //standard constructor
    public Defender(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, (int) (INIT_X_SCALE * courtWidth), (int) (INIT_Y_SCALE * courtHeight),
                (int) (WIDTH_SCALE * courtWidth), (int) (HEIGHT_SCALE * courtHeight), courtWidth, courtHeight);
    }

    //creates a new laser that player shoots
    public Laser shoot() {
        return new Laser(Laser.centered(this.getPx(), (int) (WIDTH_SCALE * getCourtWidth()),
                (int) (Laser.WIDTH_SCALE * getCourtWidth())), this.getPy() - (int)(Laser.HEIGHT_SCALE * getCourtHeight()) - 1,
                LaserType.PLAYER, this.getCourtWidth(), this.getCourtHeight());
    }

    //draws defender based on graphics context
    public void paint(Canvas canvas) {
        canvas.drawBitmap(BITMAP, getPx(), getPy(), null);
    }
}
