package com.scrawlsoft.gjournal.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class DbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GJDataContract.Entry.TABLE_NAME + " (" +
                    GJDataContract.Entry._ID + " INTEGER PRIMARY KEY, " +
                    GJDataContract.Entry.COLUMN_NAME_TEXT + " TEXT, " +
                    GJDataContract.Entry.COLUMN_NAME_TYPE + " INTEGER, " +
                    GJDataContract.Entry.COLUMN_NAME_DATE + " INTEGER, " +
                    GJDataContract.Entry.COLUMN_NAME_DONE + " INTEGER, " +
                    GJDataContract.Entry.COLUMN_NAME_MIGRATED + " INTEGER, " +
                    GJDataContract.Entry.COLUMN_NAME_PARENT_ID + " INTEGER, " +
                    GJDataContract.Entry.COLUMN_NAME_SIGNIFIER + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GJDataContract.Entry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "FeedReader.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

        // Every BuJo starts with at least an empty Future Log.
        ContentValues values = new ContentValues();
        // TODO: Localize these.
        values.put(GJDataContract.Entry.COLUMN_NAME_TEXT, "Future log");
        values.put(GJDataContract.Entry.COLUMN_NAME_TYPE, 0);
        values.put(GJDataContract.Entry.COLUMN_NAME_PARENT_ID, 0);
        db.insertOrThrow(GJDataContract.Entry.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: make a real upgrade system.
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
