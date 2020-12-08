package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    TelephonyManager telephonyManager;

    StringBuilder wifiScanStringBuilder;

    FloatingActionButton wifiScanButton;
    FloatingActionButton telephonyScanButton;

    TextView wifiScanText;
    TextView telephonyScanText;

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

        wifiManager =  (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiScanStringBuilder = new StringBuilder();

        wifiScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ScanResult> scanResultList = wifiManager.getScanResults();
                for(ScanResult scanResult : scanResultList){
                    String wifiScanString = String.format("Scan result:\nSSID - %s;\nRSSi - %d;\n", scanResult.SSID, scanResult.level);
                    wifiScanStringBuilder.append(wifiScanString);
                }
                wifiScanText.setText(wifiScanStringBuilder.toString());
            }
        });

        telephonyScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Scan telephony
            }
        });
    }
}