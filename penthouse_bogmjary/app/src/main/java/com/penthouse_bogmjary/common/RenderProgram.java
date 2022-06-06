package com.penthouse_bogmjary.common;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RenderProgram {
    private static final String TAG = "RenderProgram";
    private String mFragmentS;
    private int mPixelShader;
    private int mProgram;
    private String mVertexS;
    private int mVertexShader;

    public RenderProgram(String str, String str2) {
        setup(str, str2);
    }

    public RenderProgram(int i, int i2, Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(i)));
            for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                stringBuffer.append(readLine + "\n");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(i2)));
            for (String readLine2 = bufferedReader2.readLine(); readLine2 != null; readLine2 = bufferedReader2.readLine()) {
                stringBuffer2.append(readLine2 + "\n");
            }
            stringBuffer2.deleteCharAt(stringBuffer2.length() - 1);
        } catch (Exception e) {
            Log.d(TAG, "Could not read shader: " + e.getLocalizedMessage());
        }
        setup(stringBuffer.toString(), stringBuffer2.toString());
    }

    private void setup(String str, String str2) {
        this.mVertexS = str;
        this.mFragmentS = str2;
        if (createProgram() != 1) {
            throw new RuntimeException("Error at creating shaders");
        }
    }

    private int createProgram() {
        this.mVertexShader = loadShader(35633, this.mVertexS);
        if (this.mVertexShader == 0) {
            return 0;
        }
        this.mPixelShader = loadShader(35632, this.mFragmentS);
        if (this.mPixelShader == 0) {
            return 0;
        }
        this.mProgram = GLES20.glCreateProgram();
        int i = this.mProgram;
        if (i != 0) {
            GLES20.glAttachShader(i, this.mVertexShader);
            GLES20.glAttachShader(this.mProgram, this.mPixelShader);
            GLES20.glLinkProgram(this.mProgram);
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(this.mProgram, 35714, iArr, 0);
            if (iArr[0] != 1) {
                Log.e(TAG, "Could not link _program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(this.mProgram));
                GLES20.glDeleteProgram(this.mProgram);
                this.mProgram = 0;
                return 0;
            }
        } else {
            Log.d("CreateProgram", "Could not create program");
        }
        return 1;
    }

    private int loadShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        if (glCreateShader == 0) {
            return glCreateShader;
        }
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        Log.e(TAG, "Could not compile shader " + i + ":");
        Log.e(TAG, GLES20.glGetShaderInfoLog(glCreateShader));
        GLES20.glDeleteShader(glCreateShader);
        return 0;
    }

    public int getProgram() {
        return this.mProgram;
    }
}