package com.core.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;

/**
 * 身份证工具类
 */
public final class IdCardUtils {
    /**
     * 单例
     */
    private IdCardUtils() {
    }
    
    /**
     * 根据身份证号码，返回年龄
     *
     * @param cid 身份证号码
     * @return
     * @throws ParseException
     */
    public static int parseAge(String cid) throws ParseException {
        LocalDate birthday = IdCardUtils.parseBirthday(cid);
        LocalDate now = LocalDate.now();
        Period period = Period.between(birthday, now);
        return period.getYears();
    }
    
    /**
     * 根据身份证号截取出生日期
     *
     * @param cid 身份证号码
     * @return
     * @throws ParseException
     */
    public static LocalDate parseBirthday(String cid) throws ParseException {
        if (IdCardUtils.check(cid)) { // 检查身份证是否正确
            String year = cid.substring(6, 10);
            String month = cid.substring(10, 12);
            String day = cid.substring(12, 14);
            return LocalDate.of(new Integer(year), new Integer(month), new Integer(day));
        } else {
            throw new ParseException("错误的身份证号码", 0);
        }
    }
    
    /**
     * 检测身份证号是否合法
     *
     * @param cid 身份证号码
     * @return
     * @throws ParseException
     */
    public static boolean check(String cid) throws ParseException {
        int len = cid.length();
        if (len != 18) {
            throw new ParseException("不支持15位身份证号码", 0);
        }
        int kx = 0;
        // 身份证号第一位到第十七位的系数装入到一个整型数组
        int[] weight = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        // 需要进行运算的是身份证前17位
        for (int i = 0; i < (len - 1); i++) {
            // 把身份证的数字分拆成一个个数字
            int x = Integer.parseInt(String.valueOf(cid.charAt(i)));
            // 然后相加起来
            kx += weight[i] * x;
        }
        // 用加出来和模以11，看余数是多少？
        int mod = kx % 11;
        // 最后一位身份证的号码的对应号码,一一对应
        // (0,1,2,3,4,5,6,7,8,9,10)
        // (1,0,X,9,8,7,6,5,4,3,2)
        Character[] checkMods = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        // 获取身份证最后的一个验证码
        Character lastCode = cid.charAt(len - 1);
        // 判断是否对应
        String idNumber = lastCode.toString().toLowerCase();
        String checkMods2 = checkMods[mod].toString().toLowerCase();
        return checkMods2.equals(idNumber);
    }
    
    /**
     * 根据所传身份证号解析其性别,true为男,false为女
     *
     * @param cid 身份证号码
     * @return
     * @throws ParseException
     */
    public static boolean parseSex(String cid) throws ParseException {
        if (IdCardUtils.check(cid)) { // 检查身份证是否正确
            char c = cid.charAt(cid.length() - 2);
            int sex = Integer.parseInt(String.valueOf(c));
            return (sex % 2) != 0;
        } else {
            throw new ParseException("错误的身份证号码", 0);
        }
    }
}
