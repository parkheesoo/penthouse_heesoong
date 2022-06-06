package com.penthouse_bogmjary;

import android.graphics.Point;
import android.opengl.GLES20;
import com.penthouse_bogmjary.triangulation.DelaunayTriangulator;
import com.penthouse_bogmjary.triangulation.NotEnoughPointsException;
import com.penthouse_bogmjary.triangulation.Triangle2D;
import com.penthouse_bogmjary.triangulation.Vector2D;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;

public class Cube {
    float[] ColorData;
    float[] NormalData;
    float[] PositionData;
    private final FloatBuffer cubeColor;
    private final FloatBuffer cubeNormal;
    private final FloatBuffer cubePosition;
    float cx;
    float cy;
    mdata data = null;
    int up_tri_count;
    FloatBuffer uvBuffer;
    float[] uvData;

    /* access modifiers changed from: package-private */
    public int ccw(int i, int i2, int i3, int i4, int i5, int i6) {
        int i7 = (((((i * i4) + (i3 * i6)) + (i5 * i2)) - (i2 * i3)) - (i4 * i5)) - (i6 * i);
        if (i7 > 0) {
            return 1;
        }
        return i7 < 0 ? -1 : 0;
    }

    /* access modifiers changed from: package-private */
    public void Cross_Product(VECTOR vector, VECTOR vector2, VECTOR vector3) {
        vector3.x = (vector.y * vector2.z) - (vector.z * vector2.y);
        vector3.y = (vector.z * vector2.x) - (vector.x * vector2.z);
        vector3.z = (vector.x * vector2.y) - (vector.y * vector2.x);
    }

    public Cube(mdata mdata, float[] fArr, float f, float[] fArr2, float f2) {
        int i;
        float f3;
        int i2;
        int i3;
        Point point;
        float f4;
        int i4;
        DelaunayTriangulator delaunayTriangulator;
        int i5;
        Point point2;
        float f5;
        Point point3;
        int i6 = 0;
        this.up_tri_count = 0;
        this.data = mdata;
        float f6 = 50.0f;
        float f7 = (((float) mdata.sc_v) * 50.0f) / ((float) mdata.sc_h);
        Point point4 = mdata.points.get(0);
        this.cx = (((float) (point4.x - (mdata.sc_h / 2))) * 50.0f) / ((float) mdata.sc_h);
        this.cy = (((float) (point4.y - (mdata.sc_v / 2))) * f7) / ((float) mdata.sc_v);
        float f8 = ((f2 * 50.0f) * ((float) mdata.height)) / ((float) mdata.sc_h);
        if (!isccw(mdata.points)) {
            Collections.reverse(mdata.points);
        }
        ArrayList arrayList = new ArrayList();
        DelaunayTriangulator delaunayTriangulator2 = new DelaunayTriangulator(arrayList);
        for (int i7 = 0; i7 < mdata.points.size(); i7++) {
            arrayList.add(new Vector2D((double) mdata.points.get(i7).x, (double) mdata.points.get(i7).y));
        }
        try {
            delaunayTriangulator2.triangulate();
        } catch (NotEnoughPointsException unused) {
        }
        int size = delaunayTriangulator2.getTriangles().size();
        int i8 = 0;
        while (true) {
            i = 1;
            if (i8 >= size) {
                break;
            }
            if (inn(delaunayTriangulator2.getTriangles().get(i8), mdata.points)) {
                this.up_tri_count++;
            }
            i8++;
        }
        this.PositionData = new float[((mdata.points.size() * 6 * 3) + (this.up_tri_count * 3 * 3 * 2))];
        int i9 = 0;
        int i10 = 0;
        while (i9 < mdata.points.size()) {
            Point point5 = mdata.points.get(i9);
            if (i9 == mdata.points.size() - i) {
                point3 = mdata.points.get(i6);
            } else {
                point3 = mdata.points.get(i9 + 1);
            }
            Point point6 = point3;
            int i11 = i10 + 1;
            this.PositionData[i10] = ((((float) (point5.x - point4.x)) * f6) / ((float) mdata.sc_h)) + this.cx;
            float[] fArr3 = this.PositionData;
            int i12 = i11 + 1;
            fArr3[i11] = 0.0f;
            int i13 = i12 + 1;
            fArr3[i12] = ((((float) (point5.y - point4.y)) * f7) / ((float) mdata.sc_v)) + this.cy;
            int i14 = i13 + 1;
            this.PositionData[i13] = ((((float) (point6.x - point4.x)) * f6) / ((float) mdata.sc_h)) + this.cx;
            float[] fArr4 = this.PositionData;
            int i15 = i14 + 1;
            fArr4[i14] = 0.0f;
            int i16 = i15 + 1;
            fArr4[i15] = ((((float) (point6.y - point4.y)) * f7) / ((float) mdata.sc_v)) + this.cy;
            int i17 = i16 + 1;
            this.PositionData[i16] = ((((float) (point6.x - point4.x)) * f6) / ((float) mdata.sc_h)) + this.cx;
            float[] fArr5 = this.PositionData;
            int i18 = i17 + 1;
            float f9 = f8 + 0.0f;
            fArr5[i17] = f9;
            int i19 = i18 + 1;
            fArr5[i18] = ((((float) (point6.y - point4.y)) * f7) / ((float) mdata.sc_v)) + this.cy;
            int i20 = i19 + 1;
            this.PositionData[i19] = ((((float) (point5.x - point4.x)) * 50.0f) / ((float) mdata.sc_h)) + this.cx;
            float[] fArr6 = this.PositionData;
            int i21 = i20 + 1;
            fArr6[i20] = 0.0f;
            int i22 = i21 + 1;
            fArr6[i21] = ((((float) (point5.y - point4.y)) * f7) / ((float) mdata.sc_v)) + this.cy;
            int i23 = i22 + 1;
            this.PositionData[i22] = ((((float) (point6.x - point4.x)) * 50.0f) / ((float) mdata.sc_h)) + this.cx;
            float[] fArr7 = this.PositionData;
            int i24 = i23 + 1;
            fArr7[i23] = f9;
            int i25 = i24 + 1;
            fArr7[i24] = ((((float) (point6.y - point4.y)) * f7) / ((float) mdata.sc_v)) + this.cy;
            int i26 = i25 + 1;
            this.PositionData[i25] = ((((float) (point5.x - point4.x)) * 50.0f) / ((float) mdata.sc_h)) + this.cx;
            float[] fArr8 = this.PositionData;
            int i27 = i26 + 1;
            fArr8[i26] = f9;
            i10 = i27 + 1;
            fArr8[i27] = ((((float) (point5.y - point4.y)) * f7) / ((float) mdata.sc_v)) + this.cy;
            i9++;
            i6 = 0;
            f6 = 50.0f;
            i = 1;
        }
        int i28 = 0;
        while (i28 < size) {
            Triangle2D triangle2D = delaunayTriangulator2.getTriangles().get(i28);
            if (!inn(triangle2D, mdata.points)) {
                i5 = i28;
                point2 = point4;
                f4 = f8;
                i4 = size;
                delaunayTriangulator = delaunayTriangulator2;
                f5 = f7;
            } else if (!triangle2D.isOrientedCCW()) {
                Vector2D vector2D = triangle2D.a;
                Vector2D vector2D2 = triangle2D.b;
                Vector2D vector2D3 = triangle2D.c;
                float[] fArr9 = this.PositionData;
                int i29 = i10 + 1;
                double d = (double) 50.0f;
                i5 = i28;
                double d2 = vector2D.x;
                double d3 = (double) point4.x;
                Double.isNaN(d3);
                Double.isNaN(d);
                fArr9[i10] = (((float) ((d2 - d3) * d)) / ((float) mdata.sc_h)) + this.cx;
                float[] fArr10 = this.PositionData;
                int i30 = i29 + 1;
                float f10 = f8 + 0.0f;
                fArr10[i29] = f10;
                int i31 = i30 + 1;
                double d4 = (double) f7;
                delaunayTriangulator = delaunayTriangulator2;
                double d5 = vector2D.y;
                point2 = point4;
                i4 = size;
                f4 = f8;
                double d6 = (double) point2.y;
                Double.isNaN(d6);
                Double.isNaN(d4);
                fArr10[i30] = (((float) ((d5 - d6) * d4)) / ((float) mdata.sc_v)) + this.cy;
                float[] fArr11 = this.PositionData;
                int i32 = i31 + 1;
                double d7 = vector2D2.x;
                double d8 = (double) point2.x;
                Double.isNaN(d8);
                Double.isNaN(d);
                fArr11[i31] = (((float) ((d7 - d8) * d)) / ((float) mdata.sc_h)) + this.cx;
                float[] fArr12 = this.PositionData;
                int i33 = i32 + 1;
                fArr12[i32] = f10;
                int i34 = i33 + 1;
                double d9 = vector2D2.y;
                double d10 = (double) point2.y;
                Double.isNaN(d10);
                Double.isNaN(d4);
                fArr12[i33] = (((float) (d4 * (d9 - d10))) / ((float) mdata.sc_v)) + this.cy;
                float[] fArr13 = this.PositionData;
                int i35 = i34 + 1;
                double d11 = vector2D3.x;
                double d12 = (double) point2.x;
                Double.isNaN(d12);
                Double.isNaN(d);
                fArr13[i34] = (((float) (d * (d11 - d12))) / ((float) mdata.sc_h)) + this.cx;
                float[] fArr14 = this.PositionData;
                int i36 = i35 + 1;
                fArr14[i35] = f10;
                double d13 = vector2D3.y;
                double d14 = (double) point2.y;
                Double.isNaN(d14);
                Double.isNaN(d4);
                fArr14[i36] = (((float) (d4 * (d13 - d14))) / ((float) mdata.sc_v)) + this.cy;
                i10 = i36 + 1;
                f5 = f7;
            } else {
                i5 = i28;
                point2 = point4;
                f4 = f8;
                i4 = size;
                delaunayTriangulator = delaunayTriangulator2;
                Vector2D vector2D4 = triangle2D.a;
                Vector2D vector2D5 = triangle2D.b;
                Vector2D vector2D6 = triangle2D.c;
                float[] fArr15 = this.PositionData;
                int i37 = i10 + 1;
                double d15 = (double) 50.0f;
                double d16 = vector2D6.x;
                double d17 = (double) point2.x;
                Double.isNaN(d17);
                Double.isNaN(d15);
                fArr15[i10] = (((float) ((d16 - d17) * d15)) / ((float) mdata.sc_h)) + this.cx;
                float[] fArr16 = this.PositionData;
                int i38 = i37 + 1;
                float f11 = f4 + 0.0f;
                fArr16[i37] = f11;
                int i39 = i38 + 1;
                f5 = f7;
                double d18 = (double) f5;
                double d19 = vector2D6.y;
                double d20 = (double) point2.y;
                Double.isNaN(d20);
                Double.isNaN(d18);
                fArr16[i38] = (((float) ((d19 - d20) * d18)) / ((float) mdata.sc_v)) + this.cy;
                float[] fArr17 = this.PositionData;
                int i40 = i39 + 1;
                double d21 = vector2D5.x;
                double d22 = (double) point2.x;
                Double.isNaN(d22);
                Double.isNaN(d15);
                fArr17[i39] = (((float) ((d21 - d22) * d15)) / ((float) mdata.sc_h)) + this.cx;
                float[] fArr18 = this.PositionData;
                int i41 = i40 + 1;
                fArr18[i40] = f11;
                int i42 = i41 + 1;
                double d23 = vector2D5.y;
                double d24 = (double) point2.y;
                Double.isNaN(d24);
                Double.isNaN(d18);
                fArr18[i41] = (((float) ((d23 - d24) * d18)) / ((float) mdata.sc_v)) + this.cy;
                float[] fArr19 = this.PositionData;
                int i43 = i42 + 1;
                double d25 = vector2D4.x;
                double d26 = (double) point2.x;
                Double.isNaN(d26);
                Double.isNaN(d15);
                fArr19[i42] = (((float) (d15 * (d25 - d26))) / ((float) mdata.sc_h)) + this.cx;
                float[] fArr20 = this.PositionData;
                int i44 = i43 + 1;
                fArr20[i43] = f11;
                double d27 = vector2D4.y;
                double d28 = (double) point2.y;
                Double.isNaN(d28);
                Double.isNaN(d18);
                fArr20[i44] = (((float) (d18 * (d27 - d28))) / ((float) mdata.sc_v)) + this.cy;
                i10 = i44 + 1;
            }
            i28 = i5 + 1;
            f7 = f5;
            point4 = point2;
            delaunayTriangulator2 = delaunayTriangulator;
            size = i4;
            f8 = f4;
        }
        Point point7 = point4;
        float f12 = f7;
        int i45 = size;
        int i46 = 0;
        while (i46 < i45) {
            Triangle2D triangle2D2 = delaunayTriangulator2.getTriangles().get(i46);
            if (!inn(triangle2D2, mdata.points)) {
                i2 = i45;
                i3 = i46;
                f3 = f12;
            } else if (!triangle2D2.isOrientedCCW()) {
                Vector2D vector2D7 = triangle2D2.a;
                Vector2D vector2D8 = triangle2D2.b;
                Vector2D vector2D9 = triangle2D2.c;
                float[] fArr21 = this.PositionData;
                int i47 = i10 + 1;
                double d29 = (double) 50.0f;
                i2 = i45;
                i3 = i46;
                double d30 = vector2D9.x;
                double d31 = (double) point7.x;
                Double.isNaN(d31);
                Double.isNaN(d29);
                fArr21[i10] = (((float) ((d30 - d31) * d29)) / ((float) mdata.sc_h)) + this.cx;
                float[] fArr22 = this.PositionData;
                int i48 = i47 + 1;
                fArr22[i47] = 0.0f;
                int i49 = i48 + 1;
                double d32 = (double) f12;
                double d33 = vector2D9.y;
                f3 = f12;
                double d34 = (double) point7.y;
                Double.isNaN(d34);
                Double.isNaN(d32);
                fArr22[i48] = (((float) ((d33 - d34) * d32)) / ((float) mdata.sc_v)) + this.cy;
                float[] fArr23 = this.PositionData;
                int i50 = i49 + 1;
                double d35 = vector2D8.x;
                double d36 = (double) point7.x;
                Double.isNaN(d36);
                Double.isNaN(d29);
                fArr23[i49] = (((float) ((d35 - d36) * d29)) / ((float) mdata.sc_h)) + this.cx;
                float[] fArr24 = this.PositionData;
                int i51 = i50 + 1;
                fArr24[i50] = 0.0f;
                int i52 = i51 + 1;
                double d37 = vector2D8.y;
                double d38 = (double) point7.y;
                Double.isNaN(d38);
                Double.isNaN(d32);
                fArr24[i51] = (((float) ((d37 - d38) * d32)) / ((float) mdata.sc_v)) + this.cy;
                float[] fArr25 = this.PositionData;
                int i53 = i52 + 1;
                double d39 = vector2D7.x;
                double d40 = (double) point7.x;
                Double.isNaN(d40);
                Double.isNaN(d29);
                fArr25[i52] = (((float) (d29 * (d39 - d40))) / ((float) mdata.sc_h)) + this.cx;
                float[] fArr26 = this.PositionData;
                int i54 = i53 + 1;
                fArr26[i53] = 0.0f;
                double d41 = vector2D7.y;
                double d42 = (double) point7.y;
                Double.isNaN(d42);
                Double.isNaN(d32);
                fArr26[i54] = (((float) (d32 * (d41 - d42))) / ((float) mdata.sc_v)) + this.cy;
                i10 = i54 + 1;
            } else {
                i2 = i45;
                i3 = i46;
                f3 = f12;
                Vector2D vector2D10 = triangle2D2.a;
                Vector2D vector2D11 = triangle2D2.b;
                Vector2D vector2D12 = triangle2D2.c;
                float[] fArr27 = this.PositionData;
                int i55 = i10 + 1;
                double d43 = (double) 50.0f;
                double d44 = vector2D10.x;
                double d45 = (double) point7.x;
                Double.isNaN(d45);
                Double.isNaN(d43);
                fArr27[i10] = (((float) ((d44 - d45) * d43)) / ((float) mdata.sc_h)) + this.cx;
                float[] fArr28 = this.PositionData;
                int i56 = i55 + 1;
                fArr28[i55] = 0.0f;
                int i57 = i56 + 1;
                double d46 = (double) f3;
                double d47 = vector2D10.y;
                point = point7;
                double d48 = (double) point.y;
                Double.isNaN(d48);
                Double.isNaN(d46);
                fArr28[i56] = (((float) ((d47 - d48) * d46)) / ((float) mdata.sc_v)) + this.cy;
                float[] fArr29 = this.PositionData;
                int i58 = i57 + 1;
                double d49 = vector2D11.x;
                double d50 = (double) point.x;
                Double.isNaN(d50);
                Double.isNaN(d43);
                fArr29[i57] = (((float) (d43 * (d49 - d50))) / ((float) mdata.sc_h)) + this.cx;
                float[] fArr30 = this.PositionData;
                int i59 = i58 + 1;
                fArr30[i58] = 0.0f;
                int i60 = i59 + 1;
                double d51 = vector2D11.y;
                double d52 = (double) point.y;
                Double.isNaN(d52);
                Double.isNaN(d46);
                fArr30[i59] = (((float) ((d51 - d52) * d46)) / ((float) mdata.sc_v)) + this.cy;
                float[] fArr31 = this.PositionData;
                int i61 = i60 + 1;
                double d53 = vector2D12.x;
                double d54 = (double) point.x;
                Double.isNaN(d54);
                Double.isNaN(d43);
                fArr31[i60] = (((float) (d43 * (d53 - d54))) / ((float) mdata.sc_h)) + this.cx;
                float[] fArr32 = this.PositionData;
                int i62 = i61 + 1;
                fArr32[i61] = 0.0f;
                double d55 = vector2D12.y;
                double d56 = (double) point.y;
                Double.isNaN(d56);
                Double.isNaN(d46);
                fArr32[i62] = (((float) (d46 * (d55 - d56))) / ((float) mdata.sc_v)) + this.cy;
                i10 = i62 + 1;
                i46 = i3 + 1;
                point7 = point;
                i45 = i2;
                f12 = f3;
            }
            point = point7;
            i46 = i3 + 1;
            point7 = point;
            i45 = i2;
            f12 = f3;
        }
        this.NormalData = new float[((mdata.points.size() * 6 * 3) + (this.up_tri_count * 2 * 3 * 3))];
        int i63 = 0;
        int i64 = 0;
        while (i63 < mdata.points.size()) {
            VECTOR vector = new VECTOR();
            float[] fArr33 = this.PositionData;
            int i65 = i63 * 6 * 3;
            VECTOR vector2 = new VECTOR(fArr33[i65 + 0], fArr33[i65 + 1], fArr33[i65 + 2], fArr33[i65 + 3], fArr33[i65 + 4], fArr33[i65 + 5]);
            float[] fArr34 = this.PositionData;
            Cross_Product(vector2, new VECTOR(fArr34[i65 + 9], fArr34[i65 + 10], fArr34[i65 + 11], fArr34[i65 + 15], fArr34[i65 + 16], fArr34[i65 + 17]), vector);
            vector.normalizer();
            int i66 = i64;
            int i67 = 0;
            while (i67 < 6) {
                int i68 = i66 + 1;
                this.NormalData[i66] = vector.x;
                int i69 = i68 + 1;
                this.NormalData[i68] = vector.y;
                this.NormalData[i69] = vector.z;
                i67++;
                i66 = i69 + 1;
            }
            i63++;
            i64 = i66;
        }
        int i70 = 0;
        while (i70 < this.up_tri_count) {
            VECTOR vector3 = new VECTOR();
            float[] fArr35 = this.PositionData;
            int i71 = i64 + 1;
            int i72 = i64 + 2;
            VECTOR vector4 = new VECTOR(fArr35[i64], fArr35[i71], fArr35[i72], fArr35[i64 + 3], fArr35[i64 + 4], fArr35[i64 + 5]);
            float[] fArr36 = this.PositionData;
            Cross_Product(vector4, new VECTOR(fArr36[i64], fArr36[i71], fArr36[i72], fArr36[i64 + 6], fArr36[i64 + 7], fArr36[i64 + 8]), vector3);
            vector3.normalizer();
            int i73 = i64;
            int i74 = 0;
            while (i74 < 3) {
                int i75 = i73 + 1;
                this.NormalData[i73] = vector3.x;
                int i76 = i75 + 1;
                this.NormalData[i75] = vector3.y;
                this.NormalData[i76] = vector3.z;
                i74++;
                i73 = i76 + 1;
            }
            i70++;
            i64 = i73;
        }
        int i77 = 0;
        while (i77 < this.up_tri_count) {
            VECTOR vector5 = new VECTOR();
            float[] fArr37 = this.PositionData;
            int i78 = i64 + 1;
            int i79 = i64 + 2;
            VECTOR vector6 = new VECTOR(fArr37[i64], fArr37[i78], fArr37[i79], fArr37[i64 + 3], fArr37[i64 + 4], fArr37[i64 + 5]);
            float[] fArr38 = this.PositionData;
            Cross_Product(vector6, new VECTOR(fArr38[i64 + 0], fArr38[i78], fArr38[i79], fArr38[i64 + 6], fArr38[i64 + 7], fArr38[i64 + 8]), vector5);
            vector5.normalizer();
            int i80 = i64;
            int i81 = 0;
            while (i81 < 3) {
                int i82 = i80 + 1;
                this.NormalData[i80] = vector5.x;
                int i83 = i82 + 1;
                this.NormalData[i82] = vector5.y;
                this.NormalData[i83] = vector5.z;
                i81++;
                i80 = i83 + 1;
            }
            i77++;
            i64 = i80;
        }
        this.ColorData = new float[((mdata.points.size() * 2 * 3 * 4) + (this.up_tri_count * 4 * 3 * 2))];
        for (int i84 = 0; i84 < (mdata.points.size() * 2 * 3) + (this.up_tri_count * 2 * 3); i84++) {
            float[] fArr39 = this.ColorData;
            int i85 = i84 * 4;
            fArr39[i85 + 0] = fArr2[0];
            fArr39[i85 + 1] = fArr2[1];
            fArr39[i85 + 2] = fArr2[2];
            fArr39[i85 + 3] = fArr2[3];
        }
        this.uvData = new float[((mdata.points.size() * 2 * 3 * 2) + (this.up_tri_count * 2 * 3 * 2))];
        int i86 = 0;
        int i87 = 0;
        while (i86 < mdata.points.size()) {
            float[] fArr40 = this.uvData;
            fArr40[i87 + 0] = 0.0f;
            fArr40[i87 + 1] = 1.0f;
            fArr40[i87 + 2] = 1.0f;
            fArr40[i87 + 3] = 1.0f;
            fArr40[i87 + 4] = 1.0f;
            fArr40[i87 + 5] = (1.0f - (((float) mdata.height) * 0.01f)) - 0.005f;
            float[] fArr41 = this.uvData;
            fArr41[i87 + 6] = 0.0f;
            fArr41[i87 + 7] = 1.0f;
            fArr41[i87 + 8] = 1.0f;
            fArr41[i87 + 9] = (1.0f - (((float) mdata.height) * 0.01f)) - 0.005f;
            float[] fArr42 = this.uvData;
            fArr42[i87 + 10] = 0.0f;
            fArr42[i87 + 11] = (1.0f - (((float) mdata.height) * 0.01f)) - 0.005f;
            i86++;
            i87 += 12;
        }
        int i88 = 0;
        while (i88 < this.up_tri_count * 2) {
            float[] fArr43 = this.uvData;
            fArr43[i87 + 0] = 0.0f;
            fArr43[i87 + 1] = 0.1f;
            fArr43[i87 + 2] = 0.1f;
            fArr43[i87 + 3] = 0.1f;
            fArr43[i87 + 4] = 0.1f;
            fArr43[i87 + 5] = 0.0f;
            i88++;
            i87 += 6;
        }
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(this.uvData.length * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        this.uvBuffer = allocateDirect.asFloatBuffer();
        this.uvBuffer.put(this.uvData);
        this.uvBuffer.position(0);
        ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(this.PositionData.length * 4);
        allocateDirect2.order(ByteOrder.nativeOrder());
        this.cubePosition = allocateDirect2.asFloatBuffer();
        ByteBuffer allocateDirect3 = ByteBuffer.allocateDirect(this.NormalData.length * 4);
        allocateDirect3.order(ByteOrder.nativeOrder());
        this.cubeNormal = allocateDirect3.asFloatBuffer();
        ByteBuffer allocateDirect4 = ByteBuffer.allocateDirect(this.ColorData.length * 4);
        allocateDirect4.order(ByteOrder.nativeOrder());
        this.cubeColor = allocateDirect4.asFloatBuffer();
        this.cubePosition.put(this.PositionData).position(0);
        this.cubeNormal.put(this.NormalData).position(0);
        this.cubeColor.put(this.ColorData).position(0);
    }

    /* access modifiers changed from: package-private */
    public boolean isccw(ArrayList<Point> arrayList) {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < arrayList.size()) {
            Point point = arrayList.get(i);
            int i4 = i + 1;
            Point point2 = arrayList.get(i4 % arrayList.size());
            Point point3 = arrayList.get((i + 2) % arrayList.size());
            int ccw = ccw(point.x, point.y, point2.x, point2.y, point3.x, point3.y);
            if (ccw < 0) {
                i2++;
            } else if (ccw > 0) {
                i3++;
            }
            i = i4;
        }
        if (i2 >= i3) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean inn(Triangle2D triangle2D, ArrayList<Point> arrayList) {
        if (isInside((triangle2D.a.x + triangle2D.b.x) / 2.0d, (triangle2D.a.y + triangle2D.b.y) / 2.0d, arrayList) && isInside((triangle2D.a.x + triangle2D.c.x) / 2.0d, (triangle2D.a.y + triangle2D.c.y) / 2.0d, arrayList) && isInside((triangle2D.b.x + triangle2D.c.x) / 2.0d, (triangle2D.b.y + triangle2D.c.y) / 2.0d, arrayList)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean isInside(double d, double d2, ArrayList<Point> arrayList) {
        boolean z = false;
        int i = 0;
        int i2 = 0;
        while (true) {
            boolean z2 = true;
            if (i >= arrayList.size() -1) {
                break;
            }
            int i3 = i + 1;
            int size = i3 % arrayList.size();
            if (((double) arrayList.get(i).y) != d2) {
                boolean z3 = ((double) arrayList.get(i).y) > d2;
                if (((double) arrayList.get(size).y) <= d2) {
                    z2 = false;
                }
                if (z3 == z2) {
                    i = i3;
                }
            }
            double d3 = (double) (arrayList.get(size).x - arrayList.get(i).x);
            double d4 = (double) arrayList.get(i).y;
            Double.isNaN(d4);
            Double.isNaN(d3);
            double d5 = (double) (arrayList.get(size).y - arrayList.get(i).y);
            Double.isNaN(d5);
            double d6 = (d3 * (d2 - d4)) / d5;
            double d7 = (double) arrayList.get(i).x;
            Double.isNaN(d7);
            if (d <= d6 + d7) {
                i2++;
            }
            i = i3;
        }
        boolean z4 = (i2 % 2 > 0) | false;
        int i4 = 0;
        int i5 = 0;
        while (i4 < arrayList.size()-1) {
            int i6 = i4 + 1;
            int size2 = i6 % arrayList.size();
            if (((double) arrayList.get(i4).y) != d2) {
                if ((((double) arrayList.get(i4).y) > d2) == (((double) arrayList.get(size2).y) > d2)) {
                    i4 = i6;
                }
            }
            double d8 = (double) (arrayList.get(size2).x - arrayList.get(i4).x);
            double d9 = (double) arrayList.get(i4).y;
            Double.isNaN(d9);
            Double.isNaN(d8);
            double d10 = d8 * (d2 - d9);
            double d11 = (double) (arrayList.get(size2).y - arrayList.get(i4).y);
            Double.isNaN(d11);
            double d12 = d10 / d11;
            double d13 = (double) arrayList.get(i4).x;
            Double.isNaN(d13);
            if (d >= d12 + d13) {
                i5++;
            }
            i4 = i6;
        }
        if (i5 % 2 > 0) {
            z = true;
        }
        return z4 | z;
    }

    /* access modifiers changed from: package-private */
    public boolean in(int i, int i2, ArrayList<Point> arrayList) {
        int i3;
        int size = arrayList.size();
        int i4 = 1;
        int i5 = 0;
        while (true) {
            i3 = size + 1;
            if (i4 >= i3) {
                break;
            }
            int i6 = i4 - 1;
            int i7 = ((i4 % size) + 1) - 1;
            if ((arrayList.get(i6).y > i2) != (arrayList.get(i7).y > i2)) {
                if (((double) i) < ((double) ((((arrayList.get(i7).x - arrayList.get(i6).x) * (i2 - arrayList.get(i6).y)) / (arrayList.get(i7).y - arrayList.get(i6).y)) + arrayList.get(i6).x))) {
                    i5++;
                }
            }
            i4++;
        }
        boolean z = i5 % 2 > 0;
        if (z) {
            return z;
        }
        int i8 = 0;
        for (int i9 = 1; i9 < i3; i9++) {
            int i10 = i9 - 1;
            int i11 = ((i9 % size) + 1) - 1;
            if ((arrayList.get(i10).y > i2) != (arrayList.get(i11).y > i2)) {
                if (((double) i) > ((double) ((((arrayList.get(i11).x - arrayList.get(i10).x) * (i2 - arrayList.get(i10).y)) / (arrayList.get(i11).y - arrayList.get(i10).y)) + arrayList.get(i10).x))) {
                    i8++;
                }
            }
        }
        return i8 % 2 > 0;
    }

    public void render(int i, int i2, int i3, int i4, boolean z) {
        this.cubePosition.position(0);
        GLES20.glVertexAttribPointer(i, 3, 5126, false, 0, (Buffer) this.cubePosition);
        GLES20.glEnableVertexAttribArray(i);
        if (!z) {
            this.cubeNormal.position(0);
            GLES20.glVertexAttribPointer(i2, 3, 5126, false, 0, (Buffer) this.cubeNormal);
            GLES20.glEnableVertexAttribArray(i2);
            this.cubeColor.position(0);
            GLES20.glVertexAttribPointer(i3, 4, 5126, false, 0, (Buffer) this.cubeColor);
            GLES20.glEnableVertexAttribArray(i3);
            this.uvBuffer.position(0);
            GLES20.glVertexAttribPointer(i4, 2, 5126, false, 0, (Buffer) this.uvBuffer);
            GLES20.glEnableVertexAttribArray(i4);
        }
        GLES20.glDrawArrays(4, 0, this.PositionData.length / 3);
    }
}