package com.example.prototype;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.AccessNetworkConstants;
import android.telephony.NetworkScanRequest;
import android.telephony.RadioAccessSpecifier;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.telephony.NetworkScanRequest.SCAN_TYPE_ONE_SHOT;

public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    TelephonyManager telephonyManager;

    StringBuilder wifiScanStringBuilder;

    FloatingActionButton wifiScanButton;
    FloatingActionButton telephonyScanButton;

    TextView wifiScanText;
    TextView telephonyScanText;

    String phoneTypeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiScanButton = (FloatingActionButton) findViewById(R.id.wifiScanButton);
        telephonyScanButton = (FloatingActionButton) findViewById(R.id.telephonyScanButton);
        wifiScanText = (TextView) findViewById(R.id.wifiScanText);
        telephonyScanText = (TextView) findViewById(R.id.telephonyScanText);

        wifiScanText.setText(R.string.wifiScanTextDefaultValue);
        telephonyScanText.setText(R.string.telephonyScanTextDefaultValue);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiScanStringBuilder = new StringBuilder();

        telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(TELEPHONY_SERVICE);
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
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        String softwareVersion = telephonyManager.getDeviceSoftwareVersion();
//        String voiceMailNumber = telephonyManager. getVoiceMailNumber();

        wifiScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ScanResult> scanResultList = wifiManager.getScanResults();
                for (ScanResult scanResult : scanResultList) {
                    String wifiScanString = String.format("Scan result:\nSSID - %s;\nRSSi - %d;\n", scanResult.SSID, scanResult.level);
                    wifiScanStringBuilder.append(wifiScanString);
                }
                wifiScanText.setText(wifiScanStringBuilder.toString());
            }
        });

        telephonyScanButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {

                RadioAccessSpecifier radioAccessSpecifiers[];
                int bands[];
                NetworkScanRequest networkScanRequest;

                ArrayList<String> PLMNIds = new ArrayList<String>(Arrays.asList("42501"));

                bands[0] = AccessNetworkConstants.UtranBand.BAND_1;
                radioAccessSpecifiers = new RadioAccessSpecifier[1];
                radioAccessSpecifiers[0] = new RadioAccessSpecifier(
                        AccessNetworkConstants.AccessNetworkType.UTRAN,
                        bands,
                        null);

                /*
                 NOTE:searchPeriodicity & incrementalResultsPeriodicity cannot be 0,
                 despite the documentation, and irrelevance of these parameters for one shot scan.
                */

                networkScanRequest = new NetworkScanRequest(
                        NetworkScanRequest.SCAN_TYPE_ONE_SHOT,
                        radioAccessSpecifiers,
                        1,
                        60,
                        false,
                        1,
                        PLMNIds);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                //telephonyManager.requestNetworkScan(networkScanRequest, AsyncTask.SERIAL_EXECUTOR, new RadioCallback());
            }
        });
    }
}