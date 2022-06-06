package com.penthouse_bogmjary;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

public class ScoreResultTwoActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    String value;
    String uid;
    int monthMoney=0;
    int bo=0;
    int big = 0;
    String jucha;
    String animal;
    String ev;
    int iljoScore=0;
    int pyonScore=0;
    int otherScore = 0;
    int elecMoney=0;
    int gasMoney=0;
    int waterMoney=0;
    int monthlyFee=0;
    int score_month = 0;
    int real_month =0;

    TextView first_tv;
    TextView second_tv;
    TextView third_tv;
    TextView fourth_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_result_two);

        first_tv = findViewById(R.id.first_info_tv);
        second_tv = findViewById(R.id.second_info_tv);
        third_tv = findViewById(R.id.third_info_tv);
        fourth_tv = findViewById(R.id.fourth_info_tv);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        TextView current_address_tv_score_result_two = (TextView) findViewById(R.id.current_address_tv_score_result_two);

        mDatabase.child("currentseeaddress").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                value = dataSnapshot.getValue(String.class);
                current_address_tv_score_result_two.setText(value);
                init();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });
    }

    private void init(){
        mDatabase.child("bookmark").child(uid).child(value).child("feeData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FeeData group = dataSnapshot.getValue(FeeData.class);

                elecMoney = group.getElec_y();
                gasMoney = group.getGas_y();
                waterMoney = group.getWater_y();
                init2();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void init2(){
        mDatabase.child("bookmark").child(uid).child(value).child("info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bookmark group2 = dataSnapshot.getValue(Bookmark.class);
                String monthMoneyImsi = group2.getMonthMoney();
                String monthlyFeeImsi = group2.getMonthlyFee();
                String otherScoreImsi = group2.getOtherScore();
                String boImsi = group2.getBo();
                String bigImsi = group2.getBig();
                jucha = group2.getParking();
                animal = group2.getAnimal();
                ev = group2.getEv();

                monthMoney =  Integer.parseInt(monthMoneyImsi);
                monthlyFee = Integer.parseInt(monthlyFeeImsi);
                otherScore =  Integer.parseInt(otherScoreImsi);
                bo = Integer.parseInt(boImsi);
                big = Integer.parseInt(bigImsi);
                first_tv.setText("월세 " + monthMoney +" 보증금 " + bo+ " " + big + "평");
                second_tv.setText("주차 " + jucha);
                third_tv.setText("애완동물 " + animal);
                fourth_tv.setText("엘레베이터 " + ev);


                real_month = monthlyFee*10000 + elecMoney + gasMoney + waterMoney;
                System.out.println(real_month);
                if (real_month<=470000){
                    score_month = 100;
                }
                else if (real_month<=520000){
                    score_month = 85;
                }
                else if (real_month<=570000){
                    score_month = 70;
                }
                else if (real_month<=620000){
                    score_month = 55;
                }
                else if (real_month<=670000){
                    score_month = 40;
                }
                else if (real_month<=720000){
                    score_month = 25;
                }
                else{
                    score_month = 10;
                }
                System.out.println(score_month);
                init3();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }

    private void init3(){
        mDatabase.child("bookmark").child(uid).child(value).child("pyoninfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pyonScore = dataSnapshot.getValue(int.class);
                init4();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void init4(){
        mDatabase.child("bookmark").child(uid).child(value).child("suninfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                iljoScore = dataSnapshot.getValue(int.class);
                init5();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void init5(){
        BarChart mBarChart = (BarChart) findViewById(R.id.barchart);

        mBarChart.addBar(new BarModel("일조량", iljoScore, 0xFFffb854));
        mBarChart.addBar(new BarModel("월부담금", score_month,  0xFFffdd00));
        mBarChart.addBar(new BarModel("편의시설", pyonScore, 0xFF81cbfc));
        mBarChart.addBar(new BarModel("기타", otherScore, 0xFF96d492));

        mBarChart.startAnimation();

    }
 }



