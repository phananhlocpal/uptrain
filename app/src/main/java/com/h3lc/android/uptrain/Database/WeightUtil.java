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

public class WeightUtil {

    SQLiteDatabase mDatabase;
    Context mContext;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public WeightUtil(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new dbHeathHelper(mContext).getWritableDatabase();
    }

    private ContentValues weightContentValues(Weight weight){
        ContentValues contentValues = new ContentValues();
        String date = dateFormat.format(weight.getmDate());

        contentValues.put(dbHealthSchema.WeightTable.Value, weight.getmValue());
        contentValues.put(dbHealthSchema.WeightTable.Date, date);
        return contentValues;
    }

    //Inssẻt
    public long add(int Value, Date Date){
        Weight mWeight = new Weight(Value, Date);
        ContentValues contentValues = weightContentValues(mWeight);
        long result = mDatabase.insert("weight",null,contentValues);

        return result;
    }

    //Update
    public long update(Weight weight){
        ContentValues contentValues = weightContentValues(weight);
        long result= mDatabase.update("weight",contentValues, dbHealthSchema.WeightTable.Id +"=?",new String[]{String.valueOf(weight.getmId())});
        return result;
    }
    //delete
    public long delete(int id){
        String weightID = Integer.toString(id);
        return mDatabase.delete("weight", dbHealthSchema.WeightTable.Id +"=?", new String[]{weightID});
    }
    //view data
    public Cursor viewData(){
        String view = " SELECT * FROM " + dbHealthSchema.WeightTable.TABLE_NAME;
        Cursor cursor = mDatabase.rawQuery(view, null);
        return cursor;
    }
    // Truy van toan bo du lieu do ve 1 danh sach
    public List<Weight> getAllWeight(){
        List <Weight> weights = new ArrayList<>();
        Cursor cursor = viewData();
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String id = cursor.getString(cursor.getColumnIndex(dbHealthSchema.WeightTable.Id));
                String value = cursor.getString(cursor.getColumnIndex(dbHealthSchema.WeightTable.Value));
                String date = cursor.getString(cursor.getColumnIndex(dbHealthSchema.WeightTable.Date));

                Weight weight = new Weight();
                try{
                    weight.setmId(Integer.parseInt(id));
                    weight.setmValue(Integer.parseInt(value));
                    weight.setmDate(dateFormat.parse(date));
                }catch (Exception e){
                    Log.d("Database_Exp:",e.getMessage());
                }

                weights.add(weight);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return weights;
    }

    public Weight getLastestWeight() {
        Weight lastestWeight = new Weight();
        Cursor cursor = viewData();
        cursor.moveToLast();
        if (cursor.getCount() > 0) {
            String id = cursor.getString(cursor.getColumnIndex(dbHealthSchema.WeightTable.Id));
            String value = cursor.getString(cursor.getColumnIndex(dbHealthSchema.WeightTable.Value));
            String date = cursor.getString(cursor.getColumnIndex(dbHealthSchema.WeightTable.Date));
            try{
                lastestWeight.setmId(Integer.parseInt(id));
                lastestWeight.setmValue(Integer.parseInt(value));
                lastestWeight.setmDate(dateFormat.parse(date));
            }catch (Exception e){
                Log.d("Database_Exp:",e.getMessage());
            }

        }
        return lastestWeight;
    }

    public List<Weight> getWeightInRange(Date StartDate, Date EndDate){

        String starDate = dateFormat.format(StartDate);
        String endDate = dateFormat.format(EndDate);

        String query = " SELECT * FROM " + dbHealthSchema.WeightTable.TABLE_NAME +
                " WHERE " + dbHealthSchema.WeightTable.Date + " BETWEEN \"" + starDate + "\" AND \"" + endDate +"\"";
        Cursor cursor = mDatabase.rawQuery(query,null);
        List <Weight> weights = new ArrayList<>();
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String id = cursor.getString(cursor.getColumnIndex(dbHealthSchema.WeightTable.Id));
                String value = cursor.getString(cursor.getColumnIndex(dbHealthSchema.WeightTable.Value));
                String date = cursor.getString(cursor.getColumnIndex(dbHealthSchema.WeightTable.Date));

                Weight weight = new Weight();
                try{
                    weight.setmId(Integer.parseInt(id));
                    weight.setmValue(Integer.parseInt(value));
                    weight.setmDate(dateFormat.parse(date));
                }catch (Exception e){
                    Log.d("Database_Exp:",e.getMessage());
                }

                weights.add(weight);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return weights;
    }
}
