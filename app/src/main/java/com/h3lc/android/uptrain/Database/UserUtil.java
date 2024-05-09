package com.h3lc.android.uptrain.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.h3lc.android.uptrain.Models.*;

import java.util.ArrayList;
import java.util.List;

//viet try catch
public class UserUtil {

    SQLiteDatabase mDatabase;
    Context mContext;
    public UserUtil(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new dbHeathHelper(mContext).getWritableDatabase();
    }

    private ContentValues userContentValues(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHealthSchema.UserTable.NAME,user.getmName());
        contentValues.put(dbHealthSchema.UserTable.AGE,user.getmAge());
        contentValues.put(dbHealthSchema.UserTable.GENDER,user.getmGender());
        contentValues.put(dbHealthSchema.UserTable.EMAIL,user.getmEmail());
        contentValues.put(dbHealthSchema.UserTable.PHONE,user.getmPhone());
        return contentValues;
    }
    //Insert
    public long add(String Name, int Age, String Gender, String Email, String PhoneNo ){
        User mUser = new User(Name, Age, Gender, Email, PhoneNo);
        ContentValues contentValues = userContentValues(mUser);
        long result = mDatabase.insert("user", null,contentValues);
        return result;
    }

    //Update
        public User update(User user){
        ContentValues contentValues = userContentValues(user);
        long result= mDatabase.update("user",contentValues, dbHealthSchema.UserTable.Id +"=?",new String[]{String.valueOf(user.getmId())});
        user = getAllUser().get(0);
        return user;
    }
    //delete
    public long delete(int id){
        String userID = Integer.toString(id);
        return mDatabase.delete("user", dbHealthSchema.UserTable.Id +"=?", new String[]{userID});
    }
    //view data
    public Cursor viewData(){
        String view = " SELECT * FROM " + dbHealthSchema.UserTable.TABLE_NAME;
        Cursor cursor = mDatabase.rawQuery(view, null);
        return cursor;
    }
    // Truy van toan bo du lieu do ve 1 danh sach toan bo user
    public List<User> getAllUser(){
        List <User> users = new ArrayList<>();
        Cursor cursor = viewData();
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String id = cursor.getString(cursor.getColumnIndex(dbHealthSchema.UserTable.Id));
                String name = cursor.getString(cursor.getColumnIndex(dbHealthSchema.UserTable.NAME));
                String age = cursor.getString(cursor.getColumnIndex(dbHealthSchema.UserTable.AGE));
                String gender = cursor.getString(cursor.getColumnIndex(dbHealthSchema.UserTable.GENDER));
                String email = cursor.getString(cursor.getColumnIndex(dbHealthSchema.UserTable.EMAIL));
                String phone = cursor.getString(cursor.getColumnIndex(dbHealthSchema.UserTable.PHONE));

                User user = new User();
                user.setmId(Integer.parseInt(id));
                user.setmName(name);
                user.setmAge(Integer.parseInt(age));
                user.setmGender(gender);
                user.setmEmail(email);
                user.setmPhone(phone);

                users.add(user);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return users;
    }
}
