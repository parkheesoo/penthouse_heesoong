package com.penthouse_bogmjary;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class FeeActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String value;
    private Button close_fee;

    String new_gy;
    int new_wy=0;
    String new_ey;

    int sunScore = 0;
    Button btn_cal;
    Button btn_include;
    Button btn_exclude;
    Button btn_include_water;
    Button btn_exclude_water;
    Button btn_elec;
    Button btn_gas;
    Button btn_water;
    Button btn_calculate;
    Button btn_exist;
    Button btn_exist_n;
    Button btn_know;
    Button btn_dknow;
    Button btn_down;
    Button btn_avg;
    Button btn_up;
    Button btn_one;
    Button btn_two;
    Button btn_three;
    TextView manage_include;
    TextView manage_include_water;
    TextView usage_before;
    TextView usage_new;
    TextView usage_before_won;
    TextInputLayout usage_before_input;
    TextView usage_new_won;
    TextInputLayout usage_new_input;
    TextView number_person;
    LinearLayout select_tap;
    LinearLayout manage_include_layout;
    LinearLayout manage_include_layout_water;
    LinearLayout usage_before_layout;
    LinearLayout usage_new_input_layout;
    LinearLayout calculate_layout;
    LinearLayout usage_new_layout;
    LinearLayout usage_before_input_layout;
    LinearLayout usage_before_check_layout;
    LinearLayout number_person_layout;
    TextView elec_y;
    TextView elec_s;
    TextView elec_w;
    TextView elec_y_r;
    TextView elec_s_r;
    TextView elec_w_r;
    TextView gas_y;
    TextView gas_s;
    TextView gas_w;
    TextView gas_y_r;
    TextView gas_s_r;
    TextView gas_w_r;
    TextView water_y;
    TextView water_y_r;
    TextView fee_elec;
    TextView fee_gas;
    TextView fee_water;
    Button btn_save;


    private Handler mHandler;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    //private String ip = "192.168.0.5"; // ㅈㅣㅂ
    // private String ip = "10.123.228.28"; //pc
    // private String ip = "172.20.10.4";
    // private String ip = "192.168.1.254";
    private String ip = "172.20.10.2";

    private int port_elec = 8080; // port 번호
    private int port_gas = 8000; // port 번호

    int tap = 0;

    int manage_elec = 0;
    int before_elec = 0;
    int select_elec = 0;
    int new_elec = 0;

    int manage_gas = 0;
    int before_gas = 0;
    int select_gas = 0;
    int new_gas = 0;

    int manage_water = 0;
    int number_water = 0;

    public String readString(DataInputStream dis) throws IOException{
        int length = dis.readInt();
        System.out.println(length);
        byte[] data = new byte[length];
        dis.readFully(data, 0, length);
        String text = new String(data, StandardCharsets.UTF_8);
        System.out.println(text);
        return text;
    }



    void connect_elec(String data){
        mHandler = new Handler();
        Log.w("connect","연결 하는중");

        Thread checkUpdate = new Thread() {
            public void run() {
                try {
                    System.out.println('0');
                    System.out.println(ip);
                    System.out.println(port_elec);
                    socket = new Socket(ip, port_elec);
                    System.out.println('1');
                    Log.w("서버 접속됨", "서버 접속됨");
                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }

                Log.w("edit 넘어가야 할 값 : ","안드로이드에서 서버로 연결요청");


                try {
                    dos = new DataOutputStream(socket.getOutputStream()); // output에 보낼꺼 넣음
                    dis = new DataInputStream(socket.getInputStream()); // input에 받을꺼 넣어짐
                    dos.writeUTF(data);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                Log.w("버퍼","버퍼생성 잘됨");

                try {
                    String new_es;
                    String new_ew;

                    while(true) {
                        new_ey = readString(dis);
                        new_es = readString(dis);
                        new_ew = readString(dis);

                        Log.w("연간 전기세 ",""+new_ey);
                        Log.w("하절기 전기세 ",""+new_es);
                        Log.w("동절기 전기세 ",""+new_ew);

                        elec_y_r.setText(new_ey);
                        elec_s_r.setText(new_es);
                        elec_w_r.setText(new_ew);
                    }
                }catch (Exception e){
                }
            }
        };

// 소켓 접속 시도, 버퍼생성
        checkUpdate.start();
    }

    void connect_gas(String data){
        mHandler = new Handler();
        Log.w("connect","연결 하는중");

        Thread checkUpdate = new Thread() {
            public void run() {
                try {
                    System.out.println('0');
                    System.out.println(ip);
                    System.out.println(port_gas);
                    socket = new Socket(ip, port_gas);
                    System.out.println('1');
                    Log.w("서버 접속됨", "서버 접속됨");
                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }

                Log.w("edit 넘어가야 할 값 : ","안드로이드에서 서버로 연결요청");


                try {
                    dos = new DataOutputStream(socket.getOutputStream()); // output에 보낼꺼 넣음
                    dis = new DataInputStream(socket.getInputStream()); // input에 받을꺼 넣어짐
                    dos.writeUTF(data);


                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                Log.w("버퍼","버퍼생성 잘됨");

                try {
                    String new_gs;
                    String new_gw;

                    while(true) {
                        new_gy = readString(dis);
                        new_gs = readString(dis);
                        new_gw = readString(dis);

                        Log.w("연간 가스비 ",""+new_gy);
                        Log.w("하절기 가스비 ",""+new_gs);
                        Log.w("동절기 가스비 ",""+new_gw);

                        gas_y_r.setText(new_gy);
                        gas_s_r.setText(new_gs);
                        gas_w_r.setText(new_gw);
                    }
                }catch (Exception e){
                }
            }
        };

// 소켓 접속 시도, 버퍼생성
        checkUpdate.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        close_fee = findViewById(R.id.close_feeactivity_btn);
        btn_include = findViewById(R.id.btn_include);
        btn_exclude = findViewById(R.id.btn_exclude);
        btn_include_water = findViewById(R.id.btn_include_water);
        btn_exclude_water = findViewById(R.id.btn_exclude_water);
        btn_elec = findViewById(R.id.btn_elec);
        btn_gas = findViewById(R.id.btn_gas);
        btn_water = findViewById(R.id.btn_water);
        btn_calculate = findViewById(R.id.btn_calculate);
        manage_include = findViewById(R.id.manage_include);
        manage_include_water = findViewById(R.id.manage_include_water);
        usage_before = findViewById(R.id.usage_before);
        btn_exist = findViewById(R.id.btn_exist);
        btn_exist_n = findViewById(R.id.btn_exist_n);
        usage_before_input = findViewById(R.id.usage_before_input);
        usage_before_won = findViewById(R.id.usage_before_won);
        usage_new = findViewById(R.id.usage_new);
        btn_know = findViewById(R.id.btn_know);
        btn_dknow = findViewById(R.id.btn_dknow);
        usage_new_input = findViewById(R.id.usage_new_input);
        usage_new_won = findViewById(R.id.usage_new_won);
        btn_down =findViewById(R.id.btn_down);
        btn_avg = findViewById(R.id.btn_avg);
        btn_up = findViewById(R.id.btn_up);
        select_tap = findViewById(R.id.select_tap);
        usage_before_layout = findViewById(R.id.usage_before_layout);
        manage_include_layout = findViewById(R.id.manage_include_layout);
        manage_include_layout_water = findViewById(R.id.manage_include_layout_water);
        usage_new_input_layout = findViewById(R.id.usage_new_input_layout);
        calculate_layout = findViewById(R.id.calculate_layout);
        usage_new_layout = findViewById(R.id.usage_new_layout);
        usage_before_input_layout = findViewById(R.id.usage_before_input_layout);
        usage_before_check_layout = findViewById(R.id.usage_before_check_layout);
        number_person = findViewById(R.id.number_person);
        number_person_layout = findViewById(R.id.number_person_layout);
        btn_one = findViewById(R.id.btn_one);
        btn_two = findViewById(R.id.btn_two);
        btn_three = findViewById(R.id.btn_three);
        elec_y = findViewById(R.id.elec_y);
        elec_s = findViewById(R.id.elec_s);
        elec_w = findViewById(R.id.elec_w);
        elec_y_r = findViewById(R.id.elec_y_r);
        elec_s_r = findViewById(R.id.elec_s_r);
        elec_w_r = findViewById(R.id.elec_w_r);
        gas_y = findViewById(R.id.gas_y);
        gas_s = findViewById(R.id.gas_s);
        gas_w = findViewById(R.id.gas_w);
        gas_y_r = findViewById(R.id.gas_y_r);
        gas_s_r = findViewById(R.id.gas_s_r);
        gas_w_r = findViewById(R.id.gas_w_r);
        water_y = findViewById(R.id.water_y);
        water_y_r = findViewById(R.id.water_y_r);
        fee_elec = findViewById(R.id.fee_elec);
        fee_gas = findViewById(R.id.fee_gas);
        fee_water = findViewById(R.id.fee_water);
        btn_cal = findViewById(R.id.btn_cal);
        btn_save = findViewById(R.id.btn_save);

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        mDatabase.child("currentseeaddress").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                value = dataSnapshot.getValue(String.class);

                mDatabase.child("bookmark").child(uid).child(value).child("suninfo").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Get Post object and use the values to update the UI
                        sunScore = dataSnapshot.getValue(int.class);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Getting Post failed, log a message
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });

        //비활성화
        //btn_visible.setEnabled(false);
        btn_elec.setEnabled(true);

        btn_elec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usage_before_input.getEditText().getText().clear();
                usage_new_input.getEditText().getText().clear();

                btn_save.setVisibility(btn_save.INVISIBLE);
                btn_elec.setEnabled(false);
                btn_cal.setEnabled(true);
                btn_gas.setEnabled(true);
                btn_water.setEnabled(true);
                btn_include.setEnabled(true);
                btn_exclude.setEnabled(true);
                btn_exist.setEnabled(true);
                btn_exist_n.setEnabled(true);
                btn_know.setEnabled(true);
                btn_dknow.setEnabled(true);
                btn_calculate.setEnabled(true);
                btn_down.setEnabled(true);
                btn_avg.setEnabled(true);
                btn_up.setEnabled(true);

                btn_include_water.setEnabled(true);
                btn_exclude_water.setEnabled(true);
                btn_one.setEnabled(true);
                btn_two.setEnabled(true);
                btn_three.setEnabled(true);

                elec_y.setVisibility(elec_y.INVISIBLE);
                elec_s.setVisibility(elec_s.INVISIBLE);
                elec_w.setVisibility(elec_w.INVISIBLE);
                elec_y_r.setVisibility(elec_y_r.INVISIBLE);
                elec_s_r.setVisibility(elec_s_r.INVISIBLE);
                elec_w_r.setVisibility(elec_w_r.INVISIBLE);
                gas_y.setVisibility(gas_y.INVISIBLE);
                gas_s.setVisibility(gas_s.INVISIBLE);
                gas_w.setVisibility(gas_w.INVISIBLE);
                gas_y_r.setVisibility(gas_y_r.INVISIBLE);
                gas_s_r.setVisibility(gas_s_r.INVISIBLE);
                gas_w_r.setVisibility(gas_w_r.INVISIBLE);
                water_y.setVisibility(water_y.INVISIBLE);
                water_y_r.setVisibility(water_y_r.INVISIBLE);
                fee_elec.setVisibility(fee_elec.INVISIBLE);
                fee_gas.setVisibility(fee_gas.INVISIBLE);
                fee_water.setVisibility(fee_water.INVISIBLE);


                btn_include.setVisibility(btn_include.VISIBLE);
                btn_exclude.setVisibility(btn_exclude.VISIBLE);
                manage_include.setVisibility(manage_include.VISIBLE);

                btn_include_water.setVisibility(btn_include_water.INVISIBLE);
                btn_exclude_water.setVisibility(btn_exclude_water.INVISIBLE);
                manage_include_water.setVisibility(manage_include_water.INVISIBLE);
                number_person.setVisibility(number_person.INVISIBLE);
                btn_one.setVisibility(btn_one.INVISIBLE);
                btn_two.setVisibility(btn_two.INVISIBLE);
                btn_three.setVisibility(btn_three.INVISIBLE);

                usage_before.setVisibility(usage_before.INVISIBLE);
                btn_exist.setVisibility(btn_exist.INVISIBLE);
                btn_exist_n.setVisibility(btn_exist_n.INVISIBLE);
                btn_down.setVisibility(btn_down.INVISIBLE);
                btn_avg.setVisibility(btn_avg.INVISIBLE);
                btn_up.setVisibility(btn_up.INVISIBLE);
                btn_know.setVisibility(btn_know.INVISIBLE);
                btn_dknow.setVisibility(btn_dknow.INVISIBLE);
                usage_new.setVisibility(usage_new.INVISIBLE);
                usage_new.setVisibility(usage_new.INVISIBLE);
                usage_before_input_layout.setVisibility(usage_before_input_layout.INVISIBLE);
                usage_new_input.setVisibility(usage_new_input.INVISIBLE);
                usage_new_won.setVisibility(usage_new_won.INVISIBLE);
                btn_calculate.setVisibility(btn_calculate.INVISIBLE);




                // manage_include_layout.setVisibility(manage_include_layout.VISIBLE);
//                usage_before_layout.setVisibility(usage_before_layout.INVISIBLE);
//                usage_before_input_layout.setVisibility(usage_before_input_layout.INVISIBLE);
//                usage_before_check_layout.setVisibility(usage_before_check_layout.INVISIBLE);
//                usage_new_layout.setVisibility(usage_new_layout.INVISIBLE);
//                usage_new_input_layout.setVisibility(usage_new_input_layout.INVISIBLE);
//                calculate_layout.setVisibility(usage_new_input_layout.INVISIBLE);
//
//                btn_include.setVisibility(btn_include.VISIBLE);
//                btn_exclude.setVisibility(btn_exclude.VISIBLE);
//                manage_include.setVisibility(manage_include.VISIBLE);
                //btn_visible.setEnabled(true);

            }
        });

        btn_gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                manage_include_layout.setVisibility(manage_include_layout.VISIBLE);
//                usage_before_layout.setVisibility(usage_before_layout.INVISIBLE);
//                usage_before_input_layout.setVisibility(usage_before_input_layout.INVISIBLE);
//                usage_before_check_layout.setVisibility(usage_before_check_layout.INVISIBLE);
//                usage_new_layout.setVisibility(usage_new_layout.INVISIBLE);
//                usage_new_input_layout.setVisibility(usage_new_input_layout.INVISIBLE);
//                calculate_layout.setVisibility(usage_new_input_layout.INVISIBLE);
                usage_before_input.getEditText().getText().clear();
                usage_new_input.getEditText().getText().clear();

                btn_save.setVisibility(btn_save.INVISIBLE);
                btn_gas.setEnabled(false);
                btn_elec.setEnabled(true);
                btn_water.setEnabled(true);
                btn_cal.setEnabled(true);
                btn_include.setEnabled(true);
                btn_exclude.setEnabled(true);
                btn_exist.setEnabled(true);
                btn_exist_n.setEnabled(true);
                btn_know.setEnabled(true);
                btn_dknow.setEnabled(true);
                btn_calculate.setEnabled(true);
                btn_down.setEnabled(true);
                btn_avg.setEnabled(true);
                btn_up.setEnabled(true);

                btn_include_water.setEnabled(true);
                btn_exclude_water.setEnabled(true);
                btn_one.setEnabled(true);
                btn_two.setEnabled(true);
                btn_three.setEnabled(true);

                elec_y.setVisibility(elec_y.INVISIBLE);
                elec_s.setVisibility(elec_s.INVISIBLE);
                elec_w.setVisibility(elec_w.INVISIBLE);
                elec_y_r.setVisibility(elec_y_r.INVISIBLE);
                elec_s_r.setVisibility(elec_s_r.INVISIBLE);
                elec_w_r.setVisibility(elec_w_r.INVISIBLE);
                gas_y.setVisibility(gas_y.INVISIBLE);
                gas_s.setVisibility(gas_s.INVISIBLE);
                gas_w.setVisibility(gas_w.INVISIBLE);
                gas_y_r.setVisibility(gas_y_r.INVISIBLE);
                gas_s_r.setVisibility(gas_s_r.INVISIBLE);
                gas_w_r.setVisibility(gas_w_r.INVISIBLE);
                water_y.setVisibility(water_y.INVISIBLE);
                water_y_r.setVisibility(water_y_r.INVISIBLE);
                fee_elec.setVisibility(fee_elec.INVISIBLE);
                fee_gas.setVisibility(fee_gas.INVISIBLE);
                fee_water.setVisibility(fee_water.INVISIBLE);

                btn_include_water.setVisibility(btn_include_water.INVISIBLE);
                btn_exclude_water.setVisibility(btn_exclude_water.INVISIBLE);
                manage_include_water.setVisibility(manage_include_water.INVISIBLE);
                number_person.setVisibility(number_person.INVISIBLE);
                btn_one.setVisibility(btn_one.INVISIBLE);
                btn_two.setVisibility(btn_two.INVISIBLE);
                btn_three.setVisibility(btn_three.INVISIBLE);

                btn_include.setVisibility(btn_include.VISIBLE);
                btn_exclude.setVisibility(btn_exclude.VISIBLE);
                manage_include.setVisibility(manage_include.VISIBLE);
                usage_before.setVisibility(usage_before.INVISIBLE);
                btn_exist.setVisibility(btn_exist.INVISIBLE);
                btn_exist_n.setVisibility(btn_exist_n.INVISIBLE);
                btn_down.setVisibility(btn_down.INVISIBLE);
                btn_avg.setVisibility(btn_avg.INVISIBLE);
                btn_up.setVisibility(btn_up.INVISIBLE);
                btn_know.setVisibility(btn_know.INVISIBLE);
                btn_dknow.setVisibility(btn_dknow.INVISIBLE);
                usage_new.setVisibility(usage_new.INVISIBLE);
                usage_new.setVisibility(usage_new.INVISIBLE);
                usage_before_input_layout.setVisibility(usage_before_input_layout.INVISIBLE);
                usage_new_input.setVisibility(usage_new_input.INVISIBLE);
                usage_new_won.setVisibility(usage_new_won.INVISIBLE);
                btn_calculate.setVisibility(btn_calculate.INVISIBLE);

                //btn_visible.setEnabled(true);

            }
        });

        btn_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //manage_include_layout.setVisibility(manage_include_layout.VISIBLE);


                usage_before_input.getEditText().getText().clear();
                usage_new_input.getEditText().getText().clear();

                btn_save.setVisibility(btn_save.INVISIBLE);
                btn_water.setEnabled(false);
                btn_cal.setEnabled(true);
                btn_elec.setEnabled(true);
                btn_gas.setEnabled(true);

                btn_include.setEnabled(true);
                btn_exclude.setEnabled(true);
                btn_exist.setEnabled(true);
                btn_exist_n.setEnabled(true);
                btn_know.setEnabled(true);
                btn_dknow.setEnabled(true);
                btn_calculate.setEnabled(true);
                btn_down.setEnabled(true);
                btn_avg.setEnabled(true);
                btn_up.setEnabled(true);

                btn_include_water.setEnabled(true);
                btn_exclude_water.setEnabled(true);
                btn_one.setEnabled(true);
                btn_two.setEnabled(true);
                btn_three.setEnabled(true);

                elec_y.setVisibility(elec_y.INVISIBLE);
                elec_s.setVisibility(elec_s.INVISIBLE);
                elec_w.setVisibility(elec_w.INVISIBLE);
                elec_y_r.setVisibility(elec_y_r.INVISIBLE);
                elec_s_r.setVisibility(elec_s_r.INVISIBLE);
                elec_w_r.setVisibility(elec_w_r.INVISIBLE);
                gas_y.setVisibility(gas_y.INVISIBLE);
                gas_s.setVisibility(gas_s.INVISIBLE);
                gas_w.setVisibility(gas_w.INVISIBLE);
                gas_y_r.setVisibility(gas_y_r.INVISIBLE);
                gas_s_r.setVisibility(gas_s_r.INVISIBLE);
                gas_w_r.setVisibility(gas_w_r.INVISIBLE);
                water_y.setVisibility(water_y.INVISIBLE);
                water_y_r.setVisibility(water_y_r.INVISIBLE);
                fee_elec.setVisibility(fee_elec.INVISIBLE);
                fee_gas.setVisibility(fee_gas.INVISIBLE);
                fee_water.setVisibility(fee_water.INVISIBLE);

                btn_include_water.setVisibility(btn_include_water.VISIBLE);
                btn_exclude_water.setVisibility(btn_exclude_water.VISIBLE);
                manage_include_water.setVisibility(manage_include_water.VISIBLE);

                number_person.setVisibility(number_person.INVISIBLE);
                btn_one.setVisibility(btn_one.INVISIBLE);
                btn_two.setVisibility(btn_two.INVISIBLE);
                btn_three.setVisibility(btn_three.INVISIBLE);

                btn_include.setVisibility(btn_include.INVISIBLE);
                btn_exclude.setVisibility(btn_exclude.INVISIBLE);
                manage_include.setVisibility(manage_include.INVISIBLE);
                usage_before.setVisibility(usage_before.INVISIBLE);
                btn_exist.setVisibility(btn_exist.INVISIBLE);
                btn_exist_n.setVisibility(btn_exist_n.INVISIBLE);
                btn_down.setVisibility(btn_down.INVISIBLE);
                btn_avg.setVisibility(btn_avg.INVISIBLE);
                btn_up.setVisibility(btn_up.INVISIBLE);
                btn_know.setVisibility(btn_know.INVISIBLE);
                btn_dknow.setVisibility(btn_dknow.INVISIBLE);
                usage_new.setVisibility(usage_new.INVISIBLE);
                usage_new.setVisibility(usage_new.INVISIBLE);
                usage_before_input_layout.setVisibility(usage_before_input_layout.INVISIBLE);
                usage_new_input.setVisibility(usage_new_input.INVISIBLE);
                usage_new_won.setVisibility(usage_new_won.INVISIBLE);
                btn_calculate.setVisibility(btn_calculate.INVISIBLE);
//                btn_include.setVisibility(btn_include.VISIBLE);
//                btn_exclude.setVisibility(btn_exclude.VISIBLE);
//                manage_include.setVisibility(manage_include.VISIBLE);
                //btn_visible.setEnabled(true);

            }
        });

        btn_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_gas.setEnabled(true);
                btn_elec.setEnabled(true);
                btn_water.setEnabled(true);
                btn_cal.setEnabled(false);

                elec_y.setVisibility(elec_y.VISIBLE);
                elec_s.setVisibility(elec_s.VISIBLE);
                elec_w.setVisibility(elec_w.VISIBLE);
                elec_y_r.setVisibility(elec_y_r.VISIBLE);
                elec_s_r.setVisibility(elec_s_r.VISIBLE);
                elec_w_r.setVisibility(elec_w_r.VISIBLE);
                gas_y.setVisibility(gas_y.VISIBLE);
                gas_s.setVisibility(gas_s.VISIBLE);
                gas_w.setVisibility(gas_w.VISIBLE);
                gas_y_r.setVisibility(gas_y_r.VISIBLE);
                gas_s_r.setVisibility(gas_s_r.VISIBLE);
                gas_w_r.setVisibility(gas_w_r.VISIBLE);
                water_y.setVisibility(water_y.VISIBLE);
                water_y_r.setVisibility(water_y_r.VISIBLE);
                fee_elec.setVisibility(fee_elec.VISIBLE);
                fee_gas.setVisibility(fee_gas.VISIBLE);
                fee_water.setVisibility(fee_water.VISIBLE);

                btn_save.setVisibility(btn_save.VISIBLE);
                btn_include_water.setVisibility(btn_include_water.INVISIBLE);
                btn_exclude_water.setVisibility(btn_exclude_water.INVISIBLE);
                manage_include_water.setVisibility(manage_include_water.INVISIBLE);

                number_person.setVisibility(number_person.INVISIBLE);
                btn_one.setVisibility(btn_one.INVISIBLE);
                btn_two.setVisibility(btn_two.INVISIBLE);
                btn_three.setVisibility(btn_three.INVISIBLE);

                btn_include.setVisibility(btn_include.INVISIBLE);
                btn_exclude.setVisibility(btn_exclude.INVISIBLE);
                manage_include.setVisibility(manage_include.INVISIBLE);
                usage_before.setVisibility(usage_before.INVISIBLE);
                btn_exist.setVisibility(btn_exist.INVISIBLE);
                btn_exist_n.setVisibility(btn_exist_n.INVISIBLE);
                btn_down.setVisibility(btn_down.INVISIBLE);
                btn_avg.setVisibility(btn_avg.INVISIBLE);
                btn_up.setVisibility(btn_up.INVISIBLE);
                btn_know.setVisibility(btn_know.INVISIBLE);
                btn_dknow.setVisibility(btn_dknow.INVISIBLE);
                usage_new.setVisibility(usage_new.INVISIBLE);
                usage_new.setVisibility(usage_new.INVISIBLE);
                usage_before_input_layout.setVisibility(usage_before_input_layout.INVISIBLE);
                usage_new_input.setVisibility(usage_new_input.INVISIBLE);
                usage_new_won.setVisibility(usage_new_won.INVISIBLE);
                btn_calculate.setVisibility(btn_calculate.INVISIBLE);

            }
        });

        btn_include.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_include.setEnabled(false);
                btn_exclude.setEnabled(true);

                btn_exist.setEnabled(true);
                btn_exist_n.setEnabled(true);
                btn_know.setEnabled(true);
                btn_dknow.setEnabled(true);
                btn_calculate.setEnabled(true);
                btn_down.setEnabled(true);
                btn_avg.setEnabled(true);
                btn_up.setEnabled(true);

                btn_calculate.setVisibility(btn_calculate.VISIBLE);
                usage_before.setVisibility(usage_before.INVISIBLE);
                btn_exist.setVisibility(btn_exist.INVISIBLE);
                btn_exist_n.setVisibility(btn_exist_n.INVISIBLE);
                btn_down.setVisibility(btn_down.INVISIBLE);
                btn_avg.setVisibility(btn_avg.INVISIBLE);
                btn_up.setVisibility(btn_up.INVISIBLE);
                btn_know.setVisibility(btn_know.INVISIBLE);
                btn_dknow.setVisibility(btn_dknow.INVISIBLE);
                usage_new.setVisibility(usage_new.INVISIBLE);
                usage_new.setVisibility(usage_new.INVISIBLE);
                usage_before_input_layout.setVisibility(usage_before_input_layout.INVISIBLE);
                usage_new_input.setVisibility(usage_new_input.INVISIBLE);
                usage_new_won.setVisibility(usage_new_won.INVISIBLE);

                if (btn_elec.isEnabled()==false){
                    tap = 1;
                    manage_elec = 1;
                    System.out.println(tap);
                    System.out.println(manage_elec);
                }
                else if (btn_gas.isEnabled()==false){
                    tap = 2;
                    manage_gas = 1;
                    System.out.println(tap);
                    System.out.println(manage_gas);
                }
                //btn_visible.setEnabled(true);
            }
        });

        btn_exclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_exclude.setEnabled(false);
                btn_include.setEnabled(true);


                btn_exist.setEnabled(true);
                btn_exist_n.setEnabled(true);
                btn_know.setEnabled(true);
                btn_dknow.setEnabled(true);
                btn_calculate.setEnabled(true);
                btn_down.setEnabled(true);
                btn_avg.setEnabled(true);
                btn_up.setEnabled(true);

                btn_exist.setVisibility(btn_exist.VISIBLE);
                btn_exist_n.setVisibility(btn_exist_n.VISIBLE);
                usage_before.setVisibility(usage_before.VISIBLE);
                //btn_visible.setEnabled(true);

                if (btn_elec.isEnabled()==false){
                    tap = 1;
                    manage_elec = 2;
                    System.out.println(tap);
                    System.out.println(manage_elec);
                }
                else if (btn_gas.isEnabled()==false){
                    tap = 2;
                    manage_gas = 2;
                    System.out.println(tap);
                    System.out.println(manage_gas);
                }

            }
        });

        btn_exist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_exist.setEnabled(false);

                btn_exist_n.setEnabled(true);
                btn_know.setEnabled(true);
                btn_dknow.setEnabled(true);
                btn_calculate.setEnabled(true);
                btn_down.setEnabled(true);
                btn_avg.setEnabled(true);
                btn_up.setEnabled(true);


                btn_know.setVisibility(btn_know.VISIBLE);
                btn_dknow.setVisibility(btn_dknow.VISIBLE);
                usage_new.setVisibility(usage_new.VISIBLE);
                usage_before_input_layout.setVisibility(usage_before_input_layout.VISIBLE);
                btn_down.setVisibility(btn_down.INVISIBLE);
                btn_avg.setVisibility(btn_avg.INVISIBLE);
                btn_up.setVisibility(btn_up.INVISIBLE);

                if (btn_elec.isEnabled()==false){
                    tap = 1;
                    before_elec = 1;
                    System.out.println(tap);
                    System.out.println(before_elec);
                }
                else if (btn_gas.isEnabled()==false){
                    tap = 2;
                    before_gas = 1;
                    System.out.println(tap);
                    System.out.println(before_gas);
                }
//                usage_before_input.setVisibility(usage_before_input.VISIBLE);
//                usage_before_won.setVisibility(usage_before_won.VISIBLE);
                //btn_visible.setEnabled(true);
            }
        });

        btn_exist_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_exist_n.setEnabled(false);

                btn_exist.setEnabled(true);
                btn_know.setEnabled(true);
                btn_dknow.setEnabled(true);
                btn_calculate.setEnabled(true);
                btn_down.setVisibility(btn_down.VISIBLE);
                btn_avg.setVisibility(btn_avg.VISIBLE);
                btn_up.setVisibility(btn_up.VISIBLE);
                btn_know.setVisibility(btn_know.VISIBLE);
                btn_dknow.setVisibility(btn_dknow.VISIBLE);
                usage_new.setVisibility(usage_new.VISIBLE);
                usage_before_input_layout.setVisibility(usage_before_input_layout.INVISIBLE);

                if (btn_elec.isEnabled()==false){
                    tap = 1;
                    before_elec = 2;
                    System.out.println(tap);
                    System.out.println(before_elec);
                }
                else if (btn_gas.isEnabled()==false){
                    tap = 1;
                    before_gas = 2;
                    System.out.println(tap);
                    System.out.println(before_gas);
                }
                //btn_visible.setEnabled(true);
            }
        });

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_down.setEnabled(false);
                btn_avg.setEnabled(true);
                btn_up.setEnabled(true);
                btn_exist.setEnabled(true);
                btn_know.setEnabled(true);
                btn_dknow.setEnabled(true);
                btn_calculate.setEnabled(true);

                if (btn_elec.isEnabled()==false){
                    tap = 1;
                    select_elec = 1;
                    System.out.println(tap);
                    System.out.println(select_elec);
                }
                else if (btn_gas.isEnabled()==false){
                    tap = 1;
                    select_gas = 1;
                    System.out.println(tap);
                    System.out.println(select_gas);
                }

            }
        });

        btn_avg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_down.setEnabled(true);
                btn_avg.setEnabled(false);
                btn_up.setEnabled(true);
                btn_exist.setEnabled(true);
                btn_know.setEnabled(true);
                btn_dknow.setEnabled(true);
                btn_calculate.setEnabled(true);

                if (btn_elec.isEnabled()==false){
                    tap = 1;
                    select_elec = 2;
                    System.out.println(tap);
                    System.out.println(select_elec);
                }
                else if (btn_gas.isEnabled()==false){
                    tap = 1;
                    select_gas = 2;
                    System.out.println(tap);
                    System.out.println(select_gas);
                }

            }
        });

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_down.setEnabled(true);
                btn_avg.setEnabled(true);
                btn_up.setEnabled(false);
                btn_exist.setEnabled(true);
                btn_know.setEnabled(true);
                btn_dknow.setEnabled(true);
                btn_calculate.setEnabled(true);

                if (btn_elec.isEnabled()==false){
                    tap = 1;
                    select_elec = 3;
                    System.out.println(tap);
                    System.out.println(select_elec);
                }
                else if (btn_gas.isEnabled()==false){
                    tap = 1;
                    select_gas = 3;
                    System.out.println(tap);
                    System.out.println(select_gas);
                }

            }
        });

        btn_dknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                btn_know.setEnabled(true);
                btn_calculate.setEnabled(true);
                btn_dknow.setEnabled(false);
                btn_calculate.setVisibility(btn_calculate.VISIBLE);
                usage_new_input.setVisibility(usage_new_input.INVISIBLE);
                usage_new_won.setVisibility(usage_new_won.INVISIBLE);

                if (btn_elec.isEnabled()==false){
                    tap = 1;
                    new_elec = 2;
                    System.out.println(tap);
                    System.out.println(new_elec);
                }
                else if (btn_gas.isEnabled()==false){
                    tap = 2;
                    new_gas = 2;
                    System.out.println(tap);
                    System.out.println(new_gas);
                }
                //btn_visible.setEnabled(true);
            }
        });

        btn_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_know.setEnabled(false);

                btn_dknow.setEnabled(true);
                btn_calculate.setEnabled(true);
                usage_new_input.setVisibility(usage_new_input.VISIBLE);
                usage_new_won.setVisibility(usage_new_won.VISIBLE);
                btn_calculate.setVisibility(btn_calculate.VISIBLE);
                //btn_visible.setEnabled(true);

                if (btn_elec.isEnabled()==false){
                    tap = 1;
                    new_elec = 1;
                    System.out.println(tap);
                    System.out.println(new_elec);
                }
                else if (btn_gas.isEnabled()==false){
                    tap = 2;
                    new_gas = 1;
                    System.out.println(tap);
                    System.out.println(new_gas);
                }
            }
        });

        btn_include_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_include_water.setEnabled(false);
                btn_exclude_water.setEnabled(true);

                btn_one.setEnabled(true);
                btn_two.setEnabled(true);
                btn_three.setEnabled(true);

                btn_calculate.setVisibility(btn_calculate.VISIBLE);

                tap = 3;
                manage_water = 1;
                new_wy = 0;
                water_y_r.setText("0");
            }
        });

        btn_exclude_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_include_water.setEnabled(true);
                btn_exclude_water.setEnabled(false);

                btn_one.setEnabled(true);
                btn_two.setEnabled(true);
                btn_three.setEnabled(true);

                number_person.setVisibility(number_person.VISIBLE);
                btn_one.setVisibility(btn_one.VISIBLE);
                btn_two.setVisibility(btn_two.VISIBLE);
                btn_three.setVisibility(btn_three.VISIBLE);

                tap = 3;
                manage_water = 2;
            }
        });

        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_one.setEnabled(false);
                btn_two.setEnabled(true);
                btn_three.setEnabled(true);


                btn_calculate.setVisibility(btn_calculate.VISIBLE);


                number_water = 1;
                new_wy = 6000;
                water_y_r.setText("6000");

            }
        });

        btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_one.setEnabled(true);
                btn_two.setEnabled(false);
                btn_three.setEnabled(true);
                btn_calculate.setVisibility(btn_calculate.VISIBLE);

                number_water = 2;
                new_wy = 11000;
                water_y_r.setText("11000");
            }
        });

        btn_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_one.setEnabled(true);
                btn_two.setEnabled(true);
                btn_three.setEnabled(false);
                btn_calculate.setVisibility(btn_calculate.VISIBLE);

                number_water = 3;
                new_wy = 17000;
                water_y_r.setText("17000");
            }
        });

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_elec.isEnabled()==false){
//                    System.out.printf("탭은 %d.", tap);
//                    System.out.printf("관리비는 %d.", manage_elec);
//                    System.out.printf("있는지는 %d.", before_elec);
//                    System.out.printf("이전사용량은 %d.", select_elec);
//                    System.out.printf("이사갈요금은 %d.", new_elec);
                    // tap, manage_elec, before_elec -> select_elec, new_elec, new_input
                    String input_before_e = usage_before_input.getEditText().getText().toString();
                    System.out.println(input_before_e);
                    String input_new_e = usage_new_input.getEditText().getText().toString();
                    System.out.println(input_new_e);
                    String d1 = Integer.toString(manage_elec);
                    String d2 = Integer.toString(before_elec);
                    String d3 = Integer.toString(select_elec);
                    String d4 = Integer.toString(new_elec);
                    String iljo_level = Integer.toString(sunScore);
                    String data = d1 +'+'+ d2 +'+'+ d3 +'+'+ d4 +'+'+ input_before_e +'+'+ input_new_e+ '+' + iljo_level;
                    System.out.println(data);
                    //String data = d1 +'+'+ d2 +'+'+ d3 +'+'+ d4 +'+'+ d5 +'+'+ d6;
//                    String ip = "192.168.0.5";
//                    int port = 8080;
                    connect_elec(data);



                }
                else if (btn_gas.isEnabled()==false){
                    System.out.println(tap);
                    System.out.println(manage_gas);
                    System.out.println(before_gas);
                    System.out.println(select_gas);
                    System.out.println(new_gas);
                    String input_before_g = usage_before_input.getEditText().getText().toString();
                    System.out.println(input_before_g);
                    String input_new_g = usage_new_input.getEditText().getText().toString();
                    System.out.println(input_new_g);

                    String d1 = Integer.toString(manage_gas);
                    String d2 = Integer.toString(before_gas);
                    String d3 = Integer.toString(select_gas);
                    String d4 = Integer.toString(new_gas);
                    String iljo_level2 = Integer.toString(sunScore);
                    String data = d1 +'+'+ d2 +'+'+ d3 +'+'+ d4 +'+'+ input_before_g +'+'+ input_new_g +'+' + iljo_level2;
                    System.out.println(data);

                    connect_gas(data);
                }
                else if (btn_water.isEnabled()==false){
                    System.out.println(tap);
                    System.out.println(manage_water);
                    System.out.println(number_water);
                }
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(new_ey);
                System.out.println(new_gy);
                System.out.println(new_wy);
                FeeData feeData = new FeeData(Integer.parseInt(new_ey), Integer.parseInt(new_gy), new_wy);
                mDatabase.child("bookmark").child(uid).child(value).child("feeData").setValue(feeData);
            }
        });

        close_fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}