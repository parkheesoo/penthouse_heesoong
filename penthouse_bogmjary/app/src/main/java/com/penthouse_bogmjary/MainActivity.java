package com.penthouse_bogmjary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    LinearLayout home_ly;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        SettingListener();

        bottomNavigationView.setSelectedItemId(R.id.home);
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
                    return true;
                }
                case R.id.navigation_bookmarks:{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new BookmarkFragment()).commit();
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
                    return true;
                }
            }
            return false;
        }
    }
}