package com.example.myapplication.Fragments;

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

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.telephony.CellIdentityLte;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.CellSignalStrengthLte;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.TELEPHONY_SERVICE;
import static android.content.Context.WIFI_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanFragment extends Fragment {
    private static final String TAG = "ScanFragment";

    static final int PCI_UNAVAILABLE = 2147483647;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanFragment newInstance(String param1, String param2) {
        ScanFragment fragment = new ScanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button wifiScanButton = view.findViewById(R.id.wifi_scan_button);
        Button telephonyScanButton = view.findViewById(R.id.telephony_scan_button);

        TextView wifiScanText = view.findViewById(R.id.wifi_scan_text);
        TextView telephonyScanText = view.findViewById(R.id.telephony_scan_text);

        WifiManager wifiManager = (WifiManager) Objects.requireNonNull(getContext()).getSystemService(WIFI_SERVICE);
        StringBuilder wifiScanStringBuilder = new StringBuilder();

        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(TELEPHONY_SERVICE);
        StringBuilder telephonyScanStringBuilder = new StringBuilder();

        wifiScanButton.setOnClickListener(v -> {
            boolean success = wifiManager.startScan();
            if (!success) {
                // scan failure handling
                Log.d(TAG, "Couldn't initiate wifi scan");
//                    scanFailure();
            }
        });

        telephonyScanButton.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                List<CellInfo> allCellInfo = telephonyManager.getAllCellInfo();
                telephonyScanStringBuilder.append("Scan result:\n");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    String timestamp = String.valueOf(Instant.now().getEpochSecond());
                    telephonyScanStringBuilder.append("Timestamp:").append(timestamp).append("\n");
                }
                if (allCellInfo != null) {
                    for (CellInfo cellInfo : allCellInfo) {
                        if (cellInfo instanceof CellInfoLte) {
                            CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                            CellSignalStrengthLte signalStrengthLte = cellInfoLte.getCellSignalStrength();

//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                                telephonyScanStringBuilder.append("RSSi:").append(signalStrengthLte.getRssi()).append(" dBm\n");
//                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                telephonyScanStringBuilder.append("RSRP:").append(signalStrengthLte.getRsrp()).append(" dBm\n");
                                telephonyScanStringBuilder.append("RSRQ:").append(signalStrengthLte.getRsrq()).append(" dBm\n");
                            } else {
                                telephonyScanStringBuilder.append("dBm:").append(signalStrengthLte.getDbm()).append(" dBm\n");
                            }

                            CellIdentityLte identityLte = cellInfoLte.getCellIdentity();
                            if (identityLte != null) {
                                int pci = identityLte.getPci();
                                if (pci == PCI_UNAVAILABLE) {
                                    pci = -1;
                                }
                                telephonyScanStringBuilder.append("PCI:").append(pci).append("\n");
                            }
                        }
                    }
                }
                telephonyScanText.setText(telephonyScanStringBuilder.toString());

            }

        });

        BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onReceive(Context c, Intent intent) {
                Log.d("BroadcastReceiver","Started");

                boolean success = intent.getBooleanExtra(
                        WifiManager.EXTRA_RESULTS_UPDATED, false);
                if (success) {
                    Log.d("BroadcastReceiver","Scan success");
                    List<ScanResult> scanResultList = wifiManager.getScanResults();
                    wifiScanStringBuilder.append("Scan result:\n");
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        String timestamp = String.valueOf(Instant.now().getEpochSecond());
                        wifiScanStringBuilder.append("Timestamp:").append(timestamp).append("\n");
                    }
                    if (scanResultList != null) {
                        for (ScanResult scanResult : scanResultList) {
                            String wifiScanString = String.format(Locale.US,
                                    "SSID - %s;\nRSSi - %d dBm;\n", scanResult.SSID, scanResult.level);
                            wifiScanStringBuilder.append(wifiScanString);
                        }
                    }

                    wifiScanText.setText(wifiScanStringBuilder.toString());
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
        getContext().registerReceiver(wifiScanReceiver,  intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}