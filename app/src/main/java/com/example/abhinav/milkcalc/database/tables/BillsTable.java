package com.example.abhinav.milkcalc.database.tables;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.example.abhinav.milkcalc.pojo.Bill;

import timber.log.Timber;


public final class BillsTable implements BaseColumns {
    public static final String TABLE_NAME = "bills";

    //The unique id on server (push ids for firebase)
    public static final String SERVER_ID = "server_id";
    public static final String TANKER_NUMBER = "tanker_number";
    public static final String FROM_DAIRY = "from_dairy";
    public static final String TO_DAIRY = "to_dairy";
    public static final String DISTANCE = "distance";
    public static final String DIESEL = "diesel";
    public static final String AVERAGE = "average";
    public static final String BALANCE_HSD = "balance_hsd";
    public static final String CASH = "cash";
    public static final String TOLL_TAX = "toll_tax";
    public static final String BALANCE_CASH = "balance_cash";


    // Database creation SQL statement
    // The AUTOINCREMENT keyword imposes extra CPU, memory, disk space,
    // and disk I/O overhead and should be avoided if not strictly needed.
    private static final String DATABASE_CREATE = String.format("CREATE TABLE %s " +
                    "(%s INTEGER PRIMARY KEY, " +
                    "%s TEXT, %s TEXT, %s TEXT, " +
                    "%s TEXT, %s TEXT, %s TEXT, " +
                    "%s TEXT, %s TEXT, %s TEXT, " +
                    "%s TEXT, %s TEXT, " +
                    "UNIQUE (%s) ON CONFLICT REPLACE);", TABLE_NAME,
            _ID, SERVER_ID, TANKER_NUMBER, FROM_DAIRY,
            TO_DAIRY, DISTANCE, DIESEL,
            AVERAGE, BALANCE_HSD, CASH,
            TOLL_TAX, BALANCE_CASH,
            SERVER_ID);

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Timber.w("Upgrading database version from %d to %d", oldVersion, newVersion);
        database.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
        onCreate(database);
    }

    public static ContentValues buildContentValues(@NonNull Bill bill) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SERVER_ID, bill.serverId);
        contentValues.put(TANKER_NUMBER, bill.tankerNumber);
        contentValues.put(FROM_DAIRY, bill.fromDairy);
        contentValues.put(TO_DAIRY, bill.toDairy);
        contentValues.put(DISTANCE, bill.distance);
        contentValues.put(DIESEL, bill.dieselTotal);
        contentValues.put(AVERAGE, bill.average);
        contentValues.put(BALANCE_HSD, bill.balanceHSD);
        contentValues.put(CASH, bill.cash);
        contentValues.put(TOLL_TAX, bill.tollTax);
        contentValues.put(BALANCE_CASH, bill.balanceCash);

        return contentValues;
    }
}
