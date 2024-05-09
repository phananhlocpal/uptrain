package com.h3lc.android.uptrain.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.h3lc.android.uptrain.Models.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HeightUtil {

    SQLiteDatabase mDatabase;
    Context mContext;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public HeightUtil(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new dbHeathHelper(mContext).getWritableDatabase();
    }

    private ContentValues heightContentValues(Height height){
        ContentValues contentValues = new ContentValues();
        String date = dateFormat.format(height.getmDate());
        contentValues.put(dbHealthSchema.HeightTable.Value,height.getmValue());
        contentValues.put(dbHealthSchema.HeightTable.Date, date);
        return contentValues;
    }

    //Insert
    public long add(int Value, Date Date){
        Height mHeight = new Height(Value, Date);
        ContentValues contentValues = heightContentValues(mHeight);
        long result = mDatabase.insert("height",null,contentValues);

        return result;
    }

    //Update
    public long update(Height height){
        ContentValues contentValues = heightContentValues(height);
        long result= mDatabase.update("height",contentValues, dbHealthSchema.HeightTable.Id +"=?",new String[]{String.valueOf(height.getmId())});
        return result;
    }
    //delete
    public long delete(int id){
        String heightID = Integer.toString(id);
        return mDatabase.delete("height", dbHealthSchema.HeightTable.Id +"=?", new String[]{heightID});
    }
    //view data
    public Cursor viewData(){
        String view = " SELECT * FROM " + dbHealthSchema.HeightTable.TABLE_NAME;
        Cursor cursor = mDatabase.rawQuery(view, null);
        return cursor;
    }
    // Truy van toan bo du lieu do ve 1 danh sach
    public List<Height> getAllHeight(){
        List <Height> heights = new ArrayList<>();
        Cursor cursor = viewData();
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String id = cursor.getString(cursor.getColumnIndex(dbHealthSchema.HeightTable.Id));
                String value = cursor.getString(cursor.getColumnIndex(dbHealthSchema.HeightTable.Value));
                String date = cursor.getString(cursor.getColumnIndex(dbHealthSchema.HeightTable.Date));

                Height height = new Height();
                try{
                    height.setmId(Integer.parseInt(id));
                    height.setmValue(Integer.parseInt(value));
                    height.setmDate(dateFormat.parse(date));
                }catch (Exception e){
                    Log.d("Database_Exp:",e.getMessage());
                }

                heights.add(height);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return heights;
    }

    public Height getLastestHeight(){
        Height lastestHeight = new Height();
        Cursor cursor = viewData();
        cursor.moveToLast();
        if(cursor.getCount()>0){
            String id = cursor.getString(cursor.getColumnIndex(dbHealthSchema.HeightTable.Id));
            String value = cursor.getString(cursor.getColumnIndex(dbHealthSchema.HeightTable.Value));
            String date = cursor.getString(cursor.getColumnIndex(dbHealthSchema.HeightTable.Date));

            try{
                lastestHeight.setmId(Integer.parseInt(id));
                lastestHeight.setmValue(Integer.parseInt(value));
                lastestHeight.setmDate(dateFormat.parse(date));
            }catch (Exception e){
                Log.d("Database_Exp:",e.getMessage());
            }
        }
        return lastestHeight;
    }

    public List<Height> getHeightInRange(Date StartDate, Date EndDate){

        String starDate = dateFormat.format(StartDate);
        String endDate = dateFormat.format(EndDate);

        String query = " SELECT * FROM " + dbHealthSchema.HeightTable.TABLE_NAME +
                " WHERE " + dbHealthSchema.HeightTable.Date + " BETWEEN \"" + starDate + "\" AND \"" + endDate +"\"";
        Cursor cursor = mDatabase.rawQuery(query,null);
        List <Height> heights = new ArrayList<>();
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String id = cursor.getString(cursor.getColumnIndex(dbHealthSchema.WeightTable.Id));
                String value = cursor.getString(cursor.getColumnIndex(dbHealthSchema.WeightTable.Value));
                String date = cursor.getString(cursor.getColumnIndex(dbHealthSchema.WeightTable.Date));

                Height height = new Height();
                try{
                    height.setmId(Integer.parseInt(id));
                    height.setmValue(Integer.parseInt(value));
                    height.setmDate(dateFormat.parse(date));
                }catch (Exception e){
                    Log.d("Database_Exp:",e.getMessage());
                }

                heights.add(height);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return heights;
    }
}
