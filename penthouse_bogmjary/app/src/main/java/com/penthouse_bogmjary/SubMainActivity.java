package com.penthouse_bogmjary;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SubMainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public TextView mTvAddress;
    private String value;
    public static Context context_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main);

        mTvAddress = findViewById(R.id.current_address_tv);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button bir_btn = (Button) findViewById(R.id.building_info_reg_btn);
        Button fee_iv = (Button) findViewById(R.id.button_score_money);
        Button iljo = (Button) findViewById(R.id.button_score_sun);
        Button button_score_map = (Button)findViewById(R.id.button_score_map);
        Button close_subact_btn = (Button)findViewById(R.id.close_activity_btn);
        ImageView bookmark_iv = (ImageView)findViewById(R.id.bookmark_iv);
        Button score_result_btn = (Button) findViewById(R.id.button_score_score);

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
                // Getting Post failed, log a message
            }
        });

        bir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BIRActivity.class);
                startActivity(intent);
            }
        });

        button_score_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        iljo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IljoMainActivity.class);
                startActivity(intent);
            }
        });

        fee_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FeeActivity.class);
                startActivity(intent);
            }
        });

        close_subact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        score_result_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
                startActivity(intent);
            }
        });

        bookmark_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ba = value;
                bookmark_iv.setImageResource(R.drawable.yesbookmark);
                Bookmark bookmark = new Bookmark(ba,"a","a","a","a","a","a","a","a","a", "a","a");
                mDatabase.child("bookmark").child(uid).child(ba).child("info").setValue(bookmark);
            }
        });

        context_address = this;
    }
}