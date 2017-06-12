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
    int ii=0;

    int horizontalCells=10;
    int verticalCells=20;
    int screenWidth;
    int screenHeight;


    BrickConfiguration(int screenWidth,int screenHeight){
        this.screenWidth=screenWidth;
        this.screenHeight=screenHeight;
    }

    static Iterator<Configuration> brickConfigurationIterator;
    void loadBrickData(){
       brickConfigList = new Vector<>();
        Log.v("called pos x in conf", String.valueOf(mXOrigin));



        Log.v("called pos y in conf", String.valueOf(mYOrigin));
       // int i=0,j=0;

        float i=1;float j=1;
        float cellWidth=((float)screenWidth/(float)horizontalCells);
        float cellHeight=((float)screenHeight/(float)verticalCells);
        brickConfigList.add(new Configuration((cellWidth)*i, (cellHeight)*j,(cellWidth)+3, (cellHeight)+3));

        i=1;j=2;
        brickConfigList.add(new Configuration((cellWidth)*i, (cellHeight)*j,(cellWidth)+3, (cellHeight)+3));

        i=2;j=2;
        brickConfigList.add(new Configuration((cellWidth)*i, (cellHeight)*j,(cellWidth) +3, (cellHeight)+3));

        i=3;j=3;
        brickConfigList.add(new Configuration((cellWidth)*i, (cellHeight)*j,(cellWidth)+3 , (cellHeight)+3));

        i=9;j=19;
        brickConfigList.add(new Configuration((cellWidth)*i, (cellHeight)*j,(cellWidth)+3 , 3+(cellHeight)));

        i=8;j=18;
        brickConfigList.add(new Configuration((cellWidth)*i, (cellHeight)*j,(cellWidth) +3, 3+(cellHeight)));
        i=6;j=15;
        brickConfigList.add(new Configuration((cellWidth)*i, (cellHeight)*j,(cellWidth+3) , (cellHeight+3)));

        /*for(int i=0;i<horizontalCells;i++) {
            for(int j=0;j<verticalCells;j++) {
                //brickConfigList.add(new Configuration(120, i*40, .005f, .005f));
                brickConfigList.add(new Configuration(0.001f, i * 0.002f, .005f, .005f));
            }
        }
*/
    }

    void startIterating(){
        ii=0;
    }
    boolean hasMoreConfig(){
       if(ii<brickConfigList.size())
           return true;
        return false;
    }
    Configuration getNextConfiguration(){
        return brickConfigList.elementAt(ii++);
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
        float getX(){
            return x;
        }
        float getY(){
            return (y);
        }
        float getWidth(){
            return width;
        }
        float getHeight(){
            return height;
        }
    }

}
