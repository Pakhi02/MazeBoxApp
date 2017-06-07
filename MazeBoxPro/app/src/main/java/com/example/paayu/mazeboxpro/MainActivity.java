package com.example.paayu.mazeboxpro;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private PowerManager mPowerManager;
    private WindowManager mWindowManager;
    private Display mDisplay;
    private PowerManager.WakeLock mWakeLock;
    private Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get an instance of the SensorManager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Get an instance of the PowerManager
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);

        // Get an instance of the WindowManager
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();

        // Create a bright wake lock
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass()
                .getName());


        GameManager gameManager = new GameManager();
        mSensorManager.registerListener(gameManager,mAccelerometer,SensorManager.SENSOR_DELAY_GAME);
    }

}
