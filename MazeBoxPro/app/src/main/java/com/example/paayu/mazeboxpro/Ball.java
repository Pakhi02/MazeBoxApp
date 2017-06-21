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

    public Ball(Context context, float diam, float mMetersToPixelsX, float mMetersToPixelsY) {

        super(context);
        sBallDiameter = diam;
        setBackgroundResource(R.drawable.ball);
        this.mMetersToPixelsX = mMetersToPixelsX;
        this.mMetersToPixelsY = mMetersToPixelsY;
        mOldPosY = 0;
        mOldPosX = 0;
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

        final float ax = -sx / 5;
        final float ay = -sy / 5;


        mPosX += mVelX * dT + ax * dT * dT / 2;
        mPosY += mVelY * dT + ay * dT * dT / 2;

        mVelX += ax * dT;
        mVelX = mVelX / 1f;
        mVelY += ay * dT;
        mVelY = mVelY / 1f;
    }


    /*
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

                if(oldXCenter < (brickConfig.getX() - radius*mMetersToPixelsX))
                    mPosX = mOldPosX;

                else if(oldXCenter > (brickConfig.getX()+ brickConfig.getWidth()+radius*mMetersToPixelsX))
                    mPosX = mOldPosX;

                else if(oldYCenter <  (brickConfig.getY() - radius*mMetersToPixelsY))
                    mPosY = mOldPosY;

                else if(oldYCenter > (brickConfig.getY()+brickConfig.getHeight()+radius*mMetersToPixelsY))
                    mPosY=mOldPosY;

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
     */
    public Point takeAWalk(double x1, double y1, double x2, double y2, BrickConfiguration config, float mHorizontalBound, float mVerticalBound, float xOrigin, float yOrigin) {

        double slope = (y2 - y1) / (x2 - x1);
        double stepDistance = 1;
        double newX = x1, newY = y1;
        double oldX = x1, oldY = y1;

        boolean isBoundary = false;
        if (x2 < xOrigin) {
            x2 = 0;
            //mPosX =0;
            mVelX = 0;
            isBoundary = true;
        } else if (x2 > mHorizontalBound) {
            x2 = mHorizontalBound;
            mVelX = 0;
            //mPosX = mHorizontalBound/mMetersToPixelsX;
            isBoundary = true;
        }

        if (y2 < yOrigin) {
            y2 = 0;
            //mPosY=0;
            mVelY = 0;
            isBoundary = true;
        } else if (y2 > mVerticalBound) {
            y2 = mVerticalBound;
            //mPosY = -mVerticalBound/mMetersToPixelsY;
            mVelY = 0;
            isBoundary = true;
        }


        double xSign = x2 - x1, ySign = y2 - y1;
        while ((xSign > 0 && (x2 - newX) > 0) || (xSign < 0 && (x2 - newX) < 0) || (ySign > 0 && (y2 - newY) > 0) || (ySign < 0 && (y2 - newY) < 0)) {

            oldX = newX;
            oldY = newY;
            double tDistance;
            tDistance = Math.sqrt((x2 - newX) * (x2 - newX) + (y2 - newY) * (y2 - newY));
            newX = newX + (stepDistance / tDistance) * (x2 - newX);
            newY = newY + (stepDistance / tDistance) * (y2 - newY);

            int result = collidingOnBrick(newX, newY, config, mHorizontalBound, mVerticalBound, xOrigin, yOrigin);
            if (result != -1) {
                BrickConfiguration.Configuration culpritBrick = config.getBrickAtIndex(result);

                if(culpritBrick.getType() == 1)
                {
                    return new Point(-1,-1);
                }

                double radiusX = (sBallDiameter * mMetersToPixelsX) / 2;
                double radiusY = (sBallDiameter * mMetersToPixelsY) / 2;

                double oldBallCentreX = (oldX + radiusX);
                double oldBallCentreY = (oldY + radiusY);

//                //ball is on Left side of brick
//                if (oldBallCentreX < (culpritBrick.getX() - radiusX) && oldBallCentreY < (culpritBrick.getY() + culpritBrick.getHeight() + radiusY) && oldBallCentreY > culpritBrick.getY() - radiusY)
//                    mVelX = -mVelX / 4;
//                else
//                    //ball is on Right side of brick
//                    if (oldBallCentreX > (culpritBrick.getX() + culpritBrick.getWidth() +radiusX) && oldBallCentreY < (culpritBrick.getY() + culpritBrick.getHeight() + radiusY) && oldBallCentreY > culpritBrick.getY() - radiusY)
//                        mVelX = -mVelX / 4;
//                    else
//                        //ball is on Top side of brick
//                        if (oldBallCentreY < (culpritBrick.getY() - radiusY) && oldBallCentreX < (culpritBrick.getX() + culpritBrick.getWidth() + radiusX) && oldBallCentreX > culpritBrick.getX() - radiusX)
//                            mVelY = -mVelY / 4;
//                        else
//                            //ball is on Bottom side of brick
//                            if (oldBallCentreY > (culpritBrick.getY() +culpritBrick.getHeight()+ radiusY) && oldBallCentreX < (culpritBrick.getX() + culpritBrick.getWidth() + radiusX) && oldBallCentreX > culpritBrick.getX() - radiusX)
//                                mVelY = -mVelY / 4;


                //ball is on Left/right side of brick
                //if( (oldBallCentreX > (culpritBrick.getX() - radiusX) ) && (oldBallCentreX < (culpritBrick.getX() + culpritBrick.getWidth() +radiusX) ))
                if( (oldBallCentreX < (culpritBrick.getX() - radiusX) ) || (oldBallCentreX > (culpritBrick.getX() + culpritBrick.getWidth() +radiusX) ))
                {
                    mVelX = 0;
                }
                else {
                    oldX = x2;
                }

                //ball is on Top/bottom side of brick
                //if( (oldBallCentreY > (culpritBrick.getY() - radiusY) ) && (oldBallCentreY < (culpritBrick.getY() +culpritBrick.getHeight()+ radiusY) ))
                if( (oldBallCentreY < (culpritBrick.getY() - radiusY) ) || (oldBallCentreY > (culpritBrick.getY() +culpritBrick.getHeight()+ radiusY) )) {
                    mVelY = 0;
                }
                else {
                    oldY = y2;
                }

                break;
            }

        }

        return new Point(oldX, oldY);

    }

    int collidingOnBrick(double x, double y, BrickConfiguration config, float mHorizontalBound, float mVerticalBound, float xOrigin, float yOrigin) {

        double radiusX = (sBallDiameter * mMetersToPixelsX) / 2;
        double radiusY = (sBallDiameter * mMetersToPixelsY) / 2;

        double ballCentreX = (x + radiusX);
        double ballCentreY = (y + radiusY);


        config.startIterating();
        int jj = 0;
        while (config.hasMoreContinousConfig()) {
            BrickConfiguration.Configuration brickConfig = config.getNextContinousConfiguration();

            if ((ballCentreX > (brickConfig.getX() - radiusX)) && (ballCentreX < (brickConfig.getX() + brickConfig.getWidth() + radiusX)) && (ballCentreY > (brickConfig.getY() - radiusY)) && (ballCentreY < (brickConfig.getY() + brickConfig.getHeight() + radiusY))) {
                return jj;
            }
            jj++;
        }
        return -1;
    }


    public boolean resolveCollisionWithBounds(float mHorizontalBound, float mVerticalBound, BrickConfiguration config, float xOrigin, float yOrigin, float sx, float sy) {

        //Check for detection with bricks
        double x = mPosX * mMetersToPixelsX;
        double y = -mPosY * mMetersToPixelsY;
        Point finalPosition = takeAWalk(mOldPosX * mMetersToPixelsX, -mOldPosY * mMetersToPixelsY, x, y, config, mHorizontalBound, mVerticalBound, xOrigin, yOrigin);

        if(finalPosition.x==-1 && finalPosition.y==-1)
            return false;

        x = finalPosition.x / mMetersToPixelsX;
        y = -finalPosition.y / mMetersToPixelsY;
        //Check for detection with boundaries
        mPosX = (float) x;
        mPosY = (float) y;
        return true;
    }

    /*
        * Update the position of each particle in the system using the
        * Verlet integrator.
        */
    public boolean updatePositions(float sx, float sy, long timestamp, float mHorizontalBound, float mVerticalBound, BrickConfiguration brickConfig, float xOrigin, float yOrigin) {
        final long t = timestamp;
        boolean result =true;
        if (mLastT != 0) {
            final float dT = (float) (t - mLastT) / 1000.f /** (1.0f / 1000000000.0f)*/;

            computePhysics(sx, sy, dT);
             result = resolveCollisionWithBounds(mHorizontalBound, mVerticalBound, brickConfig, xOrigin, yOrigin, sx, sy);

            mOldPosX = mPosX;
            mOldPosY = mPosY;
        }
        mLastT = t;
        return result;
    }

    public float getPosX() {
        return mPosX;
    }

    public float getPosY() {
        return mPosY;
    }

}

class Point {
    double x;
    double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}