package com.penthouse_bogmjary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegsunscoreActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String value;
    private TextView mTvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsunscore);

        mTvAddress = findViewById(R.id.current_address_tv_regsunact);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button close_resunact_btn = (Button)findViewById(R.id.close_sunscoreactivity_btn);
        Button reg_sunScore_btn = (Button)findViewById(R.id.reg_sun_score_btn);
        EditText sun_score_et = (EditText) findViewById(R.id.sunscore_et);

        close_resunact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        mDatabase.child("currentseeaddress").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                value = dataSnapshot.getValue(String.class);
                mTvAddress.setText(value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        reg_sunScore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sun_sun_score = sun_score_et.getText().toString().trim();
                mDatabase.child("bookmark").child(uid).child(value).child("suninfo").setValue(Integer.parseInt(sun_sun_score));
            }
        });
    }
}