package com.h3lc.android.uptrain.Models;

public class Location {
    private int mLocationId;
    private int mJourneyId;
    private double mAltitude;
    private double mLongitude;
    private double mLatitude;

    public Location() {
        this.mLocationId = mLocationId;
        this.mJourneyId = mJourneyId;
        this.mAltitude = mAltitude;
        this.mLongitude = mLongitude;
        this.mLatitude = mLatitude;
    }

    public Location(int mJourneyId, double mAltitude, double mLongitude, double mLatitude) {
        this.mJourneyId = mJourneyId;
        this.mAltitude = mAltitude;
        this.mLongitude = mLongitude;
        this.mLatitude = mLatitude;
    }

    public int getmLocationId() {
        return mLocationId;
    }

    public void setmLocationId(int mLocationId) {
        this.mLocationId = mLocationId;
    }

    public int getmJourneyId() {
        return mJourneyId;
    }

    public void setmJourneyId(int mJourneyId) {
        this.mJourneyId = mJourneyId;
    }

    public double getmAltitude() {
        return mAltitude;
    }

    public void setmAltitude(double mAltitude) {
        this.mAltitude = mAltitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }
}
