package com.example.multithreading;



import     android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView statusText;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);
        btnStart = findViewById(R.id.btnStartThread);

        // 1. Create an Executor (Pool of background threads)
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // 2. Create a Handler (To post results back to the Main/UI Thread)
        Handler handler = new Handler(Looper.getMainLooper());

        btnStart.setOnClickListener(v -> {
            statusText.setText("Task Started...");
            btnStart.setEnabled(false);

            // Execute the background task
            executor.execute(() -> {
                // --- BACKGROUND THREAD STARTS HERE ---
                try {
                    // Simulate a heavy task (like downloading a file)
                    for (int i = 1; i <= 5; i++) {
                        Thread.sleep(1000); // Wait 1 second
                        int progress = i * 20;

                        // Update UI from background using handler
                        handler.post(() -> statusText.setText("Progress: " + progress + "%"));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // --- TASK FINISHED ---
                handler.post(() -> {
                    statusText.setText("Task Completed!");
                    btnStart.setEnabled(true);
                });
            });
        });
    }
}