package com.penthouse_bogmjary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookmarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookmarkFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    String monthMone;
    String bo;
    String big;
    String jucha;
    String animal;
    String ev;
    String address;
    private ArrayList<String> arrayListStirng;
    private ArrayList<Bookmark> arrayList1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookmarkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookmarkFragment newInstance(String param1, String param2) {
        BookmarkFragment fragment = new BookmarkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bookmark, container, false);
        RecyclerView rv_bookmark_list = v.findViewById(R.id.rv_bookmark_list);

        BookmarkAdapter bookmarkAdapter = new BookmarkAdapter(getSampleList());
        rv_bookmark_list.setAdapter(bookmarkAdapter);
        return v;
    }

    private ArrayList<BookmarkData> getSampleList(){
        ArrayList<BookmarkData> lstBookmarkData = new ArrayList<>();
        for(int i = 0;i < 3;i++){
            if(i ==0){
                BookmarkData bookmarkData = new BookmarkData();
                bookmarkData.setBuildingAddressBookmark("서울 동작구 상도로 47바길 48");
                bookmarkData.setBuildingInfo("1000/50, 5평, 주차가능,\n 애완동물 가능, E/V유");
                bookmarkData.setBuildingScore(78);
                lstBookmarkData.add(bookmarkData);
            }
            else if(i == 1){
                BookmarkData bookmarkData = new BookmarkData();
                bookmarkData.setBuildingAddressBookmark("서울 상도1동 647-1");
                bookmarkData.setBuildingInfo("1000/61, 14평, 주차가능,\n 애완동물 안됨, E/V무");
                bookmarkData.setBuildingScore(88);
                lstBookmarkData.add(bookmarkData);
            }
            else{
                BookmarkData bookmarkData = new BookmarkData();
                bookmarkData.setBuildingAddressBookmark("서울특별시 관악구 신림동 신림동길 6");
                bookmarkData.setBuildingInfo("500/45, 6평, 주차안됨,\n 애완동물 안됨, E/V무");
                bookmarkData.setBuildingScore(40);
                lstBookmarkData.add(bookmarkData);
            }

        }
        return lstBookmarkData;
    }

    private void init(int x){
        System.out.println(x);
    }
}