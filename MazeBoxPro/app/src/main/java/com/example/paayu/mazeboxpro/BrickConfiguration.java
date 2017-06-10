package com.example.paayu.mazeboxpro;

import android.content.res.Configuration;

import java.util.Iterator;
import java.util.Vector;

/**
 * Created by aysharma on 6/8/2017.
 */

public class BrickConfiguration {

    Vector<Configuration> brickConfigList;
    int i=0;
    static Iterator<Configuration> brickConfigurationIterator;
    void loadBrickData(){
       brickConfigList = new Vector<>();

        for(int i=0;i<10;i++) {
            brickConfigList.add(new Configuration(120, i*40, .005f, .005f));
        }

        for(int i=2;i<12;i++) {
            brickConfigList.add(new Configuration(120*3, i*40, .005f, .005f));
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
        int x,y;
        float width,height;
        Configuration(int x,int y,float width,float height){
            this.x=x;
            this.y=y;
            this.width=width;
            this.height=height;
        }
        int getX(){
            return x;
        }
        int getY(){
            return y;
        }
        float getWidth(){
            return width;
        }
        float getHeight(){
            return height;
        }
    }

}
