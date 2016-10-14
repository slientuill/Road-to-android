package com.example.slien.andoirdanimation;

/**
 * Created by slien on 2016/10/11.
 */
public class BallThread extends Thread{
    Moveable father;
    boolean flag=false;//线程执行标识
    int sleepSpan=10;//休眠时间
    float g=500;//加速度
    double current;//当前时间
    int MultR=1+(int)(5*Math.random());
    int MultX=1+(int)(3*Math.random());
    int MultY=1+(int)(3*Math.random());
    public  BallThread(Moveable father){
        this.father=father;
        this.flag=true;
    }
    public void run () {
            while (flag) {
                current = System.nanoTime();
                double timeSpanX = (double) ((current - father.timeX) / 1000 / 1000 / 1000); // 获取水平方向走过的时间
                father.x = (int) (father.startX + MultX*father.v_x * timeSpanX);
                father.r=(int)(father.startR+MultR*timeSpanX);
                if(father.r>300){
                    MultR=0-MultR;
                }
                if (father.bfall) { // 处理竖直方向上的运动，判断球是否已经移出挡板
                    double timeSpanY = (double) ((current - father.timeY) / 1000 / 1000 / 1000);
                    father.y=(int)(father.startY+MultY*father.v_y*timeSpanY);
                    // 判断小球是否撞地
                    if (father.y + father.r>= 1500 && father.v_y > 0||father.y  <= 0 && father.v_y <0) {
                        father.v_y = 0 - father.v_y;
                            father.startR=father.r;
                            father.startX = father.x;
                            father.timeX = System.nanoTime();
                            father.startY = father.y;
                            father.timeY = System.nanoTime();
                            father.startVY = father.v_y;
                    }
                    if (father.x + father.r>= 1100&& father.v_x > 0||father.x  <= 0&& father.v_x < 0) {
                        father.v_x = 0 - father.v_x; // 衰减竖直方向的速度并改变方向
                        father.startX = father.x;
                        father.timeX = System.nanoTime();
                        father.startY = father.y;
                        father.timeY = System.nanoTime();
                        father.startVY = father.v_y;
                        father.startR=father.r;
                    }


                }
                else if (father.x + father.r / 2 >= 0) {// 通过X坐标判断球是否移出了挡板
                    father.timeY = System.nanoTime();
                    father.bfall = true; // 确定下落
                }
                try {
                    Thread.sleep(sleepSpan);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

