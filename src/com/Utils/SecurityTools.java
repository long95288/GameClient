package com.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityTools {
    public SecurityTools() {
    }

    public static String getPassWordDigest(String passWord){
        String generatePassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passWord.getBytes());
            // 获得哈希值
            byte[] bytes = md.digest();
            // 转成字符串
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0;i<bytes.length;i++){
                // 转成16进制字符串
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatePassword = stringBuilder.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        if (generatePassword == null) {
            // 没有密码生成
            System.err.println("密码hash取值出错");
            return "error";
        }
        return generatePassword;
    }
}
