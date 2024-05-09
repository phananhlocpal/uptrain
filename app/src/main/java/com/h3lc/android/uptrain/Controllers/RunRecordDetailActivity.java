package com.h3lc.android.uptrain.Controllers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.h3lc.android.uptrain.Models.Journey;
import com.h3lc.android.uptrain.Models.Location;
import com.h3lc.android.uptrain.R;
import com.h3lc.android.uptrain.Database.JourneyUtil;
import com.h3lc.android.uptrain.Database.LocationUtil;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;

public class RunRecordDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    JourneyUtil mJourneyUtil;
    LocationUtil mLocationUtil;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("E, HH:mm dd/MM/yyyy");
    GoogleMap mMap;

    Button backButton;
    ImageButton editButton;
    ImageButton deleteButton;
    TextView dateTextview;
    TextView journeyNameTextview;
    TextView commentTextview;
    TextView distanceTextview;
    TextView timeTextview;
    RatingBar ratingBar;

    Journey mJourney;

    private int journeyID;

    public RunRecordDetailActivity() {

    }

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_record_detail);

        mJourneyUtil = new JourneyUtil(getBaseContext());
        mLocationUtil = new LocationUtil(getBaseContext());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        journeyID = bundle.getInt("journeyID");

        mJourney = mJourneyUtil.getJourneyByID(journeyID);

        dateTextview = (TextView) findViewById(R.id.date_textview);
        journeyNameTextview = (TextView) findViewById(R.id.journey_name_textview);
        commentTextview = (TextView) findViewById(R.id.comment_textview);
        distanceTextview = (TextView) findViewById(R.id.distance_textview);
        timeTextview = (TextView) findViewById(R.id.time_textview);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        editButton = findViewById(R.id.edit_btn);
       backButton = (Button) findViewById(R.id.back_btn);
       deleteButton =  findViewById(R.id.delete_btn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RunRecordDetailActivity.this.finish();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getBaseContext(), "Vote Rating:" + rating, Toast.LENGTH_SHORT).show();
                mJourney.setmRating(rating);
                mJourneyUtil.update(mJourney);
                loadLayout();
            }
        });
        loadLayout();
    }

    private void loadLayout() {
        mJourney = mJourneyUtil.getJourneyByID(journeyID);
        //get data by journey Id
        String journeyDate = dateFormat.format(mJourney.getmDate());
        String journeyName = mJourney.getmName();
        String comment = mJourney.getmComment();
        float rating = mJourney.getmRating();
        final long hours = mJourney.getmDuration() / 3600;
        final long minutes = (mJourney.getmDuration() % 3600) / 60;
        final long seconds = mJourney.getmDuration() % 60;

        float distance = mJourney.getmDistance();
        dateTextview.setText(journeyDate);
        journeyNameTextview.setText(journeyName);
        commentTextview.setText(comment);
        ratingBar.setRating(rating);
        distanceTextview.setText(String.format("%.2fkm", distance));
        timeTextview.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    //Show dialog add information
    public void showEditDialog() {
        EditText journey_name_Edittext;
        EditText comment_Edittext;
        Button cancel_Button;
        Button save_Button;
        LayoutInflater i = this.getLayoutInflater();
        View v = i.inflate(R.layout.dialog_run_record_edit, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        journey_name_Edittext = (EditText) v.findViewById(R.id.journey_name_edt);
        comment_Edittext = (EditText) v.findViewById(R.id.comment_edt);
        cancel_Button = (Button) v.findViewById(R.id.cancel_btn);
        save_Button = (Button) v.findViewById((R.id.save_btn));
        //set builder view
        builder.setView(v);
        //Set text view text display name and journey comment
        journey_name_Edittext.setText(mJourney.getmName());
        comment_Edittext.setText(mJourney.getmComment());
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        //Set action for save button
        save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJourney.setmName(journey_name_Edittext.getText().toString());
                mJourney.setmComment(comment_Edittext.getText().toString());
                mJourneyUtil.update(mJourney);
                dialog.dismiss();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        loadLayout();
                    }
                }, 2000);
            }
        });
        //Set Action for cancel button
        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //show dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    //Show delete dialog
    private void showConfirmDialog(){
        Button mPositiveButton, mNegativeButton;
        TextView mDialogTitle, mDialogMessage;
        LayoutInflater i = RunRecordDetailActivity.this.getLayoutInflater();
        View view = i.inflate(R.layout.dialog_custom_confirm,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(RunRecordDetailActivity.this);
        //set builder view
        builder.setView(view);
        mDialogTitle = view.findViewById(R.id.dialog_title);
        mDialogMessage = view.findViewById(R.id.dialog_message);
        mPositiveButton = view.findViewById(R.id.positive_button);
        mNegativeButton = view.findViewById(R.id.negative_button);
        mDialogTitle.setText("CONFIRM");
        mDialogMessage.setText("Delete this Running Record?");

        Dialog dialog = builder.create();
        mPositiveButton.setText("Delete");
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJourneyUtil.delete(journeyID);
                dialog.dismiss();
                Toast.makeText(getApplication(), "Updated", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mNegativeButton.setText("Cancel");
        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // Create the AlertDialog object and return it
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        mMap = googleMap;
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style_night);
        mMap.setMapStyle(style);
        // draw polyline

        List<Location> locations = mLocationUtil.getLocationsByJourney(journeyID);

        PolylineOptions line = new PolylineOptions().clickable(false).width(10).color(getResources().getColor(R.color.green_malachite));
        ;
        LatLng firstLoc = null;
        LatLng lastLoc = null;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (!locations.isEmpty()) {
            for (int i = 1; i < locations.size() - 1; i++) {
                LatLng loc = new LatLng(locations.get(i).getmLatitude(), locations.get(i).getmLongitude());
                line.add(loc);
                builder.include(loc);
            }
            firstLoc = new LatLng(locations.get(0).getmLatitude(), locations.get(0).getmLongitude());
            lastLoc = new LatLng(locations.get(locations.size() - 1).getmLatitude(), locations.get(locations.size() - 1).getmLongitude());
            builder.include(firstLoc);
            builder.include(lastLoc);
        }
        if (lastLoc != null && firstLoc != null) {
            //Bitmap start_pin = Bitmap.createScaledBitmap(, 120, 120, false);
            Bitmap marker_pin = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.end_map_pin), 50, 50, false);
            mMap.addMarker(new MarkerOptions().position(firstLoc).title("Start")).setIcon(BitmapDescriptorFactory.fromBitmap(marker_pin));
            mMap.addMarker(new MarkerOptions().position(lastLoc).title("End")).setIcon(BitmapDescriptorFactory.fromBitmap(marker_pin));

            //Zoom
            googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    LatLngBounds bounds = builder.build();
                    final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                    mMap.animateCamera(cu, 1500 , null);
                    mMap.addPolyline(line);
                }
            });
        }
    }
}