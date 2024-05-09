package com.h3lc.android.uptrain.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.h3lc.android.uptrain.Controllers.InputActivity.FirstInputActivity;
import com.h3lc.android.uptrain.Controllers.InputActivity.SecondInputActivity;
import com.h3lc.android.uptrain.Controllers.InputActivity.ThirdInputActivity;
import com.h3lc.android.uptrain.Controllers.InputActivity.FourthInputAcitivity;
import com.h3lc.android.uptrain.Controllers.InputActivity.FifthInputActivity;

import org.jetbrains.annotations.NotNull;

public class SliderAdapter extends FragmentPagerAdapter {


    public SliderAdapter(@NonNull @NotNull FragmentManager fm) {
        super(fm);
    }

    public SliderAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FirstInputActivity();
            case 1:
                return new SecondInputActivity();
            case 2:
                return new ThirdInputActivity();
            case 3:
                return new FourthInputAcitivity();
            case 4:
                return new FifthInputActivity();
            default:
                return new FirstInputActivity();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
