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
