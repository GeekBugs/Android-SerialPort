package com.android.serialport.entity;

import android.util.Log;
import java.io.File;
import java.util.ArrayList;

/**
 * @author F1ReKing
 * @date 2019/10/31 18:45
 * @Description
 */
public class Driver {

    private static final String TAG = Driver.class.getSimpleName();

    private String driverName;
    private String deviceRoot;

    public Driver(String driverName, String deviceRoot) {
        this.driverName = driverName;
        this.deviceRoot = deviceRoot;
    }

    public ArrayList<File> getDevices() {
        ArrayList<File> devices = new ArrayList<>();
        File dev = new File("/dev");

        if (!dev.exists()) {
            Log.i(TAG, "getDevices: " + dev.getAbsolutePath() + " no found");
            return devices;
        }
        if (!dev.canRead()) {
            Log.i(TAG, "getDevices: " + dev.getAbsolutePath() + " no read permissions");
            return devices;
        }

        File[] files = dev.listFiles();

        int i;
        for (i = 0; i < files.length; i++) {
            if (files[i].getAbsolutePath().startsWith(deviceRoot)) {
                Log.d(TAG, "Found new device: " + files[i]);
                devices.add(files[i]);
            }
        }
        return devices;
    }

    public String getName() {
        return driverName;
    }
}
