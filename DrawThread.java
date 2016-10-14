package com.example.slien.andoirdanimation;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
/**
 * Created by slien on 2016/10/11.
 */
public class DrawThread extends Thread {
    BallView bv;
    SurfaceHolder surfaceHolder;
    boolean flag=false;
    int sleepSpan=30;
    public DrawThread(BallView bv,SurfaceHolder surfaceHolder) {
        this.bv=bv;
        this.surfaceHolder=surfaceHolder;
        this.flag=true;
    }
    public void run() {
        Canvas canvas=null;
        while(flag) {
            try {
                canvas=surfaceHolder.lockCanvas(null); //获取BallView的画布
                synchronized (surfaceHolder) {
                    bv.doDraw(canvas);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(canvas!=null) {
                    surfaceHolder.unlockCanvasAndPost(canvas); // surfaceHolder解锁，并将画布传回
                }
            }
            try {
                Thread.sleep(sleepSpan);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}