package com.h3lc.android.uptrain.Helper;

import android.icu.util.Calendar;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Date;

public class CustomDateRangeCalendar {
    //Set Calendar
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static MaterialDatePicker<Pair<Long,Long>> ShowCalendar(Pair<Long,Long> selectionDate){
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.dateRangePicker();
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointBackward.now());
        builder.setCalendarConstraints(constraintsBuilder.build());
        //Set date range = selection date value
        builder.setSelection(selectionDate);
        MaterialDatePicker<Pair<Long,Long>> picker = builder.build();
        return picker;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Pair<Date,Date> getDateFromSeclection(Pair<Long,Long> selectionDate){
        //Get data from database:
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        //Get Start Date
        Date startDate = new Date(selectionDate.first);
        cal.setTime(startDate);
        cal.set(Calendar.HOUR_OF_DAY,now.getHours());
        cal.set(Calendar.MINUTE,now.getMinutes());
        cal.set(Calendar.SECOND,now.getSeconds());
        startDate = cal.getTime();

        //Get End Date
        Date endDate = new Date(selectionDate.second);
        cal.setTime(endDate);
        cal.set(Calendar.HOUR_OF_DAY,24);
        cal.set(Calendar.MINUTE,00);
        cal.set(Calendar.SECOND,00);
        endDate = cal.getTime();

        return new Pair<>(startDate,endDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Pair<Long, Long> getMonthStartToNow(){
        //Get start Date of
        Date endDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        int days = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, days);
        long second = endDate.getTime();

        Date startDate = cal.getTime();
        cal.setTime(startDate);
        cal.set(Calendar.HOUR_OF_DAY,00);
        cal.set(Calendar.MINUTE,00);
        cal.set(Calendar.SECOND,00);
        startDate = cal.getTime();
        long first = startDate.getTime();

        return new Pair<>(first,second);
    }
}
