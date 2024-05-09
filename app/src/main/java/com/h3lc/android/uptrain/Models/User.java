package com.h3lc.android.uptrain.Models;

public class User {
    private int mId;
    private String mName;
    private int mAge;
    private String mGender;
    private String mEmail;
    private String mPhone;

    public User(String mName, int mAge, String mGender, String mEmail, String mPhone) {
        this.mName = mName;
        this.mAge = mAge;
        this.mGender = mGender;
        this.mEmail = mEmail;
        this.mPhone = mPhone;
    }

    public User() {

    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmAge() {
        return mAge;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public User(int mId, String mName, int mAge, String mGender, String mEmail, String mPhone) {
        this.mId = mId;
        this.mName = mName;
        this.mAge = mAge;
        this.mGender = mGender;
        this.mEmail = mEmail;
        this.mPhone = mPhone;
    }

    public void setmId(Integer id) {
    }

    public void setmAge(Integer age) {
    }

}
