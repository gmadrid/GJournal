package com.scrawlsoft.gjournal;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.scrawlsoft.gjournal.data.GJDataContract;
import com.scrawlsoft.gjournal.data.GJEntryProvider;
import com.scrawlsoft.gjournal.model.Entry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private CursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.index_list);
        adapter = new CursorAdapter(this, null, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                int index = cursor.getColumnIndex(GJDataContract.Entry.COLUMN_NAME_TYPE);
                int type = cursor.getInt(index);
                int resourceId = 0;
                switch (type) {
                    case Entry.TYPE_INVALID:
                    default:
                        // log an error here or something.
                        break;
                    case Entry.TYPE_FUTURE_LOG:
                        resourceId = android.R.layout.simple_list_item_1;
                        break;
                    case Entry.TYPE_MONTH_LOG:
                        resourceId = R.layout.future_log_month;
                        break;
                }
                return LayoutInflater.from(context).inflate(resourceId, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                System.out.println("THERE");
            }
        };

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
        return false;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, GJEntryProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
