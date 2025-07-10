package com.ablec.myarchitecture.logic.pag;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import org.libpag.PAGFile;
import org.libpag.PAGLayer;
import org.libpag.PAGPlayer;
import org.libpag.PAGSurface;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRender implements GLSurfaceView.Renderer {

    public static final String TAG = "GLRender";

    static final float SQUARE_COORDS[] = {
            1.0f, -1.0f,
            -1.0f, -1.0f,
            1.0f, 1.0f,
            -1.0f, 1.0f,
    };
    static final float TEXTURE_COORDS[] = {
            1f, 1f,
            0f, 1f,
            1f, 0f,
            0f, 0f
    };

    private static final String VERTEX_MAIN =
            "attribute vec2  vPosition;\n" +
                    "attribute vec2  vTexCoord;\n" +
                    "varying vec2    texCoord;\n" +
                    "\n" +
                    "void main() {\n" +
                    "    texCoord = vTexCoord;\n" +
                    "    gl_Position = vec4 ( vPosition.x, vPosition.y, 0.0, 1.0 );\n" +
                    "}";
    private static final String FRAGMENT_MAIN =
            "precision mediump float;\n" +
                    "\n" +
                    "varying vec2                texCoord;\n" +
                    "uniform sampler2D sTexture;\n" +
                    "\n" +
                    "void main() {\n" +
                    "    gl_FragColor = texture2D(sTexture, texCoord);\n" +
                    "}";
    static FloatBuffer VERTEX_BUF, TEXTURE_COORD_BUF;
    private final Context context;
    private final List<PagLayerWrapper> layers;
    private final List<PagLayerWrapper> runningLayers = new ArrayList<>();

    /**
     * 对应layer层
     */
    private final Map<Integer, Long> timeStampMap = new HashMap<>();
    private int mTextureId;
    private PAGPlayer mPagPlayer;
    private int mProgram;
    private PAGFile pagFile;

    /**
     * 以index 0 为底图，后续添加的layer均依赖于0，不可移除
     * @param context
     * @param layers
     */
    public GLRender(Context context, List<PagLayerWrapper> layers) {
        this.context = context;
        this.layers = layers;
    }

    private void initShader() {

        if (VERTEX_BUF == null) {
            VERTEX_BUF = ByteBuffer.allocateDirect(SQUARE_COORDS.length * 4)
                    .order(ByteOrder.nativeOrder()).asFloatBuffer();
            VERTEX_BUF.put(SQUARE_COORDS);
            VERTEX_BUF.position(0);
        }
        if (TEXTURE_COORD_BUF == null) {
            TEXTURE_COORD_BUF = ByteBuffer.allocateDirect(TEXTURE_COORDS.length * 4)
                    .order(ByteOrder.nativeOrder()).asFloatBuffer();
            TEXTURE_COORD_BUF.put(TEXTURE_COORDS);
            TEXTURE_COORD_BUF.position(0);
        }
        if (mProgram == 0) {
            mProgram = GLUtil.buildProgram(VERTEX_MAIN, FRAGMENT_MAIN);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // ✅ 确保 EGLContext 创建后立刻重新构建 shader program
        initShader();

        pagFile = (PAGFile) layers.get(0).getPagLayer();
        int width = pagFile.width();
        int height = pagFile.height();
        mTextureId = initRenderTarget(width, height);
        PAGSurface surface = PAGSurface.FromTexture(mTextureId, width, height);
        mPagPlayer = new PAGPlayer();
        mPagPlayer.setSurface(surface);
        resetRoot(0);
    }

    public void resetRoot(int index){
        timeStampMap.clear();
        PagLayerWrapper pagLayerWrapper = layers.get(index);
        pagFile = (PAGFile) pagLayerWrapper.getPagLayer();
        mPagPlayer.setComposition(pagFile);
        runningLayers.clear();
        runningLayers.add(pagLayerWrapper);
    }

    /**
     * @param index
     */
    public void addLayer(int index) {
        PagLayerWrapper pagLayerWrapper = layers.get(index);
        pagFile.addLayer(pagLayerWrapper.getPagLayer());
        runningLayers.add(pagLayerWrapper);
    }

    public boolean removeLayer(int index) {
        PAGLayer pagLayer = pagFile.removeLayer(layers.get(index).getPagLayer());
        runningLayers.remove(pagLayer);
        return pagLayer != null;
    }

    public void animAlphaAll(float alpha){
        for (int i = 0; i < runningLayers.size(); i++) {
            runningLayers.get(i).getPagLayer().setAlpha(alpha);
        }
    }

    public void release() {
        mPagPlayer.release();
        if (mProgram != 0) {
            GLES20.glDeleteProgram(mProgram);
            mProgram = 0;
        }
        int[] textures = new int[]{mTextureId};
        GLES20.glDeleteTextures(1, textures, 0);
        GLES20.glDeleteFramebuffers(1, textures, 0);
        mTextureId = 0;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        Log.d(TAG, "width is " + width + " height is " + height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //layer 播放
        if (mPagPlayer == null){
            return;
        }
        Iterator<PagLayerWrapper> iterator = runningLayers.listIterator();
        int index = 0;
        while (iterator.hasNext()) {
            PagLayerWrapper pagLayerWrapper = iterator.next();
            Long timestamp = timeStampMap.get(index);
            if (timestamp == null || timestamp == 0) {
                timestamp = System.currentTimeMillis();
                timeStampMap.put(index, timestamp);
            }
            long playTime = (System.currentTimeMillis() - timestamp) * 1000;
            PAGLayer pagLayer = pagLayerWrapper.getPagLayer();
            long duration = pagLayer.duration();
            if (playTime >= duration) {
                PlayEndStrategy strategy = pagLayerWrapper.getStrategy();
                switch (strategy) {
                    case REPEAT:
                        timeStampMap.put(index, 0L);
                        break;
                    case ONCE:
                        onLayerOnceFinish(index);
                        break;
                }
            } else {
                double progress = (double) playTime / duration;
                pagLayer.setProgress(progress);
                Log.d(TAG, "setProgress:playTime is " + playTime + " duration is " + duration + " progress is " + progress);
            }
            index++;
        }
        mPagPlayer.flush();
        //可以拿到当前截图
        //pagPlayer.getSurface().makeSnapshot();
        initShader();
        Log.d(TAG, "draw texture id is " + mTextureId);
        GLES20.glUseProgram(mProgram);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        int vPositionLocation = GLES20.glGetAttribLocation(mProgram, "vPosition");
        if (vPositionLocation >= 0) {
            GLES20.glEnableVertexAttribArray(vPositionLocation);
            GLES20.glVertexAttribPointer(vPositionLocation, 2, GLES20.GL_FLOAT, false, 4 * 2, VERTEX_BUF);
        }
        int vTexCoordLocation = GLES20.glGetAttribLocation(mProgram, "vTexCoord");
        GLES20.glEnableVertexAttribArray(vTexCoordLocation);
        GLES20.glVertexAttribPointer(vTexCoordLocation, 2, GLES20.GL_FLOAT, false, 4 * 2, TEXTURE_COORD_BUF);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);


        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }


    private void onLayerOnceFinish(int i) {
        Log.d(TAG, "onLayerOnceFinish");
//            removeLayer(i);
    }


    private int initRenderTarget(int width, int height) {
        int id[] = {0};
        GLES20.glGenTextures(1, id, 0);
        if (id[0] == 0) {
            return 0;
        }
        int textureId = id[0];
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        return textureId;
    }



}