package com.example.slien.andoirdanimation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by slien on 2016/10/11.
 */
public class Moveable {
    int startX=0;
    int startY=0;
    int x,y;//实时坐标
    float startVX=0f;//初始水平方向速度
    float startVY=0f;//竖直方向的速度
    float v_x=0f;
    float v_y=0f;
    float r;//半径
    float startR;
    double timeX;//x的运动时间
    double timeY;//Y的时间
    Bitmap bitmap=null;//图片？？？
    BallThread bt=null;//小球移动线程
    boolean bfall=false;//滑落测试
    int color;
    public Moveable(int x,int y,float r,Bitmap bitmap,int color){

        this.startX=x;
        this.x=x;
        this.startY=y;
        this.y=y;
        this.startR=r;
        this.r=r;
        this.bitmap=bitmap;
        timeX=System.nanoTime();
        this.v_x=-100+(int)(100*Math.random());
        this.v_x*=10;
        this.v_y=-100+(int)(100*Math.random());
        this.v_y*=10;
        this.color=color;
        bt=new BallThread(this);
        bt.start();
    }
    public  void drawSelf(Canvas canvas){
       // canvas.drawBitmap(bitmap,x,y,null);
        Paint paint=new Paint();
        paint.setColor(color);
        canvas.drawCircle(x,y,r,paint);
    }
}
