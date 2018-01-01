package com.yman.camerautils;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        glSurfaceView = findViewById(R.id.gl_view);

        glSurfaceView.setEGLContextClientVersion(2);

        glSurfaceView.setRenderer(new MyCameraSurfaceRender());

        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);


    }
}
