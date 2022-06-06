package com.penthouse_bogmjary;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OptionActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_save;
    private Context context;
    String dirPath;
    String loadPath;
    private final nation_info[] nation = {new nation_info("나라(nation)", new city_info[]{new city_info("도시(city)", 0.0f, 0.0f, 0)}), new nation_info("한국(korea)", new city_info[]{new city_info("서울(seoul)", -126.58f, 37.33f, 9)}), new nation_info("뉴질랜드(New Zealand)", new city_info[]{new city_info("오클랜드(Auckland)", -174.76573f, -36.847385f, 12)}), new nation_info("독일(Germany)", new city_info[]{new city_info("라이프치히 (Leipzig)", -12.371358f, 51.339764f, 1), new city_info("뮌헨 (Munich)", -11.580213f, 48.139126f, 1), new city_info("베를린 (Berlin)", -13.411895f, 52.52378f, 1), new city_info("슈투트가르트 (Stuttgart)", -9.180708f, 48.777126f, 1), new city_info("쾰른 (Koeln)", -6.959911f, 50.940662f, 1), new city_info("프랑크푸르트 (Frankfurt)", -8.684307f, 50.11226f, 1), new city_info("하노버 (Hannover)", -9.735672f, 52.372025f, 1), new city_info("함부르크 (Hamburg)", -9.973259f, 53.54984f, 1)}), new nation_info("미국(America)", new city_info[]{new city_info("괌 (Guam)", -144.79373f, 13.444304f, 10), new city_info("뉴올리언스 (New Orleans)", 90.075f, 29.954445f, -5), new city_info("뉴욕 (New York)", 74.006386f, 40.71417f, -4), new city_info("라스베이거스 (Las Vegas)", 115.13639f, 36.175f, -7), new city_info("로스앤젤레스 (Los Angeles)", 118.242775f, 34.052223f, -7), new city_info("마이애미 (Miami)", 80.193886f, 25.77389f, -4), new city_info("보스턴 (Boston)", 71.06028f, 42.358334f, -4), new city_info("사이판 (Saipan)", -145.765f, 15.193458f, 10), new city_info("샌디에이고 (San Diego)", 117.15639f, 32.71528f, -7), new city_info("샌프란시스코 (San Francisco)", 122.418335f, 37.775f, -7), new city_info("시애틀 (Seattle)", 122.33083f, 47.606388f, -7), new city_info("시카고 (Chicago)", 87.65f, 41.85f, -5), new city_info("애틀랜타 (Atlanta)", 84.388054f, 33.74889f, -4), new city_info("오스틴 (Austin)", 97.742775f, 30.266945f, -5), new city_info("워싱턴DC (Washington DC)", 77.03667f, 38.895f, -4), new city_info("필라델피아 (Philadelphia)", 75.16417f, 39.95222f, -4), new city_info("하와이 (Hawaii)", 157.91917f, 21.39055f, -10)}), new nation_info("싱가포르(Singapore)", new city_info[]{new city_info("싱가포르 (Singapore)", -103.85f, 1.299999f, 8)}), new nation_info("영국(England)", new city_info[]{new city_info("런던 (London)", 0.126171f, 51.500195f, 0)}), new nation_info("오스트리아(Austria)", new city_info[]{new city_info("빈 (Wien)", -16.37f, 48.22f, 1)}), new nation_info("이탈리아(Italy)", new city_info[]{new city_info("로마 (Rome)", -12.482323f, 41.895466f, 1), new city_info("밀라노 (Milano)", -9.188126f, 45.463673f, 1), new city_info("베네치아 (Venezia)", -12.338397f, 45.43449f, 1), new city_info("피렌체 (Firenze)", -11.25687f, 43.76872f, 1)}), new nation_info("일본(Japan)", new city_info[]{new city_info("나고야 (Nagoya)", -136.91f, 35.15f, 9), new city_info("도쿄 (Tokyo)", -139.77f, 35.67f, 9), new city_info("삿포로 (Sapporo)", -141.33997f, 43.059982f, 9), new city_info("오사카 (Osaka)", -135.5f, 34.68f, 9)}), new nation_info("중국(China)", new city_info[]{new city_info("광저우 (Guangzhou)", -113.24999f, 23.119995f, 8), new city_info("난징 (Nanjing)", -118.779976f, 32.049988f, 8), new city_info("마카오 (Macao)", -113.54388f, 22.198746f, 8), new city_info("베이징 (Beijing)", -116.39998f, 39.93001f, 8), new city_info("상하이 (Shanghai)", -121.46999f, 31.229977f, 8), new city_info("선양 (Shenyang)", -123.45002f, 41.799984f, 8), new city_info("시안 (Xian)", -108.9f, 34.269985f, 8), new city_info("쑤저우 (Suzhou)", -120.62001f, 31.299969f, 8), new city_info("연길 (Yanji)", -129.52005f, 42.87999f, 8), new city_info("옌타이 (Yantai)", -121.40004f, 37.53001f, 8), new city_info("청두 (Chengdu)", -104.06997f, 30.67003f, 8), new city_info("칭다오 (Qingdao)", -104.06997f, 30.67003f, 8), new city_info("톈진 (Tianjin)", -117.200005f, 39.129993f, 8), new city_info("하얼빈 (Harbin)", -126.65005f, 45.75004f, 8), new city_info("항저우 (Hangzhou)", -120.170006f, 30.250023f, 8), new city_info("해남도 (Hainandao)", -109.79736f, 19.093267f, 8)}), new nation_info("체코(Czech)", new city_info[]{new city_info("프라하 (Prague)", -14.43f, 50.08f, 1)}), new nation_info("캐나다(Canada)", new city_info[]{new city_info("몬트리올 (Montreal)", 73.67563f, 45.512363f, -4), new city_info("밴쿠버 (Vancouver)", 123.11193f, 49.250492f, -4), new city_info("토론토 (Toronto)", 79.39249f, 43.723057f, -4)}), new nation_info("태국(Thailand)", new city_info[]{new city_info("방콕 (Bangkok)", -100.5f, 13.729999f, 7), new city_info("파타야 (Pattaya)", -114.08203f, 22.268764f, 7), new city_info("푸켓 (Phuket)", -98.3515f, 7.975008f, 7)}), new nation_info("프랑스(France)", new city_info[]{new city_info("파리 (Paris)", -2.350966f, 48.85656f, 1)}), new nation_info("헝가리(Hungary)", new city_info[]{new city_info("부다페스트 (Budapest)", -19.08f, 47.51f, 1)}), new nation_info("호주(Australia)", new city_info[]{new city_info("골드코스트 (Gold Coast)", -153.45703f, -27.994402f, 10), new city_info("멜버른 (Melbourne)", -144.96317f, -37.81425f, 10), new city_info("브리즈번 (Brisbane)", -153.02856f, -27.469288f, 10), new city_info("시드니 (Sydney)", -151.2071f, -33.867138f, 10), new city_info("캔버라 (Canberra)", -149.12636f, -35.306767f, 10)}), new nation_info("홍콩(Hong Kong)", new city_info[]{new city_info("홍콩 (Hong Kong)", -114.08203f, 22.268764f, 8)})};
    public Spinner sp_cities;
    public Spinner sp_nation;
    public EditText txt_latitude;
    public EditText txt_longitude;
    public EditText txt_month;
    public EditText txt_step;
    public EditText txt_timezone;

    /* access modifiers changed from: package-private */
    public class nation_info {
        public city_info[] cities;
        public String nation;

        public nation_info(String str, city_info[] city_infoArr) {
            this.nation = str;
            this.cities = city_infoArr;
        }
    }

    class city_info {
        String city;
        float latitude;
        float longitude;
        int timezone;

        public city_info(String str, float f, float f2, int i) {
            this.city = str;
            this.latitude = f2;
            this.longitude = f;
            this.timezone = i;
        }
    }

    /* access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_option);
        this.txt_step = (EditText) findViewById(R.id.txt_step);
        this.txt_latitude = (EditText) findViewById(R.id.txt_latitude);
        this.txt_longitude = (EditText) findViewById(R.id.txt_logitude);
        this.txt_timezone = (EditText) findViewById(R.id.txt_timezone);
        this.txt_month = (EditText) findViewById(R.id.txt_month);
        this.sp_nation = (Spinner) findViewById(R.id.sp_nation);
        this.sp_cities = (Spinner) findViewById(R.id.sp_citys);
        this.bt_save = (Button) findViewById(R.id.bt_save);
        this.bt_save.setOnClickListener(this);
        this.dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/shadow_sim";
        try {
            this.loadPath = this.dirPath + "/conf.dat";
            if (new File(this.loadPath).exists()) {
                FileInputStream fileInputStream = new FileInputStream(this.loadPath);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                this.txt_step.setText(bufferedReader.readLine());
                this.txt_latitude.setText(bufferedReader.readLine());
                this.txt_longitude.setText(bufferedReader.readLine());
                this.txt_timezone.setText(bufferedReader.readLine());
                this.txt_month.setText(bufferedReader.readLine());
                fileInputStream.close();
            }
        } catch (Exception unused) {
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            nation_info[] nation_infoArr = this.nation;
            if (i < nation_infoArr.length) {
                arrayList.add(nation_infoArr[i].nation);
                i++;
            } else {
                this.sp_nation.setAdapter((SpinnerAdapter) new ArrayAdapter(this, (int) R.layout.support_simple_spinner_dropdown_item, arrayList));
                this.context = this;
                this.sp_nation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    /* class com.shadow_sim.OptionActivity.AnonymousClass1 */

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        ArrayList arrayList = new ArrayList();
                        for (int i2 = 0; i2 < OptionActivity.this.nation[i].cities.length; i2++) {
                            arrayList.add(OptionActivity.this.nation[i].cities[i2].city);
                        }
                        OptionActivity.this.sp_cities.setAdapter((SpinnerAdapter) new ArrayAdapter(OptionActivity.this.context, (int) R.layout.support_simple_spinner_dropdown_item, arrayList));
                    }
                });
                this.sp_cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    /* class com.shadow_sim.OptionActivity.AnonymousClass2 */

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }

                    @Override // android.widget.AdapterView.OnItemSelectedListener
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                        if (OptionActivity.this.sp_nation.getSelectedItem() != "나라(nation)") {
                            int selectedItemId = (int) OptionActivity.this.sp_nation.getSelectedItemId();
                            OptionActivity.this.txt_latitude.setText(String.valueOf(OptionActivity.this.nation[selectedItemId].cities[i].latitude));
                            OptionActivity.this.txt_longitude.setText(String.valueOf(OptionActivity.this.nation[selectedItemId].cities[i].longitude));
                            OptionActivity.this.txt_timezone.setText(String.valueOf(OptionActivity.this.nation[selectedItemId].cities[i].timezone));
                        }
                    }
                });
                return;
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.bt_save) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(this.dirPath + "/conf.dat");
                String obj = this.txt_step.getText().toString();
                fileOutputStream.write((obj + '\n').getBytes());
                String obj2 = this.txt_latitude.getText().toString();
                fileOutputStream.write((obj2 + '\n').getBytes());
                String obj3 = this.txt_longitude.getText().toString();
                fileOutputStream.write((obj3 + '\n').getBytes());
                String obj4 = this.txt_timezone.getText().toString();
                fileOutputStream.write((obj4 + '\n').getBytes());
                String obj5 = this.txt_month.getText().toString();
                fileOutputStream.write((obj5 + '\n').getBytes());
                fileOutputStream.close();
                finish();
            } catch (Exception unused) {
            }
        }
    }
}