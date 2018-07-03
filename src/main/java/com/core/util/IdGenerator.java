package com.core.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 数据库主键生成器
 *
 * @author lzt
 */
public final class IdGenerator {
    /**
     * 序列
     */
    private static int sequence = 0;
    
    /**
     * 单例
     */
    private IdGenerator() {
    }
    
    /**
     * 生成唯一主键
     *
     * @return
     */
    public static String generate() {
        return IdGenerator.getTimestamp() + IdGenerator.getSequence() + RandomUtils.upperNumberString(9);
    }
    
    /**
     * 获取当前时间戳
     *
     * @return
     */
    private static String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }
    
    /**
     * 获取序列字符串
     *
     * @return 序列字符串
     */
    private static String getSequence() {
        String code = String.valueOf(IdGenerator.sequence);
        IdGenerator.sequence++;
        if (IdGenerator.sequence == 1000000) {
            IdGenerator.sequence = 0;
        }
        return StringUtils.leftPad(code, 6, "0");
    }
    
}
