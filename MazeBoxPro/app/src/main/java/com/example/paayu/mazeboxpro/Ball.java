package com.example.paayu.mazeboxpro;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;


public class Ball extends View {
        private float mPosX = (float) Math.random();
        private float mPosY = (float) Math.random();
        private float mVelX;
        private float mVelY;
        private float sBallDiameter;
        private float sBallDiameter2;
        private long mLastT;

    public Ball(Context context, float diam) {
            super(context);
        sBallDiameter = diam;
        sBallDiameter2 = diam*diam;
        setBackgroundResource(R.drawable.ball);
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
            mVelY += ay * dT;
        }

        /*
         * Resolving constraints and collisions with the Verlet integrator
         * can be very simple, we simply need to move a colliding or
         * constrained particle in such way that the constraint is
         * satisfied.
         */
        public void resolveCollisionWithBounds() {
//            final float xmax = mHorizontalBound;
//            final float ymax = mVerticalBound;
            final float x = mPosX;
            final float y = mPosY;
//            if (x > xmax) {
//                mPosX = xmax;
//                mVelX = 0;
//            } else if (x < -xmax) {
//                mPosX = -xmax;
//                mVelX = 0;
//            }
//            if (y > ymax) {
//                mPosY = ymax;
//                mVelY = 0;
//            } else if (y < -ymax) {
//                mPosY = -ymax;
//                mVelY = 0;
//            }
//        }
    }
    /*
            * Update the position of each particle in the system using the
            * Verlet integrator.
            */
    public void updatePositions(float sx, float sy, long timestamp) {
        final long t = timestamp;
        if (mLastT != 0) {
            final float dT = (float) (t - mLastT) / 1000.f /** (1.0f / 1000000000.0f)*/;

                computePhysics(sx, sy, dT);

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
