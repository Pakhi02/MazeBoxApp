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
        private float sBallDiameter2;
        private long mLastT;

        private float mMetersToPixelsX;
        private float mMetersToPixelsY;

    public Ball(Context context, float diam,float mMetersToPixelsX,float mMetersToPixelsY) {
            super(context);
        sBallDiameter = diam;
        sBallDiameter2 = diam*diam;
        setBackgroundResource(R.drawable.ball);
        this.mMetersToPixelsX=mMetersToPixelsX;
        this.mMetersToPixelsY=mMetersToPixelsY;
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
            mOldPosX = mPosX;
            mOldPosY = mPosY;


            mPosX += mVelX * dT + ax * dT * dT / 2;
            mPosY += mVelY * dT + ay * dT * dT / 2;

            mVelX += ax * dT;
            mVelX = mVelX/1.5f;
            mVelY += ay * dT;
            mVelY = mVelY/1.5f;
        }

        /*
         * Resolving constraints and collisions with the Verlet integrator
         * can be very simple, we simply need to move a colliding or
         * constrained particle in such way that the constraint is
         * satisfied.
         */
        public void resolveCollisionWithBounds(float mHorizontalBound, float mVerticalBound, BrickConfiguration config,  float xOrigin, float yOrigin, float sx, float sy) {
            final float xmax = mHorizontalBound;
            final float ymax = mVerticalBound;
            float x =   mPosX*mMetersToPixelsX;
            float y =  - mPosY*mMetersToPixelsY;

//            if(sx>0)
//                x=-x;
////            else
////                x=2*x;
//
//            if(sy>0)
//                y=-y;

            //Check for detection with bricks
int found = 0;
            config.startIterating();
            while(config.hasMoreConfig() && (found == 0))
            {
                BrickConfiguration.Configuration brickConfig = config.getNextConfiguration();

                if((sx>0) && (x >brickConfig.getX()) && ( x< (brickConfig.getX()+brickConfig.getWidth()))  && (y>brickConfig.getY()) && (y<(brickConfig.getY()+brickConfig.getHeight())))
                {
                    //if(sx<0)
                        //mPosX = (brickConfig.getX() -sBallDiameter*mMetersToPixelsX)/mMetersToPixelsX;
//                        else
                        mPosX = (brickConfig.getX()+brickConfig.getWidth())/mMetersToPixelsX;
                    mVelX = 0;
                    found = 1;
                }

                else if((sx<0) && ((x+sBallDiameter*mMetersToPixelsX) >brickConfig.getX()) && ( (x+sBallDiameter*mMetersToPixelsX)< (brickConfig.getX()+brickConfig.getWidth()))  && (y>brickConfig.getY()) && (y<(brickConfig.getY()+brickConfig.getHeight())))
                {
                   // if(sx<0)
                        mPosX = (brickConfig.getX() -sBallDiameter*mMetersToPixelsX)/mMetersToPixelsX;
//                    else
//                        mPosX = (brickConfig.getX()+brickConfig.getWidth())/mMetersToPixelsX;
                    mVelX = 0;
                    found = 1;
                }

                if((sy<0) && (y >brickConfig.getY()) && ( y< (brickConfig.getY()+brickConfig.getHeight()))  && (x >brickConfig.getX()) && ( x< (brickConfig.getX()+brickConfig.getWidth())))
                {

                    mPosY = (brickConfig.getY()+brickConfig.getHeight())/mMetersToPixelsY;
                    mPosY = -mPosY;
                    mVelY = 0;
                    found = 1;
                }
//                else if((sy>0) && ((y+sBallDiameter*mMetersToPixelsY) >brickConfig.getY()) && ( (y+sBallDiameter*mMetersToPixelsY)< (brickConfig.getY()+brickConfig.getHeight()))  && (x >brickConfig.getX()) && ( x< (brickConfig.getX()+brickConfig.getWidth())))
//                {
//                    //if(sx<0)
//                    //mPosX = (brickConfig.getX() -sBallDiameter*mMetersToPixelsX)/mMetersToPixelsX;
////                        else
//                    mPosY = (brickConfig.getY()-sBallDiameter*mMetersToPixelsY)/mMetersToPixelsY;
//                    mPosY = -mPosY;
//                    mVelY = 0;
//                    found = 1;
//                }

                //else if(mPosX < mOldPosX)
//                {
//                    float xStart=(brickConfig.getX()/mMetersToPixelsX);
//                    float xEnd=((brickConfig.getX()+brickConfig.getWidth())/mMetersToPixelsX);
//                    //Coming from right
//                    if( (x >xStart) && ( x< xEnd) )
//                    {
//                        mPosX = xEnd;
//                        mVelX = 0;
//                        found = 1;
//                    }
//                }

//                if(mPosY > mOldPosY)
//                {
//                    float yStart=(brickConfig.getY()/mMetersToPixelsY);
//                    float yEnd=((brickConfig.getY()+brickConfig.getHeight())/mMetersToPixelsY);
//                    //Coming from left
//                    if( (y >yStart) && ( y< yEnd) )
//                    {
//                        mPosY =yStart;
//                        mVelY = 0;
//                        found = 1;
//                    }
//                }
//                else if(mPosX < mOldPosX)
//                {
//                    float yStart=(brickConfig.getY()/mMetersToPixelsY);
//                    float yEnd=((brickConfig.getY()+brickConfig.getHeight())/mMetersToPixelsY);
//                    //Coming from right
//                    if( (y >yStart) && ( y< yEnd) )
//                    {
//                        mPosY = yEnd;
//                        mVelY = 0;
//                        found = 1;
//                    }
                //}

            }

            //Check for detection with boundaries

            if (x < xOrigin) {
                mPosX = 0; //xOrigin/mMetersToPixelsX
                mVelX = 0;
            } else if (x > mHorizontalBound) {
                mPosX = mHorizontalBound/mMetersToPixelsX;
                mVelX = 0;
            }


            if (y < yOrigin) {
                mPosY = 0; //yOrigin/mMetersToPixelsY
                mVelY = 0;
            }
            else if (y > mVerticalBound) {
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
