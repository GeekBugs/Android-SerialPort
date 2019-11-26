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
package me.f1reking.serialportlib.util;

/**
 * @author F1ReKing
 * @date 2019/11/1 14:08
 * @Description
 */
public class ByteUtils {

    public static int isOdd(int num) {
        return num & 0x1;
    }

    public static int HexToInt(String inHex) {
        return Integer.parseInt(inHex, 16);
    }

    public static byte HexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    public static byte[] hexToByteArr(String hex) {
        int hexLen = hex.length();
        byte[] result = null;
        if (isOdd(hexLen) == 1) {
            hexLen++;
            result = new byte[hexLen / 2];
            hex = "0" + hex;
        } else {
            result = new byte[hexLen / 2];
        }
        int j = 0;
        for (int i = 0; i < hexLen; i++) {
            result[j] = HexToByte(hex.substring(i, i + 2));
            j++;
        }
        return result;
    }
}
