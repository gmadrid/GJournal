package com.scrawlsoft.gjournal.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class GJEntryProvider extends ContentProvider {
    private static final String PROVIDER_NAME = "com.scrawlsoft.gjournal.entries";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/entries");

    private static final int ENTRIES = 1;
    private static final int ENTRY_ID = 2;
    private static final UriMatcher uriMatcher = getUriMatcher();
    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "entries", ENTRIES);
        uriMatcher.addURI(PROVIDER_NAME, "entries/#", ENTRY_ID);
        return uriMatcher;
    }

    private DbHelper entryDatabase;

    @Override
    public boolean onCreate() {
        entryDatabase = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(GJDataContract.Entry.TABLE_NAME);

        if (uriMatcher.match(uri) == ENTRY_ID) {
            long id = ContentUris.parseId(uri);
            if (id < 0) {
                // TODO: log here.
                return null;
            }
            queryBuilder.appendWhere(GJDataContract.Entry._ID + " = " + id);
        }

        // TODO: deal with ids.
        if (sortOrder == null || sortOrder.equals("")) {
            sortOrder = GJDataContract.Entry.COLUMN_NAME_TEXT; // TODO: make this make sense.
        }
        return queryBuilder.query(entryDatabase.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        System.out.println("Provider INSERT");
        long newId = entryDatabase.getWritableDatabase().insertOrThrow(GJDataContract.Entry.TABLE_NAME, null, values);
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, newId);
        System.out.println("NEW URI: " + newUri.toString());
        return newUri;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String whereClause = selection;
        String[] whereArgs = selectionArgs;
        if (uriMatcher.match(uri) == ENTRY_ID) {
            long id = ContentUris.parseId(uri);
            if (id < 0) {
                return 0;  // TODO: what?
            }
            whereClause = "_id=?";
            whereArgs = new String[]{String.valueOf(id)};
        }
        return entryDatabase.getWritableDatabase().update(GJDataContract.Entry.TABLE_NAME, values, whereClause, whereArgs);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        String whereClause = selection;
        String[] whereArgs = selectionArgs;
        if (uriMatcher.match(uri) == ENTRY_ID) {
            long id = ContentUris.parseId(uri);
            if (id < 0) {
                return 0;
            }
            whereClause = "_id=?";
            whereArgs = new String[]{String.valueOf(id)};
        }
        return entryDatabase.getWritableDatabase().delete(GJDataContract.Entry.TABLE_NAME, whereClause, whereArgs);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ENTRIES:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + PROVIDER_NAME;
            case ENTRY_ID:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + PROVIDER_NAME;
            default:
                return "";
        }
    }
}
