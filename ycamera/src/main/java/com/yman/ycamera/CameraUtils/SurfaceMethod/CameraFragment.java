package com.yman.ycamera.CameraUtils.SurfaceMethod;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.yman.ycamera.R;

import java.io.IOException;

/**
 * Created by Yman on 2017/12/22.
 * E-Mail : yin_xiangyang@outlook.com
 * WeChat : y22620
 */

public class CameraFragment extends Fragment{

    public static final String TAG = "CameraFragment";

    private Camera mCamera;

    private View mRootView;
    private SurfaceView mGlSurfaceView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        checkPermission();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_camera, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void initView(){
        mGlSurfaceView = mRootView.findViewById(R.id.surface_view);
        mGlSurfaceView.getHolder().addCallback(new CameraCallBack());
    }

    public void initPreview(){
        mCamera = Camera.open(0);
        try {
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(mGlSurfaceView.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] bytes, Camera camera) {
                Log.i("mCamera","onPreviewFrame");
            }
        });
        mCamera.setFaceDetectionListener(new Camera.FaceDetectionListener() {
            @Override
            public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                Log.i(TAG,"faces:" + faces.length);
            }
        });
        mCamera.startPreview();
        mCamera.startFaceDetection();
    }

    public void checkPermission(){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    class CameraCallBack implements SurfaceHolder.Callback{

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            initPreview();
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    }
}
