package com.example.slien.andoirdanimation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by slien on 2016/10/11.
 */
public class BallView extends SurfaceView implements  SurfaceHolder.Callback {
    Bitmap[] bitmapArray =new Bitmap[6];
    Bitmap bmpBack;
    // Bitmap bmpWood; // 挡板图片
    String fps="FPS:N/A"; //用于显示帧速率的字符串
    int ballNumber =8; //小球数目
    ArrayList<Moveable> alMovable=new ArrayList<Moveable>(); //小球对象数组
    DrawThread dt; //后台屏幕绘制线程
    public  BallView(Context activity){
        super(activity);
        getHolder().addCallback(this);
        initBitmaps(getResources());
        initMoveables();

        dt=new DrawThread(this,getHolder());
    }
    public void initBitmaps(Resources r){
        bitmapArray[0]=BitmapFactory.decodeResource(r, R.drawable.ball_yellow);
        bitmapArray[1]=BitmapFactory.decodeResource(r, R.drawable.ball_blue);
        bitmapArray[2]=BitmapFactory.decodeResource(r, R.drawable.ball_black);
        bitmapArray[3]=BitmapFactory.decodeResource(r, R.drawable.ball_red);
        bitmapArray[4]=BitmapFactory.decodeResource(r, R.drawable.ball_pink);
        bitmapArray[5]=BitmapFactory.decodeResource(r, R.drawable.ball_brown);
        bmpBack=BitmapFactory.decodeResource(r, R.drawable.back);
    }
    public void initMoveables(){
        int MultC;
        int MultX;
        int MultY;
        int c[]= {Color.CYAN,Color.MAGENTA,Color.YELLOW,Color.RED,Color.BLUE,
                        Color.DKGRAY,Color.GREEN,Color.BLACK,Color.YELLOW,Color.RED};
        for(int i=0;i<10;i++){
            MultY=1+(int)(1500*Math.random());
            MultX=1+(int)(1100*Math.random());
            MultC=1+(int)(5*Math.random());
            Moveable m=new Moveable(MultX,MultY, 20, null,c[i]);

            alMovable.add(m);

             //加入列表中
        }
    }
    public int Create(){
        for(int i=0;i<alMovable.size();i++)
        {
            Moveable m=alMovable.get(i);

            if(m.r>100&&alMovable.size()<30){
                Moveable newball=new Moveable(m.x,m.y, m.r*7/10, null,m.color);
                Moveable newball2=new Moveable(m.x,m.y, m.r*7/10, null,m.color);
                alMovable.add(newball);
                alMovable.add(newball2);
                alMovable.remove(i);
            }
        }
        return alMovable.size();
    }
    public void doDraw(Canvas canvas) { //绘制程序中需要的图片等信息
        canvas.drawBitmap(bmpBack, 0, 0,null);
        //  canvas.drawBitmap(bmpWood, 0, 60,null);
       // for (Moveable m : alMovable)
        for(int i=0;i<alMovable.size();i++)
        { //遍历绘制每个Movable对象
            Moveable m=alMovable.get(i);
            m.drawSelf(canvas);
        }
        Paint p=new Paint();
        p.setColor(Color.BLUE);
        p.setTextSize(50);
        p.setAntiAlias(true); //设置抗锯齿
        canvas.drawText(String.valueOf(Create()), 20, 100, p); //画出帧速率字符串
        Paint p2=new Paint();
        p2.setColor(Color.BLUE);
        p2.setTextSize(50);
        p2.setAntiAlias(true); //设置抗锯齿
        canvas.drawText(String.valueOf(alMovable.get(1).color), 20, 150, p2); //画出帧速率字符串
        Paint p3=new Paint();
        p3.setColor(Color.BLUE);
        p3.setTextSize(50);
        p3.setAntiAlias(true); //设置抗锯齿
        canvas.drawText(String.valueOf(alMovable.get(2).color), 20, 200, p3); //画出帧速率字符串
        Paint p4=new Paint();
        p4.setColor(Color.BLUE);
        p4.setTextSize(50);
        p4.setAntiAlias(true); //设置抗锯齿
        canvas.drawText(String.valueOf(alMovable.get(3).color), 20, 250, p4); //画出帧速率字符串


    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    public void surfaceCreated(SurfaceHolder arg0) {
        if(!dt.isAlive()) {
            dt.start();
        }
    }

    public void surfaceDestroyed(SurfaceHolder arg0) {
        dt.flag=false;
        dt=null;
    }
}

