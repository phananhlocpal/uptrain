package com.h3lc.android.uptrain.Models;

import java.util.Date;

public class Weight {
    private int mId;
    private int mValue;
    private Date mDate;

    public Weight() {
        this.mId = mId;
        this.mValue = mValue;
        this.mDate = mDate;
    }

    public Weight(int mValue, Date mDate) {
        this.mValue = mValue;
        this.mDate = mDate;
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
}
