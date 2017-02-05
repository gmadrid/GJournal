package com.scrawlsoft.gjournal.data;

import android.provider.BaseColumns;

public final class GJDataContract {
    // Never make one of these.
    private GJDataContract() {}

    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_DONE = "done";
        public static final String COLUMN_NAME_MIGRATED = "migrated";
        public static final String COLUMN_NAME_PARENT_ID = "parentid";
        public static final String COLUMN_NAME_SIGNIFIER = "signifier";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_SORT_ORDER = "sortorder";
    }
}
