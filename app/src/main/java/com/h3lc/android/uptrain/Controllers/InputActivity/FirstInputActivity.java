package com.h3lc.android.uptrain.Controllers.InputActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.h3lc.android.uptrain.Helper.NonSwipeableViewPager;
import com.h3lc.android.uptrain.R;

public class FirstInputActivity extends Fragment {
    Button next;
    Button backButton;
    Button nextButton;

    NonSwipeableViewPager viewPager;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_welcome, container, false);
        viewPager = getActivity().findViewById(R.id.slide_viewpager);

        if(viewPager.getCurrentItem() == 0){
            next = view.findViewById(R.id.next_btn);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(1,false);
                }
            });
            nextButton = getActivity().findViewById(R.id.next_button);
            backButton = getActivity().findViewById(R.id.back_button);
            nextButton.setVisibility(View.GONE);
            backButton.setVisibility(View.GONE);
        }
        return view;
    }
}
