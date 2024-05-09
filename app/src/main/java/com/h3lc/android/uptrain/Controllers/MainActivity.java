package com.h3lc.android.uptrain.Controllers;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.h3lc.android.uptrain.Helper.NonSwipeableViewPager;
import com.h3lc.android.uptrain.Adapter.ViewPagerAdapter;
import com.h3lc.android.uptrain.R;

public class MainActivity extends AppCompatActivity {
    // Declare fields
    private BottomNavigationView mNavigationView;
    private NonSwipeableViewPager mViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DOM navigation and view paper
        mNavigationView = findViewById(R.id.bottom_nav);
        mViewpager = findViewById(R.id.view_pager);

        mViewpager.setOffscreenPageLimit(0);
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_1:
                        mViewpager.setCurrentItem(0,false);
                        break;
                    case R.id.action_2:
                        mViewpager.setCurrentItem(1,false);
                        break;
                    case R.id.action_3:
                        mViewpager.setCurrentItem(2,false);
                        break;
                    case R.id.action_4:
                        mViewpager.setCurrentItem(3,false);
                        break;
                    case R.id.action_5:
                        mViewpager.setCurrentItem(4,false);
                        break;
                }
                return true;
            }
        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewpager.setAdapter(viewPagerAdapter);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mNavigationView.getMenu().findItem(R.id.action_1).setChecked(true);
                        break;
                    case 1:
                        mNavigationView.getMenu().findItem(R.id.action_2).setChecked(true);
                        break;
                    case 2:
                        mNavigationView.getMenu().findItem(R.id.action_3).setChecked(true);
                        break;
                    case 3:
                        mNavigationView.getMenu().findItem(R.id.action_4).setChecked(true);
                        break;
                    case 4:
                        mNavigationView.getMenu().findItem(R.id.action_5).setChecked(true);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, String[] permissions, int[] results) {
        super.onRequestPermissionsResult(reqCode,permissions,results);
    }
}


