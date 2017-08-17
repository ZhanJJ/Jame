package com.example.test.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Kin on 2017/5/24.
 */

public class StringUtil {

    public static String hashKeyFromUrl(String url) {
        String hashKey;
        try {
            MessageDigest mDigest = MessageDigest.getInstance("md5");
            mDigest.update(url.getBytes());
            hashKey = bytesToHexStrin(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            hashKey = String.valueOf(url.hashCode());
        }
        return hashKey;
    }

    public static String bytesToHexStrin(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xff & aByte); //byte转为十六进制值
            if (hex.length() == 1) {
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
