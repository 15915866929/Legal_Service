package com.core.util;

import java.util.Random;

/**
 * 生成随机字符串
 *
 * @author 梁湛桐
 */
public final class RandomUtils {
    
    /**
     * 单例
     */
    private RandomUtils() {
    }
    
    /**
     * 生产随机字符串(大写字母，小写字母，数字)
     *
     * @param length 生成字符串长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sf = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);// 0~61
            sf.append(str.charAt(number));
        }
        return sf.toString();
    }
    
    /**
     * 生成随机字符串（大写字母）
     *
     * @param length 生成字符串长度
     * @return 随机字符串
     */
    public static String upperString(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sf = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(26);// 0~25
            sf.append(str.charAt(number));
        }
        return sf.toString();
    }
    
    /**
     * 生成随机字符串（小写字母）
     *
     * @param length 生成字符串长度
     * @return 随机字符串
     */
    public static String lowerString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sf = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(26);// 0~25
            sf.append(str.charAt(number));
        }
        return sf.toString();
    }
    
    /**
     * 生成随机字符串（数字）
     *
     * @param length 生成字符串长度
     * @return 随机字符串
     */
    public static String numberString(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuilder sf = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(10);// 0~9
            sf.append(str.charAt(number));
        }
        return sf.toString();
    }
    
    /**
     * 生成随机字符串（大写字母，小写字母）
     *
     * @param length 生成字符串长度
     * @return 随机字符串
     */
    public static String letterString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sf = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(52);// 0~51
            sf.append(str.charAt(number));
        }
        return sf.toString();
    }
    
    /**
     * 生成随机字符串（大写字母，数字）
     *
     * @param length 生成字符串长度
     * @return 随机字符串
     */
    public static String upperNumberString(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sf = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(36);// 0~35
            sf.append(str.charAt(number));
        }
        return sf.toString();
    }
    
    /**
     * 生成随机字符串（小写字母，数字）
     *
     * @param length 生成字符串长度
     * @return 随机字符串
     */
    public static String lowerNumberString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzZ0123456789";
        Random random = new Random();
        StringBuilder sf = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(36);// 0~35
            sf.append(str.charAt(number));
        }
        return sf.toString();
    }
}
