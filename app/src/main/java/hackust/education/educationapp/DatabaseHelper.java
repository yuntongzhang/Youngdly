package hackust.education.educationapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final int DATABASE_VERSION =1;
    static final String DATABASE_NAME="register.db";
    static final String TABLE_NAME="register";
    static final String COL_1="email";
    static final String COL_2="pass";
    static final String COL_3="cpass"; // this may be used to hold the id of the contact
    static final String COL_4="name";
    static final String COL_5="phone";
    SQLiteDatabase db;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertContact(Contact c) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, c.getEmail());
        contentValues.put(COL_2, c.getPass());
        contentValues.put(COL_3, c.getCpass());
        contentValues.put(COL_4, c.getName());
        contentValues.put(COL_5, c.getPhone());
        db.insert(TABLE_NAME, null, contentValues);
        db.close();

    }

    public String searchPass(String email) {
        db = this.getReadableDatabase();
        String query = "select email, pass from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);

                if (a.equals(email)) {
                    b = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }

        return b;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, pass TEXT, pass2 TEXT, name TEXT, phone TEXT)");
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME); // Drop older table if exists
        onCreate(db);
    }
}