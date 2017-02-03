package com.scrawlsoft.gjournal;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.scrawlsoft.gjournal.data.GJDataContract;
import com.scrawlsoft.gjournal.data.GJEntryProvider;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView listView;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.index_list);
        adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{GJDataContract.Entry.COLUMN_NAME_TEXT},
                new int[]{android.R.id.text1});
        listView.setAdapter(adapter);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: do this for realsies.
        System.out.println("OPTIONS ITEM SELECTED");
        ContentValues values = new ContentValues();
        values.put(GJDataContract.Entry.COLUMN_NAME_TEXT, "Another thing");
        values.put(GJDataContract.Entry.COLUMN_NAME_TYPE, 0);
        values.put(GJDataContract.Entry.COLUMN_NAME_PARENT_ID, 0);
        getContentResolver().insert(GJEntryProvider.CONTENT_URI, values);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        System.out.println("CREATE LOADER: " + id);
        Uri uri = GJEntryProvider.CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        System.out.println("LOAD FINISHED:" + cursor.getCount());
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        System.out.println("LOAD RESET");
        adapter.swapCursor(null);
    }
}
