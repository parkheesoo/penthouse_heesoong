package com.penthouse_bogmjary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    LinearLayout home_ly;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Context context = this;
    private TextView tool_bar_tv;
    private DrawerLayout mDrawerLayout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        SettingListener();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_list_24); //뒤로가기 버튼 이미지 지정

        tool_bar_tv = (TextView)findViewById(R.id.toolbar_title);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                mDatabase.child("users").child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Get Post object and use the values to update the UI
                        User user = dataSnapshot.getValue(User.class);

                        String nick = user.getNickname();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Getting Post failed, log a message
                    }
                });
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id == R.id.menu_item1){
                    Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menu_item2){
                    Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.menu_item3){
                    Toast.makeText(context, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    private void init(){
        home_ly = findViewById(R.id.home_ly);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private  void SettingListener(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
            switch (menuItem.getItemId()){
                case R.id.navigation_history:{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new FolderFragment()).commit();
                    tool_bar_tv.setText("검색기록");
                    return true;
                }
                case R.id.navigation_bookmarks:{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new BookmarkFragment()).commit();
                    tool_bar_tv.setText("책갈피");
                    return true;
                }
                case R.id.navigation_home:{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new HomeFragment()).commit();
                    return true;
                }
                case R.id.navigation_user:{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new UserFragment()).commit();
                    tool_bar_tv.setText("내정보");
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}