package com.h3lc.android.uptrain.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.h3lc.android.uptrain.Controllers.BMIActivity;
import com.h3lc.android.uptrain.Controllers.Fragment.AboutUsFragment;
import com.h3lc.android.uptrain.Controllers.Fragment.HomeFragment;
import com.h3lc.android.uptrain.Controllers.Fragment.RunningListFragment;
import com.h3lc.android.uptrain.Controllers.Fragment.RunRecordFragment;
import com.h3lc.android.uptrain.Controllers.Fragment.UserInfoFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new UserInfoFragment();
            case 2:
                return new RunRecordFragment();
            case 3:
                return new RunningListFragment();
            case 4:
                return new AboutUsFragment();
            default:
                return null;

        }

    }
    @Override
    public int getCount() {
        return 5;
    }
}
