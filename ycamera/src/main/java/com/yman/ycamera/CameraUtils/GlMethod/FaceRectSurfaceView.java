package com.yman.ycamera.CameraUtils.GlMethod;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Yman on 2018/1/5.
 * E-Mail : yin_xiangyang@outlook.com
 * WeChat : y22620
 */

public class FaceRectSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private static final String TAG = "FaceRectSurfaceView";

    private Paint mPaint;
    private Canvas mCanvas;
    public FaceRectSurfaceView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        canvas.drawARGB(0, 255,255,255);
    }

    public FaceRectSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG,"init SurfaceView");
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);
        setZOrderMediaOverlay(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        getHolder().addCallback(this);
    }

    public FaceRectSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i(TAG,"onSurfaceCreated");
        new DrwaThread(getHolder()).run();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.i(TAG,"onSurfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.i(TAG,"onSurfaceDestroyed");
    }

    public void drawRect(Canvas canvas, Camera.Face[] faces){
        if(CameraFaceData.sFaceArrayList.size() == 0) {
            Log.i(TAG,"size is 0");
            return;
        }
        Log.i(TAG,"draw Rect");

        Paint handPaint = new Paint(mPaint);
        handPaint.setStrokeWidth(2);
        handPaint.setStyle(Paint.Style.FILL);

        canvas.save();
        for(Camera.Face face : faces){
            //canvas.drawRect(face.rect, handPaint);
            canvas.drawARGB(0, 255,255,255);
        }
        canvas.restore();
    }
    class DrwaThread extends Thread{
        private SurfaceHolder mSurfaceHolder;
        public boolean isRun;

        public DrwaThread(SurfaceHolder surfaceHolder) {
            mSurfaceHolder = surfaceHolder;
            isRun = true;
        }

        @Override
        public void run() {
            Log.i(TAG,"run ~~~~~~~~~~~~~~");
            while (isRun){
                try {
                    synchronized (mSurfaceHolder) {
                        mCanvas = mSurfaceHolder.lockCanvas(null);
                        if(CameraFaceData.sFaceArrayList.size() > 0) {
                            Camera.Face[] faces = CameraFaceData.sFaceArrayList.remove(0);
                            if (null != faces) {
                                drawRect(mCanvas, faces);
                            }
                        }
                        mSurfaceHolder.unlockCanvasAndPost(mCanvas); // 解锁画布，提交画好的图像
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
