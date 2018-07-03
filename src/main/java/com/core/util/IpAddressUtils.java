package com.core.util;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * IP地址转换工具类
 *
 * @author 梁湛桐
 */
public final class IpAddressUtils {
    /**
     * 单例
     */
    private IpAddressUtils() {
    }
    
    /**
     * 获取两个IP地址是否等价的,通常用于检查IPV6是否相同
     *
     * @param ip        ip地址
     * @param anotherIp ip地址
     * @return 相同则返回true, 否则返回false
     */
    public static boolean isEqual(String ip, String anotherIp) {
        if (!IpAddressUtils.isIpAddress(ip)) {
            throw new RuntimeException("错误的IP地址");
        }
        if (!IpAddressUtils.isIpAddress(anotherIp)) {
            throw new RuntimeException("错误的IP地址");
        }
        BigInteger bigInteger = IpAddressUtils.stringToNumber(ip);
        BigInteger bigInteger2 = IpAddressUtils.stringToNumber(anotherIp);
        return bigInteger.compareTo(bigInteger2) == 0;
    }
    
    /**
     * 判断输入地址是否合法的IP地址<br />
     * 本方法使用 {@link InetAddress#getByName(String)}
     * 来验证时候合法,性能要比正则表达式低一些
     *
     * @param ip 要判断的IP地址
     * @return 如果true标识合法, false标识不合法
     */
    public static boolean isIpAddress(String ip) {
        ip = ip.trim();
        try {
            InetAddress.getByName(ip);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * 将字符串形式的ip地址转换为BigInteger
     *
     * @param ip 字符串形式的ip地址
     * @return 整数形式的ip地址
     */
    public static BigInteger stringToNumber(String ip) {
        ip = ip.trim();
        if (!IpAddressUtils.isIpAddress(ip)) {
            throw new RuntimeException("错误的IP地址");
        }
        byte[] bytes;
        if (ip.contains(":")) {
            bytes = IpAddressUtils.ipv6ToBytes(ip);
        } else {
            bytes = IpAddressUtils.ipv4ToBytes(ip);
        }
        return new BigInteger(bytes);
    }
    
    /**
     * ipv6地址转有符号byte[17]
     */
    private static byte[] ipv6ToBytes(String ipv6) {
        byte[] ret = new byte[17];
        ret[0] = 0;
        int ib = 16;
        boolean comFlag = false;// ipv4混合模式标记
        if (ipv6.startsWith(":")) {
            ipv6 = ipv6.substring(1);
        }
        String[] groups = ipv6.split(":");
        for (int ig = groups.length - 1; ig > -1; ig--) {// 反向扫描
            if (groups[ig].contains(".")) {
                // 出现ipv4混合模式
                byte[] temp = IpAddressUtils.ipv4ToBytes(groups[ig]);
                ret[ib--] = temp[4];
                ret[ib--] = temp[3];
                ret[ib--] = temp[2];
                ret[ib--] = temp[1];
                comFlag = true;
            } else if ("".equals(groups[ig])) {
                // 出现零长度压缩,计算缺少的组数
                int zlg = 9 - (groups.length + (comFlag ? 1 : 0));
                while (zlg-- > 0) {// 将这些组置0
                    ret[ib--] = 0;
                    ret[ib--] = 0;
                }
            } else {
                int temp = Integer.parseInt(groups[ig], 16);
                ret[ib--] = (byte) temp;
                ret[ib--] = (byte) (temp >> 8);
            }
        }
        return ret;
    }
    
    /**
     * ipv4地址转有符号byte[5]
     */
    private static byte[] ipv4ToBytes(String ipv4) {
        byte[] ret = new byte[5];
        ret[0] = 0;
        // 先找到IP地址字符串中.的位置
        int position1 = ipv4.indexOf(".");
        int position2 = ipv4.indexOf(".", position1 + 1);
        int position3 = ipv4.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ret[1] = (byte) Integer.parseInt(ipv4.substring(0, position1));
        ret[2] = (byte) Integer.parseInt(ipv4.substring(position1 + 1, position2));
        ret[3] = (byte) Integer.parseInt(ipv4.substring(position2 + 1, position3));
        ret[4] = (byte) Integer.parseInt(ipv4.substring(position3 + 1));
        return ret;
    }
    
    /**
     * 将整数形式的ip地址转换为字符串形式
     *
     * @param number 整数形式的ip地址
     * @return 字符串形式的ip地址
     */
    public static String numberToString(BigInteger number) {
        byte[] bytes = number.toByteArray();
        if (bytes.length < 4) {
            byte[] newByte = new byte[]{0, 0, 0, 0};
            int j = 3;
            for (int i = bytes.length - 1; i >= 0; i--) {
                newByte[j] = bytes[i];
                j--;
            }
            bytes = newByte;
        }
        byte[] unsignedBytes = Arrays.copyOfRange(bytes, 1, bytes.length);
        if ((bytes.length == 4) || (bytes.length == 16)) {
            unsignedBytes = bytes;
        }
        // 去除符号位
        try {
            String ip = InetAddress.getByAddress(unsignedBytes).toString();
            return ip.substring(ip.indexOf('/') + 1).trim();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
