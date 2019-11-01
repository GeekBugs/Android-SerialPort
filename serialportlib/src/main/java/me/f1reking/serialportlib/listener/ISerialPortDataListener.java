package me.f1reking.serialportlib.listener;

/**
 * @author F1ReKing
 * @date 2019/11/1 10:58
 * @Description 串口消息监听
 */
public interface ISerialPortDataListener {

    void onDataReceived(byte[] bytes);

    void onDataSend(byte[] bytes);
}
