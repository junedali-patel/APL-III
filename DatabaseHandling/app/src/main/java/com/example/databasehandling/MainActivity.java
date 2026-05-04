package com.example.databasehandling;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText courseNameEdt, courseTracksEdt, courseDurationEdt, courseDescriptionEdt;
    private Button addCourseBtn;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        courseNameEdt = findViewById(R.id.idEdtCourseName);
        courseTracksEdt = findViewById(R.id.idEdtCourseTracks);
        courseDurationEdt = findViewById(R.id.idEdtCourseDuration);
        courseDescriptionEdt = findViewById(R.id.idEdtCourseDescription);
        addCourseBtn = findViewById(R.id.idBtnAddCourse);

        // ✅ FIXED LINE
        dbHandler = new DBHandler(getApplicationContext());

        addCourseBtn.setOnClickListener(v -> {

            String courseName = courseNameEdt.getText().toString().trim();
            String courseTracks = courseTracksEdt.getText().toString().trim();
            String courseDuration = courseDurationEdt.getText().toString().trim();
            String courseDescription = courseDescriptionEdt.getText().toString().trim();

            if (courseName.isEmpty() || courseTracks.isEmpty() ||
                    courseDuration.isEmpty() || courseDescription.isEmpty()) {

                Toast.makeText(MainActivity.this,
                        "Please enter all the data",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            dbHandler.addNewCourse(courseName, courseDuration, courseDescription, courseTracks);

            Toast.makeText(MainActivity.this,
                    "Course has been added",
                    Toast.LENGTH_SHORT).show();

            courseNameEdt.setText("");
            courseTracksEdt.setText("");
            courseDurationEdt.setText("");
            courseDescriptionEdt.setText("");
        });
    }
}