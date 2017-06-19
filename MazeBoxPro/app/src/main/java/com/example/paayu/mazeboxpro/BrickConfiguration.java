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

        float cellWidth=((float)screenWidth/(float)horizontalCells);
        float cellHeight=((float)screenHeight/(float)verticalCells);

        for(float j=0;j<verticalCells-3;j++){
            brickConfigList.add(new Configuration((cellWidth)*3, (cellHeight)*j,(cellWidth)+3, (cellHeight)+3,0));
        }

        for(float j=3;j<verticalCells;j++){
            brickConfigList.add(new Configuration((cellWidth)*7, (cellHeight)*j,(cellWidth)+3, (cellHeight)+3,0));
        }

        for(float j=0;j<verticalCells-3;j++){
            brickConfigList.add(new Configuration((cellWidth)*11, (cellHeight)*j,(cellWidth)+3, (cellHeight)+3,0));
        }

        for(float j=3;j<verticalCells;j++){
            brickConfigList.add(new Configuration((cellWidth)*15, (cellHeight)*j,(cellWidth)+3, (cellHeight)+3,0));
        }


        brickConfigList.add(new Configuration((cellWidth)*(horizontalCells-1), (cellHeight)*(verticalCells-1),(cellWidth)+3, (cellHeight)+3,1));
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

    public Configuration getGoalBrick(){
        startIterating();
        while (hasMoreConfig()){
            Configuration config = getNextConfiguration();
            if(config.type==1)
                return  config;
        }
        return new Configuration(-1f,-1f,-1f,-1f,0);
    }


    class Configuration{
        float x,y;
        float width,height;

        //0 for normal
        //1 for end
        int type;
        Configuration(float x,float y,float width,float height,int type){
            this.x=x;
            this.y=y;
            this.width=width;
            this.height=height;
            this.type = type;
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
