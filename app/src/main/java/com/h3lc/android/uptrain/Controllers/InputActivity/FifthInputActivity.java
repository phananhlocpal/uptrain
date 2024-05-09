package com.h3lc.android.uptrain.Controllers.InputActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.h3lc.android.uptrain.Controllers.MainActivity;
import com.h3lc.android.uptrain.Controllers.WelcomeActivity;
import com.h3lc.android.uptrain.Helper.NonSwipeableViewPager;
import com.h3lc.android.uptrain.R;
import com.h3lc.android.uptrain.Database.HeightUtil;
import com.h3lc.android.uptrain.Database.UserUtil;
import com.h3lc.android.uptrain.Database.WeightUtil;

import java.util.Date;

public class FifthInputActivity extends Fragment {
    MediaPlayer mp;
    NonSwipeableViewPager viewPager;
    Button backButton;
    Button finishButton;
    NumberPicker heightNumPicker;
    UserUtil mUU;
    HeightUtil mHU;
    WeightUtil mWU;
    int heightValue = 0;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_input_height, container, false);
        viewPager = getActivity().findViewById(R.id.slide_viewpager);
        mUU = new UserUtil(this.getContext());
        mHU = new HeightUtil(this.getContext());
        mWU = new WeightUtil(this.getContext());
        heightNumPicker = (NumberPicker) view.findViewById(R.id.height_numberpicker);
        if (heightNumPicker != null) {
            heightNumPicker.setMinValue(1);
            heightNumPicker.setMaxValue(220);
            heightNumPicker.setValue(150);
            heightNumPicker.setWrapSelectorWheel(true);
            EditText input = findInput(heightNumPicker);
            TextWatcher tw = new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() != 0) {
                        heightValue = Integer.parseInt(s.toString());
                        if (heightValue >= heightNumPicker.getMinValue() && heightValue <= heightNumPicker.getMaxValue()) {
                            heightNumPicker.setValue(heightValue);
                        }
                    }
                }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void afterTextChanged(Editable s) {}
            };
            input.addTextChangedListener(tw);
            heightNumPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    heightValue = heightNumPicker.getValue();
                    Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(100);
                    mp = MediaPlayer.create(getActivity(),R.raw.effect_tick);
                    mp.setLooping(false);
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        };
                    });
                }
            });
        }
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            finishButton = getActivity().findViewById(R.id.next_button);
            finishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    WelcomeActivity.height.setmDate(new Date());
                    WelcomeActivity.height.setmValue(heightNumPicker.getValue());
                    Log.d("Test Weight:", "Height: " +WelcomeActivity.height.getmValue()
                            + " - Date: " +WelcomeActivity.height.getmDate());
                    mUU.add(WelcomeActivity.user.getmName(),WelcomeActivity.user.getmAge(),WelcomeActivity.user.getmGender(),WelcomeActivity.user.getmEmail(),WelcomeActivity.user.getmPhone());
                    mWU.add(WelcomeActivity.weight.getmValue(),WelcomeActivity.weight.getmDate());
                    mHU.add(WelcomeActivity.height.getmValue(),WelcomeActivity.height.getmDate());
                }
            });
            finishButton.setVisibility(View.VISIBLE);
            finishButton.setText("Done");

            backButton = getActivity().findViewById(R.id.back_button);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(3);
                }
            });
            backButton.setVisibility(View.VISIBLE);
        }
    }
    private EditText findInput(ViewGroup np) {
        int count = np.getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = np.getChildAt(i);
            if (child instanceof ViewGroup) {
                findInput((ViewGroup) child);
            } else if (child instanceof EditText) {
                return (EditText) child;
            }
        }
        return null;
    }
}
