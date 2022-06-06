package com.penthouse_bogmjary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_delete;
    private Button bt_save;
    int count = 0;
    private String currentPath = BuildConfig.FLAVOR;
    private ArrayList<mdata> datas;
    private String dirPath;
    float height_calibrate = 0.0f;
    private CustomChoiceListViewAdapter listAdapter;
    private ListView listView;
    private TextView messageView;
    private String nextPath = BuildConfig.FLAVOR;
    private String prevPath = BuildConfig.FLAVOR;
    long previousMil = 0;
    int previousPosition = -1;
    private String rootPath = BuildConfig.FLAVOR;
    private TextView textView;

    public void save() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save");
        builder.setMessage("파일 이름 입력");
        final EditText editText = new EditText(this);
        builder.setView(editText);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            /* class com.shadow_sim.FileActivity.AnonymousClass1 */

            public void onClick(DialogInterface dialogInterface, int i) {
                String obj = editText.getText().toString();
                dialogInterface.dismiss();
                FileActivity.this.save_file(obj);
                FileActivity fileActivity = FileActivity.this;
                if (!fileActivity.Init(fileActivity.rootPath)) {
                }
            }
        });
        builder.setNeutralButton("What?", new DialogInterface.OnClickListener() {
            /* class com.shadow_sim.FileActivity.AnonymousClass2 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            /* class com.shadow_sim.FileActivity.AnonymousClass3 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void save_file(String str) {
        if (this.datas != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(this.dirPath + "/" + str));
                fileOutputStream.write((Float.toString(this.height_calibrate) + '\n').getBytes());
                for (int i = 0; i < this.datas.size(); i++) {
                    mdata mdata = this.datas.get(i);
                    fileOutputStream.write((Integer.toString(mdata.height) + '\n').getBytes());
                    fileOutputStream.write((Integer.toString(mdata.points.size()) + '\n').getBytes());
                    for (int i2 = 0; i2 < mdata.points.size(); i2++) {
                        fileOutputStream.write((Integer.toString(mdata.points.get(i2).x) + '\n').getBytes());
                        fileOutputStream.write((Integer.toString(mdata.points.get(i2).y) + '\n').getBytes());
                    }
                }
                fileOutputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_file);
        this.bt_save = (Button) findViewById(R.id.bt_save);
        this.bt_save.setOnClickListener(this);
        this.bt_delete = (Button) findViewById(R.id.bt_delete);
        this.bt_delete.setOnClickListener(this);
        Intent intent = getIntent();
        this.datas = (ArrayList) intent.getSerializableExtra("data");
        this.height_calibrate = intent.getFloatExtra("height_calibrate", this.height_calibrate);
        this.dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/shadow_sim";
        File file = new File(this.dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        this.dirPath += "/data/";
        File file2 = new File(this.dirPath);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        this.textView = (TextView) findViewById(R.id.textView);
        this.listAdapter = new CustomChoiceListViewAdapter();
        this.listView = (ListView) findViewById(R.id.listView);
        this.listView.setAdapter((ListAdapter) this.listAdapter);
        this.rootPath = this.dirPath;
        if (Init(this.rootPath)) {
            this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                /* class com.shadow_sim.FileActivity.AnonymousClass4 */

                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    if (FileActivity.this.previousPosition == i && System.currentTimeMillis() - FileActivity.this.previousMil <= 300) {
                        String text = FileActivity.this.listAdapter.listViewItemList.get(i).getText();
                        FileActivity fileActivity = FileActivity.this;
                        fileActivity.currentPath = FileActivity.this.textView.getText().toString() + "/" + text;
                        Intent intent = new Intent();
                        intent.putExtra("key_file", FileActivity.this.currentPath);
                        FileActivity.this.setResult(-1, intent);
                        FileActivity.this.finish();
                    }
                    FileActivity fileActivity2 = FileActivity.this;
                    fileActivity2.previousPosition = i;
                    fileActivity2.previousMil = System.currentTimeMillis();
                }
            });
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_delete) {
            for (int count2 = this.listAdapter.getCount() - 1; count2 >= 0; count2--) {
                if (this.listView.isItemChecked(count2)) {
                    new File(this.rootPath + this.listAdapter.listViewItemList.get(count2).getText()).delete();
                    this.listAdapter.listViewItemList.remove(count2);
                }
            }
            this.listView.clearChoices();
            this.listAdapter.notifyDataSetChanged();
        } else if (id == R.id.bt_save) {
            save();
        }
    }

    public boolean Init(String str) {
        File file = new File(str);
        if (!file.isDirectory()) {
            Toast.makeText(this, "Not Directory", Toast.LENGTH_SHORT).show();
            return false;
        }
        this.textView.setText(str);
        String[] list = file.list();
        if (list == null) {
            Toast.makeText(this, "Could not find List", Toast.LENGTH_SHORT).show();
            return false;
        }
        this.listAdapter.listViewItemList.clear();
        for (String str2 : list) {
            this.listAdapter.addItem(ContextCompat.getDrawable(this, R.mipmap.ic_launcher), str2);
        }
        this.listAdapter.notifyDataSetChanged();
        return true;
    }

    public void nextPath(String str) {
        this.prevPath = this.currentPath;
        this.nextPath = this.currentPath + "/" + str;
        File file = new File(this.nextPath);
        if (!file.isDirectory()) {
            Toast.makeText(this, "Not Directory", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < file.list().length; i++) {
        }
        this.textView.setText(this.nextPath);
        this.listAdapter.notifyDataSetChanged();
    }

    public void prevPath(String str) {
        String str2 = this.currentPath;
        this.nextPath = str2;
        this.prevPath = str2;
        this.prevPath = this.prevPath.substring(0, this.prevPath.lastIndexOf("/"));
        File file = new File(this.prevPath);
        if (!file.isDirectory()) {
            Toast.makeText(this, "Not Directory", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < file.list().length; i++) {
        }
        this.textView.setText(this.prevPath);
        this.listAdapter.notifyDataSetChanged();
    }
}