package com.core.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;

/**
 * aes加密工具类
 */
public class AesUtils {

    // 加密
    public static String Encrypt(String sSrc, String sKey, String iv) throws Exception {
        if (sKey == null) {
            return null;
        }        // 判断Key是否为16位
        if (sKey.length() != 16) {
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AesUtils");
        Cipher cipher = Cipher.getInstance("AesUtils/CBC/PKCS5Padding");//"算法/模式/补码方式"  创建密码器
        IvParameterSpec ivs = new IvParameterSpec(iv.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度，初始化向量参数16bytes
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivs);//初始化密码器，第一个参数为加密（Cipher.ENCRYPT_MODE）或解密（Cipher.DECRYPT_MODE）
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());//将数据加密
        String str = parseByte2HexStr(encrypted); //先用parseByte2HexStr.将二进制转成16进制
//        Base64.encodeBase64String(str.getBytes())
        return EscapeUtils.encodeBase64(str.getBytes()); //再用base64加密

    }

    // 解密
    public static String Decrypt(String sSrc, String sKey, String iv) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AesUtils");
            Cipher cipher = Cipher.getInstance("AesUtils/CBC/PKCS5Padding");
            IvParameterSpec ivs = new IvParameterSpec(iv
                    .getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivs);
            sSrc = URLDecoder.decode(sSrc,"UTF-8");
//            Base64.decodeBase64(sSrc)
            byte[] base64Encrypted = EscapeUtils.decodeBase64(sSrc); //先用BASE64解密
            String str = new String(base64Encrypted);
            byte[] aesEncrypted = parseHexStr2Byte(str); //再用parseHexStr2Byte解密 16进制转二进制
            try {
                byte[] original = cipher.doFinal(aesEncrypted);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}