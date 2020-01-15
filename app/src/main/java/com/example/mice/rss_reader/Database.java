package com.example.mice.rss_reader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public Database(Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //truy vấn không trả kết quả: CREATE, INSERT, UPDATE, DELETE...
    public void QueryData(String sql)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //truy vấn có trả kết quả: SELECT
    public Cursor GetData(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
