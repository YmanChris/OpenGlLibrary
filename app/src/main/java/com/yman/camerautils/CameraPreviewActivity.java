package com.yman.camerautils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yman.ycamera.CameraUtils.GlMethod.GLCameraFragment;

/**
 * Created by Yman on 2017/12/28.
 * E-Mail : yin_xiangyang@outlook.com
 * WeChat : y22620
 */

public class CameraPreviewActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
        initFragment();
    }

    public void initFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new GLCameraFragment()).commit();
    }


}
