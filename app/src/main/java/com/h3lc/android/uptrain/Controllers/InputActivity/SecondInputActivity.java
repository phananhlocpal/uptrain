package com.h3lc.android.uptrain.Controllers.InputActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.h3lc.android.uptrain.Controllers.WelcomeActivity;
import com.h3lc.android.uptrain.Helper.NonSwipeableViewPager;
import com.h3lc.android.uptrain.Helper.ValidateHelper;
import com.h3lc.android.uptrain.R;

public class SecondInputActivity extends Fragment {
    Button nextButton;
    Button backButton;
    NonSwipeableViewPager viewPager;
    EditText nameEdt;
    EditText emailEdt;
    EditText phoneEdt;
    EditText ageEdt;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_input_individual_info, container, false);
        nameEdt = (EditText) view.findViewById(R.id.name_text2);
        emailEdt = (EditText) view.findViewById(R.id.email_text);
        phoneEdt = (EditText) view.findViewById(R.id.phone_text);
        ageEdt = (EditText) view.findViewById(R.id.age_text);

        viewPager = getActivity().findViewById(R.id.slide_viewpager);
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            nextButton = getActivity().findViewById(R.id.next_button);
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setText("Next");
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean error = false;
                    if(nameEdt.getText().toString().isEmpty()){
                        nameEdt.setError("Please enter your name here.");
                        error = true;
                    }else {
                        if(!ValidateHelper.validateName(nameEdt)){
                            nameEdt.setError("Name can't be contains number.");
                            error = true;
                        }
                    }
                    if(phoneEdt.getText().toString().isEmpty()){
                        phoneEdt.setError("Please enter your phone number.");
                        error = true;
                    }else {
                        if(!ValidateHelper.validatePhone(phoneEdt)) {
                            phoneEdt.setError("Please enter a valid Viet Nam \nphone number (i.e 0798469633)");
                            error = true;
                        }
                    }
                    if(ageEdt.getText().toString().isEmpty()){
                        ageEdt.setError("Please enter your age.");
                        error = true;
                    }else {
                        if(!ValidateHelper.validateAge(ageEdt)){
                            ageEdt.setError("Please enter a valid age number.");
                            error = true;
                        }
                    }
                    if(emailEdt.getText().toString().isEmpty()){
                        emailEdt.setError("Please enter your email address.");
                        error = true;
                    }else {
                        if(!ValidateHelper.validateEmail(emailEdt)) {
                            emailEdt.setError("Please enter a valid individual email\n address (i.e name12@email.com). ");
                            error = true;
                        }
                    }
                    if(!error){
                        WelcomeActivity.user.setmName(nameEdt.getText().toString());
                        WelcomeActivity.user.setmEmail(emailEdt.getText().toString());
                        WelcomeActivity.user.setmPhone(phoneEdt.getText().toString());
                        WelcomeActivity.user.setmAge(Integer.parseInt(ageEdt.getText().toString()));
                        viewPager.setCurrentItem(2);
                    }
                }
            });
            backButton = getActivity().findViewById(R.id.back_button);
            backButton.setVisibility(View.GONE);
        }
    }
}
