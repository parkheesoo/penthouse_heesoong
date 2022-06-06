package com.penthouse_bogmjary;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class IljoMainActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    View.OnClickListener ButtonClick = new View.OnClickListener() {
        /* class com.shadow_sim.MainActivity.AnonymousClass1 */

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_option /*{ENCODED_INT: 2131230822}*/:
                    IljoMainActivity.this.startActivityForResult(new Intent(IljoMainActivity.this, OptionActivity.class), 1);
                    return;
                case R.id.img_start /*{ENCODED_INT: 2131230823}*/:
                    IljoMainActivity.this.startActivity(new Intent(IljoMainActivity.this, Activity2D.class));
                    return;
                default:
                    return;
            }
        }
    };
    private Button bt_help;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_iljo_main);
        Button close_iljo_btn = (Button)findViewById(R.id.close_iljoactivity_btn);
        Button reg_sunscore=(Button)findViewById(R.id.register_sun_score_btn);

        close_iljo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        reg_sunscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegsunscoreActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.img_start).setOnClickListener(this.ButtonClick);
        findViewById(R.id.img_option).setOnClickListener(this.ButtonClick);
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            fileinit();
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 2);
    }

    public void onClick(View view) {
        view.getId();
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 2) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                String str = strArr[i2];
                int i3 = iArr[i2];
                if (str.equals("android.permission.READ_EXTERNAL_STORAGE") && i3 == 0) {
                    fileinit();
                }
            }
        }
    }

    public static void copy(File file, File file2) {
        Throwable th;
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        Exception e;
        File[] listFiles = file.listFiles();
        for (File file3 : listFiles) {
            File file4 = new File(file2.getAbsolutePath() + File.separator + file3.getName());
            if (file3.isDirectory()) {
                file4.mkdir();
                copy(file3, file4);
            } else {
                FileInputStream fileInputStream2 = null;
                try {
                    fileInputStream = new FileInputStream(file3);
                    try {
                        fileOutputStream = new FileOutputStream(file4);
                    } catch (Exception e2) {
                        e = e2;
                        fileOutputStream = null;
                        fileInputStream2 = fileInputStream;
                        try {
                            e.printStackTrace();
                            fileInputStream2.close();
                            fileOutputStream.close();
                        } catch (Throwable th2) {
                            th = th2;
                            fileInputStream = fileInputStream2;
                            try {
                                fileInputStream.close();
                                fileOutputStream.close();
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        fileOutputStream = null;
                        fileInputStream.close();
                        fileOutputStream.close();
                        throw th;
                    }
                    try {
                        byte[] bArr = new byte[4096];
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read != -1) {
                                fileOutputStream.write(bArr, 0, read);
                            } else {
                                try {
                                    break;
                                } catch (Exception e4) {
                                    e4.printStackTrace();
                                }
                            }
                        }
                        fileInputStream.close();
                        fileOutputStream.close();
                    } catch (Exception e5) {
                        e = e5;
                        fileInputStream2 = fileInputStream;
                        e.printStackTrace();
                        fileInputStream2.close();
                        fileOutputStream.close();
                    } catch (Throwable th4) {
                        th = th4;
                        fileInputStream.close();
                        fileOutputStream.close();
                        throw th;
                    }
                } catch (Exception e6) {
                    e = e6;
                    fileOutputStream = null;
                    e.printStackTrace();
                } catch (Throwable th5) {
                    th = th5;
                    fileOutputStream = null;
                    fileInputStream = null;
                }
            }
        }
    }

    public static void delete(String str) {
        File file = new File(str);
        try {
            if (file.exists()) {
                File[] listFiles = file.listFiles();
                for (int i = 0; i < listFiles.length; i++) {
                    if (listFiles[i].isFile()) {
                        listFiles[i].delete();
                    } else {
                        delete(listFiles[i].getPath());
                    }
                    listFiles[i].delete();
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void fileinit() {
        File file = new File(getFilesDir().getAbsolutePath());
        copy(file, new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/shadow_sim"));
        delete(file.toString());
        String str = Environment.getExternalStorageDirectory().getAbsolutePath() + "/shadow_sim";
        try {
            File file2 = new File(str);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            String str2 = str + "/conf.dat";
            boolean z = false;
            boolean z2 = true;
            if (new File(str2).exists()) {
                FileInputStream fileInputStream = new FileInputStream(str2);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                if (bufferedReader.readLine() == null) {
                    z = true;
                }
                if (bufferedReader.readLine() == null) {
                    z = true;
                }
                if (bufferedReader.readLine() == null) {
                    z = true;
                }
                if (bufferedReader.readLine() == null) {
                    z = true;
                }
                if (bufferedReader.readLine() != null) {
                    z2 = z;
                }
                fileInputStream.close();
            }
            if (z2) {
                FileOutputStream fileOutputStream = new FileOutputStream(str2);
                fileOutputStream.write("2.8\n".getBytes());
                fileOutputStream.write("37.34\n".getBytes());
                fileOutputStream.write("-126.589999\n".getBytes());
                fileOutputStream.write("9\n".getBytes());
                fileOutputStream.write("8\n".getBytes());
                fileOutputStream.close();
            }
        } catch (Exception unused) {
        }
    }
}