package com.scrawlsoft.gjournal.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.scrawlsoft.gjournal.model.Entry;

import org.joda.time.LocalDate;

final class EntryDbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GJDataContract.Entry.TABLE_NAME + " (" +
                    GJDataContract.Entry._ID + " INTEGER PRIMARY KEY, " +
                    GJDataContract.Entry.COLUMN_SORT_ORDER + " INTEGER DEFAULT 50, " +
                    GJDataContract.Entry.COLUMN_NAME_TEXT + " TEXT NOT NULL, " +
                    GJDataContract.Entry.COLUMN_NAME_TYPE + " INTEGER NOT NULL, " +
                    GJDataContract.Entry.COLUMN_NAME_DATE + " INTEGER, " +
                    GJDataContract.Entry.COLUMN_NAME_DONE + " INTEGER DEFAULT 0, " +
                    GJDataContract.Entry.COLUMN_NAME_MIGRATED + " INTEGER DEFAULT 0, " +
                    GJDataContract.Entry.COLUMN_NAME_PARENT_ID + " INTEGER DEFAULT 0, " +
                    GJDataContract.Entry.COLUMN_NAME_SIGNIFIER + " INTEGER DEFAULT 0)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GJDataContract.Entry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "GJournal.db";

    EntryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

        // Every BuJo starts with at least an empty Future Log.
        db.insertOrThrow(GJDataContract.Entry.TABLE_NAME, null, valuesForEntry(Entry.FutureLog()));

        // ...and a Month Log for the current month.
        Entry monthLog = Entry.MonthLog(LocalDate.now());
        db.insertOrThrow(GJDataContract.Entry.TABLE_NAME, null, valuesForEntry(monthLog));
    }

    static ContentValues valuesForEntry(Entry entry) {
        ContentValues values = new ContentValues();
        values.put(GJDataContract.Entry.COLUMN_NAME_TEXT, entry.getText());
        values.put(GJDataContract.Entry.COLUMN_NAME_TYPE, entry.getType());
        LocalDate date = entry.getDate();
        if (date != null) {
            values.put(GJDataContract.Entry.COLUMN_NAME_DATE, date.toDate().getTime());
        }
        values.put(GJDataContract.Entry.COLUMN_NAME_DONE, entry.isDone());
        values.put(GJDataContract.Entry.COLUMN_NAME_PARENT_ID, entry.getParentId());
        values.put(GJDataContract.Entry.COLUMN_SORT_ORDER, entry.getSortOrder());
        return values;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: make a real upgrade system.
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
