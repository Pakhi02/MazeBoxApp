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
        mVelX = mVelX/1.5f;
        mVelY += ay * dT;
        mVelY = mVelY/1.5f;
    }


    public Point takeAWalk(double x1,double y1,double x2,double y2,BrickConfiguration config,float mHorizontalBound, float mVerticalBound, float xOrigin, float yOrigin){

        double slope = (y2-y1)/(x2-x1);
        double stepDistance=5;
        double newX=x1,newY=y1;
        double oldX=x1,oldY=y1;


        if (x2 < xOrigin)
        {
            x2 = 0;
            mVelX = 0;
        }
        else if (x2 > mHorizontalBound)
        {
            x2 = mHorizontalBound;
            mVelX = 0;
        }

        if (y2 < yOrigin)
        {
            y2 = 0;
            mVelY = 0;
        }
        else if (y2 > mVerticalBound)
        {
            y2 = (mVerticalBound);
            mVelY = 0;
        }
        if( x1!=x2 || y1!=y2 )
            return new Point(x2,y2);

        double xSign = x2-x1 ,ySign = y2-y1;
        while( (xSign>0 && x2-newX>0) || (xSign<0 && x2-newX<0) || (ySign>0 && y2-newY>0) || (ySign<0 && y2-newY<0) ) {

            oldX=newX;
            oldY=newY;

            if (x2 < x1) {
                newX = newX - (stepDistance / Math.sqrt(1 + slope * slope));
            } else {
                newX = newX + (stepDistance / Math.sqrt(1 + slope * slope));
            }

            if (y2 < y1) {
                newY = newY - (stepDistance / Math.sqrt(1 + slope * slope));
            } else {
                newY = newY + (stepDistance / Math.sqrt(1 + slope * slope));
            }

            if(isColliding(newX,newY,config,mHorizontalBound,mVerticalBound,xOrigin,yOrigin)) {
                mVelX=0;
                mVelY=0;
                break;
            }

        }

        return new Point(oldX,oldY);

    }

    boolean isColliding(double x,double y,BrickConfiguration config,float mHorizontalBound, float mVerticalBound, float xOrigin, float yOrigin){

        double radiusX = (sBallDiameter*mMetersToPixelsX)/2;
        double radiusY = (sBallDiameter*mMetersToPixelsY)/2;

        double ballCentreX = (x + radiusX);
        double ballCentreY = (y + radiusY);




        config.startIterating();
        while(config.hasMoreConfig())
        {
            BrickConfiguration.Configuration brickConfig = config.getNextConfiguration();

            if( (ballCentreX > (brickConfig.getX() - radiusX)) && (ballCentreX < (brickConfig.getX()+ brickConfig.getWidth()+radiusX)) && (ballCentreY > (brickConfig.getY() - radiusY)) && (ballCentreY < (brickConfig.getY()+brickConfig.getHeight()+radiusY)))
            {
                return true;
            }
        }
        return false;
    }


    public void resolveCollisionWithBounds(float mHorizontalBound, float mVerticalBound, BrickConfiguration config,  float xOrigin, float yOrigin, float sx, float sy) {

        //Check for detection with bricks
        double x =   mPosX*mMetersToPixelsX;
        double y =  - mPosY*mMetersToPixelsY;
        Point finalPosition = takeAWalk(mOldPosX*mMetersToPixelsX,mOldPosY*mMetersToPixelsY,x,y,config,mHorizontalBound,mVerticalBound,xOrigin,yOrigin);


        x = finalPosition.x/mMetersToPixelsX;
        y = finalPosition.y/mMetersToPixelsY;
        //Check for detection with boundaries
        mPosX = (float)x;
        mPosY = (float)y;
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
class Point{
    double x;
    double y;
    Point(double x,double y){
        this.x=x;
        this.y=y;
    }
}