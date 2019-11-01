package com.android.serialport.entity;

/**
 * @author F1ReKing
 * @date 2019/10/31 18:04
 * @Description 串口停止位定义
 */
public enum STOPB {

    /**
     * 1位停止位
     */
    B1(1),

    /**
     * 2位停止位
     */
    B2(2);

    int stopBit;

    STOPB(int stopBit) {
        this.stopBit = stopBit;
    }

    public int getStopBit() {
        return this.stopBit;
    }

    public static int getStopBit(STOPB stopb) {
        return stopb.getStopBit();
    }
}
