package hackust.education.educationapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TutorsDbAdaptor {
    //these are the column names
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_SUBJECT = "subject";

    //these are the corresponding indices
    public static final int INDEX_ID = 0;
    public static final int INDEX_NAME = INDEX_ID + 1;
    public static final int INDEX_SUBJECT = INDEX_ID + 2;

    //used for logging
    private static final String TAG = "TutorsDbAdapter";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "dba_tutors";
    private static final String TABLE_NAME = "tbl_tutors";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;

    //SQL statement used to create the database
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    COL_NAME + " TEXT, " +
                    COL_SUBJECT + " TEXT );";


    public TutorsDbAdaptor(Context ctx) {
        this.mCtx = ctx;
    }

    //open
    public void open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }

    //close
    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    //CREATE
    //note that the id will be created for you automatically
    public void createTutor(String name, String subject) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_SUBJECT, subject);
        mDb.insert(TABLE_NAME, null, values);
    }
    //overloaded to take a tutor
    public long createTutor(Tutor tutor) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, tutor.getName());
        values.put(COL_SUBJECT, tutor.getSubject());
        // Inserting Row
        return mDb.insert(TABLE_NAME, null, values);
    }

    //READ
    public Tutor fetchTutorById(int id) {
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{COL_ID,
                        COL_NAME, COL_SUBJECT}, COL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null
        );
        if (cursor != null)
            cursor.moveToFirst();
        return new Tutor(
                cursor.getInt(INDEX_ID),
                cursor.getString(INDEX_NAME),
                cursor.getString(INDEX_SUBJECT)
        );
    }

    public Cursor fetchAllTutors() {
        Cursor mCursor = mDb.query(TABLE_NAME, new String[]{COL_ID,
                        COL_NAME, COL_SUBJECT},
                null, null, null, null, null
        );
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //UPDATE
    public void updateTutor(Tutor tutor) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, tutor.getName());
        values.put(COL_SUBJECT, tutor.getSubject());
        mDb.update(TABLE_NAME, values,
                COL_ID + "=?", new String[]{String.valueOf(tutor.getId())});
    }

    //DELETE
    public void deleteTutorById(int nId) {
        mDb.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(nId)});
    }
    public void deleteAllTutors() {
        mDb.delete(TABLE_NAME, null, null);
    }

    // inner helper class
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
