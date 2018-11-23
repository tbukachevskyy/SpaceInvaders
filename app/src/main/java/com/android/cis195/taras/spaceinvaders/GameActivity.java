package com.android.cis195.taras.spaceinvaders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements SensorEventListener{
    //Game objects
    static GameView gameView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private HashSet<Laser> invaderLasers;
    private Invader[][] invaders;
    private Defender player;
    private BonusAlien bonusAlien;
    private Laser playerLaser;
    private String playerName;
    private Timer timer;

    //Game States
    private boolean invadersMovingRight;
    private int countToMovement;
    private int nextCount;

    //Game Constants
    private int courtWidth;
    private int courtHeight;
    private int invaderVelX;
    private int invaderVelY;
    private int bonusAlienVel;
    private int initInvY;
    private int hBuffer;
    private int vBuffer;
    private int initInvX;
    public static final double SHOT_CHANCE = 0.003;
    public static final double SPAWN_CHANCE = 0.001;
    private static final int MAIN_INTERVAL = 35;
    private static final int LASER_INTERVAL = 100;
    public static final int DIFFICULTY_INTERVAL = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        courtWidth = displaymetrics.widthPixels;
        courtHeight = displaymetrics.widthPixels;

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.fast_invader);
        BonusAlien.BITMAP = Bitmap.createScaledBitmap(bm, (int)(BonusAlien.WIDTH_SCALE * courtWidth),
                (int)(BonusAlien.HEIGHT_SCALE * courtHeight), false);
        bm.recycle();
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.player);
        Defender.BITMAP = Bitmap.createScaledBitmap(bm, (int)(Defender.WIDTH_SCALE * courtWidth),
                (int)(Defender.HEIGHT_SCALE * courtHeight), false);
        bm.recycle();
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.laser_player);
        Laser.LASERS[0] = Bitmap.createScaledBitmap(bm, (int)(Laser.WIDTH_SCALE * courtWidth),
                (int)(Laser.HEIGHT_SCALE * courtHeight), false);
        bm.recycle();
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.laser_slow);
        Laser.LASERS[1] = Bitmap.createScaledBitmap(bm, (int)(Laser.WIDTH_SCALE * courtWidth),
                (int)(Laser.HEIGHT_SCALE * courtHeight), false);
        bm .recycle();
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.laser_fast);
        Laser.LASERS[2] = Bitmap.createScaledBitmap(bm, (int)(Laser.WIDTH_SCALE * courtWidth),
                (int)(Laser.HEIGHT_SCALE * courtHeight), false);
        bm.recycle();
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.invader0_closed);
        Invader0.CLOSED_FORM = Bitmap.createScaledBitmap(bm, (int)(Invader0.WIDTH_SCALE * courtWidth),
                (int)(Invader0.HEIGHT_SCALE * courtHeight), false);
        bm.recycle();
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.invader0_open);
        Invader0.OPEN_FORM = Bitmap.createScaledBitmap(bm, (int)(Invader0.WIDTH_SCALE * courtWidth),
                (int)(Invader0.HEIGHT_SCALE * courtHeight), false);bm.recycle();
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.invader1_closed);
        Invader1.CLOSED_FORM = Bitmap.createScaledBitmap(bm, (int)(Invader1.WIDTH_SCALE * courtWidth),
                (int)(Invader1.HEIGHT_SCALE * courtHeight), false);
        bm.recycle();
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.invader1_open);
        Invader1.OPEN_FORM = Bitmap.createScaledBitmap(bm, (int)(Invader1.WIDTH_SCALE * courtWidth),
                (int)(Invader1.HEIGHT_SCALE * courtHeight), false);bm.recycle();
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.invader2_closed);
        Invader2.CLOSED_FORM = Bitmap.createScaledBitmap(bm, (int)(Invader2.WIDTH_SCALE * courtWidth),
                (int)(Invader2.HEIGHT_SCALE * courtHeight), false);
        bm.recycle();
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.invader2_open);
        Invader2.OPEN_FORM = Bitmap.createScaledBitmap(bm, (int)(Invader2.WIDTH_SCALE * courtWidth),
                (int)(Invader2.HEIGHT_SCALE * courtHeight), false);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        playerName = (String) intent.getStringExtra("name");
        int invader0Width = (int)(Invader0.WIDTH_SCALE * courtWidth);
        int invader1Width = (int)(Invader1.WIDTH_SCALE * courtWidth);
        int invader2Width = (int)(Invader2.WIDTH_SCALE * courtWidth);
        invaderVelX = courtWidth / 55;
        invaderVelY = invaderVelX * 3;
        bonusAlienVel = courtWidth / 140;
        initInvY = (int) (courtHeight / 3.5);
        hBuffer = courtWidth / 58;
        vBuffer = (int)(courtWidth / 87.5);
        initInvX = (courtWidth - 11 * (hBuffer + invader0Width)) / 2;
        this.invadersMovingRight = true;
        this.invaderLasers = new HashSet<Laser>();
        this.invaders = new Invader[11][5];
        this.player = new Defender(courtWidth, courtHeight);
        this.bonusAlien = null;
        this.playerLaser = null;
        this.countToMovement = 30;
        this.nextCount = 30;
        this.gameView = new GameView(this, courtWidth, courtHeight);
        setContentView(gameView);
        gameView.gameObjects.add(this.player);

        for (int i = 0; i < invaders.length; i++) {
            for (int j = 0; j < invaders[0].length; j++) {
                if (j == 0) {
                    invaders[i][j] = new Invader2(GameObj.centered(initInvX + i *
                            (hBuffer + invader0Width), invader0Width, invader2Width),
                            initInvY + (j * (vBuffer + (int)(Invader2.HEIGHT_SCALE * courtHeight))),
                            courtWidth, courtHeight);
                }
                else if (j == 1 || j == 2) {
                    invaders[i][j] = new Invader1(GameObj.centered(initInvX + i *
                            (hBuffer + invader0Width), invader0Width, invader1Width),
                            initInvY + (j * (vBuffer + (int)(Invader1.HEIGHT_SCALE * courtHeight))),
                            courtWidth, courtHeight);
                }
                else if (j == 3 || j == 4) {
                    invaders[i][j] = new Invader0(initInvX + i * (hBuffer + invader0Width),
                            initInvY + (j * (vBuffer + (int)(Invader0.HEIGHT_SCALE * courtHeight))),
                            courtWidth, courtHeight);
                }
                gameView.gameObjects.add(invaders[i][j]);
            }
        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mainTick();
                if (countToMovement == 0) {
                    invaderTick();
                    countToMovement = nextCount;
                }
                countToMovement--;
            }
        }, 0, MAIN_INTERVAL);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                nextCount = (int) (0.7 * nextCount);
            }
        }, 0, DIFFICULTY_INTERVAL);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Laser laser: invaderLasers) {
                    laser.invert();
                }
            }
        }, 0, LASER_INTERVAL);

        gameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerLaser == null) {
                    playerLaser = player.shoot();
                    gameView.gameObjects.add(playerLaser);
                }
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
                , SensorManager.SENSOR_DELAY_FASTEST);

        gameView.start();
    }

    /* Controls invader movements when they hit a wall.
     * Tells them to move down and then switch directions */
    private void invadersBounce() {
        boolean movingDown = false;
        boolean hitWall = false;
        for (Invader[] invaderColumn: invaders) {
            for (Invader invader: invaderColumn) {
                if (invader.hitWall() != null && invader.isAlive()) hitWall = true;
                else if (invader.getVy() != 0) movingDown = true;
            }
        }
        for (Invader[] invaderColumn: invaders) {
            for (Invader invader: invaderColumn) {
                if (movingDown) {
                    invader.setVy(0);
                    if (invadersMovingRight) invader.setVx(-invaderVelX);
                    else invader.setVx(invaderVelX);
                }
                else if (hitWall) {
                    invader.setVy(invaderVelY);
                    invader.setVx(0);
                }
            }
        }
        if (movingDown) this.invadersMovingRight = !this.invadersMovingRight;
    }

    private void spawnBonusAlien() {
        if (bonusAlien == null) {
            if(Math.random() < SPAWN_CHANCE) {
                if(Math.random() < 0.05) {
                    bonusAlien = new BonusAlien(bonusAlienVel,
                            -(int)(BonusAlien.WIDTH_SCALE * courtWidth) + 1, courtWidth, courtHeight);
                    gameView.gameObjects.add(bonusAlien);
                }
                else {
                    bonusAlien = new BonusAlien(-bonusAlienVel,
                            courtWidth - 1, courtWidth, courtHeight);
                    gameView.gameObjects.add(bonusAlien);
                }
            }
        }
    }

    //tick method for mainTimer
    private void mainTick() {
        if (gameView.playing) {

            boolean allInvadersDead = true;

            HashSet<Laser> lasersToRemove = new HashSet<Laser>();

            player.move();
            if (playerLaser != null) {
                playerLaser.move();
                if (playerLaser.outsideBorders()) {
                    gameView.gameObjects.remove(playerLaser);
                    playerLaser = null;
                }
            }


            //checks whether playerLaser hit bonusAlien
            if (bonusAlien != null && playerLaser != null) {
                if (playerLaser.intersects(bonusAlien)) {
                    gameView.points += bonusAlien.getPointVal();
                    gameView.gameObjects.remove(playerLaser);
                    playerLaser = null;
                    gameView.gameObjects.remove(bonusAlien);
                    bonusAlien = null;
                }
            }
            spawnBonusAlien();

            /* Moves invader lasers. If they hit the player,
             * the player loses a life and the lasers are removed*/
            for (Laser laser: invaderLasers) {
                laser.move();
                if (laser.intersects(player)) {
                    gameView.lives  = Math.max(0, gameView.lives - 1);
                    //game ends
                    if (gameView.lives == 0) {
                        endGame(false);
                    }
                    lasersToRemove.add(laser);
                }
                else if (laser.outsideBorders()) lasersToRemove.add(laser);
            }

            if (bonusAlien != null)  {
                bonusAlien.move();
                if (bonusAlien.outsideBorders()) {
                    gameView.gameObjects.remove(bonusAlien);
                    bonusAlien = null;
                }
            }

            /* Iterates through invaders starting with the bottom of each column.
             * Makes sure that only the bottom-most invader of each column fires
             * based on SHOT_CHANCE. Also checks whether playerLaser has hit any of the invaders*/
            for (Invader[] invaderColumn: invaders) {
                boolean fired = false;
                for (int i = invaderColumn.length - 1; i >= 0; i--) {
                    if (invaderColumn[i].isAlive()) allInvadersDead = false;
                    if (Math.random() < SHOT_CHANCE &&!fired) {
                        Laser l = invaderColumn[i].shoot();
                        if (l != null) {
                            invaderLasers.add(l);
                            gameView.gameObjects.add(l);
                        }
                    }
                    if (invaderColumn[i].isAlive()) fired = true;
                    if (playerLaser != null && playerLaser.intersects(invaderColumn[i]) &&
                            invaderColumn[i].isAlive()) {
                        invaderColumn[i].die();
                        gameView.points += invaderColumn[i].getPointVal();
                        gameView.gameObjects.remove(playerLaser);
                        playerLaser = null;
                    }
                    if ((invaderColumn[i].intersects(player) ||
                            invaderColumn[i].hitWall() == Direction.DOWN)
                            && invaderColumn[i].isAlive()) {
                        endGame(false);
                    }
                }
            }

            invaderLasers.removeAll(lasersToRemove);
            gameView.gameObjects.removeAll(lasersToRemove);

            if(allInvadersDead) {
                endGame(true);
            }
        }
    }

    /*tick method for invaderTimer. Moves each invader and changes their state. Also checks if
    invaders hit wall*/
    private void invaderTick() {
        if (gameView.playing) {
            invadersBounce();
            for (Invader[] invaderColumn: invaders) {
                for (Invader invader: invaderColumn) {
                    invader.move();
                    invader.changeState();
                }
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (Math.abs(sensorEvent.values[0]) > .5) {
            player.setVx( - (int) (3 * sensorEvent.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPause() {
        super.onPause();
        gameView.playing = false;
        timer.cancel();
    }

    private void endGame(boolean gameWon) {
        gameView.playing = false;
        Intent intent = new Intent(GameActivity.this, GameEndActivity.class);
        intent.putExtra("name", this.playerName);
        intent.putExtra("score", gameView.points);
        if (gameWon) {
            intent.putExtra("result", "You Won!");
        } else {
            intent.putExtra("result", "You Lost!");
        }
        startActivity(intent);
        gameView.end();
        timer.cancel();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.leaderboard) {
            startActivity(new Intent(GameActivity.this, LeaderboardActivity.class));
            return true;
        } else if (item.getItemId() == R.id.newGame) {
            startActivity(new Intent(GameActivity.this, PlayerActivity.class));
            return true;
        } else if (item.getItemId() == R.id.mainMenu) {
            startActivity(new Intent(GameActivity.this, TitleActivity.class));
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
