package com.example.implicit_intent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    private TextView tvTitle;
    private ListView listViewPhoneDetails;
    private ArrayList<String> phoneDetailsList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        // Initialize views
        initializeViews();

        // Get data from intent and populate ListView
        populateListView();
    }

    /**
     * Initialize all views
     */
    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        listViewPhoneDetails = findViewById(R.id.listViewPhoneDetails);
        phoneDetailsList = new ArrayList<>();
    }

    /**
     * Populate ListView with phone details from intent
     */
    private void populateListView() {
        Intent intent = getIntent();

        if (intent != null) {
            String phoneNumber = intent.getStringExtra("PHONE_NUMBER");
            String message = intent.getStringExtra("MESSAGE");
            String url = intent.getStringExtra("URL");

            // Add phone details to list
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                addPhoneDetailsToList(phoneNumber, message, url);
            }

            // Create adapter and set to ListView
            adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    phoneDetailsList
            );
            listViewPhoneDetails.setAdapter(adapter);
        }
    }

    /**
     * Add phone details to ArrayList
     */
    private void addPhoneDetailsToList(String phoneNumber, String message, String url) {
        // Clear previous data
        phoneDetailsList.clear();

        // Add header
        phoneDetailsList.add("═══════════════════════════");
        phoneDetailsList.add("📱 PHONE DETAILS");
        phoneDetailsList.add("═══════════════════════════");
        phoneDetailsList.add("");

        // Phone Number
        phoneDetailsList.add("📞 Phone Number:");
        phoneDetailsList.add("   " + phoneNumber);
        phoneDetailsList.add("");

        // Country Code
        String countryCode = extractCountryCode(phoneNumber);
        phoneDetailsList.add("🌍 Country Code:");
        phoneDetailsList.add("   " + countryCode);
        phoneDetailsList.add("");

        // Total Digits
        String cleanNumber = phoneNumber.replaceAll("[^0-9]", "");
        phoneDetailsList.add("🔢 Total Digits:");
        phoneDetailsList.add("   " + cleanNumber.length() + " digits");
        phoneDetailsList.add("");

        // Phone Number Type
        String numberType = determineNumberType(phoneNumber);
        phoneDetailsList.add("📋 Number Type:");
        phoneDetailsList.add("   " + numberType);
        phoneDetailsList.add("");

        // Message
        phoneDetailsList.add("💬 Message:");
        if (message != null && !message.isEmpty()) {
            phoneDetailsList.add("   " + message);
        } else {
            phoneDetailsList.add("   No message provided");
        }
        phoneDetailsList.add("");

        // URL
        phoneDetailsList.add("🌐 Website URL:");
        if (url != null && !url.isEmpty()) {
            phoneDetailsList.add("   " + url);
        } else {
            phoneDetailsList.add("   No URL provided");
        }
        phoneDetailsList.add("");

        // Device Information
        phoneDetailsList.add("═══════════════════════════");
        phoneDetailsList.add("📱 DEVICE INFORMATION");
        phoneDetailsList.add("═══════════════════════════");
        phoneDetailsList.add("");

        phoneDetailsList.add("📱 Device Model:");
        phoneDetailsList.add("   " + Build.MODEL);
        phoneDetailsList.add("");

        phoneDetailsList.add("🏭 Manufacturer:");
        phoneDetailsList.add("   " + Build.MANUFACTURER);
        phoneDetailsList.add("");

        phoneDetailsList.add("🤖 Android Version:");
        phoneDetailsList.add("   " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")");
        phoneDetailsList.add("");

        phoneDetailsList.add("🏷️ Device Brand:");
        phoneDetailsList.add("   " + Build.BRAND);
        phoneDetailsList.add("");

        // Additional Stats
        phoneDetailsList.add("═══════════════════════════");
        phoneDetailsList.add("📊 ADDITIONAL STATISTICS");
        phoneDetailsList.add("═══════════════════════════");
        phoneDetailsList.add("");

        phoneDetailsList.add("Total Characters in Number:");
        phoneDetailsList.add("   " + phoneNumber.length());
        phoneDetailsList.add("");

        phoneDetailsList.add("Has Special Characters:");
        phoneDetailsList.add("   " + (phoneNumber.matches(".*[+\\-()\\s].*") ? "Yes" : "No"));
        phoneDetailsList.add("");

        phoneDetailsList.add("═══════════════════════════");
    }

    /**
     * Extract country code from phone number
     */
    private String extractCountryCode(String phoneNumber) {
        if (phoneNumber.startsWith("+")) {
            int endIndex = 1;
            while (endIndex < phoneNumber.length() &&
                    endIndex <= 4 &&
                    Character.isDigit(phoneNumber.charAt(endIndex))) {
                endIndex++;
            }
            return phoneNumber.substring(0, endIndex);
        } else if (phoneNumber.startsWith("00")) {
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

    /**
     * Determine phone number type based on format
     */
    private String determineNumberType(String phoneNumber) {
        if (phoneNumber.startsWith("+")) {
            return "International Format";
        } else if (phoneNumber.startsWith("00")) {
            return "International (00 prefix)";
        } else if (phoneNumber.length() == 10) {
            return "National Format";
        } else {
            return "Custom Format";
        }
    }
}
