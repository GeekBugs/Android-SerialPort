package com.android.serialport.entity;

/**
 * @author F1ReKing
 * @date 2019/10/31 18:06
 * @Description 串口流控定义
 */
public enum FLOWCON {

    /**
     * 不使用流控
     */
    NONE(0),
    /**
     * 硬件流控
     */
    HARD(1),
    /**
     * 软件流控
     */
    SOFT(2);

    int flowCon;

    FLOWCON(int flowCon) {
        this.flowCon = flowCon;
    }

    public int getFlowCon() {
        return this.flowCon;
    }

    public static int getFlowCon(FLOWCON flowcon) {
        return flowcon.getFlowCon();
    }
}
