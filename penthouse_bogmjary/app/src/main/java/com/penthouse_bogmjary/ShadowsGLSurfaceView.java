package com.penthouse_bogmjary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ShadowsGLSurfaceView extends GLSurfaceView {
    private final float TOUCH_SCALE_FACTOR = 0.5625f;
    Bitmap bm = null;
    Context context;
    private float mPreviousX;
    private float mPreviousY;
    public ShadowsRenderer mRenderer;

    public ShadowsGLSurfaceView(Context context2) {
        super(context2);
        this.context = context2;
        this.bm = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
    }

    public Bitmap rotateImage(Bitmap bitmap, float f) {
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (motionEvent.getAction() == 2) {
            this.mRenderer.mRadian_H += (x - this.mPreviousX) / 200.0f;
            this.mRenderer.mRadian_V -= (y - this.mPreviousY) / 200.0f;
            if (this.mRenderer.mRadian_H > 6.2831845f) {
                this.mRenderer.mRadian_H = 0.0f;
            }
            if (this.mRenderer.mRadian_V > 1.2566369f) {
                this.mRenderer.mRadian_V = 1.2566369f;
            }
            if (this.mRenderer.mRadian_V < 0.0f) {
                this.mRenderer.mRadian_V = 0.0f;
            }
            ShadowsRenderer shadowsRenderer = this.mRenderer;
            shadowsRenderer.getClass();
            shadowsRenderer.eyex = ((float) (Math.cos((double) this.mRenderer.mRadian_H) * 20.0d)) + this.mRenderer.eyex_o;
            ShadowsRenderer shadowsRenderer2 = this.mRenderer;
            shadowsRenderer2.getClass();
            shadowsRenderer2.eyez = ((float) (Math.sin((double) this.mRenderer.mRadian_H) * 20.0d)) + this.mRenderer.eyez_o;
            ShadowsRenderer shadowsRenderer3 = this.mRenderer;
            shadowsRenderer3.getClass();
            double d = (double) this.mRenderer.eyey_o;
            Double.isNaN(d);
            shadowsRenderer3.eyey = (float) ((Math.cos((double) this.mRenderer.mRadian_V) * 20.0d) + d);
            ((ImageView) ((Activity3D) this.context).findViewById(R.id.arrow)).setImageBitmap(rotateImage(this.bm, ((this.mRenderer.mRadian_H - 1.5707961f) * 360.0f) / 6.2831845f));
            requestRender();
        }
        this.mPreviousX = x;
        this.mPreviousY = y;
        return true;
    }

    public void setRenderer(ShadowsRenderer shadowsRenderer) {
        this.mRenderer = shadowsRenderer;
        super.setRenderer((GLSurfaceView.Renderer) shadowsRenderer);
    }
}