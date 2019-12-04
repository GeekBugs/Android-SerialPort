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

    public static int getDataBit(DATAB datab) {
        return datab.getDataBit();
    }
}
