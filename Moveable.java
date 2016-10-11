package com.example.slien.andoirdanimation;

import android.graphics.Bitmap;
import android.graphics.Canvas;

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
    int r;//半径
    double timeX;//x的运动时间
    double timeY;//Y的时间
    Bitmap bitmap=null;//图片？？？
    BallThread bt=null;//小球移动线程
    boolean bfall=false;//滑落测试
    float impactFactor =0.25f;//速度损失系数
    public Moveable(int x,int y,int r,Bitmap bitmap){
        this.startX=x;
        this.x=x;
        this.startY=y;
        this.y=y;
        this.r=r;
        this.bitmap=bitmap;
        timeX=System.nanoTime();
        this.v_x=BallView.V_MIN+(int)((BallView.V_MAX-BallView.V_MIN)*Math.random());
        bt=new BallThread(this);
        bt.start();
    }
    public  void drawSelf(Canvas canvas){
        canvas.drawBitmap(bitmap,x,y,null);
    }
}
