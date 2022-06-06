package com.penthouse_bogmjary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.SystemClock;

import android.util.Log;
import android.widget.TextView;

import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;

import com.penthouse_bogmjary.common.FPSCounter;
import com.penthouse_bogmjary.common.RenderConstants;
import com.penthouse_bogmjary.common.RenderProgram;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ShadowsRenderer implements GLSurfaceView.Renderer {
    protected static final int SIZE_OF_FLOAT = 4;
    private static final String TAG = "ShadowsRenderer";
    public final float RADIAN = 0.01745329f;
    public final float R_H = 20.0f;
    public final float R_V = 20.0f;
    Bitmap bitmap;
    public long cum_time = 0;
    public mdata data;
    ArrayList<mdata> datas;
    int[] depthTextureId;
    float eyex = 0.0f;
    float eyex_o = 0.0f;
    float eyey = 4.0f;
    float eyey_o = 0.0f;
    float eyez = -12.0f;
    float eyez_o = 0.0f;
    int[] fboId;
    Bitmap img;
    public boolean is_run = true;
    double lst;
    private int mActiveProgram;
    private float[] mActualLightPosition = new float[4];
    private final float[] mCubeRotation = new float[16];
    private ArrayList<Cube> mCubes = new ArrayList<>();
    private RenderProgram mDepthMapProgram;
    private int mDisplayHeight;
    private int mDisplayWidth;
    private FPSCounter mFPSCounter;
    private boolean mHasDepthTextureExtension = false;
    private final float[] mLightMvpMatrix_dynamicShapes = new float[16];
    private final float[] mLightMvpMatrix_staticShapes = new float[16];
    private final float[] mLightPosInEyeSpace = new float[16];
    private final float[] mLightPosModel = {100.0f, 0.0f, 0.0f, 1.0f};
    private final float[] mLightProjectionMatrix = new float[16];
    private final float[] mLightViewMatrix = new float[16];
    private final float[] mMVMatrix = new float[16];
    private final float[] mMVPMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];
    private final float[] mNormalMatrix = new float[16];
    private RenderProgram mPCFShadowDynamicBiasProgram;
    private Plane mPlane;
    private final float[] mProjectionMatrix = new float[16];
    public float mRadian_H = 1.57f;
    public float mRadian_V = 0.0f;
    private float mRotationX;
    private float mRotationY;
    private int mShadowMapHeight;
    private int mShadowMapWidth;
    private final Activity3D mShadowsActivity;
    private final float[] mViewMatrix = new float[16];
    public long p_time = 0;
    int[] renderPlaneTextureId;
    int[] renderTextureId;
    private int scene_colorAttribute;
    private int scene_lightPosUniform;
    private int scene_mapStepXUniform;
    private int scene_mapStepYUniform;
    private int scene_mvMatrixUniform;
    private int scene_mvpMatrixUniform;
    private int scene_normalAttribute;
    private int scene_normalMatrixUniform;
    private int scene_planetextureUniform;
    private int scene_positionAttribute;
    private int scene_schadowProjMatrixUniform;
    private int scene_shadowtextureUniform;
    private int scene_textureUniform;
    int[] shadowTextureId;
    private int shadow_mvpMatrixUniform;
    private int shadow_positionAttribute;
    float sunatt = 0.0f;
    float suncenter_x = 0.0f;
    float suncenter_y = 0.0f;
    Sunset sunset;
    TextView textTime;
    private int texture_mvpMatrixUniform;
    private int texture_positionAttribute;
    private int texture_texCoordAttribute;
    private int texture_textureUniform;
    double time_res;
    int uvHandle;

    private void setRenderProgram() {
    }

    public ShadowsRenderer(Activity3D activity3D, Bitmap bitmap2) {
        this.sunatt = (float) activity3D.sunatt;
        this.suncenter_x = (float) activity3D.suncenter_x;
        this.suncenter_y = (float) activity3D.suncenter_y;
        this.eyex = (float) (Math.cos((double) this.mRadian_H) * 20.0d);
        this.eyez = (float) (Math.sin((double) this.mRadian_H) * 20.0d);
        this.eyey = (float) (Math.cos((double) this.mRadian_V) * 20.0d);
        this.img = bitmap2;
        this.mShadowsActivity = activity3D;
        this.bitmap = Bitmap.createBitmap(100, PathInterpolatorCompat.MAX_NUM_POINTS, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.bitmap);
        this.bitmap.eraseColor(-1);
        Paint paint = new Paint();
        paint.setTextSize(20.0f);
        paint.setAntiAlias(true);
        paint.setARGB(255, 0, 0, 0);
        for (int i = 1; i <= 100; i++) {
            float f = (float) ((3000 - (i * 30)) + 10);
            canvas.drawText(Integer.toString(i), 10.0f, f, paint);
            canvas.drawText(Integer.toString(i), 70.0f, f, paint);
        }
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        this.mFPSCounter = new FPSCounter();
        if (GLES20.glGetString(7939).contains("OES_depth_texture")) {
            this.mHasDepthTextureExtension = true;
        }
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glEnable(2929);
        GLES20.glEnable(2884);
        boolean z = false;
        for (int i = 0; i < this.datas.size(); i++) {
            this.mCubes.add(new Cube(this.datas.get(i), new float[]{-4.0f, -3.9f, 4.0f}, 2.0f, new float[]{0.0f, 0.0f, 0.0f, -1.0f}, this.mShadowsActivity.height_calibrate));
        }
        int i2 = this.datas.get(0).sc_h;
        int i3 = this.datas.get(0).sc_v;
        if (this.img != null) {
            z = true;
        }
        this.mPlane = new Plane(i2, i3, z);
        Matrix.setLookAtM(this.mViewMatrix, 0, this.eyex, this.eyey, this.eyez, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        if (!this.mHasDepthTextureExtension) {
            this.mPCFShadowDynamicBiasProgram = new RenderProgram(R.raw.v_with_shadow, R.raw.f_with_pcf_shadow_dynamic_bias, this.mShadowsActivity);
            this.mDepthMapProgram = new RenderProgram(R.raw.v_depth_map, R.raw.f_depth_map, this.mShadowsActivity);
        } else {
            this.mPCFShadowDynamicBiasProgram = new RenderProgram(R.raw.depth_tex_v_with_shadow, R.raw.depth_tex_f_with_pcf_shadow_dynamic_bias, this.mShadowsActivity);
            this.mDepthMapProgram = new RenderProgram(R.raw.depth_tex_v_depth_map, R.raw.depth_tex_f_depth_map, this.mShadowsActivity);
        }
        this.mActiveProgram = this.mPCFShadowDynamicBiasProgram.getProgram();
    }

    public void generateShadowFBO() {
        this.mShadowMapWidth = Math.round(((float) this.mDisplayWidth) * this.mShadowsActivity.getmShadowMapRatio());
        this.mShadowMapHeight = Math.round(((float) this.mDisplayHeight) * this.mShadowsActivity.getmShadowMapRatio());
        this.fboId = new int[1];
        this.depthTextureId = new int[1];
        this.shadowTextureId = new int[1];
        this.renderTextureId = new int[1];
        this.renderPlaneTextureId = new int[1];
        GLES20.glGenFramebuffers(1, this.fboId, 0);
        GLES20.glGenRenderbuffers(1, this.depthTextureId, 0);
        GLES20.glBindRenderbuffer(36161, this.depthTextureId[0]);
        GLES20.glRenderbufferStorage(36161, 33189, this.mShadowMapWidth, this.mShadowMapHeight);
        int[] iArr = this.shadowTextureId;
        GLES20.glGenTextures(iArr.length, iArr, 0);
        GLES20.glBindTexture(3553, this.shadowTextureId[0]);
        GLES20.glTexParameteri(3553, 10241, 9728);
        GLES20.glTexParameteri(3553, 10240, 9728);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glBindFramebuffer(36160, this.fboId[0]);
        if (!this.mHasDepthTextureExtension) {
            GLES20.glTexImage2D(3553, 0, 6408, this.mShadowMapWidth, this.mShadowMapHeight, 0, 6408, 5121, null);
            GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.shadowTextureId[0], 0);
            GLES20.glFramebufferRenderbuffer(36160, 36096, 36161, this.depthTextureId[0]);
        } else {
            GLES20.glTexImage2D(3553, 0, 6402, this.mShadowMapWidth, this.mShadowMapHeight, 0, 6402, 5125, null);
            GLES20.glFramebufferTexture2D(36160, 36096, 3553, this.shadowTextureId[0], 0);
        }
        int[] iArr2 = this.renderTextureId;
        GLES20.glGenTextures(iArr2.length, iArr2, 0);
        GLES20.glBindTexture(3553, this.renderTextureId[0]);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLUtils.texImage2D(3553, 0, this.bitmap, 0);
        if (this.img != null) {
            int[] iArr3 = this.renderPlaneTextureId;
            GLES20.glGenTextures(iArr3.length, iArr3, 0);
            GLES20.glBindTexture(3553, this.renderPlaneTextureId[0]);
            GLES20.glTexParameteri(3553, 10240, 9729);
            GLES20.glTexParameteri(3553, 10241, 9729);
            GLUtils.texImage2D(3553, 0, this.img, 0);
        }
        if (GLES20.glCheckFramebufferStatus(36160) != 36053) {
            Log.e(TAG, "GL_FRAMEBUFFER_COMPLETE failed, CANNOT use FBO");
            throw new RuntimeException("GL_FRAMEBUFFER_COMPLETE failed, CANNOT use FBO");
        }
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        this.mDisplayWidth = i;
        this.mDisplayHeight = i2;
        GLES20.glViewport(0, 0, this.mDisplayWidth, this.mDisplayHeight);
        generateShadowFBO();
        float f = ((float) this.mDisplayWidth) / ((float) this.mDisplayHeight);
        Matrix.frustumM(this.mProjectionMatrix, 0, -f, f, -1.0f, 1.0f, 1.0f, 100.0f);
        Matrix.orthoM(this.mLightProjectionMatrix, 0, -50.0f, 50.0f, -50.0f, 50.0f, -150.0f, 200.0f);
    }

    public void onDrawFrame(GL10 gl10) {
        this.mFPSCounter.logFrame();
        Matrix.setLookAtM(this.mViewMatrix, 0, this.eyex, this.eyey, this.eyez, this.eyex_o, this.eyey_o, this.eyez_o, 0.0f, 1.0f, 0.0f);
        this.uvHandle = GLES20.glGetAttribLocation(this.mActiveProgram, "a_texCoord");
        this.scene_mvpMatrixUniform = GLES20.glGetUniformLocation(this.mActiveProgram, RenderConstants.MVP_MATRIX_UNIFORM);
        this.scene_mvMatrixUniform = GLES20.glGetUniformLocation(this.mActiveProgram, RenderConstants.MV_MATRIX_UNIFORM);
        this.scene_normalMatrixUniform = GLES20.glGetUniformLocation(this.mActiveProgram, RenderConstants.NORMAL_MATRIX_UNIFORM);
        this.scene_lightPosUniform = GLES20.glGetUniformLocation(this.mActiveProgram, RenderConstants.LIGHT_POSITION_UNIFORM);
        this.scene_schadowProjMatrixUniform = GLES20.glGetUniformLocation(this.mActiveProgram, RenderConstants.SHADOW_PROJ_MATRIX);
        this.scene_shadowtextureUniform = GLES20.glGetUniformLocation(this.mActiveProgram, RenderConstants.SHADOW_TEXTURE);
        this.scene_textureUniform = GLES20.glGetUniformLocation(this.mActiveProgram, RenderConstants.TEXTURE);
        this.scene_planetextureUniform = GLES20.glGetUniformLocation(this.mActiveProgram, RenderConstants.PLANE_TEXTURE);
        this.scene_positionAttribute = GLES20.glGetAttribLocation(this.mActiveProgram, RenderConstants.POSITION_ATTRIBUTE);
        this.scene_normalAttribute = GLES20.glGetAttribLocation(this.mActiveProgram, RenderConstants.NORMAL_ATTRIBUTE);
        this.scene_colorAttribute = GLES20.glGetAttribLocation(this.mActiveProgram, RenderConstants.COLOR_ATTRIBUTE);
        this.scene_mapStepXUniform = GLES20.glGetUniformLocation(this.mActiveProgram, RenderConstants.SHADOW_X_PIXEL_OFFSET);
        this.scene_mapStepYUniform = GLES20.glGetUniformLocation(this.mActiveProgram, RenderConstants.SHADOW_Y_PIXEL_OFFSET);
        int program = this.mDepthMapProgram.getProgram();
        this.shadow_mvpMatrixUniform = GLES20.glGetUniformLocation(program, RenderConstants.MVP_MATRIX_UNIFORM);
        this.shadow_positionAttribute = GLES20.glGetAttribLocation(program, RenderConstants.SHADOW_POSITION_ATTRIBUTE);
        if (this.p_time == 0) {
            this.p_time = SystemClock.elapsedRealtime();
            this.cum_time = 0;
        } else {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (this.is_run) {
                this.cum_time += elapsedRealtime - this.p_time;
            }
            this.p_time = elapsedRealtime;
        }
        long j = this.cum_time % 30000;
        float[] fArr = new float[16];
        Matrix.setIdentityM(fArr, 0);
        Matrix.translateM(fArr, 0, 0.0f, this.suncenter_y, 0.0f);
        Matrix.translateM(fArr, 0, 0.0f, 0.0f, this.suncenter_x);
        Matrix.rotateM(fArr, 0, this.sunatt, 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(fArr, 0, ((float) ((int) j)) * 0.006f, 0.0f, 0.0f, 1.0f);
        Matrix.multiplyMV(this.mActualLightPosition, 0, fArr, 0, this.mLightPosModel, 0);
        Sunset sunset2 = this.sunset;
        double d = this.lst;
        double d2 = (double) j;
        double d3 = this.time_res;
        Double.isNaN(d2);
        String GetTimeString = sunset2.GetTimeString(d + (d2 * d3));
        this.textTime.setText(" " + GetTimeString);
        Matrix.setIdentityM(this.mModelMatrix, 0);
        float[] fArr2 = this.mActualLightPosition;
        if (fArr2[1] < 0.0f) {
            fArr2[0] = 0.0f;
            fArr2[1] = -200.0f;
            fArr2[2] = 0.0f;
            Matrix.setLookAtM(this.mLightViewMatrix, 0, fArr2[0], fArr2[1], fArr2[2], fArr2[0], fArr2[1] - 100.0f, fArr2[2], 0.0f, 1.0f, 0.0f);
        } else {
            Matrix.setLookAtM(this.mLightViewMatrix, 0, fArr2[0], fArr2[1], fArr2[2], 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        }
        GLES20.glCullFace(1028);
        renderShadowMap();
        GLES20.glCullFace(1029);
        renderScene();
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            Log.w(TAG, "OpenGL error: " + glGetError);
        }
    }

    private void renderShadowMap() {
        GLES20.glBindFramebuffer(36160, this.fboId[0]);
        GLES20.glViewport(0, 0, this.mShadowMapWidth, this.mShadowMapHeight);
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(16640);
        GLES20.glUseProgram(this.mDepthMapProgram.getProgram());
        float[] fArr = new float[16];
        Matrix.multiplyMM(this.mLightMvpMatrix_staticShapes, 0, this.mLightViewMatrix, 0, this.mModelMatrix, 0);
        Matrix.multiplyMM(fArr, 0, this.mLightProjectionMatrix, 0, this.mLightMvpMatrix_staticShapes, 0);
        System.arraycopy(fArr, 0, this.mLightMvpMatrix_staticShapes, 0, 16);
        GLES20.glUniformMatrix4fv(this.shadow_mvpMatrixUniform, 1, false, this.mLightMvpMatrix_staticShapes, 0);
        this.mPlane.render(this.shadow_positionAttribute, 0, 0, 0, true);
        for (int i = 0; i < this.mCubes.size(); i++) {
            this.mCubes.get(i).render(this.shadow_positionAttribute, 0, 0, 0, true);
        }
    }

    private void renderScene() {
        GLES20.glBindFramebuffer(36160, 0);
        GLES20.glClear(16640);
        GLES20.glUseProgram(this.mActiveProgram);
        GLES20.glViewport(0, 0, this.mDisplayWidth, this.mDisplayHeight);
        int i = this.scene_mapStepXUniform;
        double d = (double) this.mShadowMapWidth;
        Double.isNaN(d);
        GLES20.glUniform1f(i, (float) (1.0d / d));
        int i2 = this.scene_mapStepYUniform;
        double d2 = (double) this.mShadowMapHeight;
        Double.isNaN(d2);
        GLES20.glUniform1f(i2, (float) (1.0d / d2));
        float[] fArr = new float[16];
        float[] fArr2 = {0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.5f, 0.5f, 0.5f, 1.0f};
        float[] fArr3 = new float[16];
        Matrix.multiplyMM(fArr, 0, this.mViewMatrix, 0, this.mModelMatrix, 0);
        System.arraycopy(fArr, 0, this.mMVMatrix, 0, 16);
        GLES20.glUniformMatrix4fv(this.scene_mvMatrixUniform, 1, false, this.mMVMatrix, 0);
        Matrix.invertM(fArr, 0, this.mMVMatrix, 0);
        Matrix.transposeM(this.mNormalMatrix, 0, fArr, 0);
        GLES20.glUniformMatrix4fv(this.scene_normalMatrixUniform, 1, false, this.mNormalMatrix, 0);
        Matrix.multiplyMM(fArr, 0, this.mProjectionMatrix, 0, this.mMVMatrix, 0);
        System.arraycopy(fArr, 0, this.mMVPMatrix, 0, 16);
        GLES20.glUniformMatrix4fv(this.scene_mvpMatrixUniform, 1, false, this.mMVPMatrix, 0);
        Matrix.multiplyMV(this.mLightPosInEyeSpace, 0, this.mViewMatrix, 0, this.mActualLightPosition, 0);
        int i3 = this.scene_lightPosUniform;
        float[] fArr4 = this.mLightPosInEyeSpace;
        GLES20.glUniform3f(i3, fArr4[0], fArr4[1], fArr4[2]);
        if (this.mHasDepthTextureExtension) {
            Matrix.multiplyMM(fArr3, 0, fArr2, 0, this.mLightMvpMatrix_staticShapes, 0);
            System.arraycopy(fArr3, 0, this.mLightMvpMatrix_staticShapes, 0, 16);
        }
        GLES20.glUniformMatrix4fv(this.scene_schadowProjMatrixUniform, 1, false, this.mLightMvpMatrix_staticShapes, 0);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, this.shadowTextureId[0]);
        GLES20.glUniform1i(this.scene_shadowtextureUniform, 0);
        GLES20.glActiveTexture(33985);
        GLES20.glBindTexture(3553, this.renderTextureId[0]);
        GLES20.glUniform1i(this.scene_textureUniform, 1);
        for (int i4 = 0; i4 < this.mCubes.size(); i4++) {
            this.mCubes.get(i4).render(this.scene_positionAttribute, this.scene_normalAttribute, this.scene_colorAttribute, this.uvHandle, false);
        }
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, this.shadowTextureId[0]);
        GLES20.glUniform1i(this.scene_shadowtextureUniform, 0);
        GLES20.glActiveTexture(33986);
        GLES20.glBindTexture(3553, this.renderPlaneTextureId[0]);
        GLES20.glUniform1i(this.scene_planetextureUniform, 2);
        this.mPlane.render(this.scene_positionAttribute, this.scene_normalAttribute, this.scene_colorAttribute, this.uvHandle, false);
    }

    public float getRotationX() {
        return this.mRotationX;
    }

    public void setRotationX(float f) {
        this.mRotationX = f;
    }

    public float getRotationY() {
        return this.mRotationY;
    }

    public void setRotationY(float f) {
        this.mRotationY = f;
    }
}
