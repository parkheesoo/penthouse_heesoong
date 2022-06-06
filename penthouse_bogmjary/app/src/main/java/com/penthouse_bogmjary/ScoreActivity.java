package com.penthouse_bogmjary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScoreActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    String value;
    int elecMoney=0;
    int gasMoney=0;
    int waterMoney=0;
    int monthlyFee=0;
    int pyonScore = 0;
    int iljoScore=0;
    int otherScore=0;
    int finalScore = 0;
    TextView other_w;
    TextView month_w;
    TextView pyeon_w;
    TextView iljo_w;
    int iljo_weight = 0;
    int pyeon_weight = 0;
    int month_weight = 0;
    int other_weight = 0;
    int real_month = 0;
    int score_month = 0;

    int final_score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        TextView current_address_tv_score = (TextView)findViewById(R.id.current_address_tv_score);
        Button sAcCl_btn = (Button)findViewById(R.id.close_scoreactivity_btn);
        Button chReAc_btn = (Button) findViewById(R.id.score_cal_button);
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        iljo_w = findViewById(R.id.iljo_weight);
        month_w = findViewById(R.id.month_weight);
        pyeon_w = findViewById(R.id.pyeon_weight);
        other_w = findViewById(R.id.other_weight);

        mDatabase.child("currentseeaddress").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(String.class);
                current_address_tv_score.setText(value);

                System.out.println(value);
                mDatabase.child("bookmark").child(uid).child(value).child("feeData").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        FeeData group = dataSnapshot.getValue(FeeData.class);

                        elecMoney = group.getElec_y();
                        gasMoney = group.getGas_y();
                        waterMoney = group.getWater_y();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("ScoreActivity", String.valueOf(databaseError.toException()));
                    }
                });

                mDatabase.child("bookmark").child(uid).child(value).child("info").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Bookmark group2 = dataSnapshot.getValue(Bookmark.class);
                        String monthlyFeeImsi = group2.getMonthlyFee();
                        String otherScoreImsi = group2.getOtherScore();

                        monthlyFee =  Integer.parseInt(monthlyFeeImsi);
                        otherScore =  Integer.parseInt(otherScoreImsi);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

                mDatabase.child("bookmark").child(uid).child(value).child("pyoninfo").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        pyonScore = dataSnapshot.getValue(int.class);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

                mDatabase.child("bookmark").child(uid).child(value).child("suninfo").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        iljoScore = dataSnapshot.getValue(int.class);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });

        chReAc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScoreResultActivity.class);
                System.out.println(elecMoney + iljoScore);
                startActivity(intent);

                String iw = iljo_w.getText().toString();
                String mw = month_w.getText().toString();
                String pw = pyeon_w.getText().toString();
                String ow = other_w.getText().toString();

                iljo_weight = Integer.parseInt(iw);
                month_weight = Integer.parseInt(mw);
                pyeon_weight = Integer.parseInt(pw);
                other_weight = Integer.parseInt(ow);







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



                if (iljo_weight ==0 || month_weight ==0 || other_weight ==0 || pyeon_weight == 0 ){
                    // 1.1 1.0 0.9 곱하기
                    if (iljo_weight==1){
                        double ss = iljoScore * 1.1;
                        iljoScore = (int) Math.floor(ss);
                    }
                    else if (iljo_weight==2){
                        double ss = iljoScore * 1.0;
                        iljoScore = (int) Math.floor(ss);
                    }
                    else if (iljo_weight==3){
                        double ss = iljoScore * 0.9;
                        iljoScore = (int) Math.floor(ss);
                    }

                    if (month_weight==1){
                        double sm = score_month * 1.1;
                        score_month = (int) Math.floor(sm);
                    }
                    else if (month_weight==2){
                        double sm = score_month * 1.0;
                        score_month = (int) Math.floor(sm);
                    }
                    else if (month_weight==3){
                        double sm = score_month * 0.9;
                        score_month = (int) Math.floor(sm);
                    }

                    if (pyeon_weight==1){
                        double sp = pyonScore * 1.1;
                        pyonScore = (int) Math.floor(sp);
                    }
                    else if (pyeon_weight==2){
                        double sp = pyonScore * 1.0;
                        pyonScore = (int) Math.floor(sp);
                    }
                    else if (pyeon_weight==3){
                        double sp = pyonScore * 0.9;
                        pyonScore = (int) Math.floor(sp);
                    }

                    if (other_weight==1){
                        double so = otherScore * 1.1;
                        otherScore = (int) Math.floor(so);
                    }
                    else if (other_weight==2){
                        double so = otherScore * 1.0;
                        otherScore = (int) Math.floor(so);
                    }
                    else if (other_weight==3){
                        double so = otherScore * 0.9;
                        otherScore = (int) Math.floor(so);
                    }

                    System.out.println(iljoScore);
                    System.out.println(score_month);
                    System.out.println(pyonScore);
                    System.out.println(otherScore);

                    if (iljo_weight == 0){
                        double fs = (score_month + otherScore + pyonScore) / 3;
                        final_score = (int) Math.floor(fs);
                        System.out.println(final_score);
                    }
                    else if (month_weight == 0){
                        double fs = (iljoScore + otherScore + pyonScore) / 3;
                        final_score = (int) Math.floor(fs);
                        System.out.println(final_score);
                    }
                    else if (pyeon_weight == 0){
                        double fs = (score_month + otherScore + iljoScore) / 3;
                        final_score = (int) Math.floor(fs);
                        System.out.println(final_score);
                    }
                    else if (other_weight == 0){
                        double fs = (score_month + iljoScore + pyonScore) / 3;
                        final_score = (int) Math.floor(fs);
                        System.out.println(final_score);
                    }

                }
                else{
                    if (iljo_weight==1){
                        double ss = iljoScore * 1.15;
                        iljoScore = (int) Math.floor(ss);
                    }
                    else if (iljo_weight==2){
                        double ss = iljoScore * 1.05;
                        iljoScore = (int) Math.floor(ss);
                    }
                    else if (iljo_weight==3){
                        double ss = iljoScore * 0.95;
                        iljoScore = (int) Math.floor(ss);
                    }
                    else if (iljo_weight==4){
                        double ss = iljoScore * 0.85;
                        iljoScore = (int) Math.floor(ss);
                    }
                    if (month_weight==1){
                        double sm = score_month * 1.15;
                        score_month = (int) Math.floor(sm);
                    }
                    else if (month_weight==2){
                        double sm = score_month * 1.05;
                        score_month = (int) Math.floor(sm);
                    }
                    else if (month_weight==3){
                        double sm = score_month * 0.95;
                        score_month = (int) Math.floor(sm);
                    }
                    else if (month_weight==4){
                        double sm = score_month * 0.85;
                        score_month = (int) Math.floor(sm);
                    }
                    if (pyeon_weight==1){
                        double sp = pyonScore * 1.15;
                        pyonScore = (int) Math.floor(sp);
                    }
                    else if (pyeon_weight==2){
                        double sp = pyonScore * 1.05;
                        pyonScore = (int) Math.floor(sp);
                    }
                    else if (pyeon_weight==3){
                        double sp = pyonScore * 0.95;
                        pyonScore = (int) Math.floor(sp);
                    }
                    else if (pyeon_weight==4){
                        double sp = pyonScore * 0.85;
                        pyonScore = (int) Math.floor(sp);
                    }
                    if (other_weight==1){
                        double so = otherScore * 1.15;
                        otherScore = (int) Math.floor(so);
                    }
                    else if (other_weight==2){
                        double so = otherScore * 1.05;
                        otherScore = (int) Math.floor(so);
                    }
                    else if (other_weight==3){
                        double so = otherScore * 0.95;
                        otherScore = (int) Math.floor(so);
                    }
                    else if (other_weight==4){
                        double so = otherScore * 0.85;
                        otherScore = (int) Math.floor(so);
                    }

                    System.out.println(iljoScore);
                    System.out.println(score_month);
                    System.out.println(pyonScore);
                    System.out.println(otherScore);

                    double fs = (score_month + otherScore +pyonScore + iljoScore)/4;
                    final_score = (int) Math.floor(fs);
                    System.out.println(final_score);
                }

                mDatabase.child("bookmark").child(uid).child(value).child("finalscore").setValue(final_score);
            }
        });

        sAcCl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}