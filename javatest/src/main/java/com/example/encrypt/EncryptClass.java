package com.example.encrypt;

/**
 * Created by Kin on 2017/7/14.
 */

public class EncryptClass {
    public static void main(String[] args) {
        String string = "s23413";
        String result = EncryptUtil.getEncodeBase64(string);
        System.out.println(string + " 编码为Base64:" + result);
        System.out.println(string + " 解码为Base64:" + EncryptUtil.getDecodeBase64(result));
        System.out.println(string + " 加密为:" + EncryptUtil.getEncodeMD5(string));

//        int intValue = 255;
//        System.out.println(intValue + " 转为2进制:" + Integer.toBinaryString(intValue));
//        System.out.println(intValue + " 转为16进制:" + Integer.toHexString(intValue));
//
//        byte[] bytes = EncryptUtil.int2byte(255);
//        System.out.print(intValue + " 转为字节数组：");
//        for (int i = bytes.length - 1; i >= 0; i--) {
//            System.out.print(bytes[i]);
//        }
//        System.out.println();
//        System.out.println(intValue + "数组转为int：" + EncryptUtil.byte2int(bytes));
//
//        System.out.println(intValue + "先转字节数组，再转为16进制方法1:" + EncryptUtil.convertToHex(bytes));
//        System.out.println(intValue + "先转字节数组，再转为16进制方法2:" + EncryptUtil.bytesToHexString(bytes));
    }
}
