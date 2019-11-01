package me.f1reking.serialportlib;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import com.android.serialport.SerialPort;
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
import me.f1reking.serialportlib.listener.IOpenSerialPortListener;
import me.f1reking.serialportlib.listener.ISerialPortDataListener;
import me.f1reking.serialportlib.util.ByteUtils;

/**
 * @author F1ReKing
 * @date 2019/11/1 09:38
 * @Description
 */
public class SerialPortHelper extends SerialPort {

    private static final String TAG = SerialPortHelper.class.getSimpleName();

    private FileDescriptor mFD;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;
    private IOpenSerialPortListener mIOpenSerialPortListener;
    private ISerialPortDataListener mISerialPortDataListener;
    private HandlerThread mSendingHandlerThread;
    private Handler mSendingHandler;
    private SerialPortReceivedThread mSerialPortReceivedThread;

    /**
     * 打开串口
     *
     * @param device 串口设备的绝对路径
     * @param baudRate {@link BAUDRATE} 波特率
     * @param stopBits {@link STOPB} 停止位
     * @param dataBits {@link DATAB} 数据位
     * @param parity {@link PARITY} 校验位
     * @param flowCon {@link FLOWCON} 流控
     * @param flags O_RDWR  读写方式打开 | O_NOCTTY  不允许进程管理串口 | O_NDELAY   非阻塞
     * @return
     */
    public boolean openSerialPort(File device, int baudRate, int stopBits, int dataBits, int parity, int flowCon, int flags) {

        // 优先校验串口权限
        if (!device.canRead() || !device.canWrite()) {
            boolean chmod777 = chmod777(device);
            if (!chmod777) {
                Log.e(TAG, device.getPath() + " : 没有读写权限");
                if (null != mIOpenSerialPortListener) {
                    mIOpenSerialPortListener.onFail(device, IOpenSerialPortListener.Status.NO_READ_WRITE_PERMISSION);
                }
                //callback
                return false;
            }
        }

        try {
            mFD = open(device.getAbsolutePath(), baudRate, stopBits, dataBits, parity, flowCon, flags);
            mFileInputStream = new FileInputStream(mFD);
            mFileOutputStream = new FileOutputStream(mFD);
            Log.i(TAG, device.getPath() + " : 串口已经打开");
            // callback
            if (null != mIOpenSerialPortListener) {
                mIOpenSerialPortListener.onSuccess(device);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (null != mIOpenSerialPortListener) {
                mIOpenSerialPortListener.onFail(device, IOpenSerialPortListener.Status.OPEN_FAIL);
            }
        }
        return false;
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
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

        mIOpenSerialPortListener = null;
        mISerialPortDataListener = null;
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

    /**
     * 开启发送消息线程
     */
    private void startSendThread() {
        mSendingHandlerThread = new HandlerThread("mSendingHandlerThread");
        mSendingHandlerThread.start();

        mSendingHandler = new Handler(mSendingHandlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                byte[] sendBytes = (byte[]) msg.obj;
                if (null != mFileOutputStream && null != sendBytes && sendBytes.length > 0) {
                    try {
                        mFileOutputStream.write(sendBytes);
                        if (null != mISerialPortDataListener) {
                            mISerialPortDataListener.onDataSend(sendBytes);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    /**
     * 停止发送消息线程
     */
    private void stopSendThread() {
        mSendingHandler = null;
        if (null != mSendingHandlerThread) {
            mSendingHandlerThread.interrupt();
            mSendingHandlerThread.quit();
            mSendingHandlerThread = null;
        }
    }

    /**
     * 开启接收消息的线程
     */
    private void startReceiverdThread() {
        mSerialPortReceivedThread = new SerialPortReceivedThread(mFileInputStream) {
            @Override
            public void onDataReceived(byte[] bytes) {
                if (null != mISerialPortDataListener) {
                    mISerialPortDataListener.onDataReceived(bytes);
                }
            }
        };
        mSerialPortReceivedThread.start();
    }

    /**
     * 停止接收消息的线程
     */
    private void stopReceiverdThread() {
        if (null != mSerialPortReceivedThread) {
            mSerialPortReceivedThread.release();
        }
    }

    /**
     * 发送数据
     *
     * @param bytes
     * @return
     */
    public boolean sendBytes(byte[] bytes) {
        if (null != mFD && null != mFileInputStream && null != mFileOutputStream) {
            if (null != mSendingHandler) {
                Message message = Message.obtain();
                message.obj = bytes;
                return mSendingHandler.sendMessage(message);
            }
        }
        return false;
    }

    /**
     * 发送Hex
     *
     * @param hex
     * @return
     */
    public void sendHex(String hex) {
        byte[] hexArray = ByteUtils.hexToByteArr(hex);
        sendBytes(hexArray);
    }

    /**
     * 发送文本
     *
     * @param txt
     * @return
     */
    public void sendTxt(String txt) {
        byte[] txtArray = txt.getBytes();
        sendBytes(txtArray);
    }

    public void setIOpenSerialPortListener(IOpenSerialPortListener IOpenSerialPortListener) {
        mIOpenSerialPortListener = IOpenSerialPortListener;
    }

    public void setISerialPortDataListener(ISerialPortDataListener ISerialPortDataListener) {
        mISerialPortDataListener = ISerialPortDataListener;
    }
}
