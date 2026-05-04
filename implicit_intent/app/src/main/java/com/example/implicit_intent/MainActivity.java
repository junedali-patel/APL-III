package com.example.implicit_intent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etPhoneNumber, etMessage, etWebUrl;
    private Button btnCall, btnMessage, btnNetworkDetails, btnShowListView;

    private static final int REQUEST_CALL_PERMISSION = 1;
    private static final int REQUEST_PHONE_STATE_PERMISSION = 2;
    private String pendingPhoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        initializeViews();

        // Set click listeners
        setupClickListeners();
    }

    /**
     * Initialize all UI components
     */
    private void initializeViews() {
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etMessage = findViewById(R.id.etMessage);
        etWebUrl = findViewById(R.id.etWebUrl);

        btnCall = findViewById(R.id.btnCall);
        btnMessage = findViewById(R.id.btnMessage);
        btnNetworkDetails = findViewById(R.id.btnNetworkDetails);
        btnShowListView = findViewById(R.id.btnShowListView);
    }

    /**
     * Setup click listeners for all buttons
     */
    private void setupClickListeners() {
        // Q1: Implicit Intent to Call Phone Number
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = etPhoneNumber.getText().toString().trim();

                if (phoneNumber.isEmpty()) {
                    showToast("Please enter a phone number");
                    return;
                }

                makePhoneCall(phoneNumber);
            }
        });

        // Q2: Implicit Intent to Send SMS Message
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = etPhoneNumber.getText().toString().trim();
                String message = etMessage.getText().toString().trim();

                if (phoneNumber.isEmpty()) {
                    showToast("Please enter a phone number");
                    return;
                }

                sendSmsMessage(phoneNumber, message);
            }
        });

        // Q3: Implicit Intent to View Network Details
        btnNetworkDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNetworkSettings();
            }
        });

        // Q4: Explicit Intent to Show Phone Details in ListView
        btnShowListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = etPhoneNumber.getText().toString().trim();

                if (phoneNumber.isEmpty()) {
                    showToast("Please enter a phone number");
                    return;
                }

                showPhoneDetailsInListView(phoneNumber);
            }
        });
    }

    /**
     * Q1: Make phone call using implicit intent with ACTION_CALL
     * Requires CALL_PHONE permission (runtime permission for API 23+)
     */
    private void makePhoneCall(String phoneNumber) {
        pendingPhoneNumber = phoneNumber;

        // Check if CALL_PHONE permission is granted
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PERMISSION);
        } else {
            // Permission already granted, proceed with call
            startCallIntent(phoneNumber);
        }
    }

    /**
     * Helper method to start the call intent
     */
    private void startCallIntent(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));

        // Verify that there's an app to handle this intent
        if (callIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(callIntent);
        } else {
            showToast("No app available to make phone calls");
        }
    }

    /**
     * Q2: Send SMS message using implicit intent with ACTION_SENDTO
     * This doesn't require runtime permission, just opens SMS app
     */
    private void sendSmsMessage(String phoneNumber, String message) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse("smsto:" + phoneNumber));
        smsIntent.putExtra("sms_body", message);

        // Verify that there's an app to handle this intent
        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(smsIntent);
        } else {
            showToast("No messaging app found");
        }
    }

    /**
     * Q3: Open network settings using implicit intent
     * Shows network details of the phone
     */
    private void openNetworkSettings() {
        // Create an implicit intent to open network settings
        Intent networkIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);

        // Alternative options for different network settings:
        // Settings.ACTION_WIFI_SETTINGS - WiFi settings only
        // Settings.ACTION_DATA_ROAMING_SETTINGS - Data roaming
        // Settings.ACTION_NETWORK_OPERATOR_SETTINGS - Network operator

        if (networkIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(networkIntent);
        } else {
            showToast("Cannot open network settings");
        }
    }

    /**
     * Q4: Show phone details using explicit intent with ListView
     * Passes phone number and message to ListViewActivity
     */
    private void showPhoneDetailsInListView(String phoneNumber) {
        Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
        intent.putExtra("PHONE_NUMBER", phoneNumber);
        intent.putExtra("MESSAGE", etMessage.getText().toString().trim());
        intent.putExtra("URL", etWebUrl.getText().toString().trim());
        startActivity(intent);
    }

    /**
     * Handle permission request results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, make the call
                if (!pendingPhoneNumber.isEmpty()) {
                    startCallIntent(pendingPhoneNumber);
                }
            } else {
                showToast("Permission denied to make phone calls");
            }
        }
    }

    /**
     * Helper method to display toast messages
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
