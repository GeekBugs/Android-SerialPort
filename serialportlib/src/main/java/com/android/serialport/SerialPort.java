package com.android.serialport;

import com.android.serialport.entity.BAUDRATE;
import com.android.serialport.entity.DATAB;
import com.android.serialport.entity.FLOWCON;
import com.android.serialport.entity.PARITY;
import com.android.serialport.entity.STOPB;
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
     * @param baudRate {@link BAUDRATE} 波特率
     * @param stopBits {@link STOPB} 停止位
     * @param dataBits {@link DATAB} 数据位
     * @param parity {@link PARITY} 校验位
     * @param flowCon {@link FLOWCON} 流控
     * @param flags O_RDWR  读写方式打开 | O_NOCTTY  不允许进程管理串口 | O_NDELAY   非阻塞
     * @return
     */
    protected native static FileDescriptor open(String path, int baudRate, int stopBits, int dataBits, int parity, int flowCon, int flags);

    /**
     * 关闭串口
     */
    public native void close();
}
