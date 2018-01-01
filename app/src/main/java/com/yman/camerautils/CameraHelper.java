package com.yman.camerautils;

import android.hardware.Camera;

/**
 * Created by Yman on 2017/12/29.
 * E-Mail : yin_xiangyang@outlook.com
 * WeChat : y22620
 */

public class CameraHelper {

    private Camera mCamera;

    private int mCurrentCameraPosition = -1;

    private Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();

    public Camera getDefaultCamera(){
        return mCamera;
    }

    public void openCamera(int position){

        return;
    }

    private int getCameraCount(){
        int num = 0;
        num = Camera.getNumberOfCameras();
        return num;
    }

    public Camera getmCamera(){
        return mCamera;
    }
}
