package com.sw.bluetoothconnectorsample2;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ListView;

import java.util.HashMap;

public class MainActivity extends Activity implements android.bluetooth.BluetoothAdapter.LeScanCallback {

    private final String TAG = "BTConnectorSample";
    private static final int PERMISSION_REQUEST_TAG = 1;
    private boolean isLocationServiceOn = false, isBluetoothServiceOn = false, isBluetoothAdminServiceOn = false;
    private final String[] permissionRequired = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN};
    private ListView lv;
    private CustomAdapter mAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private HashMap<String, Beacon> map;
    private android.os.Handler mHandler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = new HashMap<>();
        lv = (ListView) findViewById(R.id.lv);
        mAdapter = new CustomAdapter(this, null);
        lv.setAdapter(mAdapter);
        checkPermission();
    }

    @Override
    protected void onDestroy() {
        stopScanMethod();
        map = new HashMap<>();
        super.onDestroy();
    }

    private Runnable r = new Runnable(){
        @Override
        public void run() {
            mAdapter.setData(map.values());
            mAdapter.notifyDataSetChanged();
        }
    };

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissionRequired)
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{
                                    permission
                            },
                            PERMISSION_REQUEST_TAG);
                else {
                    switch (permission) {
                        case Manifest.permission.ACCESS_COARSE_LOCATION:
                            isLocationServiceOn = true;
                            break;
                        case Manifest.permission.BLUETOOTH:
                            isBluetoothServiceOn = true;
                            break;
                        case Manifest.permission.BLUETOOTH_ADMIN:
                            isBluetoothAdminServiceOn = true;
                            break;
                        default:
                    }
                }
        } else {
            isLocationServiceOn = true;
            isBluetoothServiceOn = true;
            isBluetoothAdminServiceOn = true;
        }
        if (isLocationServiceOn && isBluetoothServiceOn && isBluetoothAdminServiceOn)
            initScanMethod();
    }

    private void initScanMethod() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startLeScan(this);
    }

    private void stopScanMethod() {
        if (mBluetoothAdapter != null)
            mBluetoothAdapter.stopLeScan(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_TAG: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0)
                    break;
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                        switch (permissions[i]) {
                            case Manifest.permission.ACCESS_COARSE_LOCATION:
                                isLocationServiceOn = true;
                                break;
                            case Manifest.permission.BLUETOOTH:
                                isBluetoothServiceOn = true;
                                break;
                            case Manifest.permission.BLUETOOTH_ADMIN:
                                isBluetoothAdminServiceOn = true;
                                break;
                            default:
                        }
                }
                if (isLocationServiceOn && isBluetoothServiceOn && isBluetoothAdminServiceOn)
                    initScanMethod();
                break;
            }
        }
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        Beacon beacon = new Beacon(scanRecord, rssi);
        map.put(beacon.id1+"_"+beacon.id2+"_"+beacon.id3, beacon);
        
    }
}