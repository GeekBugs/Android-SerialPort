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

    public static int getParity(PARITY parity) {
        return parity.getParity();
    }
}
