package com.example.paayu.mazeboxpro;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.ViewGroup;
import android.view.ViewGroup;

/**
 * Created by labuser on 6/7/17.
 */

public class GameManager implements SensorEventListener{


    GameManager(MainActivity obj){

        Ball ball=new Ball(obj.getApplicationContext());
        obj.addContentView(ball, new ViewGroup.LayoutParams(1,1));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
