package com.example.paayu.mazeboxpro;

import android.content.res.Configuration;
import android.util.Log;

import java.util.Iterator;
import java.util.Vector;

/**
 * Created by aysharma on 6/8/2017.
 */

public class BrickConfiguration {

    private Vector<Configuration> brickConfigList;
    private float mMetersToPixelsX;
    private float mMetersToPixelsY;
    private float mXOrigin;
    private float mYOrigin;
    int i=0;

    BrickConfiguration(float mMetersToPixelsX,float mMetersToPixelsY, float XOrigin, float YOrigin){
        this.mMetersToPixelsX=mMetersToPixelsX;
        this.mMetersToPixelsY=mMetersToPixelsY;
        mXOrigin = XOrigin;
        mYOrigin = YOrigin;
    }

    static Iterator<Configuration> brickConfigurationIterator;
    void loadBrickData(){
       brickConfigList = new Vector<>();
//        Log.v("called pos x in conf", String.valueOf(mXOrigin));
//        Log.v("called pos y in conf", String.valueOf(mYOrigin));

        for(int i=0;i<10;i++) {
            //brickConfigList.add(new Configuration(120, i*40, .005f, .005f));
            brickConfigList.add(new Configuration(0.001f, i*0.001f, .005f, .005f));

        }

        for(int i=2;i<12;i++) {
            //brickConfigList.add(new Configuration(120*3, i*40, .005f, .005f));
            brickConfigList.add(new Configuration(0.001f*2, i*0.001f, .005f, .005f));
        }
    }

    void startIterating(){
        i=0;
    }
    boolean hasMoreConfig(){
       if(i<brickConfigList.size())
           return true;
        return false;
    }
    Configuration getNextConfiguration(){
        return brickConfigList.elementAt(i++);
    }
    class Configuration{
        float x,y;
        float width,height;
        Configuration(float x,float y,float width,float height){
            this.x=x;
            this.y=y;
            this.width=width;
            this.height=height;
        }
        int getX(){
            return (int)(mXOrigin+ x*mMetersToPixelsX);
        }
        int getY(){
            return (int)(mYOrigin- y*mMetersToPixelsY);
        }
        float getWidth(){
            return (width*mMetersToPixelsX);
        }
        float getHeight(){
            return height*mMetersToPixelsY;
        }
    }

}
