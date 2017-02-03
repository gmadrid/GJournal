package com.scrawlsoft.gjournal.model;

import android.support.annotation.IntDef;

import org.joda.time.LocalDate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 The many-armed Entry class for BuJo.

 At this point, *everything* in the BuJo is an Entry.

 The Index page contains items which have no parent. They are equivalent to a "Module" in BuJo-speak.

 - Each item with no parent names a Page in your BuJo.
 - Each has a title, but since this isn't an analog notebook, there is no page number.
 - An Item "is in" a page by having the page Entry Id as its parent id.

 Future Log:
   - Each Entry in the future log has a Date associated with it. It will be displayed in the Future
     Log associated with that month/day.

 Month Log:
   - An Entry in the MonthLog with a Date will be displayed on the "left" page of the Month Log.
   - An Entry without a date will be displayed on the "left" page which shall be formatted as a
     Task List.

 Daily Log:
   - An Entry in the DailyLog should not have a Date. It gets its Date from the parent.
   - Each Entry should be a Task. (For now.)

 Tasks:
   - The basic "to do" item in a log.
   - It has a DONE field.
 */
public final class Entry {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_FUTURE_LOG, TYPE_TASK, TYPE_INVALID})
    public @interface EntryTypeIntDef {
    }

    public static final int TYPE_INVALID = 0;
    public static final int TYPE_FUTURE_LOG = 1;
    public static final int TYPE_TASK = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SORT_FIRST, SORT_NO_PRIORITY, SORT_LAST})
    public @interface EntrySortIntDef {
    }

    public static final int SORT_FIRST = 1;
    public static final int SORT_NO_PRIORITY = 50;
    public static final int SORT_LAST = 100;

    private long parentId;

    private String text;
    private
    @EntryTypeIntDef
    int type = TYPE_INVALID; // TODO: Use one of those IntThing annotations.
    private LocalDate date;
    private boolean done;
    private
    @EntrySortIntDef
    int sortOrder = SORT_NO_PRIORITY;  // TODO: IntThing


}
