package com.core.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音工具类
 *
 * @author 梁湛桐
 */
public final class PinYinUtils {
    /**
     * 单例
     */
    private PinYinUtils() {
    }
    
    /**
     * 将字符串转换成拼音数组
     *
     * @param src 要转换的字符串
     * @return 拼音数组
     */
    public static String[] stringToPinyin(String src) {
        return PinYinUtils.stringToPinyin(src, false, null);
    }
    
    /**
     * 将字符串转换成拼音数组
     *
     * @param src         要转换的字符串
     * @param isPolyphone 是否查出多音字的所有拼音
     * @param separator   多音字拼音之间的分隔符
     * @return 拼音数组
     */
    private static String[] stringToPinyin(String src, boolean isPolyphone, String separator) {
        if (StringUtils.isEmpty(src)) {
            return null;
        }
        char[] srcChar = src.toCharArray();
        int srcCount = srcChar.length;
        String[] srcStr = new String[srcCount];
        for (int i = 0; i < srcCount; i++) {
            srcStr[i] = PinYinUtils.charToPinyin(srcChar[i], isPolyphone, separator);
        }
        return srcStr;
    }
    
    /***
     * 将单个字符转换成拼音
     *
     * @param src
     *            要转换的汉字
     * @param isPolyphone
     *            是否转换多音字
     * @param separator
     *            多音字之间的分隔符
     * @return 拼音
     */
    private static String charToPinyin(char src, boolean isPolyphone, String separator) {
        // 创建汉语拼音处理类
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出设置，大小写，音标方式
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        StringBuilder tempPinying = new StringBuilder();
        // 如果是中文
        if (src > 128) {
            try {
                // 转换得出结果
                String[] strs = PinyinHelper.toHanyuPinyinStringArray(src, defaultFormat);
                // 是否查出多音字，默认是查出多音字的第一个字符
                if (isPolyphone && (null != separator)) {
                    for (int i = 0; i < strs.length; i++) {
                        tempPinying.append(strs[i]);
                        if (strs.length != (i + 1)) {
                            tempPinying.append(separator);// 多音字之间用特殊符号间隔起来
                        }
                    }
                } else {
                    tempPinying.append(strs[0]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                throw new RuntimeException(e);
            }
        } else {
            tempPinying.append(src);
        }
        return tempPinying.toString();
    }
    
    /***
     * 将字符串转换成拼音数组
     *
     * @param src
     *            要转换的字符串
     * @param separator
     *            多音字拼音之间的分隔符
     * @return 拼音数组
     */
    public static String[] stringToPinyin(String src, String separator) {
        return PinYinUtils.stringToPinyin(src, true, separator);
    }
    
    /**
     * 查找字符串首字母,默认是大写
     *
     * @param src 要转换的汉字
     * @return 字符串首字母
     */
    public static String[] getHeadByString(String src) {
        return PinYinUtils.getHeadByString(src, null);
    }
    
    /**
     * 查找字符串首字母
     *
     * @param src       要转换的汉字
     * @param separator 分隔符
     * @return 字符串首字母
     */
    public static String[] getHeadByString(String src, String separator) {
        return PinYinUtils.getHeadByString(src, true, separator);
    }
    
    /**
     * 查找字符串首字母
     *
     * @param src       要转换的汉字
     * @param isCapital 是否大写
     * @param separator 分隔符
     * @return 字符串首字母
     */
    private static String[] getHeadByString(String src, boolean isCapital, String separator) {
        char[] chars = src.toCharArray();
        String[] headString = new String[chars.length];
        int i = 0;
        for (char ch : chars) {
            char[] chs = PinYinUtils.getHeadByChar(ch, isCapital);
            StringBuilder sb = new StringBuilder();
            if (null != separator) {
                int j = 1;
                for (char ch1 : chs) {
                    sb.append(ch1);
                    if (j != chs.length) {
                        sb.append(separator);
                    }
                    j++;
                }
            } else {
                sb.append(chs[0]);
            }
            headString[i] = sb.toString();
            i++;
        }
        return headString;
    }
    
    /**
     * 取汉字的首字母
     *
     * @param src       要转换的汉字
     * @param isCapital 是否是大写
     * @return 字母
     */
    private static char[] getHeadByChar(char src, boolean isCapital) {
        // 如果不是汉字直接返回
        if (src <= 128) {
            return new char[]{src};
        }
        // 获取所有的拼音
        String[] pinyingStr = PinyinHelper.toHanyuPinyinStringArray(src);
        // 创建返回对象
        int polyphoneSize = pinyingStr.length;
        char[] headChars = new char[polyphoneSize];
        int i = 0;
        // 截取首字符
        for (String s : pinyingStr) {
            char headChar = s.charAt(0);
            // 首字母是否大写，默认是小写
            if (isCapital) {
                headChars[i] = Character.toUpperCase(headChar);
            } else {
                headChars[i] = headChar;
            }
            i++;
        }
        return headChars;
    }
}
