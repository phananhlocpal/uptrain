package com.h3lc.android.uptrain.Controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.h3lc.android.uptrain.Models.Height;
import com.h3lc.android.uptrain.Models.Weight;
import com.h3lc.android.uptrain.R;
import com.h3lc.android.uptrain.Database.HeightUtil;
import com.h3lc.android.uptrain.Database.WeightUtil;
import com.h3lc.android.uptrain.Helper.*;
import com.h3lc.android.uptrain.Helper.SwipeDismissBaseActivity;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class BMIActivity extends AppCompatActivity {
    private static boolean isHeightView = true;
    HeightUtil mHU;
    WeightUtil mWU;
    MediaPlayer mp;
    LineChart mChart;
    FloatingActionButton mFloatButtonAdd;
    Pair<Long, Long> selectionDate = null;
    private int heightValue = 0;
    private int weightValue = 0;
    private TextView mViewHeight, mViewWeight, mViewBMIScore, mViewBMIStatus, mViewDate;
    private Button mWeightBtn, mHeightBtn;
    private LottieAnimationView mCalendarBtn;
    private int underWeightColorCode, normalWeightColorCode, overWeightColorCode, obeseWeightColorCode, extremeWeightColorCode;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        mp = MediaPlayer.create(this, R.raw.effect_tick);

        mHU = new HeightUtil(this);
        mWU = new WeightUtil(this);

        // Declare Color
        underWeightColorCode = getResources().getColor(R.color.under_weight);
        normalWeightColorCode = getResources().getColor(R.color.normal_weight);
        overWeightColorCode = getResources().getColor(R.color.gray);
        obeseWeightColorCode = getResources().getColor(R.color.obese_weight);
        extremeWeightColorCode = getResources().getColor(R.color.extreme_weight);

        // DOM Method
        mViewHeight = findViewById(R.id.height_textview);
        mViewWeight = findViewById(R.id.weight_textview);
        mViewBMIScore = findViewById(R.id.scorebmi_textview);
        mViewBMIStatus = findViewById(R.id.statusbmi_textview);
        mViewDate = findViewById(R.id.date_range_textview);
        mFloatButtonAdd = findViewById(R.id.floatingActionButton);
        mWeightBtn = findViewById(R.id.view_weight_btn);
        mHeightBtn = findViewById(R.id.view_height_btn);
        mChart = findViewById(R.id.lineChart);
        mCalendarBtn = findViewById(R.id.view_cal_btn);

        // Event Handler
        mWeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHeightView = false;
                ShowWeightChart();
            }
        });

        mHeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHeightView = true;
                ShowHeightChart();
            }
        });

        mCalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowCalendar();
            }
        });

        mFloatButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialogueShow();
            }
        });

        //init Chart and Reload Layout
        internalizeChart();
        refreshLayout();
    }



    // Refresh layout
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refreshLayout() {
        Height mHeight = mHU.getLastestHeight();
        heightValue = mHeight.getmValue();
        mViewHeight.setText(String.valueOf(heightValue));

        Weight mWeight = mWU.getLastestWeight();
        weightValue = mWeight.getmValue();
        mViewWeight.setText(String.valueOf(weightValue));
        if (selectionDate == null) {
            selectionDate = CustomDateRangeCalendar.getMonthStartToNow();
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                setDateRangeText(selectionDate);
                calculateBMI(weightValue, heightValue);
                if (isHeightView) {
                    ShowHeightChart();
                } else {
                    ShowWeightChart();
                }
            }
        }, 1100);
    }

    //Set Calendar
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ShowCalendar() {
        MaterialDatePicker<Pair<Long, Long>> picker = CustomDateRangeCalendar.ShowCalendar(selectionDate);
        picker.show(getSupportFragmentManager(), picker.toString());
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                Pair<Date, Date> pair = CustomDateRangeCalendar.getDateFromSeclection(selection);
                //Set text and put to selectionDate
                setDateRangeText(selectionDate);
                selectionDate = selection;
                picker.dismiss();
                refreshLayout();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDateRangeText(Pair<Long, Long> selectionDate) {
        Pair<Date, Date> pair = CustomDateRangeCalendar.getDateFromSeclection(selectionDate);
        Date startDate = pair.first;
        Date endDate = pair.second;
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        //format dd/MM/yyyy
        String string_date = sdf2.format(startDate);
        String string_date2 = sdf2.format(endDate);
        String selectedDateStr = "Range Date: " + string_date + " - " + string_date2;
        mViewDate.setText(selectedDateStr);
    }

    //Calculate BMI index
    private void calculateBMI(int Weight, int Height) {
        double bmiScore = Weight / Math.pow(Height, 2) * 10000;
        DecimalFormat df = new DecimalFormat("0.00");
        mViewBMIScore.setText(df.format(bmiScore));
        if (bmiScore < 18) {
            mViewBMIStatus.setText("Underweight");
            mViewBMIStatus.setTextColor(underWeightColorCode);
        } else if (18 <= bmiScore && bmiScore < 25) {
            mViewBMIStatus.setText("Normal Weight");
            mViewBMIStatus.setTextColor(normalWeightColorCode);
        } else if (25 <= bmiScore && bmiScore < 30) {
            mViewBMIStatus.setText("Over Weight");
            mViewBMIScore.setTextColor(overWeightColorCode);
        } else if (30 <= bmiScore && bmiScore < 35) {
            mViewBMIStatus.setText("Obesity");
            mViewBMIStatus.setTextColor(obeseWeightColorCode);
        } else if (35 <= bmiScore) {
            mViewBMIStatus.setText("Extremely Obesity");
            mViewBMIStatus.setTextColor(extremeWeightColorCode);
        }
    }

    //Setup chart
    private void internalizeChart() {
        //Graph background color
        mChart.setBackgroundColor(getResources().getColor(R.color.white));

        // ==========================================================================
        //x-axis setting
        XAxis xAxis = mChart.getXAxis();
        //Output x-axis diagonally
        xAxis.setLabelRotationAngle(0);
        //Make the x-axis a dashed line(Dashed Line)
        xAxis.enableGridDashedLine(102f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //Display
        xAxis.setTextColor(Color.WHITE);

        // ==========================================================================
        //y-axis setting
        YAxis yAxis = mChart.getAxisLeft();
        //Make the y-axis a dashed line
        yAxis.setDrawZeroLine(false);
        yAxis.enableAxisLineDashedLine(120f, 10f, 0f);
        //The scale on the right. False if not needed
        yAxis.setEnabled(false);

        mChart.getAxisRight().setEnabled(false);
        mChart.setScaleMinima(5f, 5f);
        mChart.fitScreen();
        mChart.getLegend().setEnabled(false);
        //Animate the data. millisecond.Larger numbers are slower
        mChart.animateX(1500);
        mChart.getDescription().setEnabled(false);
    }

    //Set Button Weight Chart
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ShowWeightChart() {
        //Get data from database:
        ArrayList<Entry> valuesWeight = new ArrayList<>();
        List<Weight> weightList;
        Pair<Date, Date> pair = CustomDateRangeCalendar.getDateFromSeclection(selectionDate);
        Date startDate = pair.first;
        Date endDate = pair.second;

        //Get data from db by first date of month and today
        weightList = mWU.getWeightInRange(startDate, endDate);
        if (!weightList.isEmpty()) {
            for (int i = 0; i < weightList.size(); i++) {
                float value = (float) weightList.get(i).getmValue();
                valuesWeight.add(new Entry((float) i, value));
            }
            CreateChart(valuesWeight);
        } else {
            CreateChart(null);
        }

    }

    //Set Button Height Chart
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ShowHeightChart() {
        //Get data from database:
        ArrayList<Entry> valuesHeight = new ArrayList<>();
        List<Height> heightList;
        Pair<Date, Date> pair = CustomDateRangeCalendar.getDateFromSeclection(selectionDate);
        Date startDate = pair.first;
        Date endDate = pair.second;

        //Get data from db by first date of month and today
        heightList = mHU.getHeightInRange(startDate, endDate);
        if (!heightList.isEmpty()) {
            for (int i = 0; i < heightList.size(); i++) {
                float value = (float) heightList.get(i).getmValue();
                valuesHeight.add(new Entry((float) i, value));
            }
            CreateChart(valuesHeight);
        } else {
            CreateChart(null);
        }
    }

    //Chart
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void CreateChart(ArrayList<Entry> valuesSet) {
        if (valuesSet != null) {
            LineDataSet data;   //Line arguments
            //Set the date registered in the database to the x-axis
            mChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(getDate()));
            mChart.getXAxis().setLabelCount(getDate().size());
            if (isHeightView) {
                data = new LineDataSet(valuesSet, "Height");
                data.setColor(Color.RED);
                //Y-axis maximum / minimum setting
                mChart.getAxisLeft().setAxisMaximum(findMinMaxEntryValue(valuesSet, true) + 10f);
                mChart.getAxisLeft().setAxisMinimum(findMinMaxEntryValue(valuesSet, false) - 10f);
            } else {
                data = new LineDataSet(valuesSet, "Weight");
                data.setColor(Color.BLUE);
                //Y-axis maximum / minimum setting
                mChart.getAxisLeft().setAxisMaximum(findMinMaxEntryValue(valuesSet, true) + 10f);
                mChart.getAxisLeft().setAxisMinimum(findMinMaxEntryValue(valuesSet, false) - 10f);
            }

            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(10f);
            data.setCircleColorHole(Color.TRANSPARENT);
            data.setLineWidth(5f);
            data.setCircleSize(3f);
            data.setCircleColor(Color.BLACK);
            data.setDrawValues(true);
            data.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

            //set the line on the chart
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(data);
            LineData lineData = new LineData(dataSets);
            mChart.animateX(1000);
            mChart.setData(lineData);
            mChart.invalidate();
        } else {
            mChart.invalidate();
            mChart.clear();
        }
    }

    //Rewrite the data saved in String type to date type and SimpleDateFormat
    @RequiresApi(api = Build.VERSION_CODES.N)
    private ArrayList<String> getDate() {
        ArrayList<String> dateLabels = new ArrayList<>();
        List<Weight> weightList;
        Pair<Date, Date> pair = CustomDateRangeCalendar.getDateFromSeclection(selectionDate);
        Date startDate = pair.first;
        Date endDate = pair.second;
        weightList = mWU.getWeightInRange(startDate, endDate);
        for (Weight x : weightList) {
            //Correct the date to date
            String strDate = x.getmDate().toString();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM");
            String entryDate = dateFormat.format(x.getmDate());
            dateLabels.add(entryDate);
        }
        return dateLabels;
    }

    //Show dialog add information
    //Show dialog add information
    public void AddDialogueShow() {
        int tempWeightValue = weightValue;
        int tempHeightValue = heightValue;
        Button mSavebtn;
        Button mCancelbtn;
        NumberPicker weightNumPicker;
        NumberPicker heightNumPicker;
        LayoutInflater i = LayoutInflater.from(BMIActivity.this);
        View view = i.inflate(R.layout.dialog_custom, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(BMIActivity.this);
        //set builder view
        builder.setView(view);
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        weightNumPicker = (NumberPicker) view.findViewById(R.id.itemWeight);
        if (weightNumPicker != null) {
            weightNumPicker.setMinValue(20);
            weightNumPicker.setMaxValue(200);
            weightNumPicker.setValue(weightValue);
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
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            };
            input.addTextChangedListener(tw);
            weightNumPicker.setWrapSelectorWheel(true);
            weightNumPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    weightValue = weightNumPicker.getValue();
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(100);
                    mp.setLooping(false);
                    mp.start();
                }
            });
        }

        heightNumPicker = (NumberPicker) view.findViewById(R.id.itemHeight);
        if (heightNumPicker != null) {
            heightNumPicker.setMinValue(50);
            heightNumPicker.setMaxValue(230);
            heightNumPicker.setValue(heightValue);
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
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            };
            input.addTextChangedListener(tw);
            heightNumPicker.setWrapSelectorWheel(true);
            heightNumPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    heightValue = heightNumPicker.getValue();
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(100);
                    mp.setLooping(false);
                    mp.start();
                }
            });
        }

        mSavebtn = (Button) view.findViewById(R.id.save_button);
        mSavebtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Date date = new Date();
                mHU.add(heightValue, date);
                mWU.add(weightValue, date);
                dialog.dismiss();
                refreshLayout();
            }
        });

        mCancelbtn = (Button) view.findViewById(R.id.cancel_button);
        mCancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightNumPicker.setValue(tempWeightValue);
                heightNumPicker.setValue(tempHeightValue);
                dialog.dismiss();
            }

        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    private float findMinMaxEntryValue(ArrayList<Entry> valuesList, boolean FindMax) {
        float value = valuesList.get(0).getY();
        for (Entry x : valuesList) {
            //if function is find max value
            if (FindMax) {
                if (x.getY() > value) {
                    value = x.getY();
                }
            }
            //if function is find min
            else {
                if (x.getY() < value) {
                    value = x.getY();
                }
            }
        }
        return value;
    }

    //Set Edit text for pick number
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
