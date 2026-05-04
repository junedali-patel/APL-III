package com.example.ratingbar;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RatingBar ratingBar;
    ProgressBar progressBar;
    TextView tvRating, tvProgress;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ratingBar = findViewById(R.id.ratingBar);
        progressBar = findViewById(R.id.progressBar);
        tvRating = findViewById(R.id.tvRating);
        tvProgress = findViewById(R.id.tvProgress);
        btnSubmit = findViewById(R.id.btnSubmit);

        // RatingBar Event
        ratingBar.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar,
                                                float rating,
                                                boolean fromUser) {
                        tvRating.setText("Rating: " + rating);
                    }
                });

        // Button Click Event
        btnSubmit.setOnClickListener(v -> {

            float rating = ratingBar.getRating();

            // Convert rating (0–5) to percentage (0–100)
            int progress = (int) ((rating / 5.0) * 100);

            progressBar.setProgress(progress);
            tvProgress.setText("Progress: " + progress + "%");

            Toast.makeText(MainActivity.this,
                    "Submitted Rating: " + rating,
                    Toast.LENGTH_SHORT).show();
        });
    }
}