package com.example.paayu.mazeboxpro;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Ball extends View {
    private float mPosX = (float) Math.random();
    private float mPosY = (float) Math.random();
    private float mOldPosX = (float) Math.random();
    private float mOldPosY = (float) Math.random();

    private float mVelX;
    private float mVelY;
    private float sBallDiameter;
    private long mLastT;

    private float mMetersToPixelsX;
    private float mMetersToPixelsY;

    public Ball(Context context, float diam,float mMetersToPixelsX,float mMetersToPixelsY)
    {

        super(context);
        sBallDiameter = diam;
        setBackgroundResource(R.drawable.ball);
        this.mMetersToPixelsX=mMetersToPixelsX;
        this.mMetersToPixelsY=mMetersToPixelsY;
        mOldPosY=0;
        mOldPosX=0;
        setTranslationX(0);
        setTranslationY(0);
    }

    public Ball(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Ball(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Ball(Context context, AttributeSet attrs, int defStyleAttr,
                    int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void computePhysics(float sx, float sy, float dT) {

        final float ax = -sx/5;
        final float ay = -sy/5;


        mPosX += mVelX * dT + ax * dT * dT / 2;
        mPosY += mVelY * dT + ay * dT * dT / 2;

        mVelX += ax * dT;
        //mVelX = mVelX/1.5f;
        mVelY += ay * dT;
        //mVelY = mVelY/1.5f;
    }

    /*
     * Resolving constraints and collisions with the Verlet integrator
     * can be very simple, we simply need to move a colliding or
     * constrained particle in such way that the constraint is
     * satisfied.
     */
    public void resolveCollisionWithBounds(float mHorizontalBound, float mVerticalBound, BrickConfiguration config,  float xOrigin, float yOrigin, float sx, float sy) {
        float x =   mPosX*mMetersToPixelsX;
        float y =  - mPosY*mMetersToPixelsY;
        float radius = (sBallDiameter/2);
        float xCenter = (x+(sBallDiameter*mMetersToPixelsX)/2);
        float yCenter = ( y + (sBallDiameter*mMetersToPixelsY)/2);
        float oldXCenter = (mOldPosX*mMetersToPixelsX + radius*mMetersToPixelsX);
        float oldYCenter = ((-mOldPosY)*mMetersToPixelsY + radius*mMetersToPixelsY);

        //Check for detection with bricks

        config.startIterating();

        while(config.hasMoreConfig())
        {
            BrickConfiguration.Configuration brickConfig = config.getNextConfiguration();

            if( (xCenter > (brickConfig.getX() - radius*mMetersToPixelsX)) && (xCenter < (brickConfig.getX()+ brickConfig.getWidth()+radius*mMetersToPixelsX)) && (yCenter > (brickConfig.getY() - radius*mMetersToPixelsY)) && (yCenter < (brickConfig.getY()+brickConfig.getHeight()+radius*mMetersToPixelsY)))
            {

                if(oldXCenter < (brickConfig.getX() - radius*mMetersToPixelsX)) {
                    mPosX = mOldPosX;
                    mVelX=0;
                }

                else if(oldXCenter > (brickConfig.getX()+ brickConfig.getWidth()+radius*mMetersToPixelsX)) {
                    mPosX = mOldPosX;
                    mVelX = 0;
                }

                else if(oldYCenter <  (brickConfig.getY() - radius*mMetersToPixelsY)) {
                    mPosY = mOldPosY;
                    mVelY = 0;
                }

                else if(oldYCenter > (brickConfig.getY()+brickConfig.getHeight()+radius*mMetersToPixelsY)) {
                    mPosY = mOldPosY;
                    mVelY = 0;
                }

                break;
            }
            else if( ( (oldXCenter < (brickConfig.getX() - radius*mMetersToPixelsX)) && (xCenter > (brickConfig.getX() - radius*mMetersToPixelsX)) ) || ( (oldXCenter > (brickConfig.getX()+ brickConfig.getWidth()+radius*mMetersToPixelsX)) && (xCenter < (brickConfig.getX()+ brickConfig.getWidth()+radius*mMetersToPixelsX))))
            {
                if((oldYCenter > (brickConfig.getY() - radius*mMetersToPixelsY)) && (oldYCenter < (brickConfig.getY()+brickConfig.getHeight()+radius*mMetersToPixelsY)))
                {
                    mPosX = mOldPosX;
                    mVelX=0;
                }
                break;
            }

            else if( ( (oldYCenter <  (brickConfig.getY() - radius*mMetersToPixelsY)) && (yCenter > (brickConfig.getY() - radius*mMetersToPixelsY))) || ((oldYCenter > (brickConfig.getY()+brickConfig.getHeight()+radius*mMetersToPixelsY)) && (yCenter < (brickConfig.getY()+brickConfig.getHeight()+radius*mMetersToPixelsY))))
            {
                if((oldXCenter > (brickConfig.getX() - radius*mMetersToPixelsX)) && (oldXCenter < (brickConfig.getX()+ brickConfig.getWidth()+radius*mMetersToPixelsX))) {
                    mPosY = mOldPosY;
                    mVelY = 0;
                }
                break;
            }
        }

        //Check for detection with boundaries

        if (x < xOrigin)
        {
            mPosX = 0;
            mVelX = 0;
        }
        else if (x > mHorizontalBound)
        {
            mPosX = mHorizontalBound/mMetersToPixelsX;
            mVelX = 0;
        }

        if (y < yOrigin)
        {
            mPosY = 0;
            mVelY = 0;
        }
        else if (y > mVerticalBound)
        {
            mPosY = -(mVerticalBound/mMetersToPixelsY);
            mVelY = 0;
        }
    }

    /*
        * Update the position of each particle in the system using the
        * Verlet integrator.
        */
    public void updatePositions(float sx, float sy, long timestamp, float mHorizontalBound, float mVerticalBound, BrickConfiguration brickConfig, float xOrigin, float yOrigin) {
        final long t = timestamp;
        if (mLastT != 0) {
            final float dT = (float) (t - mLastT) / 1000.f /** (1.0f / 1000000000.0f)*/;

            computePhysics(sx, sy, dT);
            resolveCollisionWithBounds(mHorizontalBound, mVerticalBound, brickConfig,  xOrigin,  yOrigin, sx, sy);

            mOldPosX = mPosX;
            mOldPosY = mPosY;
        }
        mLastT = t;
    }

    public float getPosX() {
        return mPosX;
    }

    public float getPosY() {
        return mPosY;
    }

}
