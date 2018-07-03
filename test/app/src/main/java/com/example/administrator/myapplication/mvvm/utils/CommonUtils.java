package com.example.administrator.myapplication.mvvm.utils;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wangmaobo on 2018/7/2.
 */
public class CommonUtils {
    public static String MD5(String source) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes());
            byte buffer[] = md.digest();
            int index = 0;
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < buffer.length; i++) {
                index = buffer[i];
                if (index < 0) {
                    index += 256;
                }
                if (index < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(index));
            }
            result = sb.toString();
            System.out.println("result:" + result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String _MD5(String url) {
        Log.e("tag_MD5", url);
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            byte[] bytes = mDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }
}
