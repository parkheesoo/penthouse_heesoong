package com.penthouse_bogmjary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder>{

    private ArrayList<BookmarkData> mLstBookmarkDate;

    public BookmarkAdapter(ArrayList<BookmarkData> mLstBookmarkDate){
        this.mLstBookmarkDate = mLstBookmarkDate;
    }

    @NonNull
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_list_view, parent, false);
        return new BookmarkAdapter.ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.ViewHolder holder, int position) {
        holder.tv_address_bookmark.setText(mLstBookmarkDate.get(position).getBuildingAddressBookmark());
        holder.tv_binfo_bookmark.setText(mLstBookmarkDate.get(position).getBuildingInfo());
        holder.tv_bscore_bookmark.setText(String.valueOf(mLstBookmarkDate.get(position).getBuildingScore()));
    }

    @Override
    public int getItemCount() {
        return mLstBookmarkDate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected TextView tv_address_bookmark;
        protected TextView tv_binfo_bookmark;
        protected TextView tv_bscore_bookmark;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tv_address_bookmark = itemView.findViewById(R.id.tv_address_bookmark);
            tv_binfo_bookmark = itemView.findViewById(R.id.tv_binfo_bookmark);
            tv_bscore_bookmark = itemView.findViewById(R.id.tv_bscore_bookmark);
        }
    }
}
