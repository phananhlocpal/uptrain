package com.h3lc.android.uptrain.Models;

import java.util.Date;

public class Height {
    public int mId;
    private int mValue;
    private Date mDate;

    public Height(int value, Date date) {
        this.mValue = value;
        this.mDate = date;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmValue() {
        return mValue;
    }

    public void setmValue(int mValue) {
        this.mValue = mValue;
    }

    public Date getmDate() {
        return mDate;
    }


    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public Height() {
        this.mValue = mValue;
        this.mDate = mDate;
    }
}
