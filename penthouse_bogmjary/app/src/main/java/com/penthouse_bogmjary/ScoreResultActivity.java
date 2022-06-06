package com.penthouse_bogmjary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class ScoreResultActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    int finalScore = 0;
    TextView ment_tv;
    String value;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_result);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        Button chReTwo = (Button)findViewById(R.id.button_score_check);
        Button sReAcClose_btn = (Button)findViewById(R.id.close_scoreresultactivity_btn);
        TextView current_address_tv_score_result = (TextView)findViewById(R.id.current_address_tv_score_result);
        TextView finalscore_tv = (TextView)findViewById(R.id.final_score_tv);
        ment_tv = (TextView)findViewById(R.id.ment_tv);


        mDatabase.child("currentseeaddress").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                value = dataSnapshot.getValue(String.class);
                current_address_tv_score_result.setText(value);

                mDatabase.child("bookmark").child(uid).child(value).child("finalscore").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        finalScore = dataSnapshot.getValue(int.class);
                        finalscore_tv.setText(String.valueOf(finalScore) + "점");
                        init();
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


        sReAcClose_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        chReTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScoreResultTwoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init(){
        mDatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User user = dataSnapshot.getValue(User.class);

                String nick = user.getNickname();
                ment_tv.setText(nick + "님에게 이 집의 점수는\n" + finalScore+"점!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });
    }
}