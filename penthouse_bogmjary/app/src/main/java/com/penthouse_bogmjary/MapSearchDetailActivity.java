package com.penthouse_bogmjary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.penthouse_bogmjary.model.category_search.Document;
import com.penthouse_bogmjary.utils.IntentKey;

import java.util.ArrayList;

public class MapSearchDetailActivity extends AppCompatActivity {
    final static String TAG = "MapSearchDetailTAG";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    String value;
    String uid;
    //xml
    RadarChart radarChart;
    TextView itemCntText1, itemCntText2, itemCntText3, itemCntText4, itemCntText5, itemCntText6, itemCntText7, itemCntText8, itemCntText9;
    TextView ratingScore;
    RatingBar ratingBar;

    //value
    ArrayList<Document> bigMartList = new ArrayList<>(); //대형마트 MT1
    ArrayList<Document> gs24List = new ArrayList<>(); //편의점 CS2
    ArrayList<Document> schoolList = new ArrayList<>(); //학교 SC4
    ArrayList<Document> foodList = new ArrayList<>(); //음식점 FD6
    ArrayList<Document> subwayList = new ArrayList<>(); //지하철 SW8
    ArrayList<Document> bankList = new ArrayList<>(); //은행 BK9
    ArrayList<Document> hospitalList = new ArrayList<>(); //병원 HP8
    ArrayList<Document> pharmacyList = new ArrayList<>(); //약국 PM9
    ArrayList<Document> cafeList = new ArrayList<>(); //카페 CE7


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search_detail);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

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
        itemCntText1 = findViewById(R.id.mapsearchdetail_tv_itemcount1);
        itemCntText2 = findViewById(R.id.mapsearchdetail_tv_itemcount2);
        itemCntText3 = findViewById(R.id.mapsearchdetail_tv_itemcount3);
        itemCntText4 = findViewById(R.id.mapsearchdetail_tv_itemcount4);
        itemCntText5 = findViewById(R.id.mapsearchdetail_tv_itemcount5);
        itemCntText6 = findViewById(R.id.mapsearchdetail_tv_itemcount6);
        itemCntText7 = findViewById(R.id.mapsearchdetail_tv_itemcount7);
        itemCntText8 = findViewById(R.id.mapsearchdetail_tv_itemcount8);
        itemCntText9 = findViewById(R.id.mapsearchdetail_tv_itemcount9);
        ratingBar = findViewById(R.id.mapsearchdetail_rb_ratingbar);
        ratingScore = findViewById(R.id.mapsearchdetail_tv_rating_score);
        radarChart = findViewById(R.id.mapsearchdetail_radar_chart);
        processIntent();
        makeChart();
        initView();
    }

    //인텐트처리
    private void processIntent() {
        Intent getIntent = getIntent();
        bigMartList = getIntent.getParcelableArrayListExtra(IntentKey.CATEGOTY_SEARCH_MODEL_EXTRA1);
        gs24List = getIntent.getParcelableArrayListExtra(IntentKey.CATEGOTY_SEARCH_MODEL_EXTRA2);
        schoolList = getIntent.getParcelableArrayListExtra(IntentKey.CATEGOTY_SEARCH_MODEL_EXTRA3);
        foodList = getIntent.getParcelableArrayListExtra(IntentKey.CATEGOTY_SEARCH_MODEL_EXTRA4);
        subwayList = getIntent.getParcelableArrayListExtra(IntentKey.CATEGOTY_SEARCH_MODEL_EXTRA5);
        bankList = getIntent.getParcelableArrayListExtra(IntentKey.CATEGOTY_SEARCH_MODEL_EXTRA6);
        hospitalList = getIntent.getParcelableArrayListExtra(IntentKey.CATEGOTY_SEARCH_MODEL_EXTRA7);
        pharmacyList = getIntent.getParcelableArrayListExtra(IntentKey.CATEGOTY_SEARCH_MODEL_EXTRA8);
        cafeList = getIntent.getParcelableArrayListExtra(IntentKey.CATEGOTY_SEARCH_MODEL_EXTRA9);
    }

    private void initView(){
        float itemCnt1 = bigMartList.size();
        float itemCnt2 = gs24List.size();
        float itemCnt3 = schoolList.size();
        float itemCnt4 = foodList.size();
        float itemCnt5 = subwayList.size();
        float itemCnt6 = bankList.size();
        float itemCnt7 = hospitalList.size();
        float itemCnt8 = pharmacyList.size();
        float itemCnt9 = cafeList.size();
        itemCntText1.setText("" +(int) itemCnt1);
        itemCntText2.setText("" +(int) itemCnt2);
        itemCntText3.setText("" +(int) itemCnt3);
        itemCntText4.setText("" +(int) itemCnt4);
        itemCntText5.setText("" +(int) itemCnt5);
        itemCntText6.setText("" +(int) itemCnt6);
        itemCntText7.setText("" +(int) itemCnt7);
        itemCntText8.setText("" +(int) itemCnt8);
        itemCntText9.setText("" +(int) itemCnt9);


        //평균계산 최대 100점, 거리에 따라 점수 부여.
        if(itemCnt1 > 2){
            itemCnt1 = 10;
        }
        else if(itemCnt1 == 2)
        {
            itemCnt1 = 6;
        }
        else if(itemCnt1 == 1)
        {
            itemCnt1 = 3;
        }


        if(itemCnt2 > 2){
            itemCnt2 = 10;
        }
        else if(itemCnt2 == 2)
        {
            itemCnt2 = 6;
        }
        else if(itemCnt2 == 1)
        {
            itemCnt2 = 3;
        }

        if(itemCnt3 > 0){
            itemCnt3 = 10;
        }

        if(itemCnt4 > 2){
            itemCnt4 = 10;
        }
        else if(itemCnt4 == 2)
        {
            itemCnt4 = 6;
        }
        else if(itemCnt4 == 1)
        {
            itemCnt4 = 3;
        }

        if(itemCnt5 > 2){
            itemCnt5 = 10;
        }
        else if(itemCnt5 == 2)
        {
            itemCnt5 = 6;
        }
        else if(itemCnt5 == 1)
        {
            itemCnt5 = 3;
        }

        if(itemCnt6 > 2){
            itemCnt6 = 10;
        }
        else if(itemCnt6 == 2)
        {
            itemCnt6 = 6;
        }
        else if(itemCnt6 == 1)
        {
            itemCnt6 = 6;
        }

        if(itemCnt7 > 2){
            itemCnt7 = 10;
        }
        else if(itemCnt7 == 2)
        {
            itemCnt7 = 6;
        }
        else if(itemCnt7 == 1)
        {
            itemCnt7 = 3;
        }

        if(itemCnt8 > 2){
            itemCnt8 = 10;
        }
        else if(itemCnt8 == 2)
        {
            itemCnt8 = 6;
        }
        else if(itemCnt8 == 1)
        {
            itemCnt8 = 3;
        }

        if(itemCnt9 > 2){
            itemCnt9 = 10;
        }
        else if(itemCnt9 == 2)
        {
            itemCnt9 = 6;
        }
        else if(itemCnt9 == 1)
        {
            itemCnt9 = 6;
        }
        int averageScore = Math.round((itemCnt1 + itemCnt2 + itemCnt3 + itemCnt4 + itemCnt5 + itemCnt6 + itemCnt7 + itemCnt8 + itemCnt9)*10 /10 );
        initpyonscore(averageScore);
        ratingScore.setText(averageScore+"");
        ratingBar.setRating(averageScore/2);
    }
    private void initpyonscore(int x){
        mDatabase.child("bookmark").child(uid).child(value).child("pyoninfo").setValue(x);
    }

    //차트생성
    private void makeChart() {
        RadarDataSet dataSet = new RadarDataSet(dataValue(), "주변환경");
        dataSet.setColor(Color.BLUE);

        RadarData data = new RadarData();
        data.addDataSet(dataSet);
        String[] labels = {"대형마트", "편의점", "학교", "음식점", "지하철", "은행", "병원", "약국", "카페"};
        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        radarChart.setData(data);
    }

    //차트 데이터 생성
    private ArrayList<RadarEntry> dataValue() {
        ArrayList<RadarEntry> dataVals = new ArrayList<>();
        dataVals.add(new RadarEntry(bigMartList.size()));
        dataVals.add(new RadarEntry(gs24List.size()));
        dataVals.add(new RadarEntry(schoolList.size()));
        dataVals.add(new RadarEntry(foodList.size()));
        dataVals.add(new RadarEntry(subwayList.size()));
        dataVals.add(new RadarEntry(bankList.size()));
        dataVals.add(new RadarEntry(hospitalList.size()));
        dataVals.add(new RadarEntry(pharmacyList.size()));
        dataVals.add(new RadarEntry(cafeList.size()));
        return dataVals;
    }

}
