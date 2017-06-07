package com.example.paayu.mazeboxpro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.ViewGroup;
import android.view.ViewGroup;
import android.util.DisplayMetrics;

/**
 * Created by labuser on 6/7/17.
 */

public class GameManager implements SensorEventListener{

    private float mXDpi;
    private float mYDpi;

    private final int mDstWidth;
    private final int mDstHeight;
    private float mMetersToPixelsX;
    private float mMetersToPixelsY;



    GameManager(MainActivity obj){

        float ballDiam = 0.004f;
        DisplayMetrics metrics = new DisplayMetrics();
        obj.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mXDpi = metrics.xdpi;
        mYDpi = metrics.ydpi;
        mMetersToPixelsX = mXDpi / 0.0254f;
        mMetersToPixelsY = mYDpi / 0.0254f;

        // rescale the ball so it's about 0.5 cm on screen
        mDstWidth = (int) (ballDiam * mMetersToPixelsX + 0.5f);
        mDstHeight = (int) (ballDiam * mMetersToPixelsY + 0.5f);

//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inDither = true;
//        opts.inPreferredConfig = Bitmap.Config.RGB_565;

        Ball ball=new Ball(obj.getApplicationContext(), ballDiam);
        obj.addContentView(ball, new ViewGroup.LayoutParams(mDstWidth,mDstHeight));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class Brick{
        int x_coor,y_coor,len,wid;
        Brick()
        {
            //init with defaults
        }
    }

}
