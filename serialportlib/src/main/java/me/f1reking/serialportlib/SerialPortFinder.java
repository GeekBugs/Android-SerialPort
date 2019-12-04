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
package me.f1reking.serialportlib;

import android.util.Log;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import me.f1reking.serialportlib.entity.Device;
import me.f1reking.serialportlib.entity.Driver;

/**
 * @author F1ReKing
 * @date 2019/10/31 18:41
 * @Description
 */
public class SerialPortFinder {

    private static final String TAG = SerialPortFinder.class.getSimpleName();
    private static final String DRIVERS_PATH = "/proc/tty/drivers";
    private static final String SERIAL_FIELD = "serial";

    public SerialPortFinder() {
        File file = new File(DRIVERS_PATH);
        boolean b = file.canRead();
        Log.i(TAG, "SerialPortFinder: file.canRead() = " + b);
    }

    /**
     * get Drivers
     *
     * @return Drivers
     * @throws IOException IOException
     */
    private ArrayList<Driver> getDrivers() throws IOException {
        ArrayList<Driver> drivers = new ArrayList<>();
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(DRIVERS_PATH));
        String readLine;
        while ((readLine = lineNumberReader.readLine()) != null) {
            String driverName = readLine.substring(0, 0x15).trim();
            String[] fields = readLine.split(" +");
            if ((fields.length >= 5) && (fields[fields.length - 1].equals(SERIAL_FIELD))) {
                Log.d(TAG, "Found new driver " + driverName + " on " + fields[fields.length - 4]);
                drivers.add(new Driver(driverName, fields[fields.length - 4]));
            }
        }
        return drivers;
    }

    /**
     * Use {@link #getAllDevices()} instead.
     *
     * @return serialPort
     */
    @Deprecated
    public ArrayList<Device> getDevices() {
        return (ArrayList<Device>) getAllDevices();
    }

    /**
     * get serialPort devices
     *
     * @return serialPort
     */
    public List<Device> getAllDevices() {
        List<Device> devices = new ArrayList<>();
        try {
            List<Driver> drivers = getDrivers();
            for (Driver driver : drivers) {
                String driverName = driver.getName();
                List<File> driverDevices = driver.getDevices();
                for (File file : driverDevices) {
                    String devicesName = file.getName();
                    devices.add(new Device(devicesName, driverName, file));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return devices;
    }

    /**
     * get serialPort devices path
     *
     * @return serialPort path
     */
    public String[] getAllDeicesPath() {
        Vector<String> paths = new Vector<>();
        Iterator<Driver> drivers;
        try {
            drivers = getDrivers().iterator();
            while (drivers.hasNext()) {
                Driver driver = drivers.next();
                Iterator<File> files = driver.getDevices().iterator();
                while (files.hasNext()) {
                    String devicesPaths = files.next().getAbsolutePath();
                    paths.add(devicesPaths);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths.toArray(new String[paths.size()]);
    }
}
