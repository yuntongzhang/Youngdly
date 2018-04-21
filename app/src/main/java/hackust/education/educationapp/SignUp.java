package hackust.education.educationapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Button _bcontinue;
    EditText _email, _pass, _cpass, _name, _phone;


    Button b1, b2;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        openHelper=new DatabaseHelper(this);

        _email= (EditText) findViewById(R.id.email);

        _pass= (EditText) findViewById(R.id.pass);
        _cpass=(EditText)findViewById(R.id.cpass);
        _name=(EditText)findViewById(R.id.name);
        _phone=(EditText)findViewById(R.id.phone);
        b1=(Button)findViewById(R.id.bcontinue);
        b2=(Button)findViewById(R.id.bback);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openHelper.getWritableDatabase();
                String email = _email.getText().toString();
                String pass = _pass.getText().toString();
                String cpass = _cpass.getText().toString();
                String name = _name.getText().toString();
                String phone = _phone.getText().toString();

                insertdata(email, pass, cpass, name, phone);
                Toast.makeText(getApplicationContext(), "Signed up successfully", Toast.LENGTH_LONG).show();

            }
        });

    }
    public void insertdata (String email, String pass, String cpass, String name, String phone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_2, pass);
        contentValues.put(DatabaseHelper.COL_3, cpass);
        contentValues.put(DatabaseHelper.COL_4, name);
        contentValues.put(DatabaseHelper.COL_5, phone);
        long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);


    }

    // inner helper class
    public class DatabaseHelper extends SQLiteOpenHelper {
        static final String DATABASE_NAME="register.db";
        static final String TABLE_NAME="register";
        public static final String COL_1="email";
        static final String COL_2="pass";
        static final String COL_3="cpass";
        static final String COL_4="name";
        static final String COL_5="phone";

        DatabaseHelper(Context context){
            super (context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, pass TEXT, pass2 TEXT, name TEXT, phone TEXT)");


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME); // Drop older table if exists
            onCreate(db);

        }
    }

}
