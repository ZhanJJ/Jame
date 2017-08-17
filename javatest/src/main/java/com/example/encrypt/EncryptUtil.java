package com.example.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by Kin on 2017/7/14.
 */

public class EncryptUtil {
    public static String getEncodeBase64(String string) {
        String result = "";
        if (string != null & !string.equals("")) {
            byte[] bytes = Base64.getEncoder().encode(string.getBytes());
            result = new String(bytes);
        }
        return result;
    }

    public static String getDecodeBase64(String string) {
        String result = "";
        if (string != null & !string.equals("")) {
            byte[] bytes = Base64.getDecoder().decode(string.getBytes());
            result = new String(bytes);
        }
        return result;
    }

    //把一个任意长度的字符串变换为一定长度的十六进制字符串
    public static String getEncodeMD5(String string) {
        String result = "";
        if (string == null || string.equals("")) {
            return result;
        }
        try {
            byte[] bytes = string.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            result = convertToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Convert byte array to Hex string
     */
    public static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    /**
     * Convert byte array to Hex string
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        if (src == null || src.length <= 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < src.length; i++) {
            int temp = src[i] & 0xff;
            String hs = Integer.toHexString(temp);
            if (hs.length() < 2) {
                stringBuilder.append("0");
            }
            stringBuilder.append(hs);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        char[] chars = hexString.toCharArray();
        int halfLength = chars.length / 2;
        byte[] bytes = new byte[halfLength];
        for (int i = 0; i < halfLength; i++) {
            int point = i * 2;
            bytes[i] = (byte) (((charToByte(chars[point]) << 4)) | (chars[point + 1]));
        }
        return bytes;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * Convert int to byte array
     *
     * @param res
     * @return
     */
    public static byte[] int2byte(int res) {
        byte[] targets = new byte[4];
        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
        targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
        targets[3] = (byte) (res >> 24 & 0xff);// 最高位,无符号右移。
        return targets;
    }

    /**
     * Convert byte array to int
     *
     * @param res
     * @return
     */
    public static int byte2int(byte[] res) {
        int result = ((res[3] & 0xff) << 24)
                | ((res[2] & 0xff) << 16)
                | ((res[1] & 0xff) << 8)
                | (res[0] & 0xff);

        return result;
    }
}
