package hackust.education.educationapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

public class SeeStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_student);
        // Listview logic starts here
        ListView mListView = findViewById(R.id.students_list_view);
        mListView.setDivider(null);
        StudentsDbAdaptor mDbAdapter = new StudentsDbAdaptor(this);
        mDbAdapter.open();

        if (savedInstanceState == null) {
            // clear all data
            mDbAdapter.deleteAllStudents();
            // add now
            mDbAdapter.createStudent("Einsten", "Physics", "Rating: 5");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Cursor cursor = mDbAdapter.fetchAllStudents();

        // from columns defined in the db
        String[] from = new String[]{StudentsDbAdaptor.COL_NAME, StudentsDbAdaptor.COL_SUBJECT,
                StudentsDbAdaptor.COL_RATING};

        // to the ids of views in the layout
        int[] to = new int[]{R.id.row_name, R.id.row_subject2, R.id.row_rating};

        SimpleCursorAdapter mCursorAdapter = new CustomCursorAdapter(
                //context
                SeeStudentActivity.this,
                // the layout of the view
                R.layout.students_row,
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

        //Button b = (Button) findViewById(R.id.choose_tutor);

        //b.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        startActivity(new Intent(MainActivity.this, Pop.class));
        //   }
        //});
    }

    //public void toChoose(View view) {
    //    Intent intent = new Intent(this, Pop.class);
    //    startActivity(intent);
    //}

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

    private class CustomCursorAdapter extends SimpleCursorAdapter {

        public CustomCursorAdapter(@NonNull Context context, int layout, Cursor c, String[] from,
                                   int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return super.newView(context, cursor, parent);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            ViewHolder holder = (ViewHolder) view.getTag();
            if (holder == null) {
                holder = new ViewHolder();
                holder.button = view.findViewById(R.id.choose_student);
                holder.name = view.findViewById(R.id.row_name);
                holder.subject = view.findViewById(R.id.row_subject2);
                holder.rating = view.findViewById(R.id.row_rating);
                view.setTag(holder);
            }

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SeeStudentActivity.this, Pop.class);
                    startActivity(intent);
                }
            });
        }

    }

    public class ViewHolder {
        Button button;
        TextView name;
        TextView subject;
        TextView rating;

    }


}
