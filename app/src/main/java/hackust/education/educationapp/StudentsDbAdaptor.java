package hackust.education.educationapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StudentsDbAdaptor {
    //these are the column names
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_SUBJECT = "subject";
    public static final String COL_RATING = "rating";

    //these are the corresponding indices
    public static final int INDEX_ID = 0;
    public static final int INDEX_NAME = INDEX_ID + 1;
    public static final int INDEX_SUBJECT = INDEX_ID + 2;
    public static final int INDEX_RATING = INDEX_ID + 3;

    //used for logging
    private static final String TAG = "StudentsDbAdapter";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "dba_students";
    private static final String TABLE_NAME = "tbl_students";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;

    //SQL statement used to create the database
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    COL_NAME + " TEXT, " +
                    COL_SUBJECT + " TEXT, " +
                    COL_RATING + " TEXT );";


    StudentsDbAdaptor(Context ctx) {
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
    public void createStudent(String name, String subject, String rating) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_SUBJECT, subject);
        values.put(COL_RATING, rating);
        mDb.insert(TABLE_NAME, null, values);
    }
    //overloaded to take a student
    public long createStudent(Student student) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, student.getName());
        values.put(COL_SUBJECT, student.getSubject());
        values.put(COL_RATING, student.getRating());
        // Inserting Row
        return mDb.insert(TABLE_NAME, null, values);
    }

    //READ
    public Student fetchStudentById(int id) {
        @SuppressLint("Recycle") Cursor cursor = mDb.query(TABLE_NAME, new String[]{COL_ID,
                        COL_NAME, COL_SUBJECT, COL_RATING}, COL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null
        );
        if (cursor != null)
            cursor.moveToFirst();
        assert cursor != null;
        return new Student(
                cursor.getInt(INDEX_ID),
                cursor.getString(INDEX_NAME),
                cursor.getString(INDEX_SUBJECT),
                cursor.getString(INDEX_RATING)
        );
    }

    public Cursor fetchAllStudents() {
        Cursor mCursor = mDb.query(TABLE_NAME, new String[]{COL_ID,
                        COL_NAME, COL_SUBJECT, COL_RATING},
                null, null, null, null, null
        );
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //UPDATE
    public void updateStudent(Student student) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, student.getName());
        values.put(COL_SUBJECT, student.getSubject());
        values.put(COL_RATING, student.getRating());
        mDb.update(TABLE_NAME, values,
                COL_ID + "=?", new String[]{String.valueOf(student.getId())});
    }

    //DELETE
    public void deleteStudentById(int nId) {
        mDb.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(nId)});
    }
    public void deleteAllStudents() {
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
