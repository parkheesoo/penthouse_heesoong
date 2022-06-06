package com.penthouse_bogmjary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookmarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FolderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FolderFragment() {
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
    public static FolderFragment newInstance(String param1, String param2) {
        FolderFragment fragment = new FolderFragment();
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
        View v = inflater.inflate(R.layout.fragment_folder, container, false);
        RecyclerView rv_user_list = v.findViewById(R.id.rv_user_list);
        RecordAdapter rvAdapter = new RecordAdapter(getSamplelist());
        rv_user_list.setAdapter(rvAdapter);

        return v;
    }

    private ArrayList<FolderData> getSamplelist(){
        ArrayList<FolderData> lstUserData = new ArrayList<>();
        for(int i = 0;i < 3; i++){
            if(i == 1){
                FolderData folderData = new FolderData();
                folderData.setBuildingAddress("서울 동작구 상도로 47바길 48");
                lstUserData.add(folderData);
            }
            else if(i==2){
                FolderData folderData = new FolderData();
                folderData.setBuildingAddress("서울 상도1동 647-1");
                lstUserData.add(folderData);
            }else{
                FolderData folderData = new FolderData();
                folderData.setBuildingAddress("서울특별시 관악구 신림동 신림동길 6");
                lstUserData.add(folderData);
            }

        }
        return lstUserData;
    }
}