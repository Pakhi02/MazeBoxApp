package com.example.paayu.mazeboxpro;

import android.content.Context;
import android.view.View;

/**
 * Created by aysharma on 6/8/2017.
 */

public class Brick extends View{
    int x_coor,y_coor,len,wid;

    public Brick(Context context) {
        super(context);
        setBackgroundResource(R.drawable.square);

    }
}
