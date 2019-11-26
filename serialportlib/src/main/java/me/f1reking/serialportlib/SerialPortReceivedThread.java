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
