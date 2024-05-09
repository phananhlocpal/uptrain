package com.h3lc.android.uptrain.Controllers.InputActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.h3lc.android.uptrain.Controllers.WelcomeActivity;
import com.h3lc.android.uptrain.Helper.NonSwipeableViewPager;
import com.h3lc.android.uptrain.R;

import java.util.Date;

public class FourthInputAcitivity extends Fragment {
    MediaPlayer mp;
    NonSwipeableViewPager viewPager;
    NumberPicker weightNumPicker;
    Button nextButton;
    Button backButton;
    private int weightValue;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_input_weight, container, false);

        viewPager = getActivity().findViewById(R.id.slide_viewpager);
        weightNumPicker = (NumberPicker) view.findViewById(R.id.weight_numberpicker);
        if (weightNumPicker != null) {
            weightNumPicker.setMinValue(1);
            weightNumPicker.setMaxValue(200);
            weightNumPicker.setValue(40);
            weightNumPicker.setWrapSelectorWheel(true);
            EditText input = findInput(weightNumPicker);
            TextWatcher tw = new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() != 0) {
                        weightValue = Integer.parseInt(s.toString());
                        if (weightValue >= weightNumPicker.getMinValue() && weightValue <= weightNumPicker.getMaxValue()) {
                            weightNumPicker.setValue(weightValue);
                        }
                    }
                }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void afterTextChanged(Editable s) {}
            };
            input.addTextChangedListener(tw);
            weightNumPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    weightValue = weightNumPicker.getValue();
                    Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(100);
                    mp = MediaPlayer.create(getActivity(),R.raw.effect_tick);
                    mp.setLooping(false);
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mep) {
                            mep.release();
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
            nextButton = getActivity().findViewById(R.id.next_button);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(4);
                    WelcomeActivity.weight.setmDate(new Date());
                    WelcomeActivity.weight.setmValue(weightValue);
                }
            });
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setText("Next");

            backButton = getActivity().findViewById(R.id.back_button);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(2);
                }
            });
            backButton.setVisibility(View.VISIBLE);
            backButton.setText("Back");
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
