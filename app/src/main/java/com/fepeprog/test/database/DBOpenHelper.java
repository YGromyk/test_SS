package com.fepeprog.test.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "users.db";
    private static final int DB_VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + DBSchema.TABLE_NAME + "(" +
                DBSchema.Columns.EMAIL + " primary key, " +
                DBSchema.Columns.NAME + ", " +
                DBSchema.Columns.AGE + ", " +
                DBSchema.Columns.PHONE_NUMBER + ", " +
                DBSchema.Columns.PASSWORD + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
