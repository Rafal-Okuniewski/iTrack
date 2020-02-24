package com.ro.itrack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.kontakt.sdk.android.common.profile.RemoteBluetoothDevice;
import com.ro.itrack.services.BackgroundScanService;
import com.ro.itrack.services.ForegroundScanService;

public class BeaconForegroundScanActivity extends AppCompatActivity implements View.OnClickListener {

    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, BeaconForegroundScanActivity.class);
    }

    private Intent serviceIntent;

    private TextView statusText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_scan);

        statusText = findViewById(R.id.status_text);

        serviceIntent = ForegroundScanService.createIntent(this);

        setupToolbar();

        setupButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ForegroundScanService.ACTION_DEVICE_DISCOVERED);
        registerReceiver(scanningBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(scanningBroadcastReceiver);
        super.onPause();
    }

    private void setupToolbar() {
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupButtons() {
        final Button startScanButton = findViewById(R.id.start_scan_button);
        final Button stopScanButton = findViewById(R.id.stop_scan_button);
        startScanButton.setOnClickListener(this);
        stopScanButton.setOnClickListener(this);
    }

    private void startBackgroundService() {
        startService(serviceIntent);
    }

    private void stopBackgroundService() {
        stopService(serviceIntent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_scan_button:
                startBackgroundService();
                break;
            case R.id.stop_scan_button:
                stopBackgroundService();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private final BroadcastReceiver scanningBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int devicesCount = intent.getIntExtra(BackgroundScanService.EXTRA_DEVICES_COUNT, 0);
            RemoteBluetoothDevice device = intent.getParcelableExtra(BackgroundScanService.EXTRA_DEVICE);
            statusText.setText(String.format("Total discovered devices: %d\n\nLast scanned device:\n%s", devicesCount, device.toString()));
        }
    };

}
