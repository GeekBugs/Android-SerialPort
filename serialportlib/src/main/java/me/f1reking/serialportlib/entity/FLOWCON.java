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
package me.f1reking.serialportlib.entity;

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
