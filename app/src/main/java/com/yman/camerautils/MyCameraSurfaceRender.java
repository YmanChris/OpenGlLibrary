package com.yman.camerautils;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.yman.toolslibrary.Utils.LogUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Yman on 2017/12/22.
 * E-Mail : yin_xiangyang@outlook.com
 * WeChat : y22620
 */

public class MyCameraSurfaceRender implements GLSurfaceView.Renderer{

    private int program;
    private int vPosition;
    private int uColor;

    private int loadShader(int shaderType, String sourceCode){
        //创建一个shader
        int shader = GLES20.glCreateShader(shaderType);

        if(shader != 0){
            //加载shader源代码
            GLES20.glShaderSource(shader, sourceCode);
            //编译shader
            GLES20.glCompileShader(shader);
            //存放编译成功shader数量的数组
            int[] compiled = new int[1];
            //获取shader的编译情况
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if(compiled[0] == 0){
                LogUtils.e("ES20_ERROR","couldn't compile shader " + shaderType + ":");
                LogUtils.e("ES20_ERRPR",GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    private int createProgram(String vertexSource, String fragmentSource){
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if(vertexShader == 0 || fragmentShader == 0){
            return 0;
        }

        int program = GLES20.glCreateProgram();
        if(program != 0){
            //向程序中添加顶点着色器
            GLES20.glAttachShader(program, vertexShader);
            //向程序中添加片元着色器
                GLES20.glAttachShader(program, fragmentShader);
            //链接程序
            GLES20.glLinkProgram(program);
            //存放链接成功program数组的数量
            int[] linkStatus = new int[1];
            //获取program链接情况
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);

            if(linkStatus[0] != GLES20.GL_TRUE){
                LogUtils.e("ES20_ERROR","couldn't link program : ");
                LogUtils.e("ES20_ERROR",GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }

        }
        return program;
    }

    private FloatBuffer getVertices(){
        float vertices[] = {
                0.0f, 0.5f,
                -0.5f, -0.5f,
                0.5f, -0.5f
        };
        //创建顶点坐标数据缓冲(1 float = 4 byte)
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        FloatBuffer vertexBuf = vbb.asFloatBuffer();//转换为Float缓冲
        vertexBuf.put(vertices);//缓冲区放入顶点坐标数据
        vertexBuf.position(0);//设置缓冲区起始位置
        return vertexBuf;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //初始化着色器
        //基于顶点着色器与片元着色器创建程序
        program = createProgram(verticesShader, fragmentShader);
        //获取着色器中的属性引用id
        vPosition = GLES20.glGetAttribLocation(program, "vPosition");
        uColor = GLES20.glGetUniformLocation(program,"uColor");
        //设置clear color 颜色RGBA，设置非执行
        GLES20.glClearColor(1.0f, 0, 0, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        //设置绘图窗口(画布上划出绘制区域)
        GLES20.glViewport(0, 0, i, i1);
    }

    /**
     * 当surface需要绘制时回调此方法
     * 根据GLSurfaceView.setRenderMode()设置的渲染模式不同回调的策略也不同
     * GLSurfaceView.RENDERMODE_CONTINUOUSLY : 固定一秒回调60次(60fps)
     * GLSurfaceView.RENDERMODE_WHEN_DIRTY : 但调用GLSurfaceView.requestRender()之后回调一次
     * @param gl10
     */
    @Override
    public void onDrawFrame(GL10 gl10) {
        //获取图形的顶点坐标
        FloatBuffer vertices = getVertices();
        //清屏
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        //使用某套shader程序
        GLES20.glUseProgram(program);
        //为画笔指定顶点位置数据：vPosition
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 0, vertices);
        //允许顶点位置数据数组
        GLES20.glEnableVertexAttribArray(vPosition);
        //设置属性uColor：RGBA
        GLES20.glUniform4f(uColor, 0.0f, 1.0f, 0.0f, 1.0f);
        //绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 3);
    }

    private static final String verticesShader
            = "attribute vec2 vPosition;            \n" //顶点位置属性vPosition
            +"void main(){                          \n"
            +"  gl_Position = vec4(vPosition, 0, 1);\n"//确认顶点位置
            +"}";

    private static final String fragmentShader
            = "precision mediump float;             \n" //声明float类型的精度为中等
            +"uniform vec4  uColor;                 \n"//uniform的属性uColor
            +"void main(){                          \n"
            +"  gl_FragColor = uColor;              \n"//给此片元的填充色
            +"}";
}
