package com.example.prototype;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Instant;
import java.util.List;
import java.util.Locale;

//TODO registerScanResultsCallback

public class MainActivity extends AppCompatActivity {
    private static final String TAG="WiFiDemo";

    WifiManager wifiManager;
    TelephonyManager telephonyManager;

    StringBuilder wifiScanStringBuilder;

    FloatingActionButton wifiScanButton;
    FloatingActionButton telephonyScanButton;

    TextView scanText;
    TextView telephonyScanText;

    String phoneTypeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiScanButton = (FloatingActionButton) findViewById(R.id.wifiScanButton);
        telephonyScanButton = (FloatingActionButton) findViewById(R.id.telephonyScanButton);
        scanText = (TextView) findViewById(R.id.scanText);

        scanText.setText(R.string.wifiScanTextDefaultValue);

        //Permissions
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
//                || (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE)
//                != PackageManager.PERMISSION_GRANTED)
        ) {
            Log.d(TAG, "Requesting permissions");

            //Request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.CHANGE_WIFI_STATE
                            },
                    123);
        }
        else
            Log.d(TAG, "Permissions already granted");


        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onReceive(Context c, Intent intent) {
                Log.d("BroadcastReceiver","Started");

                boolean success = intent.getBooleanExtra(
                        WifiManager.EXTRA_RESULTS_UPDATED, false);
                if (success) {
                    Log.d("BroadcastReceiver","Scan success");
                    String timestamp = String.valueOf(Instant.now().getEpochSecond());
                    List<ScanResult> scanResultList = wifiManager.getScanResults();
                    wifiScanStringBuilder.append("Timestamp: ").append(timestamp).append("\n");
                    wifiScanStringBuilder.append("Scan result:\n");
                    for (ScanResult scanResult : scanResultList) {
                        String wifiScanString = String.format(Locale.US,
                                "SSID - %s;\nRSSi - %d dBm;\n", scanResult.SSID, scanResult.level);
                        wifiScanStringBuilder.append(wifiScanString);
                    }
                    scanText.setText(wifiScanStringBuilder.toString());
                } else {
                    // scan failure handling
                    Log.d("BroadcastReceiver","Scan failed");
//                scanFailure();
                }
                Log.d("BroadcastReceiver","Finished");
            }
        };

        IntentFilter intentFilter  = new IntentFilter();
        intentFilter .addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        getApplicationContext().registerReceiver(wifiScanReceiver,  intentFilter);

        wifiScanStringBuilder = new StringBuilder();

        telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(TELEPHONY_SERVICE);

        wifiScanButton.setOnClickListener(v -> {
            boolean success = wifiManager.startScan();
            if (!success) {
                // scan failure handling
                Log.d("MainActivity","Couldn't initiate wifi scan");
//                    scanFailure();
            }
        });

        telephonyScanButton.setOnClickListener(v -> {
        });
    }
}

//
//        int phoneType = telephonyManager.getPhoneType();
//        switch (phoneType) {
//            case (TelephonyManager.PHONE_TYPE_CDMA):
//                phoneTypeString = "CDMA";
//                break;
//            case (TelephonyManager.PHONE_TYPE_GSM):
//                phoneTypeString = "GSM";
//                break;
//            case (TelephonyManager.PHONE_TYPE_NONE):
//                phoneTypeString = "NONE";
//                break;
//            case (TelephonyManager.PHONE_TYPE_SIP):
//                phoneTypeString = "SIP";
//                break;
//        }
//
//        boolean isRoaming = telephonyManager.isNetworkRoaming();
//        String IMEINumber = telephonyManager.getDeviceId();
//        String subscriberID = telephonyManager.getSimSerialNumber();
//        String networkCountryISO = telephonyManager.getNetworkCountryIso();
//        String softwareVersion = telephonyManager.getDeviceSoftwareVersion();
//        String voiceMailNumber = telephonyManager. getVoiceMailNumber();