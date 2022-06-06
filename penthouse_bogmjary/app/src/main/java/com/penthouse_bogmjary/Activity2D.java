package com.penthouse_bogmjary;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.internal.view.SupportMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Activity2D extends AppCompatActivity implements View.OnClickListener {
    View.OnClickListener ButtonClick = new View.OnClickListener() {
        /* class com.shadow_sim.Activity2D.AnonymousClass1 */

        public void onClick(View view) {
            if (view.getId() == R.id.next && Activity2D.this.touchImageView.datas.size() > 0) {
                ArrayList<mdata> arrayList = Activity2D.this.touchImageView.datas;
                Intent intent = new Intent(Activity2D.this, Activity3D.class);
                intent.putParcelableArrayListExtra("data", arrayList);
                intent.putExtra("height_calibrate", Activity2D.this.touchImageView.height_calibrate);
                if (Activity2D.this.img != null) {
                    Bitmap createBitmap = Bitmap.createBitmap(Activity2D.this.touchImageView.viewWidth / 2, Activity2D.this.touchImageView.viewHeight / 2, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(createBitmap);
                    canvas.drawColor(-3355444);
                    Bitmap scaledBitmap = Activity2D.getScaledBitmap(Activity2D.this.img, Activity2D.this.touchImageView.viewWidth / 2, Activity2D.this.touchImageView.viewHeight / 2);
                    canvas.drawBitmap(scaledBitmap, (float) (((Activity2D.this.touchImageView.viewWidth / 2) - scaledBitmap.getWidth()) / 2), (float) (((Activity2D.this.touchImageView.viewHeight / 2) - scaledBitmap.getHeight()) / 2), (Paint) null);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    intent.putExtra("image", byteArrayOutputStream.toByteArray());
                }
                Activity2D.this.startActivity(intent);
            }
        }
    };
    private final int REQUEST_CODE_ALPHA = 100;
    private final int REQUEST_CODE_BRAVO = 100;
    private Button bt_delete;
    private Button bt_dn;
    private Button bt_pic;
    private Button bt_save;
    private Button bt_up;
    private Button enableZoomBtn;
    Bitmap img;
    private CustomImageView touchImageView;
    private TextView txt_info;

    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_activity2_d);
        findViewById(R.id.next).setOnClickListener(this.ButtonClick);
        this.enableZoomBtn = (Button) findViewById(R.id.enable_zoom);
        this.touchImageView = (CustomImageView) findViewById(R.id.zoom_iv);
        this.enableZoomBtn.setOnClickListener(this);
        this.bt_delete = (Button) findViewById(R.id.bt_delete);
        this.bt_delete.setOnClickListener(this);
        this.bt_dn = (Button) findViewById(R.id.bt_dn);
        this.bt_dn.setOnClickListener(this);
        this.bt_up = (Button) findViewById(R.id.bt_up);
        this.bt_up.setOnClickListener(this);
        this.bt_pic = (Button) findViewById(R.id.bt_pic);
        this.bt_pic.setOnClickListener(this);
        this.bt_save = (Button) findViewById(R.id.bt_file);
        this.bt_save.setOnClickListener(this);
        this.enableZoomBtn.setOnClickListener(this);
        this.enableZoomBtn.setOnClickListener(this);
        this.touchImageView.setDrawingEnabled(false);
        this.txt_info = (TextView) findViewById(R.id.txt_info);
        this.touchImageView.txt_info = this.txt_info;
        try {
            String str = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/shadow_sim") + "/conf.dat";
            if (new File(str).exists()) {
                FileInputStream fileInputStream = new FileInputStream(str);
                String readLine = new BufferedReader(new InputStreamReader(fileInputStream)).readLine();
                this.touchImageView.step = Float.parseFloat(readLine);
                this.txt_info.setText("height : 0 step : " + readLine);
                fileInputStream.close();
            }
        } catch (Exception unused) {
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_delete:
                this.touchImageView.deletedata();
                return;
            case R.id.bt_dn:
                this.touchImageView.down();
                return;
            case R.id.bt_file:
                ArrayList<mdata> arrayList = this.touchImageView.datas;
                Intent intent = new Intent(this, FileActivity.class);
                intent.putParcelableArrayListExtra("data", arrayList);
                intent.putExtra("height_calibrate", this.touchImageView.height_calibrate);
                startActivityForResult(intent, 100);
                return;
            case R.id.bt_pic:
                Intent intent2 = new Intent();
                intent2.setType("image/*");
                intent2.setAction("android.intent.action.GET_CONTENT");
                startActivityForResult(intent2, 1);
                return;
            case R.id.bt_up:
                this.touchImageView.up();
                return;
            case R.id.enable_zoom:
                if (this.enableZoomBtn.getText().equals("zoom")) {
                    this.touchImageView.setZoomEnable(false);
                    this.touchImageView.setDrawingEnabled(true);
                    this.enableZoomBtn.setText("draw");
                    this.bt_delete.setVisibility(View.INVISIBLE);
                    this.bt_dn.setVisibility(View.INVISIBLE);
                    this.bt_up.setVisibility(View.INVISIBLE);
                    return;
                }
                this.touchImageView.setZoomEnable(true);
                this.touchImageView.setDrawingEnabled(false);
                this.enableZoomBtn.setText("zoom");
                this.bt_delete.setVisibility(View.VISIBLE);
                this.bt_dn.setVisibility(View.VISIBLE);
                this.bt_up.setVisibility(View.VISIBLE);
                return;
            default:
                return;
        }
    }

    public void load_file(String str) {
        String str2 = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/shadow_sim") + "/data/";
        File file = new File(str2);
        if (file.exists() && file.listFiles().length > 0) {
            for (File file2 : file.listFiles()) {
                String str3 = str2 + "/" + file2.getName();
                if (str3.equals(str)) {
                    this.touchImageView.datas.clear();
                    try {
                        FileInputStream fileInputStream = new FileInputStream(str3);
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                        this.touchImageView.height_calibrate = Float.parseFloat(bufferedReader.readLine());
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine != null) {
                                mdata mdata = new mdata(Integer.parseInt(readLine), this.touchImageView.viewWidth, this.touchImageView.viewHeight);
                                int parseInt = Integer.parseInt(bufferedReader.readLine());
                                for (int i = 0; i < parseInt; i++) {
                                    mdata.points.add(new Point(Integer.parseInt(bufferedReader.readLine()), Integer.parseInt(bufferedReader.readLine())));
                                }
                                this.touchImageView.datas.add(mdata);
                            } else {
                                this.touchImageView.drawalldata(SupportMenu.CATEGORY_MASK);
                                fileInputStream.close();
                                return;
                            }
                        }
                    } catch (Exception unused) {
                        return;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1) {
            if (i2 == -1) {
                try {
                    InputStream openInputStream = getContentResolver().openInputStream(intent.getData());
                    this.img = BitmapFactory.decodeStream(openInputStream);
                    openInputStream.close();
                    this.touchImageView.resetview(this.img);
                    this.touchImageView.setZoomEnable(true);
                    this.touchImageView.setDrawingEnabled(false);
                    this.enableZoomBtn.setText("zoom");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (i == 100) {
            super.onActivityResult(i, i2, intent);
            if (i2 == -1 && i == 100) {
                load_file(intent.getStringExtra("key_file"));
            }
        }
    }

    public static Bitmap getScaledBitmap(Bitmap bitmap, int i, int i2) {
        Matrix matrix = new Matrix();
        matrix.setRectToRect(new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight()), new RectF(0.0f, 0.0f, (float) i, (float) i2), Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}