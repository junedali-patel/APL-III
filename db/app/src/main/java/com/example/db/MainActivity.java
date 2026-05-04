package com.example.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Button b1, b2;
    TextView t1;
    String str="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        t1=(TextView) findViewById(R.id.textView3);

        try
        {
            db = openOrCreateDatabase("StudentDB", SQLiteDatabase.CREATE_IF_NECESSARY,null);
            db.execSQL("Create Table Temp(id integer,name text)");
        }
        catch (SQLException e)
        {
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText eid = (EditText) findViewById(R.id.editText1);
                EditText ename = (EditText) findViewById(R.id.editText2);
                ContentValues values = new ContentValues();
                values.put("id", eid.getText().toString());
                values.put("name", ename.getText().toString());
                if ((db.insert("temp", null, values)) != -1) {
                    Toast.makeText(MainActivity.this, "Record Successfully Inserted", 2000).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Insert Error", 2000).show();
                }
            }});

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.rawQuery("SELECT * FROM Temp", null);
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    str = str + c.getString(0) + c.getString(1);
                    c.moveToNext();
                }
                t1.setText(str);
                c.close();
            }
        });

    }
}