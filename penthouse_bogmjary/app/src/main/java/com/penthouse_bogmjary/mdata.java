package com.penthouse_bogmjary;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class mdata implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        /* class com.shadow_sim.mdata.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public mdata createFromParcel(Parcel parcel) {
            return new mdata(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public mdata[] newArray(int i) {
            return new mdata[i];
        }
    };
    int height;
    ArrayList<Point> points = new ArrayList<>();
    int sc_h;
    int sc_v;

    public int describeContents() {
        return 0;
    }

    public mdata(int i, int i2, int i3) {
        this.height = i;
        this.sc_h = i2;
        this.sc_v = i3;
    }

    public mdata(Parcel parcel) {
        this.height = parcel.readInt();
        this.sc_h = parcel.readInt();
        this.sc_v = parcel.readInt();
        parcel.readTypedList(this.points, Point.CREATOR);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.height);
        parcel.writeInt(this.sc_h);
        parcel.writeInt(this.sc_v);
        parcel.writeTypedList(this.points);
    }
}