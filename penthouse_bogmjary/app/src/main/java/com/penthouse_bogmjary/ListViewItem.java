package com.penthouse_bogmjary;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable icon;
    private String text;

    public void setIcon(Drawable drawable) {
        this.icon = drawable;
    }

    public void setText(String str) {
        this.text = str;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public String getText() {
        return this.text;
    }
}