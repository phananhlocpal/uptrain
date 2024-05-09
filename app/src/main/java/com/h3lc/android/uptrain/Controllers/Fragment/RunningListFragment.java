package com.h3lc.android.uptrain.Controllers.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.h3lc.android.uptrain.Helper.CustomDateRangeCalendar;
import com.h3lc.android.uptrain.Adapter.HomeRecyclerAdapter;
import com.h3lc.android.uptrain.Helper.SwipeDismissBaseActivity;
import com.h3lc.android.uptrain.Models.Journey;
import com.h3lc.android.uptrain.R;
import com.h3lc.android.uptrain.Database.JourneyUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RunningListFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeRecyclerAdapter recyclerAdapter;
    private ArrayList<Journey> journeys;
    private JourneyUtil mJourneyUtil;
    private Button mBackButton;
    private LottieAnimationView mOpenCalendar;
    private TextView mDateRangeTextView;
    private Pair<Long, Long> selectionDate = null;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_running, container, false);

        // DOM
        recyclerView = view.findViewById(R.id.recycle_view);
        mBackButton = (Button) view.findViewById(R.id.back_button);
        mOpenCalendar = view.findViewById(R.id.view_cal_btn);
        mDateRangeTextView = view.findViewById(R.id.date_range_textview);

        // Event Handler
        mOpenCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowCalendar();
            }
        });

        // Reload view
        reloadLayout();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void reloadLayout(){
        // Declare
        journeys = new ArrayList<Journey>();
        mJourneyUtil = new JourneyUtil(getContext());

        // Show selection Date
        if(selectionDate == null) {
            selectionDate = CustomDateRangeCalendar.getMonthStartToNow();
        }
        Pair<Date,Date> pair = CustomDateRangeCalendar.getDateFromSeclection(selectionDate);
        journeys = mJourneyUtil.getJourneysByDate(pair.first,pair.second);
        setTextForDateRangeTextView(selectionDate);

        // Show list running
        recyclerAdapter= new HomeRecyclerAdapter(getContext(),journeys, "detail");
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ShowCalendar(){
        MaterialDatePicker<Pair<Long,Long>> picker = CustomDateRangeCalendar.ShowCalendar(selectionDate);
        picker.show(requireActivity().getSupportFragmentManager(), picker.toString());
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                Pair<Date,Date> pair = CustomDateRangeCalendar.getDateFromSeclection(selection);
                //Set text and put to selectionDate
                selectionDate = selection;
                picker.dismiss();
                reloadLayout();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setTextForDateRangeTextView(Pair<Long,Long> selectionDate){
        Pair<Date, Date> pair = CustomDateRangeCalendar.getDateFromSeclection(selectionDate);
        Date startDate = pair.first;
        Date endDate = pair.second;
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        //format dd/MM/yyyy
        String string_date = sdf2.format(startDate);
        String string_date2 = sdf2.format(endDate);
        String selectedDateStr ="Range Date: "+ string_date + " - " + string_date2;

        // SetText
        mDateRangeTextView.setText(selectedDateStr);
    }
}
