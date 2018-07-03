package com.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字操作工具类
 *
 * @author liangzhantong
 */
public final class NumberUtils {
    /**
     * 单例
     */
    private NumberUtils() {
    }
    
    /**
     * 生成BigInteger对象
     *
     * @param str 数字
     * @return BigInteger对象
     */
    public static BigInteger createBigInteger(String str) {
        return org.apache.commons.lang.math.NumberUtils.createBigInteger(str);
    }
    
    /**
     * 生成Double对象
     *
     * @param str 数字
     * @return Double对象
     */
    public static Double createDouble(String str) {
        return org.apache.commons.lang.math.NumberUtils.createDouble(str);
    }
    
    /**
     * 生成Float对象
     *
     * @param str 数字
     * @return Float对象
     */
    public static Float createFloat(String str) {
        return org.apache.commons.lang.math.NumberUtils.createFloat(str);
    }
    
    /**
     * 生成Integer对象
     *
     * @param str 数字
     * @return Integer对象
     */
    public static Integer createInteger(String str) {
        return org.apache.commons.lang.math.NumberUtils.createInteger(str);
    }
    
    /**
     * 生成Long对象
     *
     * @param str 数字
     * @return Long对象
     */
    public static Long createLong(String str) {
        return org.apache.commons.lang.math.NumberUtils.createLong(str);
    }
    
    /**
     * string转换成byte类型
     *
     * @param str 数字
     * @return string所代表的数字
     */
    public static Byte createByte(String str) {
        return org.apache.commons.lang.math.NumberUtils.toByte(str);
    }
    
    /**
     * string转换成short类型
     *
     * @param str 数字
     * @return string所代表的数字
     */
    public static Short createShort(String str) {
        return org.apache.commons.lang.math.NumberUtils.toShort(str);
    }
    
    /**
     * 生成Number对象
     *
     * @param str 数字
     * @return number对象
     */
    public static Number createNumber(String str) {
        return org.apache.commons.lang.math.NumberUtils.createNumber(str);
    }
    
    /**
     * 所有数字相加
     *
     * @param numbers
     * @return 相加的值
     */
    public static Number add(Number... numbers) {
        List<BigDecimal> list = NumberUtils.createBigDecimalList(numbers);
        BigDecimal bigDecimal = BigDecimal.ZERO;
        for (BigDecimal bigDecimal1 : list) {
            bigDecimal = bigDecimal.add(bigDecimal1);
        }
        return bigDecimal;
    }
    
    /**
     * 第一个输入的数字减去其他数字
     *
     * @param numbers
     * @return 相减的值
     */
    public static Number sub(Number... numbers) {
        List<BigDecimal> list = NumberUtils.createBigDecimalList(numbers);
        BigDecimal bigDecimal = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            bigDecimal = bigDecimal.subtract(list.get(i));
        }
        return bigDecimal;
    }
    
    /**
     * 将数字类型转换成BigDecimal
     *
     * @param numbers
     * @return BigDecimal对象
     */
    private static List<BigDecimal> createBigDecimalList(Number... numbers) {
        List<BigDecimal> list = new ArrayList<>();
        for (Number number : numbers) {
            list.add(NumberUtils.createBigDecimal(number));
        }
        return list;
    }
    
    /**
     * 将数字类型转换成BigDecimal
     *
     * @param number 数字
     * @return BigDecimal对象
     */
    public static BigDecimal createBigDecimal(Number number) {
        if ((number instanceof Byte) || (number instanceof Short) || (number instanceof Integer) || (number instanceof Long) || (number instanceof Float) || (number instanceof Double)) { // 基本类型以及包装类
            return new BigDecimal(number + "");
        } else if (number instanceof BigInteger) { // BigInteger
            BigInteger num = (BigInteger) number;
            return new BigDecimal(num);
        } else if (number instanceof BigDecimal) { // BigDecimal
            return (BigDecimal) number;
        } else {
            throw new RuntimeException("不受支持的类型");
        }
    }
    
    /**
     * 所有数字相乘
     *
     * @param numbers
     * @return 相乘的值
     */
    public static Number mul(Number... numbers) {
        List<BigDecimal> list = NumberUtils.createBigDecimalList(numbers);
        BigDecimal bigDecimal = BigDecimal.ONE;
        for (BigDecimal aList : list) {
            bigDecimal = bigDecimal.multiply(aList);
        }
        return bigDecimal;
    }
    
    /**
     * 第一个数除以其他数,只能第一个数为0,其他数不允许为0,保留12位精度
     *
     * @param numbers
     * @return 相除的值
     */
    public static Number div(Number... numbers) {
        List<BigDecimal> list = NumberUtils.createBigDecimalList(numbers);
        BigDecimal bigDecimal = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            bigDecimal = bigDecimal.divide(list.get(i), 12, RoundingMode.HALF_UP);
        }
        return bigDecimal;
    }
    
    /**
     * 计算number%number2的值
     *
     * @param number
     * @param number2
     * @return number%number2的值
     */
    public static Number mod(Integer number, Integer number2) {
        if ((number == null) || (number2 == null) || (number2 == 0)) {
            return null;
        }
        return number % number2;
    }
    
    /**
     * 取得输入数字的平均数,保留12位精度
     *
     * @param numbers
     * @return 输入数字的平均数, 保留12位精度
     */
    public static Number avg(Number... numbers) {
        Number total = NumberUtils.add(numbers);
        int size = NumberUtils.createBigDecimalList(numbers).size();
        return NumberUtils.div(total, size);
    }
    
    /**
     * 取得number的绝对值
     *
     * @param number
     * @return number的绝对值
     */
    public static Number abs(Number number) {
        BigDecimal bigDecimal = NumberUtils.createBigDecimal(number);
        return bigDecimal.abs();
    }
    
    /**
     * 计算number的n次幂
     *
     * @param number
     * @param n
     * @return number的n次幂
     */
    public static Number pow(Number number, int n) {
        BigDecimal bigDecimal = NumberUtils.createBigDecimal(number);
        return bigDecimal.pow(n);
    }
    
    /**
     * 取得比输入number大的最少整数
     *
     * @param number
     * @return 比输入number大的最少整数
     */
    public static Integer ceil(Number number) {
        if (number == null) {
            return null;
        }
        return (int) Math.ceil(number.doubleValue());
    }
    
    /**
     * 取得输入的number的整数部分
     *
     * @param number
     * @return 输入的number的整数部分
     */
    public static Integer floor(Number number) {
        if (number == null) {
            return null;
        }
        return (int) Math.floor(number.doubleValue());
    }
    
    /**
     * 获取最大值
     *
     * @param numbers 数字
     * @return 最大值
     */
    public static Number max(Number... numbers) {
        TreeSet<BigDecimal> treeSet = new TreeSet<>();
        for (Number number : numbers) {
            if ((number instanceof Byte) || (number instanceof Short) || (number instanceof Integer) || (number instanceof Long) || (number instanceof Float) || (number instanceof Double)) { // 基本类型以及包装类
                BigDecimal bigDecimal = new BigDecimal(number + "");
                treeSet.add(bigDecimal);
            } else if (number instanceof BigInteger) { // BigInteger
                BigInteger num = (BigInteger) number;
                BigDecimal bigDecimal = new BigDecimal(num);
                treeSet.add(bigDecimal);
            } else if (number instanceof BigDecimal) { // BigDecimal
                BigDecimal num = (BigDecimal) number;
                treeSet.add(num);
            } else {
                throw new RuntimeException("不受支持排序的类型");
            }
        }
        return treeSet.last();
    }
    
    /**
     * 获取最小值
     *
     * @param numbers 数字
     * @return 最小值
     */
    public static Number min(Number... numbers) {
        TreeSet<BigDecimal> treeSet = new TreeSet<>();
        for (Number number : numbers) {
            if ((number instanceof Byte) || (number instanceof Short) || (number instanceof Integer) || (number instanceof Long) || (number instanceof Float) || (number instanceof Double)) { // 基本类型以及包装类
                BigDecimal bigDecimal = new BigDecimal(number + "");
                treeSet.add(bigDecimal);
            } else if (number instanceof BigInteger) { // BigInteger
                BigInteger num = (BigInteger) number;
                BigDecimal bigDecimal = new BigDecimal(num);
                treeSet.add(bigDecimal);
            } else if (number instanceof BigDecimal) { // BigDecimal
                BigDecimal num = (BigDecimal) number;
                treeSet.add(num);
            } else {
                throw new RuntimeException("不受支持排序的类型");
            }
        }
        return treeSet.first();
    }
    
    /**
     * 在start于end之间随机抽取一个整数返回
     *
     * @param start 开始数
     * @param end   结束数
     * @return 整数
     */
    public static int random(int start, int end) {
        int tmp = end + 1;
        return (int) Math.floor(Math.random() * (tmp - start)) + start;
    }
    
    /**
     * 四舍五入输入的数字
     *
     * @param number 要格式化的数字
     * @param scale  小数点精度
     * @return 四舍五入输入的数字
     */
    public static BigDecimal round(BigDecimal number, int scale) {
        return number.setScale(scale, RoundingMode.HALF_UP);
    }
    
    /**
     * 格式化输入的数字到千分位并四舍五入到小数点两位小数
     *
     * @param number 输入数字
     * @return
     */
    public static String format(Number number) {
        return NumberUtils.format(number, "#,###.00");
    }
    
    /**
     * 格式化输入的数字
     *
     * @param number  输入数字
     * @param pattern 格式{@link DecimalFormat#DecimalFormat(String pattern)}
     * @return
     */
    public static String format(Number number, String pattern) {
        BigDecimal decimal = NumberUtils.createBigDecimal(number);
        NumberFormat formatter = new DecimalFormat(pattern);
        return formatter.format(decimal);
    }
    
    /**
     * 格式化输入的数字到千分位并四舍五入到小数点两位小数
     *
     * @param number 输入数字
     * @return
     */
    public static String format(String number) {
        return NumberUtils.format(number, "#,###.00");
    }
    
    /**
     * 格式化输入的数字
     *
     * @param number  输入数字
     * @param pattern 格式{@link DecimalFormat#DecimalFormat(String pattern)}
     * @return
     */
    public static String format(String number, String pattern) {
        BigDecimal decimal = NumberUtils.createBigDecimal(number);
        NumberFormat formatter = new DecimalFormat(pattern);
        return formatter.format(decimal);
    }
    
    /**
     * 生成BigDecimal对象
     *
     * @param str 数字
     * @return BigDecimal对象
     */
    public static BigDecimal createBigDecimal(String str) {
        return org.apache.commons.lang.math.NumberUtils.createBigDecimal(str);
    }
    
    /**
     * 检查传入的字符串是否为一个整数
     *
     * @param n 验证字符串
     * @return true为是, false为否
     */
    public static boolean isInteger(String n) {
        return NumberUtils.matches(n, "^(0|(-?[1-9]\\d*))$");
    }
    
    private static boolean matches(String n, String p) {
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(n);
        return matcher.matches();
    }
    
    /**
     * 检查传入的字符串是否为一个负整数
     *
     * @param n 验证字符串
     * @return true为是, false为否
     */
    public static boolean isNegativeInteger(String n) {
        return NumberUtils.matches(n, "^-[1-9]\\d*$");
    }
    
    /**
     * 获取是否负数
     *
     * @param n 验证字符串
     * @return true为是, false为否
     */
    public static boolean isNegativeNumber(String n) {
        return NumberUtils.matches(n, "^-(([1-9]\\d*(\\.\\d+)?)|(0\\.\\d*[1-9]\\d*))$");
    }
    
    /**
     * 传入的字符串是否一个数字包含(小数,负数,正数,0)
     *
     * @param n 验证字符串
     * @return true为是, false为否
     */
    public static boolean isNumber(String n) {
        return NumberUtils.matches(n, "^((-?[1-9]\\d*(\\.\\d+)?)|(-?0(\\.\\d+)?))$");
    }
    
    /**
     * 检查传入的字符串是否一个正整数
     *
     * @param n 验证字符串
     * @return true为是, false为否
     */
    public static boolean isPositiveInteger(String n) {
        return NumberUtils.matches(n, "^[1-9]\\d*$");
    }
    
    /**
     * 获取是否正数
     *
     * @param n 验证字符串
     * @return true为是, false为否
     */
    public static boolean isPositiveNumber(String n) {
        return NumberUtils.matches(n, "^(([1-9]\\d*(\\.\\d+)?)|(0\\.\\d*[1-9]\\d*))$");
    }
    
    /**
     * 获取是否0或者正整数
     *
     * @param n 验证字符串
     * @return true为是, false为否
     */
    public static boolean notNegativeInteger(String n) {
        return NumberUtils.matches(n, "^(0|([1-9]\\d*))$");
    }
    
    /**
     * 获取0或者正数
     *
     * @param n 验证字符串
     * @return true为是, false为否
     */
    public static boolean notNegativeNumber(String n) {
        return NumberUtils.matches(n, "^(([1-9]\\d*(\\.\\d+)?)|(0(\\.\\d+)?))$");
    }
    
    /**
     * 获取是否0或负整数
     *
     * @param n 验证字符串
     * @return true为是, false为否
     */
    public static boolean notPositiveInteger(String n) {
        return NumberUtils.matches(n, "^(0|(-[1-9]\\d*))$");
    }
    
    /**
     * 获取是否0或者负数
     *
     * @param n 验证字符串
     * @return true为是, false为否
     */
    public static boolean notPositiveNumber(String n) {
        return NumberUtils.matches(n, "^((-[1-9]\\d*(\\.\\d+)?)|(-0(\\.\\d+)?)|(0(\\.0+)?))$");
    }

    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v,int scale){

        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
