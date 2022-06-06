package com.penthouse_bogmjary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class BIRActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    LinearLayout select_type;
    Button btn_month;
    Button btn_jeonse_h;
    Button btn_jeonse;
    LinearLayout money_layout;
    TextView enter_fee;
    EditText deposit;
    TextView slash;
    EditText monthly;
    LinearLayout manage_fee_layout;
    TextView manage_fee;
    EditText manage_fee_enter;
    TextView managefee_one;
    LinearLayout parking_layout;
    TextView parking;
    Button btn_parking;
    Button btn_noparking;
    LinearLayout pet_layout;
    Button btn_pet;
    Button btn_nopet;
    LinearLayout ev_layout;
    Button btn_ev;
    Button btn_noev;
    LinearLayout size_layout;
    TextView size;
    EditText size_enter;
    TextView size_s;
    LinearLayout other_layout;
    TextInputLayout other_input;
    LinearLayout register_layout;
    Button btn_register;
    TextView something_special;

    String moneyMethod;
    String bo;
    String monthMoney;
    String jucha;
    String animal;
    String ev;
    String manageMoney;
    String big;
    String something;
    String value;

    int monthlyFee;
    int otherScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biractivity);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button close_bir_btn = findViewById(R.id.close_biractivity_btn);
        select_type = findViewById(R.id.select_type);
        btn_month = findViewById(R.id.btn_month);
        btn_jeonse_h = findViewById(R.id.btn_jeonse_h);
        btn_jeonse = findViewById(R.id.btn_jeonse);
        money_layout = findViewById(R.id.money_layout);
        enter_fee = findViewById(R.id.enter_fee);
        deposit = findViewById(R.id.deposit);
        slash = findViewById(R.id.slash);
        monthly = findViewById(R.id.monthly);
        manage_fee_layout = findViewById(R.id.manage_fee_layout);
        manage_fee = findViewById(R.id.manage_fee);
        manage_fee_enter = findViewById(R.id.manage_fee_enter);
        managefee_one = findViewById(R.id.managefee_one);
        parking_layout = findViewById(R.id.parking_layout);
        parking = findViewById(R.id.parking);
        btn_parking = findViewById(R.id.btn_parking);
        btn_noparking = findViewById(R.id.btn_noparking);
        pet_layout = findViewById(R.id.pet_layout);
        btn_pet = findViewById(R.id.btn_pet);
        btn_nopet = findViewById(R.id.btn_nopet);
        ev_layout = findViewById(R.id.ev_layout);
        btn_ev = findViewById(R.id.btn_ev);
        btn_noev = findViewById(R.id.btn_noev);
        size_layout = findViewById(R.id.size_layout);
        size = findViewById(R.id.size);
        size_enter = findViewById(R.id.size_enter);
        size_s = findViewById(R.id.size_s);
        other_layout = findViewById(R.id.other_layout);
        other_input = findViewById(R.id.other_input);
        register_layout = findViewById(R.id.register_layout);
        btn_register = findViewById(R.id.btn_register);
        something_special = findViewById(R.id.something_special_tv);

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        mDatabase.child("currentseeaddress").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                value = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });

        btn_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneyMethod = "mon";
                btn_month.setEnabled(false);
                btn_jeonse_h.setEnabled(true);
                btn_jeonse.setEnabled(true);

                btn_parking.setEnabled(true);
                btn_noparking.setEnabled(true);

                btn_pet.setEnabled(true);
                btn_nopet.setEnabled(true);

                btn_ev.setEnabled(true);
                btn_noev.setEnabled(true);

                deposit.getText().clear();
                monthly.getText().clear();
                manage_fee_enter.getText().clear();
                size_enter.getText().clear();
            }
        });

        btn_jeonse_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneyMethod = "ban";
                btn_month.setEnabled(true);
                btn_jeonse_h.setEnabled(false);
                btn_jeonse.setEnabled(true);

                btn_parking.setEnabled(true);
                btn_noparking.setEnabled(true);

                btn_pet.setEnabled(true);
                btn_nopet.setEnabled(true);

                btn_ev.setEnabled(true);
                btn_noev.setEnabled(true);

                deposit.getText().clear();
                monthly.getText().clear();
                manage_fee_enter.getText().clear();
                size_enter.getText().clear();
            }
        });

        btn_jeonse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneyMethod = "jeon";
                btn_month.setEnabled(true);
                btn_jeonse_h.setEnabled(true);
                btn_jeonse.setEnabled(false);

                btn_parking.setEnabled(true);
                btn_noparking.setEnabled(true);

                btn_pet.setEnabled(true);
                btn_nopet.setEnabled(true);

                btn_ev.setEnabled(true);
                btn_noev.setEnabled(true);

                deposit.getText().clear();
                monthly.getText().clear();
                manage_fee_enter.getText().clear();
                size_enter.getText().clear();
            }
        });

        btn_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jucha="ok";
                btn_parking.setEnabled(false);
                btn_noparking.setEnabled(true);

                btn_pet.setEnabled(true);
                btn_nopet.setEnabled(true);

                btn_ev.setEnabled(true);
                btn_noev.setEnabled(true);

            }
        });

        btn_noparking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jucha = "no";
                btn_parking.setEnabled(true);
                btn_noparking.setEnabled(false);

                btn_pet.setEnabled(true);
                btn_nopet.setEnabled(true);

                btn_ev.setEnabled(true);
                btn_noev.setEnabled(true);

            }
        });

        btn_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animal="ok";
                btn_pet.setEnabled(false);
                btn_nopet.setEnabled(true);

                btn_ev.setEnabled(true);
                btn_noev.setEnabled(true);
            }
        });

        btn_nopet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animal="no";
                btn_pet.setEnabled(true);
                btn_nopet.setEnabled(false);

                btn_ev.setEnabled(true);
                btn_noev.setEnabled(true);
            }
        });

        btn_ev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ev = "ok";
                btn_ev.setEnabled(false);
                btn_noev.setEnabled(true);
            }
        });

        btn_noev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ev="no";
                btn_ev.setEnabled(true);
                btn_noev.setEnabled(false);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r_deposit = deposit.getText().toString();
                String r_monthly = monthly.getText().toString();
                String r_manage = manage_fee_enter.getText().toString();
                String r_size = size_enter.getText().toString();
                something = something_special.getText().toString();

                int i_deposit = Integer.parseInt(r_deposit);
                int i_monthly = Integer.parseInt(r_monthly);
                int i_manage = Integer.parseInt(r_manage);
                int i_size = Integer.parseInt(r_size);

                System.out.println(r_deposit);
                System.out.println(r_monthly);
                System.out.println(r_manage);
                System.out.println(r_size);

                bo = r_deposit;
                monthMoney = r_monthly;
                manageMoney = r_manage;
                big = r_size;

                if (btn_month.isEnabled()==false){
                    monthlyFee= i_monthly + i_manage;
                }
                else if (btn_jeonse_h.isEnabled()==false){
                    double interest = i_deposit*0.9*0.028/12;
                    int in = (int) Math.floor(interest);
                    monthlyFee = i_monthly + i_manage + in;
                }
                else if (btn_jeonse.isEnabled()==false){
                    if (i_deposit >=11111){
                        double interest = 10000*0.028/12;
                        int in = (int) Math.floor(interest);
                        monthlyFee = i_manage + in;
                    }
                    else{
                        double interest = i_deposit*0.9*0.028/12;
                        int in = (int) Math.floor(interest);
                        monthlyFee = i_manage + in;
                    }
                }

                if (btn_ev.isEnabled()==false){
                    if (i_size >=10){
                        otherScore = 100;
                    }
                    else if (i_size >=8){
                        otherScore = 90;
                    }
                    else if (i_size >=6){
                        otherScore = 80;
                    }
                    else if (i_size>=5){
                        otherScore = 70;
                    }
                    else if (i_size>=4){
                        otherScore = 60;
                    }
                    else if (i_size >=3){
                        otherScore = 50;
                    }
                    else{
                        otherScore = 40;
                    }
                }
                else if (btn_noev.isEnabled()==false) {
                    if (i_size >= 10) {
                        otherScore = 90;
                    } else if (i_size >= 8) {
                        otherScore = 80;
                    } else if (i_size >= 6) {
                        otherScore = 70;
                    } else if (i_size >= 5) {
                        otherScore = 60;
                    } else if (i_size >= 4) {
                        otherScore = 50;
                    } else if (i_size >= 3) {
                        otherScore = 40;
                    } else {
                        otherScore = 30;
                    }
                }
                System.out.println(monthlyFee);

                Bookmark bookmark = new Bookmark(value,moneyMethod,bo,monthMoney,jucha,animal,ev,manageMoney,big,something, String.valueOf(monthlyFee),String.valueOf(otherScore));
                mDatabase.child("bookmark").child(uid).child(value).child("info").setValue(bookmark);
            }
        });

        close_bir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}