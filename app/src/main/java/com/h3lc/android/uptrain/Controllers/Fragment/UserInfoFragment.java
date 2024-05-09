package com.h3lc.android.uptrain.Controllers.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.h3lc.android.uptrain.Controllers.BMIActivity;
import com.h3lc.android.uptrain.Helper.ValidateHelper;
import com.h3lc.android.uptrain.Models.User;
import com.h3lc.android.uptrain.R;
import com.h3lc.android.uptrain.Adapter.IndicatorRecyclerAdapter;
import com.h3lc.android.uptrain.Database.JourneyUtil;
import com.h3lc.android.uptrain.Database.UserUtil;
import com.h3lc.android.uptrain.Pojo.DashboardItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserInfoFragment extends Fragment {
    private UserUtil mUserUtil;
    private JourneyUtil mJourneyUtil;
    private EditText mNameTextview, mGenderTextview, mEmailTextview, mPhoneTextview, mAgeTextview;
    private Button saveBtn, btnViewBMI;
    private boolean isEditable = false;
    Drawable editableBg;
    Drawable unEditableBg;
    Drawable defaultNameBackground, defaultGenderBackground, defaultEmailBackground, defaultPhoneBackground, defaultAgeBackground;
    private User user;
    RecyclerView mIndicatorRecyclerView;

    String mDashboardSpeedString;
    String mDashboardStepString;
    String mDashnoardDistanceString;
    String mDashboardTimeString;
    Boolean status = false;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_info, container, false);

        // Declare editbutton UI
        editableBg = getResources().getDrawable(R.drawable.edit_text_stoke_green);
        unEditableBg = getResources().getDrawable(android.R.color.transparent);

        // Declare userUtil
        mUserUtil = new UserUtil(getContext());
        user = mUserUtil.getAllUser().get(0);

        // DOM Method
        mIndicatorRecyclerView = (RecyclerView) view.findViewById(R.id.indicator_recyler);

        mNameTextview = view.findViewById(R.id.name_textview);
        mGenderTextview = view.findViewById(R.id.gender_textview);
        mEmailTextview = view.findViewById(R.id.email_textview);
        mPhoneTextview = view.findViewById(R.id.phone_textview);
        mAgeTextview = view.findViewById(R.id.age_textview);
        saveBtn = view.findViewById(R.id.save_button);
        btnViewBMI = view.findViewById(R.id.btnViewBMI);
        // Get default background
        defaultNameBackground = mNameTextview.getBackground();
        defaultGenderBackground = mGenderTextview.getBackground();
        defaultEmailBackground = mEmailTextview.getBackground();
        defaultPhoneBackground = mPhoneTextview.getBackground();
        defaultAgeBackground = mAgeTextview.getBackground();

        mNameTextview.setBackground(unEditableBg);
        mPhoneTextview.setBackground(unEditableBg);
        mEmailTextview.setBackground(unEditableBg);
        mAgeTextview.setBackground(unEditableBg);
        mGenderTextview.setBackground(unEditableBg);

        //Set disable edit view
        mNameTextview.setFocusable(isEditable);
        mPhoneTextview.setFocusable(isEditable);
        mEmailTextview.setFocusable(isEditable);
        mGenderTextview.setFocusable(isEditable);
        mAgeTextview.setFocusable(isEditable);

        // Event Handler
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditClicked();
                ;}
        });

        btnViewBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BMIActivity.class);
                startActivity(i);
            }
        });

        // Load Layout
        updateTotalValue();
        //Log.w("Time", mDashboardTimeString.toString());
        loadLayout();
        return view;
    }
    private void loadLayout(){
        mNameTextview.setText(user.getmName()+"");
        mEmailTextview.setText(user.getmEmail());
        mGenderTextview.setText(user.getmGender());
        mPhoneTextview.setText(user.getmPhone());
        mAgeTextview.setText(user.getmAge()+"");

        if (isEditable == true) {
            saveBtn.setText("SAVE");
        }else {
            saveBtn.setText("EDIT");
        }


        //setTextDashboardInformation
        int imageStepId = R.drawable.img_babyfeet;
        int imageTimeId = R.drawable.img_clock;
        int imageDistanceId = R.drawable.img_exercise;
        int imageSpeedId = R.drawable.img_speed;

        Drawable imageStep = getResources().getDrawable(imageStepId);
        Drawable imageTime = getResources().getDrawable(imageTimeId);
        Drawable imageDistance = getResources().getDrawable(imageDistanceId);
        Drawable imageSpeed = getResources().getDrawable(imageSpeedId);

        DashboardItem stepsItem = new DashboardItem("Steps", mDashboardStepString, imageStep);
        DashboardItem speedItem = new DashboardItem("Speed", mDashboardSpeedString, imageSpeed);
        DashboardItem distanceItem = new DashboardItem("Distance", mDashnoardDistanceString, imageDistance);
        DashboardItem timeItem = new DashboardItem("Time", mDashboardTimeString, imageTime);

        List<DashboardItem> itemList = new ArrayList<>();
        itemList.add(stepsItem);
        itemList.add(speedItem);
        itemList.add(distanceItem);
        itemList.add(timeItem);

        try {
            IndicatorRecyclerAdapter dashboardAdapter = new IndicatorRecyclerAdapter(getContext(), itemList);
            mIndicatorRecyclerView.setAdapter(dashboardAdapter);
            //GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            // layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mIndicatorRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } catch (Exception e){
            Log.w("HomeFragment", e.toString());
        }
    }

    public void onEditClicked(){
        //Set for name
        if(isEditable) {
            if(checkInput()){
                boolean s = ShowConfirmDialog();
                if (s == true)
                {
                    setViewEditButton();
                }

            }
        } else {
            setViewEditButton();
        }
    }

    private void setViewEditButton() {
        if (isEditable) {
            isEditable = false;

            mNameTextview.setFocusable(isEditable);
            mNameTextview.setBackground(unEditableBg);

            mPhoneTextview.setFocusable(isEditable);
            mPhoneTextview.setBackground(unEditableBg);

            mEmailTextview.setFocusable(isEditable);
            mEmailTextview.setBackground(unEditableBg);

            mAgeTextview.setFocusable(isEditable);
            mAgeTextview.setBackground(unEditableBg);

            mGenderTextview.setFocusable(isEditable);
            mGenderTextview.setBackground(unEditableBg);

            hideKeyboardFrom(getActivity(), mAgeTextview.getRootView());
        } else {
            isEditable = true;

            mNameTextview.setFocusableInTouchMode(isEditable);
            mNameTextview.setBackground(defaultNameBackground);

            mPhoneTextview.setFocusableInTouchMode(isEditable);
            mPhoneTextview.setBackground(defaultPhoneBackground);

            mEmailTextview.setFocusableInTouchMode(isEditable);
            mEmailTextview.setBackground(defaultEmailBackground);

            mAgeTextview.setFocusableInTouchMode(isEditable);
            mAgeTextview.setBackground(defaultAgeBackground);

            mGenderTextview.setFocusableInTouchMode(isEditable);
            mGenderTextview.setBackground(defaultGenderBackground);
        }
    }
    private boolean checkInput(){
        boolean valid = true;
        if(mNameTextview.getText().toString().isEmpty()){
            mNameTextview.setError("Please enter your name here.");
            valid = false;
        }else {
            if(!ValidateHelper.validateName(mNameTextview)){
                mNameTextview.setError("Name can't be contains number.");
                valid = false;
            }
        }
        if(mPhoneTextview.getText().toString().isEmpty()){
            mPhoneTextview.setError("Please enter your phone number.");
            valid = false;
        }else {
            if(!ValidateHelper.validatePhone(mPhoneTextview)) {
                mPhoneTextview.setError("Please enter a valid Viet Nam \nphone number (i.e 0798469633)");
                valid = false;
            }
        }
        if(mAgeTextview.getText().toString().isEmpty()){
           mAgeTextview.setError("Please enter your age.");
            valid = false;
        }else {
            if(!ValidateHelper.validateAge(mAgeTextview)){
                mAgeTextview.setError("Please enter a valid age number.");
                valid = false;
            }
        }
        if(mEmailTextview.getText().toString().isEmpty()){
            mEmailTextview.setError("Please enter your email address.");
            valid = false;
        }else {
            if(!ValidateHelper.validateEmail(mEmailTextview)) {
                mEmailTextview.setError("Please enter a valid individual email\n address (i.e name12@email.com). ");
                valid = false;
            }
        }
        return valid;
    }

    private boolean ShowConfirmDialog(){
        status = false;
        Button mPositiveButton, mNegativeButton;
        TextView mDialogTitle, mDialogMessage;
        LayoutInflater i = UserInfoFragment.this.getLayoutInflater();
        View view = i.inflate(R.layout.dialog_custom_confirm,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //set builder view
        builder.setView(view);
        mDialogTitle = view.findViewById(R.id.dialog_title);
        mDialogMessage = view.findViewById(R.id.dialog_message);
        mPositiveButton = view.findViewById(R.id.positive_button);
        mNegativeButton = view.findViewById(R.id.negative_button);
        mDialogTitle.setText("CONFIRM");
        mDialogMessage.setText("Update your information?");

        Dialog dialog = builder.create();
        mPositiveButton.setText("Update");
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setmName(mNameTextview.getText().toString());
                user.setmEmail(mEmailTextview.getText().toString());
                user.setmPhone(mPhoneTextview.getText().toString());
                user.setmAge(Integer.parseInt(mAgeTextview.getText().toString()));
                user = mUserUtil.update(user);
                setViewEditButton();
                dialog.dismiss();
                loadLayout();
                status = true;
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mNegativeButton.setText("Cancel");
        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewEditButton();
                loadLayout();
                dialog.dismiss();
                status = false;
            }
        });
        // Create the AlertDialog object and return it
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        return status;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void updateTotalValue(){
        mJourneyUtil = new JourneyUtil(getContext());
        float totalDistance = 0;
        long totalDuration = 0;
        int countRecord = 0;

        ArrayList<HashMap<String,Object>> sumDistanceList = mJourneyUtil.getTotalDistanceMonthly();
        for (HashMap x : sumDistanceList){
            totalDistance += (float) x.get("total_distance");
            totalDuration += (long) x.get("total_duration");
            countRecord += (int) x.get("num_of_record");
        }

        long hours = totalDuration / 3600;
        long minutes = (totalDuration % 3600) / 60;

        mDashnoardDistanceString = String.format("%.2f km", totalDistance);
        mDashboardTimeString = String.format("%02dh%02dm", hours, minutes);
        mDashboardStepString = countRecord+"";

    }
}
