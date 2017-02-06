package com.scrawlsoft.gjournal;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scrawlsoft.gjournal.data.GJDataContract;
import com.scrawlsoft.gjournal.data.GJEntryProvider;
import com.scrawlsoft.gjournal.model.Entry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static class TheViewHolder extends RecyclerView.ViewHolder {
        private TheViewHolder(View itemView, TextView textView) {
            super(itemView);
            this.textView = textView;
        }

        private TextView textView;
    }

    private CursorRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.index_list);
        adapter = new CursorRecyclerViewAdapter<TheViewHolder>(this, null) {

            @Override
            public int getItemViewType(int position) {
                Cursor cursor = getCursor();
                System.out.println("YYYYYYYYYYYYYYYY: " + cursor + ":" + cursor.getColumnCount());
                long id = getItemId(position);
                int index = cursor.getColumnIndex(GJDataContract.Entry.COLUMN_NAME_TYPE);
                System.out.println("INDEX: " + index);
                int type = cursor.getInt(index);
                System.out.println("TYPE: " + type + ":" + id);
                int resourceId = 0;
                switch (type) {
                    case Entry.TYPE_FUTURE_LOG:
                        resourceId = android.R.layout.simple_list_item_1;
                        break;
                    case Entry.TYPE_MONTH_LOG:
                        resourceId = R.layout.future_log_month;
                        break;
                    case Entry.TYPE_INVALID:
                    default:
                        // Should probably throw something here.
                        // TODO: do this.
                        // fallthrough
                }
                System.out.println("HERE" + position + ":" + resourceId);
                return resourceId;

//                return android.R.layout.simple_list_item_1;
//
//                System.out.println("CURSOR: " + getCursor());
//                System.out.println("0: " + getCursor().getColumnName(0));
//                System.out.println("1: " + getCursor().getColumnName(1));
            }

            @Override
            public TheViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                System.out.println("VT: " + viewType);
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(viewType, parent, false);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                return new TheViewHolder(view, textView);
            }

            @Override
            public void onBindViewHolder(TheViewHolder viewHolder, Cursor cursor) {
                System.out.println("BINDING: " + viewHolder.getAdapterPosition());
                cursor.moveToPosition(viewHolder.getAdapterPosition());
                int textIndex = cursor.getColumnIndex(GJDataContract.Entry.COLUMN_NAME_TEXT);
                viewHolder.textView.setText(cursor.getString(textIndex));
            }
        };

//        adapter = new CursorAdapter(this, null, 0) {
//            @Override
//            public View newView(Context context, Cursor cursor, ViewGroup parent) {
//                    case Entry.TYPE_FUTURE_LOG:
//                        resourceId = android.R.layout.simple_list_item_1;
//                        break;
//                    case Entry.TYPE_MONTH_LOG:
//                        resourceId = R.layout.future_log_month;
//                        break;
//                }
//                return LayoutInflater.from(context).inflate(resourceId, parent, false);
//            }
//
//            @Override
//            public void bindView(View view, Context context, Cursor cursor) {
//                // TODO: do that ViewHolder thing.
//                TextView textView = (TextView) view.findViewById(android.R.id.text1);
//                textView.setText(cursor.getString(cursor.getColumnIndex(GJDataContract.Entry.COLUMN_NAME_TEXT)));
//            }
//        };
        recyclerView.setAdapter(adapter);
//        listView.setAdapter(adapter);
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
        System.out.println("FOOBAR");
        return new CursorLoader(this, GJEntryProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        System.out.println("QUUX: " + cursor.getColumnCount());
        adapter.swapCursor(cursor);
        System.out.println("SUGAR");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
