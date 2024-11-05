package com.example.coen391app;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class WifiSetup extends DialogFragment {
    private TextView instructions;
    private EditText wifi_ssid, wifi_password;
    private Button wifi_btn, return_btn, connect_btn;
    private String current_ssid;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wifi_setup, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        current_ssid = getSSID();

        if(current_ssid.equals("ESP32_AP"))
            step2();
        else
            step1();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        instructions = view.findViewById(R.id.wifi_instructions);
        wifi_btn = view.findViewById(R.id.open_wifi_settings_button);
        wifi_ssid = view.findViewById(R.id.ssid_input);
        wifi_password = view.findViewById(R.id.password_input);
        return_btn = view.findViewById(R.id.return_button);
        connect_btn = view.findViewById(R.id.connect_button);


        wifi_btn.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivity(intent);
        });

        connect_btn.setOnClickListener(v -> {
            String ssid = wifi_ssid.getText().toString().trim();
            String password = wifi_password.getText().toString().trim();

            if (!ssid.isEmpty() && !password.isEmpty()) {

                Log.d("WifiSetup", "Sending WiFi Config - SSID: " + ssid + ", Password: " + password);
                sendWifiConfig(ssid, password);

                dismiss();
            } else {
                Toast.makeText(getContext(), "SSID and password cannot be empty", Toast.LENGTH_SHORT).show();
            }

        });

        return_btn.setOnClickListener(v -> dismiss());

    }

    private String getSSID() {

        WifiManager wifiManager = (WifiManager) this.requireContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String ssid = wifiManager.getConnectionInfo().getSSID();

        // Remove quotes from getSSID
        if (ssid != null && ssid.startsWith("\"") && ssid.endsWith("\"")) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }

        return ssid;
    }

    private void step1() {
        instructions.setText(R.string.connection_instructions1);
        wifi_btn.setVisibility(View.VISIBLE);
        wifi_ssid.setEnabled(false);
        wifi_password.setEnabled(false);
        connect_btn.setVisibility(View.GONE);
    }

    private void step2() {
        instructions.setText(R.string.connection_instructions2);
        wifi_btn.setVisibility(View.GONE);
        wifi_ssid.setEnabled(true);
        wifi_password.setEnabled(true);
        connect_btn.setVisibility(View.VISIBLE);
    }


    private void sendWifiConfig(String ssid, String password) {

        // Construct JSON object
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ssid", ssid);
            jsonObject.put("password", password);
        } catch (Exception e) {
            if (getActivity() != null) { // Ensure the fragment is attached to an activity
                getActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Failed to connect to server:\n" + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }
            return;
        }

        // Convert JSONObject to String
        String jsonString = jsonObject.toString();

        Log.d("WifiSetup", "JSON to send: " + jsonString);

        // Create request body with JSON data
        RequestBody body = RequestBody.create(jsonString, MediaType.get("application/json; charset=utf-8"));

        // Create the POST request
        Request request = new Request.Builder()
                .url("http://192.168.4.1/setWiFi")
                .post(body)
                .build();

        Log.d("WifiSetup", "Request URL: " + request.url());

        // Execute the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("WifiSetup", "Network Request Failed: " + e.getMessage());

                if (getActivity() != null) { // Ensure the fragment is attached to an activity
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Failed to connect to server:", Toast.LENGTH_SHORT).show()
                    );
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

                Log.d("WifiSetup", "Response Code: " + response.code());


                if (getActivity() == null) return; // Exit if fragment is no longer attached

                getActivity().runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "WiFi configuration sent successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed to send configuration", Toast.LENGTH_SHORT).show();
                    }
                });

                // Close the response body to release the connection
                response.close();
            }
        });
    }

}