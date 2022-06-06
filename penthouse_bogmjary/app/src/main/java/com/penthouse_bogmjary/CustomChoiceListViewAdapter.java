package com.penthouse_bogmjary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomChoiceListViewAdapter extends BaseAdapter {
    public ArrayList<ListViewItem> listViewItemList = new ArrayList<>();

    public long getItemId(int i) {
        return (long) i;
    }

    public int getCount() {
        return this.listViewItemList.size();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        if (view == null) {
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listview_item, viewGroup, false);
        }
        ListViewItem listViewItem = this.listViewItemList.get(i);
        ((ImageView) view.findViewById(R.id.imageView1)).setImageDrawable(listViewItem.getIcon());
        ((TextView) view.findViewById(R.id.textView1)).setText(listViewItem.getText());
        return view;
    }

    public Object getItem(int i) {
        return this.listViewItemList.get(i);
    }

    public void addItem(Drawable drawable, String str) {
        ListViewItem listViewItem = new ListViewItem();
        listViewItem.setIcon(drawable);
        listViewItem.setText(str);
        this.listViewItemList.add(listViewItem);
    }
}
