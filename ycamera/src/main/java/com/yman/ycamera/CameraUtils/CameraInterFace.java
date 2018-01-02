package com.yman.ycamera.CameraUtils;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;

import com.example.yman.toolslibrary.Utils.LogUtils;

import java.io.IOException;

/**
 * Created by Yman on 2018/1/1.
 * E-Mail : yin_xiangyang@outlook.com
 * WeChat : y22620
 */

public class CameraInterFace {

    private static final String TAG = "CameraInterFace";

    private static Camera sCamera;
    private static boolean isPreview = false;

    public static Camera getInstance(){
        return sCamera;
    }

    public static void openCamera(){
        if(null == sCamera){
            sCamera = Camera.open(1);
        }
    }

    public static boolean isPreviewing(){
        if(null == sCamera) {
            return false;
        }
        return isPreview;
    }

    public static void startPreview(SurfaceTexture surfaceTexture, float num){
        if(null == sCamera){
            return;
        }
        try {
            sCamera.setPreviewTexture(surfaceTexture);
            Camera.Parameters parameters = sCamera.getParameters();
            parameters.setPreviewSize(1280,720);
            sCamera.setParameters(parameters);
            sCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {
                    Log.i(TAG,"onPrviewFrame:");
                }
            });
            sCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getPreviewSize(){

    }
}
