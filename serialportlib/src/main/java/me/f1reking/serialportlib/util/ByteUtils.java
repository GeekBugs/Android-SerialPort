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
