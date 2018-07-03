package com.core.util;

public enum DateTimeSeason {
    /**
     * 秋
     */
    AUTUMN(2, "秋"), /**
     * 春
     */
    SPRING(0, "春"), /**
     * 夏
     */
    SUMMER(1, "夏"), /**
     * 冬
     */
    WINTER(3, "冬");
    
    private String name;
    private int value;
    
    DateTimeSeason(int value, String name) {
        this.value = value;
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getValue() {
        return this.value;
    }
}
