package com.example.implicit_intent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewActivity extends AppCompatActivity {

    private TextView tvPhoneNumber, tvCountryCode, tvDigitCount, tvMessage, tvUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // Initialize TextViews
        initializeViews();

        // Get and display data from intent
        displayDataFromIntent();
    }

    /**
     * Initialize all TextViews
     */
    private void initializeViews() {
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvCountryCode = findViewById(R.id.tvCountryCode);
        tvDigitCount = findViewById(R.id.tvDigitCount);
        tvMessage = findViewById(R.id.tvMessage);
        tvUrl = findViewById(R.id.tvUrl);
    }

    /**
     * Retrieve intent extras and display phone details
     */
    private void displayDataFromIntent() {
        Intent intent = getIntent();

        if (intent != null) {
            String phoneNumber = intent.getStringExtra("PHONE_NUMBER");
            String message = intent.getStringExtra("MESSAGE");
            String url = intent.getStringExtra("URL");

            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                displayPhoneDetails(phoneNumber, message, url);
            }
        }
    }

    /**
     * Display all phone details in the UI
     */
    private void displayPhoneDetails(String phoneNumber, String message, String url) {
        // Display phone number
        tvPhoneNumber.setText(phoneNumber);

        // Extract and display country code
        String countryCode = extractCountryCode(phoneNumber);
        tvCountryCode.setText(countryCode);

        // Count and display total digits
        String cleanNumber = phoneNumber.replaceAll("[^0-9]", "");
        tvDigitCount.setText(cleanNumber.length() + " digits");

        // Display message if available
        if (message != null && !message.isEmpty()) {
            tvMessage.setText(message);
        } else {
            tvMessage.setText("No message provided");
        }

        // Display URL if available
        if (url != null && !url.isEmpty()) {
            tvUrl.setText(url);
        } else {
            tvUrl.setText("No URL provided");
        }
    }

    /**
     * Extract country code from phone number
     * Handles formats like +91, +1, 00XX, etc.
     */
    private String extractCountryCode(String phoneNumber) {
        if (phoneNumber.startsWith("+")) {
            // Extract up to 3 digits after +
            int endIndex = 1;
            while (endIndex < phoneNumber.length() &&
                    endIndex <= 4 &&
                    Character.isDigit(phoneNumber.charAt(endIndex))) {
                endIndex++;
            }
            return phoneNumber.substring(0, endIndex);
        } else if (phoneNumber.startsWith("00")) {
            // Some countries use 00 instead of +
            int endIndex = 2;
            while (endIndex < phoneNumber.length() &&
                    endIndex <= 5 &&
                    Character.isDigit(phoneNumber.charAt(endIndex))) {
                endIndex++;
            }
            return phoneNumber.substring(0, endIndex);
        }
        return "Not specified";
    }
}
