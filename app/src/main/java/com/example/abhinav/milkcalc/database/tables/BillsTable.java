package com.example.abhinav.milkcalc.database.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.example.abhinav.milkcalc.pojo.Bill;
import com.google.gson.Gson;

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


    public static Bill from(@NonNull Context context, @NonNull Cursor cursor) {
        Bill bill = new Bill();
        bill._ID = cursor.getLong(Query.COLUMN_ID);
        bill.serverID = cursor.getString(Query.COLUMN_SERVER_ID);
        bill.tankerNumber = cursor.getString(Query.COLUMN_TANKER_NUMBER);
        bill.fromDairy = cursor.getString(Query.COLUMN_FROM_DAIRY);
        bill.toDairy = cursor.getString(Query.COLUMN_TO_DAIRY);
        bill.distance = cursor.getString(Query.COLUMN_DISTANCE);
        bill.dieselTotal = cursor.getString(Query.COLUMN_DIESEL);
        bill.average= cursor.getString(Query.COLUMN_AVERAGE);
        bill.balanceHSD = cursor.getString(Query.COLUMN_BALANCE_HSD);
        bill.cash= cursor.getString(Query.COLUMN_CASH);
        bill.tollTax = cursor.getString(Query.COLUMN_TOLL_TAX) ;
        bill.balanceCash = cursor.getString(Query.COLUMN_BALANCE_CASH);

        return bill;
    }

    public static ContentValues buildContentValues(@NonNull Bill bill) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SERVER_ID, bill.serverID);
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

    public static final class Query {
        public static final String[] PROJECTION = {
                _ID, SERVER_ID, TANKER_NUMBER, FROM_DAIRY,
                TO_DAIRY, DISTANCE, DIESEL,
                AVERAGE, BALANCE_HSD, CASH,
                TOLL_TAX, BALANCE_CASH
        };

        public static final int COLUMN_ID = 0;
        public static final int COLUMN_SERVER_ID = 1;
        public static final int COLUMN_TANKER_NUMBER= 2;
        public static final int COLUMN_FROM_DAIRY= 3;
        public static final int COLUMN_TO_DAIRY= 4;
        public static final int COLUMN_DISTANCE= 5;
        public static final int COLUMN_DIESEL= 6;
        public static final int COLUMN_AVERAGE= 7;
        public static final int COLUMN_BALANCE_HSD= 8;
        public static final int COLUMN_CASH= 9;
        public static final int COLUMN_TOLL_TAX= 10;
        public static final int COLUMN_BALANCE_CASH= 11;
    }
}
