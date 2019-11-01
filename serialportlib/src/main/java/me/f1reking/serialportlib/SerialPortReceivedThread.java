package me.f1reking.serialportlib;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author F1ReKing
 * @date 2019/11/1 11:43
 * @Description
 */
public abstract class SerialPortReceivedThread extends Thread {

    private static final String TAG = SerialPortReceivedThread.class.getSimpleName();

    private InputStream mInputStream;
    private byte[] mReceivedBuffer;

    public SerialPortReceivedThread(InputStream inputStream) {
        mInputStream = inputStream;
        mReceivedBuffer = new byte[1024];
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            try {
                if (null == mInputStream) {
                    return;
                }
                int size = mInputStream.read(mReceivedBuffer);
                if (0 >= size) {
                    return;
                }
                byte[] receivedBytes = new byte[size];
                System.arraycopy(mReceivedBuffer, 0, receivedBytes, 0, size);
                Log.i(TAG, "run: receiverBuffer = " + receivedBytes.toString());
                onDataReceived(receivedBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void onDataReceived(byte[] bytes);

    /**
     * 释放
     */
    public void release() {
        interrupt();

        if (null != mInputStream) {
            try {
                mInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mInputStream = null;
        }
    }

}
