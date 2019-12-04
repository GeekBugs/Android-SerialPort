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
 * @date 2019/10/31 18:03
 * @Description 串口波特率定义
 */
public enum BAUDRATE {

    B0(0),
    B50(50),
    B75(75),
    B110(110),
    B134(134),
    B150(150),
    B200(200),
    B300(300),
    B600(600),
    B1200(1200),
    B1800(1800),
    B2400(2400),
    B4800(4800),
    B9600(9600),
    B19200(19200),
    B38400(38400),
    B57600(57600),
    B115200(115200),
    B230400(230400),
    B460800(460800),
    B500000(500000),
    B576000(576000),
    B921600(921600),
    B1000000(1000000),
    B1152000(1152000),
    B1500000(1500000),
    B2000000(2000000),
    B2500000(2500000),
    B3000000(3000000),
    B3500000(3500000),
    B4000000(4000000);

    int baudrate;

    BAUDRATE(int baudrate) {
        this.baudrate = baudrate;
    }

    int getBaudrate() {
        return this.baudrate;
    }

    public static int getBaudrate(BAUDRATE baudrate) {
        return baudrate.getBaudrate();
    }
}
