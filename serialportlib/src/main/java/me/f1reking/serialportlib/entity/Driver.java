/*
 * Copyright 2019 F1ReKing.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.f1reking.serialportlib.entity;

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
