package com.example.paayu.mazeboxpro;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class GameManager extends FrameLayout implements SensorEventListener{

    private float sBallDiameter;
    private float mXDpi;
    private float mYDpi;

    private final int mDstWidth;
    private final int mDstHeight;
    private float mMetersToPixelsX;
    private float mMetersToPixelsY;
    private float mXOrigin;
    private float mYOrigin;
    private float mSensorX;
    private float mSensorY;
    private float mHorizontalBound;
    private float mVerticalBound;

    MainActivity mMainObj;
    Ball mGameBall;
    BrickConfiguration brickConfig;

    public GameManager(Context context){
        super(context);

        mMainObj = (MainActivity) context;
        DisplayMetrics metrics = new DisplayMetrics();
        mMainObj.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        sBallDiameter = 0.004f;
        mXDpi = metrics.xdpi;
        mYDpi = metrics.ydpi;
        mMetersToPixelsX = mXDpi / 0.0254f; //converting inch to meter.
        mMetersToPixelsY = mYDpi / 0.0254f;
        mXOrigin = 0;
        mYOrigin = 0;

        this.setBackgroundResource(R.drawable.night_background);

        mDstWidth = (int) (sBallDiameter * mMetersToPixelsX);
        mDstHeight = (int) (sBallDiameter * mMetersToPixelsY);

        mGameBall = new Ball(mMainObj.getApplicationContext(), sBallDiameter,mMetersToPixelsX,mMetersToPixelsY);
        addView(mGameBall, new ViewGroup.LayoutParams(mDstWidth,mDstHeight));

        //This is done to call the OnDraw()
        setWillNotDraw(false);
    }

    void addBricks(BrickConfiguration config){
        config.startIterating();
        while(config.hasMoreConfig()){
            BrickConfiguration.Configuration brickConfig = config.getNextConfiguration();
            Brick brick=new Brick(this.getContext());
            FrameLayout.LayoutParams layoutParams =  new FrameLayout.LayoutParams((int)brickConfig.getWidth(),(int)brickConfig.getHeight());
            this.addView(brick,layoutParams);
            brick.setTranslationX(brickConfig.getX());
            brick.setTranslationY(brickConfig.getY());

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        mHorizontalBound = w - (sBallDiameter*mMetersToPixelsX);
        mVerticalBound = h - (sBallDiameter*mMetersToPixelsY);

        //Add the bricks according to the new width/height
        brickConfig = new BrickConfiguration(w,h);
        brickConfig.loadBrickData();
        addBricks(brickConfig);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {


        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;

            /*
             * record the accelerometer data, the event's timestamp as well as
             * the current time. The latter is needed so we can calculate the
             * "present" time during rendering. In this application, we need to
             * take into account how the screen is rotated with respect to the
             * sensors (which always return data in a coordinate space aligned
             * to with the screen in its native orientation).
             */

        switch (mMainObj.mDisplay.getRotation()) {
            case Surface.ROTATION_0:
                mSensorX = event.values[0];
                mSensorY = event.values[1];
                break;
            case Surface.ROTATION_90:
                mSensorX = -event.values[1];
                mSensorY = event.values[0];
                break;
            case Surface.ROTATION_180:
                mSensorX = -event.values[0];
                mSensorY = -event.values[1];
                break;
            case Surface.ROTATION_270:
                mSensorX = event.values[1];
                mSensorY = -event.values[0];
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
            /*
             * Compute the new position of our object, based on accelerometer
             * data and present time.
             */

        final long now = System.currentTimeMillis();
        final float sx = mSensorX;
        final float sy = mSensorY;

        mGameBall.updatePositions(sx, sy, now, mHorizontalBound, mVerticalBound, brickConfig, mXOrigin, mYOrigin);

        final float xc = mXOrigin;
        final float yc = mYOrigin;
        final float xs = mMetersToPixelsX;
        final float ys = mMetersToPixelsY;
        /*
         * We transform the canvas so that the coordinate system matches
         * the sensors coordinate system with the origin in the center
         * of the screen and the unit is the meter.
         */
        final float x = xc + mGameBall.getPosX() * xs;
        final float y = yc - mGameBall.getPosY() * ys;

        mGameBall.setTranslationX(x);
        mGameBall.setTranslationY(y);


        // and make sure to redraw asap
        invalidate();
    }



    public void startSimulation() {
        Log.v("called", "startSimulation");
            /*
             * It is not necessary to get accelerometer events at a very high
             * rate, by using a slower rate (SENSOR_DELAY_UI), we get an
             * automatic low-pass filter, which "extracts" the gravity component
             * of the acceleration. As an added benefit, we use less power and
             * CPU resources.
             */
        mMainObj.mSensorManager.registerListener(this, mMainObj.mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void stopSimulation() {
        Log.v("called", "stopSimulation");
        mMainObj.mSensorManager.unregisterListener(this);
    }

}
