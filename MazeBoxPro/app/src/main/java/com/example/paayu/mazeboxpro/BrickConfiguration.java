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

    private int horizontalCells=10;
    private int verticalCells=20;
    private int screenWidth;
    private int screenHeight;


    BrickConfiguration(int screenWidth,int screenHeight){
        this.screenWidth=screenWidth;
        this.screenHeight=screenHeight;
    }

    static Iterator<Configuration> brickConfigurationIterator;
    void loadBrickData(){
       brickConfigList = new Vector<>();

        float cellWidth=((float)screenWidth/(float)horizontalCells);
        float cellHeight=((float)screenHeight/(float)verticalCells);

        for(float j=0;j<verticalCells-1;j++){
            brickConfigList.add(new Configuration((cellWidth)*1, (cellHeight)*j,(cellWidth)+3, (cellHeight)+3));
        }

        for(float j=1;j<verticalCells;j++){
            brickConfigList.add(new Configuration((cellWidth)*3, (cellHeight)*j,(cellWidth)+3, (cellHeight)+3));
        }

        for(float j=0;j<verticalCells-1;j++){
            brickConfigList.add(new Configuration((cellWidth)*5, (cellHeight)*j,(cellWidth)+3, (cellHeight)+3));
        }

        for(float j=1;j<verticalCells;j++){
            brickConfigList.add(new Configuration((cellWidth)*7, (cellHeight)*j,(cellWidth)+3, (cellHeight)+3));
        }
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

//    boolean posX_BelongsToBrick(float x)
//    {
//        float xStart, xEnd;
//        int index = ii-1;
//        boolean res=false;
//        if(index!=0)
//        {
//            xStart = brickConfigList.elementAt(index-1).getX();
//            xEnd = xStart+brickConfigList.elementAt(index-1).getWidth();
//            if((x>=xStart) && (x<=xEnd))
//                res = true;
//        }
//        if(index<(brickConfigList.size()-1))
//        {
//            xStart = brickConfigList.elementAt(index+1).getX();
//            xEnd = xStart+brickConfigList.elementAt(index+1).getWidth();
//            if((x>=xStart) && (x<=xEnd))
//                res = true;
//        }
//        return res;
//    }
//    boolean posY_BelongsToBrick(float y)
//    {
//
//        float yStart, yEnd;
//        int index = ii-1;
//        boolean res=false;
//        if(index!=0)
//        {
//            yStart = brickConfigList.elementAt(index-1).getY();
//            yEnd = yStart+brickConfigList.elementAt(index-1).getHeight();
//            if((y>=yStart) && (y<=yEnd))
//                res = true;
//        }
//        if(index<(brickConfigList.size()-1))
//        {
//            yStart = brickConfigList.elementAt(index+1).getY();
//            yEnd = yStart+brickConfigList.elementAt(index+1).getHeight();
//            if((y>=yStart) && (y<=yEnd))
//                res = true;
//        }
//        return res;
//    }
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
