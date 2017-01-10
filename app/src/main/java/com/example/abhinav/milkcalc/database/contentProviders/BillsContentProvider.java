package com.example.abhinav.milkcalc.database.contentProviders;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.abhinav.milkcalc.BuildConfig;
import com.example.abhinav.milkcalc.database.DatabaseHelper;
import com.example.abhinav.milkcalc.database.tables.BillsTable;

import java.util.Locale;

public class BillsContentProvider extends ContentProvider {
    private static final String BASE_PATH = "bill";

    private static final int BILL_LIST = 1001;
    private static final int BILL_DETAIL = 1002;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private static final String AUTHORITY = String.format(Locale.getDefault(),
            "%s.%s", BuildConfig.APPLICATION_ID, BASE_PATH);

    public static final Uri CONTENT_URI = Uri.parse(
            String.format("content://%s/%s", AUTHORITY, BASE_PATH));

    static {
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH, BILL_LIST);
        URI_MATCHER.addURI(AUTHORITY, String.format("%s/#", BASE_PATH), BILL_DETAIL);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        switch (URI_MATCHER.match(uri)) {
            case BILL_DETAIL:
                sqLiteQueryBuilder.appendWhere(String.format("%s=%s",
                        BillsTable._ID, uri.getLastPathSegment()));
            case BILL_LIST:
                sqLiteQueryBuilder.setTables(BillsTable.TABLE_NAME);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }

        Cursor cursor = sqLiteQueryBuilder.query(databaseHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);

        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (URI_MATCHER.match(uri)) {
            case BILL_DETAIL:
            case BILL_LIST:
                long id = databaseHelper.getWritableDatabase()
                        .insert(BillsTable.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);

                return Uri.parse(String.format(Locale.getDefault(),
                        "%s/%d", BASE_PATH, id));
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (URI_MATCHER.match(uri)) {
            case BILL_LIST:
                getContext().getContentResolver().notifyChange(uri, null);
                return databaseHelper.getWritableDatabase()
                        .delete(BillsTable.TABLE_NAME, selection, selectionArgs);

            case BILL_DETAIL:
                getContext().getContentResolver().notifyChange(uri, null);
                return databaseHelper.getWritableDatabase()
                        .delete(BillsTable.TABLE_NAME, String.format("%s=%s",
                                BillsTable._ID, uri.getLastPathSegment()), null);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowUpdated;
        switch (URI_MATCHER.match(uri)) {
            case BILL_LIST:
                rowUpdated = databaseHelper.getWritableDatabase()
                        .update(BillsTable.TABLE_NAME, values, selection, selectionArgs);
                break;

            case BILL_DETAIL:
                rowUpdated = databaseHelper.getWritableDatabase()
                        .update(BillsTable.TABLE_NAME, values,
                                String.format("%s=%s", BillsTable._ID,
                                        uri.getLastPathSegment()), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowUpdated;
    }

    private DatabaseHelper databaseHelper;
}
