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

    DatabaseHelper openHelper;
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
                String email = _email.getText().toString();
                String pass = _pass.getText().toString();
                String cpass = _cpass.getText().toString();
                String name = _name.getText().toString();
                String phone = _phone.getText().toString();

                //create a new contact
                Contact c = new Contact();
                c.setEmail(email);
                c.setPass(pass);
                c.setCpass(cpass);
                c.setName(name);
                c.setPhone(phone);

                openHelper.insertContact(c);

                Toast.makeText(getApplicationContext(), "Signed up successfully", Toast.LENGTH_LONG).show();

            }
        });

    }

}
