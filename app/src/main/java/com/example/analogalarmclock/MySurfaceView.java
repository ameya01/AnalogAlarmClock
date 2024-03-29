package com.example.analogalarmclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Calendar;

public class MySurfaceView extends SurfaceView implements Runnable {
    private Thread thread =null;
    private SurfaceHolder surfaceHolder = null;
    private boolean running =false;
    private float length;
    private Canvas canvas = null;
    private String theme;
    private String colorInt;
    private String hrHandColor ="-14638982";
    private String minHandColor ="-14638982";
    private String secHandColor ="-14638982";
    private String milliHandColor ="-14638982";
    private String bodyClock ="-14638982";
    private boolean flag = false;


    public MySurfaceView(Context context, float i){
        super(context);
        length = i;
        this.theme = theme;
        surfaceHolder=this.getHolder();
        //TODO Auto-generated constructor stub
    }
    public void setSurfaceViewCOlor(String handColor,int obj){
        Log.d("Debug","first color:" +handColor +"obj"+obj);
        if (obj == 1 && handColor.length()>0){
            flag=true;
            hrHandColor = handColor;
            Log.d("Debug","hrHandColor " + hrHandColor);
        }
        else if (obj ==2 && handColor.length()>0){
            minHandColor = handColor;
            flag=true;
        }
        else if (obj ==3 && handColor.length()>0){
            secHandColor = handColor;
            flag=true;
        }
        else if (obj ==4 && handColor.length()>0){
            milliHandColor = handColor;
            flag=true;
        }
        else if (obj ==5 && handColor.length()>0){
            bodyClock = handColor;
            flag=true;
        }
        else{
            hrHandColor = "-14638982";
            minHandColor ="-14638982";
            secHandColor ="-14638982";
            milliHandColor ="-14638982";
            handColor = "-14638982";
            flag = false;
        }
    }

    public void onResumeMySurfaceView(){
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    public void onPauseMySurfaceView(){
        boolean retry = true;
        running = false;
        while (retry)
        {
            try {
                thread.join();
                retry = false;
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run(){
        int hour = 0, min = 0, sec = 0, milli = 0;
        while (running){
            if (!surfaceHolder.getSurface().isValid()){
                continue;
            }
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            Paint paint = new Paint();
            Paint pHr = new Paint();
            pHr.setStrokeWidth(8);
            Paint pSec = new Paint();

            pSec.setStrokeWidth(8);
            Paint pMin = new Paint();
            pMin.setStrokeWidth(8);
            Paint pMilli = new Paint();
            pMilli.setStrokeWidth(5);
            pMin.setColor(Integer.parseInt(minHandColor));
            paint.setTextSize(30);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);


            if(flag){
                pHr.setColor(Integer.parseInt(hrHandColor));
                pSec.setColor(Integer.parseInt(secHandColor));
                pMin.setColor(Integer.parseInt(minHandColor));
                pMilli.setColor(Integer.parseInt(milliHandColor));
                paint.setColor(Integer.parseInt(bodyClock));
            }
            else{
                pHr.setColor(Color.GREEN);
                pMin.setColor(Color.MAGENTA);
                pMilli.setColor(Color.CYAN);
                pSec.setColor(Color.RED);
                paint.setColor(Color.BLUE);
            }
            RegPoly secMarks = new RegPoly(60,250,getWidth()/2,getHeight()/2,canvas,paint);
            RegPoly secHand = new RegPoly(60, 250 - 30, getWidth() / 2, getHeight() / 2, canvas, pSec);

            RegPoly milliMarks = new RegPoly(60,60,getWidth()/3,getHeight()/2,canvas,paint);
            RegPoly milliHand = new RegPoly(60, 60, getWidth() /3, getHeight() / 2, canvas, pMilli);

            RegPoly hourMarks = new RegPoly(12, 250, getWidth() / 2, getHeight() / 2, canvas, paint);
            RegPoly hourHand = new RegPoly(60, 250 - 120, getWidth() / 2, getHeight() / 2, canvas, pHr);

            RegPoly minMarks = new RegPoly(60,250,getWidth()/2,getHeight()/2,canvas,paint);
            RegPoly minHand = new RegPoly(60, 250 - 60, getWidth() / 2 , getHeight() / 2, canvas, pMin);



            RegPoly body = new RegPoly(60, 260, getWidth() / 2, getHeight() / 2, canvas, paint);
            RegPoly number = new RegPoly(12, 290, getWidth() / 2, getHeight() / 2, canvas, paint);


            secMarks.drawPoints();
            hourMarks.drawPoints();
            milliMarks.drawPoints();
            minMarks.drawPoints();
            body.drawRegPoly();

            // delay 1 sec
            for (int i = 1; i <= 12; i++) {
                canvas.drawText(Integer.toString(i), number.getX((i + 9) % 12), number.getY((i + 9) % 12), paint);
            }
            try {
                //Thread.sleep(1000);
                Thread.sleep(1000/60);
                milli++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
// get the date
            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR);
            min = calendar.get(Calendar.MINUTE);
            sec = calendar.get(Calendar.SECOND);
            //milli = calendar.get(Calendar.MILLISECOND);
// draw the hands
            milliHand.drawRadius( milli + 60);
            secHand.drawRadius(sec + 45);
            minHand.drawRadius(min + 45);
            hourHand.drawRadius((hour * 5) + (min / 12) + 45);

            surfaceHolder.unlockCanvasAndPost(canvas);


        }
    }

}
