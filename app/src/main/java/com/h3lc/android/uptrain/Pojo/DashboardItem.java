package com.h3lc.android.uptrain.Pojo;

import android.graphics.drawable.Drawable;

public class DashboardItem {
    private String name;
    private String info;
    private Drawable img;

    public DashboardItem(String name, String info, Drawable img) {
        this.name = name;
        this.info = info;
        this.img = img;
    }

    public Drawable getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }
}
