package com.android.serialport.entity;

/**
 * @author F1ReKing
 * @date 2019/10/31 18:05
 * @Description 串口校验位定义
 */
public enum  PARITY {

    /**
     * 无奇偶校验
     */
    NONE(0),
    /**
     * 奇校验
     */
    ODD(1),
    /**
     * 偶校验
     */
    EVEN(2);

    int parity;

    PARITY(int parity) {
        this.parity = parity;
    }

    public int getParity() {
        return this.parity;
    }
}
