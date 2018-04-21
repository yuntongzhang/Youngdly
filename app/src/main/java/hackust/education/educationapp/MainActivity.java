package hackust.education.educationapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private TutorsDbAdaptor mDbAdapter;
    private SimpleCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Listview logic starts here
        mListView = (ListView) findViewById(R.id.tutors_list_view);
        mListView.setDivider(null);
        mDbAdapter = new TutorsDbAdaptor(this);
        mDbAdapter.open();

        if (savedInstanceState == null) {
            // clear all data
            mDbAdapter.deleteAllTutors();
            // add now
            mDbAdapter.createTutor("John", "Math");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Cursor cursor = mDbAdapter.fetchAllTutors();

        // from columns defined in the db
        String[] from = new String[]{TutorsDbAdaptor.COL_NAME};

        // to the ids of views in the layout
        int[] to = new int[]{R.id.row_text};

        mCursorAdapter = new SimpleCursorAdapter(
                //context
                MainActivity.this,
                // the layout of the view
                R.layout.tutors_row,
                //cursor
                cursor,
                // from columns defined in the db
                from,
                // to the ids of views in the layout
                to,
                // flag - not use
                0);

        // the cursorAdaptor (controller) is now updating the listView (view)
        // with data from the db (model)
        mListView.setAdapter(mCursorAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
