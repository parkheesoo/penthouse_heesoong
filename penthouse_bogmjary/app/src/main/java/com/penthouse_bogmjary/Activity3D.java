package com.penthouse_bogmjary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Activity3D extends AppCompatActivity implements View.OnClickListener {
    private Button bt_back;
    private Button bt_down;
    private Button bt_forward;
    private Button bt_left;
    private Button bt_right;
    private Button bt_run;
    private Button bt_up;
    float height_calibrate = 0.0f;
    Bitmap img;
    double latitude;
    double let;
    double longitude;
    double lst;
    private float mBiasType = 1.0f;
    private ShadowsGLSurfaceView mGLView;
    ImageView mImaveView;
    private float mShadowMapRatio = 2.0f;
    private float mShadowType = 1.0f;
    double month;
    private ShadowsRenderer renderer;
    SeekBar sb;
    double sunatt;
    double suncenter_x;
    double suncenter_y;
    Sunset sunset;
    private TextView textTime;
    double timezone;

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_activity3_d);
        Intent intent = getIntent();
        ArrayList<mdata> arrayList = (ArrayList) intent.getSerializableExtra("data");
        this.height_calibrate = intent.getFloatExtra("height_calibrate", this.height_calibrate);
        int i = 0;
        try {
            byte[] byteArrayExtra = getIntent().getByteArrayExtra("image");
            this.img = BitmapFactory.decodeByteArray(byteArrayExtra, 0, byteArrayExtra.length);
        } catch (Exception unused) {
            this.img = null;
        }
        try {
            String str = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/shadow_sim") + "/conf.dat";
            if (new File(str).exists()) {
                FileInputStream fileInputStream = new FileInputStream(str);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                bufferedReader.readLine();
                this.latitude = Double.parseDouble(bufferedReader.readLine());
                this.longitude = Double.parseDouble(bufferedReader.readLine());
                this.timezone = Double.parseDouble(bufferedReader.readLine());
                this.month = Double.parseDouble(bufferedReader.readLine());
                fileInputStream.close();
            }
        } catch (Exception unused2) {
        }
        this.sunset = new Sunset();
        double sunpos = this.sunset.sunpos((this.month - 1.0d) * 30.0d);
        double d = 0.0d;
        this.sunatt = 0.0d;
        double d2 = -1.0d;
        double d3 = 0.0d;
        boolean z = false;
        while (i < 100000) {
            double sunattitude = this.sunset.sunattitude(this.latitude, sunpos, (double) (((float) i) * 2.4E-4f));
            if (sunattitude > d3) {
                d3 = sunattitude;
            }
            if (d2 >= d || z) {
                z = true;
            } else {
                double d4 = this.month;
                d2 = sunattitude;
            }
            i++;
            d = d;
        }
        double d5 = this.latitude;
        if (d5 >= d) {
            this.sunatt = 90.0d - (d5 + 23.5d);
        } else {
            this.sunatt = 90.0d - ((-d5) + 23.5d);
            this.sunatt = -this.sunatt;
        }
        double d6 = 90.0d - this.sunatt;
        double sin = Math.sin(Math.toRadians(d3)) * 100.0d;
        this.suncenter_x = (Math.cos(Math.toRadians(d3)) * 100.0d) - (Math.cos(Math.toRadians(d6)) * 100.0d);
        this.suncenter_y = sin - (Math.sin(Math.toRadians(d6)) * 100.0d);
        if (this.latitude < d) {
            this.suncenter_x = -this.suncenter_x;
            this.suncenter_y = -this.suncenter_y;
        }
        this.lst = this.sunset.GetSunriseTime(2019, 3, 21, this.latitude, this.longitude, ((int) this.timezone) * -1, 0);
        this.let = this.sunset.GetSunsetTime(2019, 3, 21, this.latitude, this.longitude, ((int) this.timezone) * -1, 0);
        this.sunset.GetTimeString(this.lst);
        this.sunset.GetTimeString(this.let);
        this.mGLView = new ShadowsGLSurfaceView(this);
        this.mGLView.setEGLContextClientVersion(2);
        this.renderer = new ShadowsRenderer(this, this.img);
        this.mGLView.setRenderer(this.renderer);
        setContentView(this.mGLView);
        addContentView(getLayoutInflater().inflate(R.layout.activity_activity3_d, (ViewGroup) null), new ViewGroup.LayoutParams(-1, -1));
        this.bt_up = (Button) findViewById(R.id.bt_up);
        this.bt_up.setOnClickListener(this);
        this.bt_down = (Button) findViewById(R.id.bt_down);
        this.bt_down.setOnClickListener(this);
        this.bt_forward = (Button) findViewById(R.id.bt_forward);
        this.bt_forward.setOnClickListener(this);
        this.bt_back = (Button) findViewById(R.id.bt_back);
        this.bt_back.setOnClickListener(this);
        this.bt_left = (Button) findViewById(R.id.bt_left);
        this.bt_left.setOnClickListener(this);
        this.bt_right = (Button) findViewById(R.id.bt_right);
        this.bt_right.setOnClickListener(this);
        this.bt_run = (Button) findViewById(R.id.bt_run);
        this.bt_run.setOnClickListener(this);
        this.mImaveView = (ImageView) findViewById(R.id.arrow);
        this.sb = (SeekBar) findViewById(R.id.sb_time);
        this.sb.setMax(29998);
        ShadowsRenderer shadowsRenderer = this.renderer;
        double d7 = this.let;
        double d8 = this.lst;
        shadowsRenderer.time_res = (d7 - d8) / 30000.0d;
        shadowsRenderer.lst = d8;
        shadowsRenderer.sunset = this.sunset;
        shadowsRenderer.textTime = (TextView) findViewById(R.id.txt_time);
        this.sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /* class com.shadow_sim.Activity3D.AnonymousClass1 */

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                int i2 = i + 1;
                Log.d("onProgressChanged", BuildConfig.FLAVOR + i2);
                Sunset sunset = Activity3D.this.sunset;
                double d = Activity3D.this.lst;
                double d2 = (double) i2;
                double d3 = Activity3D.this.renderer.time_res;
                Double.isNaN(d2);
                String GetTimeString = sunset.GetTimeString(d + (d2 * d3));
                Activity3D activity3D = Activity3D.this;
                activity3D.textTime = (TextView) activity3D.findViewById(R.id.txt_time);
                TextView textView = Activity3D.this.textTime;
                textView.setText(" " + GetTimeString);
                Activity3D.this.renderer.is_run = false;
                Activity3D.this.renderer.cum_time = (long) i2;
            }
        });
        this.renderer.datas = arrayList;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                float f = this.mGLView.mRenderer.eyex_o - this.mGLView.mRenderer.eyex;
                float f2 = this.mGLView.mRenderer.eyez_o - this.mGLView.mRenderer.eyez;
                float f3 = f * 0.1f;
                this.mGLView.mRenderer.eyex -= f3;
                float f4 = f2 * 0.1f;
                this.mGLView.mRenderer.eyez -= f4;
                this.mGLView.mRenderer.eyex_o -= f3;
                this.mGLView.mRenderer.eyez_o -= f4;
                return;
            case R.id.bt_delete:
            case R.id.bt_dn:
            case R.id.bt_file:
            case R.id.bt_pic:
            case R.id.bt_save:
            default:
                return;
            case R.id.bt_down:
                this.mGLView.mRenderer.eyey -= 1.0f;
                this.mGLView.mRenderer.eyey_o -= 1.0f;
                return;
            case R.id.bt_forward:
                float f5 = this.mGLView.mRenderer.eyex_o - this.mGLView.mRenderer.eyex;
                float f6 = this.mGLView.mRenderer.eyez_o - this.mGLView.mRenderer.eyez;
                float f7 = f5 * 0.1f;
                this.mGLView.mRenderer.eyex += f7;
                float f8 = f6 * 0.1f;
                this.mGLView.mRenderer.eyez += f8;
                this.mGLView.mRenderer.eyex_o += f7;
                this.mGLView.mRenderer.eyez_o += f8;
                return;
            case R.id.bt_left:
                float f9 = this.mGLView.mRenderer.eyex_o - this.mGLView.mRenderer.eyex;
                ShadowsRenderer shadowsRenderer = this.mGLView.mRenderer;
                float f10 = (this.mGLView.mRenderer.eyez_o - this.mGLView.mRenderer.eyez) * -1.0f * 0.1f;
                shadowsRenderer.eyex = this.mGLView.mRenderer.eyex - f10;
                float f11 = f9 * 0.1f;
                this.mGLView.mRenderer.eyez -= f11;
                this.mGLView.mRenderer.eyex_o -= f10;
                this.mGLView.mRenderer.eyez_o -= f11;
                return;
            case R.id.bt_right:
                float f12 = (this.mGLView.mRenderer.eyez_o - this.mGLView.mRenderer.eyez) * 0.1f;
                this.mGLView.mRenderer.eyex -= f12;
                ShadowsRenderer shadowsRenderer2 = this.mGLView.mRenderer;
                float f13 = (this.mGLView.mRenderer.eyex_o - this.mGLView.mRenderer.eyex) * -1.0f * 0.1f;
                shadowsRenderer2.eyez = this.mGLView.mRenderer.eyez - f13;
                this.mGLView.mRenderer.eyex_o -= f12;
                this.mGLView.mRenderer.eyez_o -= f13;
                return;
            case R.id.bt_run:
                ShadowsRenderer shadowsRenderer3 = this.renderer;
                shadowsRenderer3.is_run = !shadowsRenderer3.is_run;
                return;
            case R.id.bt_up:
                this.mGLView.mRenderer.eyey += 1.0f;
                this.mGLView.mRenderer.eyey_o += 1.0f;
                return;
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        menuItem.getItemId();
        return super.onOptionsItemSelected(menuItem);
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity
    public void onPause() {
        super.onPause();
        this.mGLView.onPause();
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        this.mGLView.onResume();
    }

    public float getmBiasType() {
        return this.mBiasType;
    }

    private void setmBiasType(float f) {
        this.mBiasType = f;
    }

    public float getmShadowType() {
        return this.mShadowType;
    }

    private void setmShadowType(float f) {
        this.mShadowType = f;
    }

    public float getmShadowMapRatio() {
        return this.mShadowMapRatio;
    }

    private void setmShadowMapRatio(float f) {
        this.mShadowMapRatio = f;
    }
}