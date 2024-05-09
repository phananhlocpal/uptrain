package com.h3lc.android.uptrain.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.h3lc.android.uptrain.Models.*;

import java.util.ArrayList;
import java.util.List;

public class LocationUtil {

    SQLiteDatabase mDatabase;
    Context mContext;
    public LocationUtil(Context context){
        mContext = context;
        mDatabase = new dbHeathHelper(mContext).getWritableDatabase();
    }

    private ContentValues locationContentValues(Location location){
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHealthSchema.LocationTable.JourneyID,location.getmJourneyId());
        contentValues.put(dbHealthSchema.LocationTable.Altitude,location.getmAltitude());
        contentValues.put(dbHealthSchema.LocationTable.Longitude,location.getmLongitude());
        contentValues.put(dbHealthSchema.LocationTable.Latitude,location.getmLatitude());
        return contentValues;
    }

    //Insert
    public long add(int JourneyId, double Altitude, double Longitude, double Latitude){
        Location mLocation = new Location(JourneyId,Altitude,Longitude,Latitude);
        ContentValues contentValues = locationContentValues(mLocation);
        long result = mDatabase.insert("location", null,contentValues);
        return result;
    }

    //Update
    public long update(Location location){
        ContentValues contentValues = locationContentValues(location);
        long result= mDatabase.update("location",contentValues, dbHealthSchema.LocationTable.LocationID +"=?",new String[]{String.valueOf(location.getmLocationId())});
        return result;
    }
    //delete
    public long delete(int JourneyID){
        String JouID = Integer.toString(JourneyID);
        return mDatabase.delete("location", dbHealthSchema.LocationTable.JourneyID +"=?", new String[]{JouID});
    }
    //view data
    public Cursor viewData(){
        String view = " SELECT * FROM " + dbHealthSchema.LocationTable.TABLE_NAME;
        Cursor cursor = mDatabase.rawQuery(view, null);
        return cursor;
    }
    // Truy van toan bo du lieu do ve 1 danh sach
    public List<Location> getAll(){
        List <Location> locations = new ArrayList<>();
        Cursor cursor = viewData();
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String location_id = cursor.getString(cursor.getColumnIndex(dbHealthSchema.LocationTable.LocationID));
                String journey_id = cursor.getString(cursor.getColumnIndex(dbHealthSchema.LocationTable.JourneyID));
                String altitude = cursor.getString(cursor.getColumnIndex(dbHealthSchema.LocationTable.Altitude));
                String longitude = cursor.getString(cursor.getColumnIndex(dbHealthSchema.LocationTable.Longitude));
                String latitude = cursor.getString(cursor.getColumnIndex(dbHealthSchema.LocationTable.Latitude));

                Location location = new Location();
                location.setmLocationId(Integer.parseInt(location_id));
                location.setmJourneyId(Integer.parseInt(journey_id));
                location.setmAltitude(Float.parseFloat(altitude));
                location.setmLongitude(Float.parseFloat(longitude));
                location.setmLatitude(Float.parseFloat(latitude));

                locations.add(location);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return locations;
    }
    public List<Location> getLocationsByJourney(int JourneyID) {
        List<Location> locationList = new ArrayList<>();
        String query = "SELECT * FROM " + dbHealthSchema.LocationTable.TABLE_NAME + " WHERE " + dbHealthSchema.LocationTable.JourneyID + " = " + JourneyID;
        Cursor cursor = mDatabase.rawQuery(query, null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String location_id = cursor.getString(cursor.getColumnIndex(dbHealthSchema.LocationTable.LocationID));
                String journey_id = cursor.getString(cursor.getColumnIndex(dbHealthSchema.LocationTable.JourneyID));
                String altitude = cursor.getString(cursor.getColumnIndex(dbHealthSchema.LocationTable.Altitude));
                String longitude = cursor.getString(cursor.getColumnIndex(dbHealthSchema.LocationTable.Longitude));
                String latitude = cursor.getString(cursor.getColumnIndex(dbHealthSchema.LocationTable.Latitude));

                Location location = new Location();
                location.setmLocationId(Integer.parseInt(location_id));
                location.setmJourneyId(Integer.parseInt(journey_id));
                location.setmAltitude(Float.parseFloat(altitude));
                location.setmLongitude(Float.parseFloat(longitude));
                location.setmLatitude(Float.parseFloat(latitude));

                locationList.add(location);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return locationList;
    }
}
