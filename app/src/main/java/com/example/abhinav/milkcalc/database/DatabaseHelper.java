package com.example.abhinav.milkcalc.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.abhinav.milkcalc.BuildConfig;
import com.example.abhinav.milkcalc.database.tables.BillsTable;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "MilkCalc.db";
    private static final int DATABASE_VERSION = BuildConfig.DB_VERSION;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        BillsTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        BillsTable.onUpgrade(db, oldVersion, newVersion);
    }
}
