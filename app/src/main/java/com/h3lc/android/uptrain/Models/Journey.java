package com.h3lc.android.uptrain.Models;

import java.util.Date;

public class Journey {
    private int mJourneyId;
    private long mDuration;
    private float mDistance;
    private Date mDate;
    private String mName;
    private float mRating;
    private String mComment;
    private String mImage;

    public Journey(long mDuration, float mDistance, Date mDate, String mName, float mRating, String mComment, String mImage) {
        this.mDuration = mDuration;
        this.mDistance = mDistance;
        this.mDate = mDate;
        this.mName = mName;
        this.mRating = mRating;
        this.mComment = mComment;
        this.mImage = mImage;
    }

    public Journey() {
        this.mJourneyId = mJourneyId;
        this.mDuration = mDuration;
        this.mDistance = mDistance;
        this.mDate = mDate;
        this.mName = mName;
        this.mRating = mRating;
        this.mComment = mComment;
        this.mImage = mImage;
    }

    public int getmJourneyId() {
        return mJourneyId;
    }

    public void setmJourneyId(int mJourneyId) {
        this.mJourneyId = mJourneyId;
    }

    public long getmDuration() {
        return mDuration;
    }

    public void setmDuration(long mDuration) {
        this.mDuration = mDuration;
    }

    public float getmDistance() {
        return mDistance;
    }

    public void setmDistance(float mDistance) {
        this.mDistance = mDistance;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public float getmRating() {
        return mRating;
    }

    public void setmRating(float mRating) {
        this.mRating = mRating;
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }
}
