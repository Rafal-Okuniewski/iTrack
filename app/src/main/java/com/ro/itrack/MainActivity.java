package com.ro.itrack;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_PERMISSIONS = 100;

    private LinearLayout buttonsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupButtons();
        checkPermissions();
    }

    private void setupButtons() {
        buttonsLayout = findViewById(R.id.buttons_layout);

        final Button beaconsScanningButton = findViewById(R.id.button_scan_beacons);
        final Button beaconsProScanningButton = findViewById(R.id.button_scan_beacons_pro);
        final Button foregroundScanButton = findViewById(R.id.button_scan_foreground);

        beaconsScanningButton.setOnClickListener(this);
        beaconsProScanningButton.setOnClickListener(this);
        foregroundScanButton.setOnClickListener(this);
    }

    private void checkPermissions() {
        int checkSelfPermissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (PackageManager.PERMISSION_GRANTED != checkSelfPermissionResult) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (REQUEST_CODE_PERMISSIONS == requestCode) {
                Toast.makeText(this, getResources().getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
            }
        } else {
            disableButtons();
            Toast.makeText(this, getResources().getString(R.string.permission_needed), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_scan_beacons:
                startActivity(BeaconScanActivity.createIntent(this));
                break;
            case R.id.button_scan_beacons_pro:
                startActivity(BeaconProScanActivity.createIntent(this));
                break;
            case R.id.button_scan_foreground:
                startActivity(BeaconForegroundScanActivity.createIntent(this));
                break;
        }
    }

    private void disableButtons() {
        for (int i = 0; i < buttonsLayout.getChildCount(); i++) {
            buttonsLayout.getChildAt(i).setEnabled(false);
        }
    }

}