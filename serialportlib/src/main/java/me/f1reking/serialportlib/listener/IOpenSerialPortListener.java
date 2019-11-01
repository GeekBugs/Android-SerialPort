package me.f1reking.serialportlib.listener;

import java.io.File;

/**
 * @author F1ReKing
 * @date 2019/11/1 10:49
 * @Description 串口打开状态监听
 */
public interface IOpenSerialPortListener {

    void onSuccess(File device);

    void onFail(File device, Status status);

    enum Status {
        NO_READ_WRITE_PERMISSION,
        OPEN_FAIL
    }
}
