package com.android.serialport;

import java.io.FileDescriptor;

/**
 * @author F1ReKing
 * @date 2019/10/31 17:57
 * @Description
 */
public class SerialPort {

    static {
        System.loadLibrary("SerialPort");
    }

    /**
     * 打开串口
     * @param path 串口设备的绝对路径
     * @param baudrate
     * @param stopBits
     * @param dataBits
     * @param parity
     * @param flowCon
     * @param flags
     * @return
     */
    private native static FileDescriptor open(String path, int baudrate, int stopBits, int dataBits, int parity, int flowCon, int flags);

    /**
     * 关闭串口
     */
    public native void close();
}
