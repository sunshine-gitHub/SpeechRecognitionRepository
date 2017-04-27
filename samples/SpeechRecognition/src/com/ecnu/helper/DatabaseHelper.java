package com.ecnu.helper;

/**
 * Created by CY on 2017/4/16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    /**
     * 在sqliteOpenHelper子类中必须有该构造函数,
     * 第一个参数为activity
     * 第二个参数：表的名字
     * 第三个函数：null
     * 第四个参数：数据库的版本
     */

    public DatabaseHelper(Context context, String name, CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    /**
     * 构造函数2
     * @param context
     * @param name
     */
    public  DatabaseHelper(Context context, String name) {
        // TODO Auto-generated constructor stub
        this(context, name, VERSION);
    }

    /**
     * 构造函数3
     * @param context
     * @param name
     * @param version
     */
    public  DatabaseHelper(Context context, String name,int version) {
        // TODO Auto-generated constructor stub
        this(context, name, null, version);
    }

    /**
     * 该函数在第一次创建数据库的时候执行，第一次得到SQLiteDatabase对象的时候，调用次方法
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        System.out.println("创建表");
        String strCreateSQL = "create table speechrecoguser(userid integer primary key autoincrement,name varchar(20),password varchar(20),age int,sex int, address varchar(50),photo varchar(20))";
        String strCreateSQL2 = "create table feedbackinfo(fbid integer primary key autoincrement,fdinfo varchar(200))";
        db.execSQL(strCreateSQL);
        db.execSQL(strCreateSQL2);
    }

    /**
     * 执行更新数据库
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
       // db.execSQL("ALTER TABLE speechrecoguser ADD photo VARCHAR(20)");
        System.out.println("更新数据库");
    }

}

