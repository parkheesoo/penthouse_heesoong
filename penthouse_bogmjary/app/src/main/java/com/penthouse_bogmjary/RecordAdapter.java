package com.penthouse_bogmjary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private ArrayList<FolderData> mLstFolderDate;

    public RecordAdapter(ArrayList<FolderData> mLstFolderDate){
        this.mLstFolderDate = mLstFolderDate;
    }

    @NonNull
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sample, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.ViewHolder holder, int position){
        holder.tv_address.setText(mLstFolderDate.get(position).getBuildingAddress());
    }

    @Override
    public int getItemCount(){
        return mLstFolderDate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected TextView tv_address;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tv_address = itemView.findViewById(R.id.tv_address);
        }
    }
}
