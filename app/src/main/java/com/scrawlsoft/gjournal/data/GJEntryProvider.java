package com.scrawlsoft.gjournal.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
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

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
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
