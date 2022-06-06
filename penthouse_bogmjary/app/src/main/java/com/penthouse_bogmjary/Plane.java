package com.penthouse_bogmjary;

import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Plane {
    private final FloatBuffer planeColor;
    float[] planeColorData = {0.5f, 0.5f, 0.5f, -2.0f, 0.5f, 0.5f, 0.5f, -2.0f, 0.5f, 0.5f, 0.5f, -2.0f, 0.5f, 0.5f, 0.5f, -2.0f, 0.5f, 0.5f, 0.5f, -2.0f, 0.5f, 0.5f, 0.5f, -2.0f};
    float[] planeColorData1 = {0.5f, 0.5f, 0.5f, 1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.5f, 0.5f, 0.5f, 1.0f};
    private final FloatBuffer planeNormal;
    float[] planeNormalData = {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private final FloatBuffer planePosition;
    float[] planePositionData;
    int translateY = 0;
    int translateZ = 0;
    FloatBuffer uvBuffer;
    float[] uvData;

    public Plane(int i, int i2, boolean z) {
        float f = ((((float) i2) * 50.0f) / ((float) i)) / 2.0f;
        float f2 = -1.0f * f;
        int i3 = this.translateZ;
        this.planePositionData = new float[]{-25.0f, 0.0f, ((float) i3) + f2, -25.0f, 0.0f, ((float) i3) + f, 25.0f, 0.0f, ((float) i3) + f2, -25.0f, 0.0f, ((float) i3) + f, 25.0f, 0.0f, f + ((float) i3), 25.0f, 0.0f, f2 + ((float) i3)};
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(this.planePositionData.length * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        this.planePosition = allocateDirect.asFloatBuffer();
        ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(this.planeNormalData.length * 4);
        allocateDirect2.order(ByteOrder.nativeOrder());
        this.planeNormal = allocateDirect2.asFloatBuffer();
        if (z) {
            ByteBuffer allocateDirect3 = ByteBuffer.allocateDirect(this.planeColorData.length * 4);
            allocateDirect3.order(ByteOrder.nativeOrder());
            this.planeColor = allocateDirect3.asFloatBuffer();
            this.planeColor.put(this.planeColorData).position(0);
        } else {
            ByteBuffer allocateDirect4 = ByteBuffer.allocateDirect(this.planeColorData1.length * 4);
            allocateDirect4.order(ByteOrder.nativeOrder());
            this.planeColor = allocateDirect4.asFloatBuffer();
            this.planeColor.put(this.planeColorData1).position(0);
        }
        this.planePosition.put(this.planePositionData).position(0);
        this.planeNormal.put(this.planeNormalData).position(0);
        this.uvData = new float[12];
        float[] fArr = this.uvData;
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        fArr[2] = 0.0f;
        fArr[3] = 1.0f;
        fArr[4] = 1.0f;
        fArr[5] = 0.0f;
        fArr[6] = 0.0f;
        fArr[7] = 1.0f;
        fArr[8] = 1.0f;
        fArr[9] = 1.0f;
        fArr[10] = 1.0f;
        fArr[11] = 0.0f;
        ByteBuffer allocateDirect5 = ByteBuffer.allocateDirect(fArr.length * 4);
        allocateDirect5.order(ByteOrder.nativeOrder());
        this.uvBuffer = allocateDirect5.asFloatBuffer();
        this.uvBuffer.put(this.uvData);
        this.uvBuffer.position(0);
    }

    public void render(int i, int i2, int i3, int i4, boolean z) {
        this.planePosition.position(0);
        GLES20.glVertexAttribPointer(i, 3, 5126, false, 0, (Buffer) this.planePosition);
        GLES20.glEnableVertexAttribArray(i);
        if (!z) {
            this.planeNormal.position(0);
            GLES20.glVertexAttribPointer(i2, 3, 5126, false, 0, (Buffer) this.planeNormal);
            GLES20.glEnableVertexAttribArray(i2);
            this.planeColor.position(0);
            GLES20.glVertexAttribPointer(i3, 4, 5126, false, 0, (Buffer) this.planeColor);
            GLES20.glEnableVertexAttribArray(i3);
            this.uvBuffer.position(0);
            GLES20.glVertexAttribPointer(i4, 2, 5126, false, 0, (Buffer) this.uvBuffer);
            GLES20.glEnableVertexAttribArray(i4);
        }
        GLES20.glDrawArrays(4, 0, 6);
    }
}
