package com.scrawlsoft.gjournal.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
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
        System.out.println("ONCREATING");
        entryDatabase = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        System.out.println("QUERYING!");
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(GJDataContract.Entry.TABLE_NAME);

        // TODO: deal with ids.
        if (sortOrder == null || sortOrder.equals("")) {
            sortOrder = GJDataContract.Entry.COLUMN_NAME_TEXT; // TODO: make this make sense.
        }
        return queryBuilder.query(entryDatabase.getReadableDatabase(), projection, selection, selectionArgs, sortOrder, null, null);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
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
