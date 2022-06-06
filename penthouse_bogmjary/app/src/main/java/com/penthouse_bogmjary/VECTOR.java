package com.penthouse_bogmjary;

public class VECTOR {
    float x;
    float y;
    float z;

    public VECTOR() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }

    public VECTOR(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public VECTOR(float f, float f2, float f3, float f4, float f5, float f6) {
        this.x = f4 - f;
        this.y = f5 - f2;
        this.z = f6 - f3;
    }

    /* access modifiers changed from: package-private */
    public void normalizer() {
        float f = this.x;
        float f2 = this.y;
        float f3 = (f * f) + (f2 * f2);
        float f4 = this.z;
        float sqrt = (float) Math.sqrt((double) (f3 + (f4 * f4)));
        this.x /= sqrt;
        this.y /= sqrt;
        this.z /= sqrt;
    }
}