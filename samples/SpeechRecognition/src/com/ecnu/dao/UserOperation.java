package com.ecnu.dao;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.ecnu.entity.User;
import com.ecnu.helper.DatabaseHelper;

/**
 * Created by CY on 2017/4/16.
 */

public class UserOperation {
    public String verifyLogin(Context context,String name, String pwd){

       // deleteUser(context,"1");
        //register(context,new User());

        DatabaseHelper dbHelper = new DatabaseHelper(context, "speechrecognition",5);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from speechrecoguser where name = ? and password = ?", new String[]{name, pwd});
        while (cursor.moveToNext()) {
            String uname = cursor.getString(cursor.getColumnIndex("name"));
            String age = cursor.getString(cursor.getColumnIndex("age"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String id = cursor.getString(cursor.getColumnIndex("userid"));
            //Toast.makeText(context, uname + "-" + age + "-" + password, Toast.LENGTH_SHORT).show();
            return id;
        }
        return "";
    }

//    public boolean f(Context context) {
//        ContentValues values = new ContentValues();
//
//        values.put("photo", "cy");
//        DatabaseHelper dbHelper = new DatabaseHelper(context, "speechrecognition",2);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.insert("speechrecoguser", null, values);
//        Toast.makeText(context, "插入成功", Toast.LENGTH_SHORT).show();
//        return true;
//    }

    public boolean register(Context context,User user){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "speechrecognition",5);
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        if (user.getUsername().equals("")||user.getUsername()==null){
            return false;
        }
        //要判断名字，在数据库里面是否重复
        Cursor cursor = db.rawQuery("select name from speechrecoguser", null);
        while (cursor.moveToNext()) {
            String uname = cursor.getString(cursor.getColumnIndex("name"));
            if (uname.equals(user.getUsername())){
                return false;
            }
        }

        ContentValues values = new ContentValues();
        values.put("name", user.getUsername());
        values.put("password", user.getPassword());
        values.put("age", user.getAge());
        values.put("sex", user.getSex());
        values.put("address", user.getAddress());
        values.put("photo", user.getPhoto());

        db.insert("speechrecoguser", null, values);
        //Toast.makeText(context, "插入成功", Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean addUsers(Context context,User user){

        //要判断名字，在数据库里面是否重复

        ContentValues values = new ContentValues();
        values.put("name", "cy");
        values.put("password", "123");
        values.put("age", 24);
        values.put("sex", 1);
        values.put("address", "河南");
        values.put("photo", "cy");
        DatabaseHelper dbHelper = new DatabaseHelper(context, "speechrecognition",5);
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        db.insert("speechrecoguser", null, values);

        ContentValues values2 = new ContentValues();
        values2.put("name", "ljy");
        values2.put("password", "123");
        values2.put("age", 23);
        values2.put("sex", 0);
        values2.put("address", "河北");
        values2.put("photo", "ljy");
        //DatabaseHelper dbHelper2 = new DatabaseHelper(context, "speechrecognition",5);
        //SQLiteDatabase  db2 = dbHelper2.getWritableDatabase();
        db.insert("speechrecoguser", null, values2);

        ContentValues values3 = new ContentValues();
        values3.put("name", "zk");
        values3.put("password", "123");
        values3.put("age", 24);
        values3.put("sex", 1);
        values3.put("address", "山东");
        values3.put("photo", "zk");
//        DatabaseHelper dbHelper3 = new DatabaseHelper(context, "speechrecognition",5);
//        SQLiteDatabase  db3 = dbHelper3.getWritableDatabase();
        db.insert("speechrecoguser", null, values3);

        ContentValues values4 = new ContentValues();
        values4.put("name", "dsy");
        values4.put("password", "123");
        values4.put("age", 23);
        values4.put("sex", 0);
        values4.put("address", "四川");
        values4.put("photo", "dsy");
       // DatabaseHelper dbHelper4 = new DatabaseHelper(context, "speechrecognition",5);
        //SQLiteDatabase  db4 = dbHelper4.getWritableDatabase();
        db.insert("speechrecoguser", null, values4);

        Toast.makeText(context, "插入成功", Toast.LENGTH_SHORT).show();
        return true;
    }


    public boolean deleteUser(Context context,String id){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "speechrecognition",5);
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        db.delete("speechrecoguser", "name = ?", new String[]{"cy"});
      //  Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
        return true;
    }

    public User queryUser(Context context,String id){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "speechrecognition",5);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from speechrecoguser where userid = ?", new String[]{id});
        while (cursor.moveToNext()) {
            String uname = cursor.getString(cursor.getColumnIndex("name"));
            String age = cursor.getString(cursor.getColumnIndex("age"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String photo = cursor.getString(cursor.getColumnIndex("photo"));
            User user = new User();
            user.setUsername(uname);
            user.setPassword(password);
            user.setAge(Integer.parseInt(age));
            user.setSex(Integer.parseInt(sex));
            user.setAddress(address);
            user.setPhoto(photo);
            //Toast.makeText(context, uname + "-" + age + "-" + password, Toast.LENGTH_SHORT).show();
            return user;
        }
        return null;
    }


    public boolean changePwd(Context context,String password,String id){
        ContentValues values = new ContentValues();
        values.put("password", password);

        DatabaseHelper dbHelper = new DatabaseHelper(context, "speechrecognition",5);
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        db.update("speechrecoguser", values, "userid=?", new String[]{id});
        //Toast.makeText(context, "更新成功", Toast.LENGTH_SHORT).show();

        return true;
    }
}
