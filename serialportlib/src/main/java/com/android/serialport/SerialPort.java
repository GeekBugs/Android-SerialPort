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
package com.android.serialport;

import android.util.Log;
import com.android.serialport.entity.BAUDRATE;
import com.android.serialport.entity.DATAB;
import com.android.serialport.entity.FLOWCON;
import com.android.serialport.entity.PARITY;
import com.android.serialport.entity.STOPB;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import me.f1reking.serialportlib.listener.ISerialPortListener;
import me.f1reking.serialportlib.listener.Status;

/**
 * @author F1ReKing
 * @date 2019/10/31 17:57
 * @Description
 */
public class SerialPort {

    private static final String TAG = SerialPort.class.getSimpleName();

    private FileDescriptor mFD;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    static {
        System.loadLibrary("serialport");
    }

    public FileInputStream getInputStream() {
        return mFileInputStream;
    }

    public FileOutputStream getOutputStream() {
        return mFileOutputStream;
    }

    public SerialPort() {
    }

    public boolean openSafe(File device, int baudRate, int stopBits, int dataBits, int parity, int flowCon, int flags, ISerialPortListener serialPortListener) {
        Log.i(TAG, String.format("SerialPort: %s: %d,%d,%d,%d,%d,%d", device.getPath(), baudRate, stopBits, dataBits, parity, flowCon, flags));
        if (!device.canRead() || !device.canWrite()) {
            boolean chmod777 = chmod777(device);
            if (!chmod777) {
                Log.e(TAG, device.getPath() + " : 没有读写权限");
                if (null != serialPortListener) {
                    serialPortListener.onFail(device, Status.NO_READ_WRITE_PERMISSION);
                }
                return false;
            }
        }
        try {
            mFD = open(device.getAbsolutePath(), baudRate, stopBits, dataBits, parity, flowCon, flags);
            mFileInputStream = new FileInputStream(mFD);
            mFileOutputStream = new FileOutputStream(mFD);
            if (null != serialPortListener) {
                serialPortListener.onSuccess(device,mFileInputStream,mFileOutputStream);
            }
            Log.i(TAG, device.getPath() + " : 串口已经打开");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (null != serialPortListener) {
                serialPortListener.onFail(device, Status.OPEN_FAIL);
            }
        }
        return false;
    }

    /**
     * 检查文件权限
     *
     * @param device 文件
     * @return 权限修改是否成功
     */
    private boolean chmod777(File device) {
        if (null == device || !device.exists()) {
            return false;
        }
        try {
            Process su = Runtime.getRuntime().exec("/system/bin/su");
            String cmd = "chmod 777" + device.getAbsolutePath() + "\n" + "exit\n";
            su.getOutputStream().write(cmd.getBytes());
            if (0 == su.waitFor() && device.canRead() && device.canWrite() && device.canExecute()) {
                return true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void closeSafe() {
        if (null != mFD) {
            close();
            mFD = null;
        }
        if (null != mFileInputStream) {
            try {
                mFileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mFileInputStream = null;
        }

        if (null != mFileOutputStream) {
            try {
                mFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mFileOutputStream = null;
        }
    }

    /**
     * 打开串口
     *
     * @param path 串口设备的绝对路径
     * @param baudRate {@link BAUDRATE} 波特率
     * @param stopBits {@link STOPB} 停止位
     * @param dataBits {@link DATAB} 数据位
     * @param parity {@link PARITY} 校验位
     * @param flowCon {@link FLOWCON} 流控
     * @param flags O_RDWR  读写方式打开 | O_NOCTTY  不允许进程管理串口 | O_NDELAY   非阻塞
     * @return
     */
    private static native FileDescriptor open(String path, int baudRate, int stopBits, int dataBits, int parity, int flowCon, int flags);

    /**
     * 关闭串口
     */
    public native void close();
}
