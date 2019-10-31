package com.android.serialport.entity;

/**
 * @author F1ReKing
 * @date 2019/10/31 18:05
 * @Description 串口数据位定义
 */
public enum DATAB {

    /**
     * 5位数据位
     */
    CS5(5),
    /**
     * 6位数据位
     */
    CS6(6),
    /**
     * 7位数据位
     */
    CS7(7),
    /**
     * 8位数据位
     */
    CS8(8);

    int dataBit;

    DATAB(int dataBit) {
        this.dataBit = dataBit;
    }

    public int getDataBit() {
        return this.dataBit;
    }
}
