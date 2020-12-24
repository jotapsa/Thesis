package com.example.myapplication.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.Objects;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {
    private static final String TAG = "InfoFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
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
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView phoneInfoText = view.findViewById(R.id.phone_info_text);
        Button phoneInfoButton = view.findViewById(R.id.phone_scan_button);

        StringBuilder telephonyScanStringBuilder = new StringBuilder();

        phoneInfoButton.setOnClickListener(v -> {
            telephonyScanStringBuilder.delete(0, Math.max(telephonyScanStringBuilder.length()-1, 0));
            telephonyScanStringBuilder.append("Phone details:\n");

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(TELEPHONY_SERVICE);

                int phoneType = telephonyManager.getPhoneType();
                String phoneTypeString = "";
                switch (phoneType){
                    case TelephonyManager.PHONE_TYPE_CDMA:
                        phoneTypeString = "CDMA";
                        break;
                    case TelephonyManager.PHONE_TYPE_GSM:
                        phoneTypeString = "GSM";
                        break;
                    case TelephonyManager.PHONE_TYPE_SIP:
                        phoneTypeString = "SIP";
                        break;
                    case TelephonyManager.PHONE_TYPE_NONE:
                        phoneTypeString = "NONE";
                        break;
                    default:
                        break;
                }

                boolean isRoaming = telephonyManager.isNetworkRoaming();

                String deviceId = "";
                String simSerialNumber = "";
                String simCountryIso = telephonyManager.getSimCountryIso();
                String networkCountryIso = telephonyManager.getNetworkCountryIso();
                String voiceMailNumber = telephonyManager.getVoiceMailNumber();
                String simOperatorName = telephonyManager.getSimOperatorName();
                String deviceSoftwareVersion = telephonyManager.getDeviceSoftwareVersion();

                telephonyScanStringBuilder.append("Phone Type: ").append(phoneTypeString).append("\n");
                telephonyScanStringBuilder.append("Roaming: ").append(isRoaming).append("\n");
                telephonyScanStringBuilder.append("Device EMEI: ").append(deviceId).append("\n");
                telephonyScanStringBuilder.append("SIM Serial Number: ").append(simSerialNumber).append("\n");
                telephonyScanStringBuilder.append("SIM Country ISO: ").append(simCountryIso).append("\n");
                telephonyScanStringBuilder.append("Network Country ISO: ").append(networkCountryIso).append("\n");
                telephonyScanStringBuilder.append("VoiceMail Number: ").append(voiceMailNumber).append("\n");
                telephonyScanStringBuilder.append("Sim Operator Name: ").append(simOperatorName).append("\n");
                telephonyScanStringBuilder.append("SoftwareVersion: ").append(deviceSoftwareVersion).append("\n");

            }

            phoneInfoText.setText(telephonyScanStringBuilder.toString());
        });
    }
}