package com.penthouse_bogmjary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class CheckableLinearLayout extends LinearLayout implements Checkable {
    public CheckableLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean isChecked() {
        return ((CheckBox) findViewById(R.id.checkBox1)).isChecked();
    }

    public void toggle() {
        setChecked(!((CheckBox) findViewById(R.id.checkBox1)).isChecked());
    }

    public void setChecked(boolean z) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox1);
        if (checkBox.isChecked() != z) {
            checkBox.setChecked(z);
        }
    }
}