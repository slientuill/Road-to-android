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
    public static final int V_MAX=35;
    public static final int V_MIN=15;
    public static final int WOOD_EDGE=60;
    public static final int GROUND_LING=1250; //代表地面的Y坐标，小球下落到此会弹起
    public static final int UP_ZERO=30; //小球在上升过程中，速度小于该值就算0
    public static final int DOWN_ZERO=60; //小球在撞击地面后，速度小于该值就算0
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
        //bmpWood= BitmapFactory.decodeResource(r, R.drawable.make);
    }
    public void initMoveables(){
        Random r=new Random();
        for(int i=0;i<ballNumber;i++){
        int index=r.nextInt(32);
        Bitmap tempBitmap=null;
        if(i<ballNumber/2) { //如果是初始化前一半球，就从大球中随机找一个
            tempBitmap=bitmapArray[3+index%3];
        } else { //如果是初始化前一半球，就从小球中随机找一个
            tempBitmap=bitmapArray[index%3];
        }
        //创建Movable对象
        Moveable m=new Moveable(0, 70-tempBitmap.getHeight(), 20, tempBitmap);
        alMovable.add(m); //加入列表中
        }
    }
    public void doDraw(Canvas canvas) { //绘制程序中需要的图片等信息
        canvas.drawBitmap(bmpBack, 0, 0,null);
      //  canvas.drawBitmap(bmpWood, 0, 60,null);
        for (Moveable m : alMovable) { //遍历绘制每个Movable对象
            m.drawSelf(canvas);
        }
        Paint p=new Paint();
        p.setColor(Color.BLUE);
        p.setTextSize(18);
        p.setAntiAlias(true); //设置抗锯齿
        canvas.drawText(fps, 30, 30, p); //画出帧速率字符串
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

