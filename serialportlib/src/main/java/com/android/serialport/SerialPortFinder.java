package com.android.serialport;

import android.util.Log;
import com.android.serialport.entity.Device;
import com.android.serialport.entity.Driver;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

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
    public List<String> getAllDeicesPath() {
        List<String> paths = new ArrayList<>();
        try {
            List<Driver> drivers = getDrivers();
            for (Driver driver : drivers) {
                List<File> driverDevices = driver.getDevices();
                for (File file : driverDevices) {
                    String devicesPaths = file.getAbsolutePath();
                    paths.add(devicesPaths);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }
}
