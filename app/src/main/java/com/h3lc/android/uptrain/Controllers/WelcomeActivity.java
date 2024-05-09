package com.h3lc.android.uptrain.Controllers;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.h3lc.android.uptrain.Helper.NonSwipeableViewPager;
import com.h3lc.android.uptrain.Adapter.SliderAdapter;
import com.h3lc.android.uptrain.Models.Height;
import com.h3lc.android.uptrain.Models.User;
import com.h3lc.android.uptrain.Models.Weight;
import com.h3lc.android.uptrain.R;
import com.h3lc.android.uptrain.Database.UserUtil;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    NonSwipeableViewPager viewPager;
    UserUtil mUserUtil;
    public static User user = new User();
    public static Weight weight = new Weight();
    public static Height height = new Height();
    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_layout);
        mUserUtil = new UserUtil(getBaseContext());
        users = mUserUtil.getAllUser();
        if (users.isEmpty()){
            viewPager = (NonSwipeableViewPager) findViewById(R.id.slide_viewpager);
            SliderAdapter adapter = new SliderAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
        }else {
            this.finish();
            Intent i = new Intent(this,SplashActivity.class);
            startActivity(i);
        }
    }
}

