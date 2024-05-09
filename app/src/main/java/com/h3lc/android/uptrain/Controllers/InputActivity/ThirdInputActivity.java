package com.h3lc.android.uptrain.Controllers.InputActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.h3lc.android.uptrain.Controllers.WelcomeActivity;
import com.h3lc.android.uptrain.Helper.NonSwipeableViewPager;
import com.h3lc.android.uptrain.R;

public class ThirdInputActivity extends Fragment {
    NonSwipeableViewPager viewPager;
    Button nextButton;
    Button backButton;
    RadioButton maleCheck;
    RadioButton femaleCheck;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_input_gender, container, false);
        viewPager = getActivity().findViewById(R.id.slide_viewpager);

        maleCheck = (RadioButton) view.findViewById(R.id.male_radiobtn);
        femaleCheck = (RadioButton) view.findViewById(R.id.female_radiobtn);
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            nextButton = getActivity().findViewById(R.id.next_button);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!maleCheck.isChecked() && !femaleCheck.isChecked()){
                        Toast.makeText(getContext(),"Please choose your gender.",Toast.LENGTH_SHORT).show();
                    }
                    if(maleCheck.isChecked()){
                        WelcomeActivity.user.setmGender("Male");
                        viewPager.setCurrentItem(3);
                    }else {
                        if(femaleCheck.isChecked()){
                            WelcomeActivity.user.setmGender("Female");
                            viewPager.setCurrentItem(3);
                        }
                    }
                    Log.d("Test user:", "name:"+WelcomeActivity.user.getmName()
                            + " - email:"+WelcomeActivity.user.getmEmail()
                            + " - phone:"+WelcomeActivity.user.getmPhone()
                            + " - Gender:"+WelcomeActivity.user.getmGender()
                            + " - Age:"+WelcomeActivity.user.getmAge());
                }
            });
            nextButton.setText("Next");
            nextButton.setVisibility(View.VISIBLE);

            backButton = getActivity().findViewById(R.id.back_button);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(1);
                }
            });
            backButton.setVisibility(View.VISIBLE);
            backButton.setText("Back");
        }
    }
}
