package com.core.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public final class DateTimeUtils {
    
    /**
     * 东八区
     */
    private static final ZoneId ZONEID = ZoneId.systemDefault();
    /**
     * 封装的时间
     */
    private Instant instant;
    
    private DateTimeUtils(Instant instant) {
        this.instant = instant;
    }
    
    /**
     * Calendar
     *
     * @param calendar
     * @return
     */
    public static DateTimeUtils of(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        Date date = calendar.getTime();
        return of(date);
    }
    
    /**
     * Date
     *
     * @param date
     * @return
     */
    public static DateTimeUtils of(Date date) {
        if (date == null) {
            return null;
        }
        return DateTimeUtils.of(date.toInstant());
    }
    
    /**
     * Instant
     *
     * @param instant
     * @return
     */
    public static DateTimeUtils of(Instant instant) {
        if (instant == null) {
            return null;
        }
        return new DateTimeUtils(instant);
        
    }
    
    /**
     * LocalDate
     *
     * @param localDate
     * @return
     */
    public static DateTimeUtils of(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return DateTimeUtils.of(localDate.atStartOfDay(DateTimeUtils.ZONEID).toInstant());
    }
    
    /**
     * 字符串
     *
     * @param date
     * @param formater
     * @return
     */
    public static DateTimeUtils of(String date, DateTimeFormater formater) {
        if (org.apache.commons.lang.StringUtils.isEmpty(date)) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(formater.getPattern()));
        return DateTimeUtils.of(localDateTime);
    }
    
    /**
     * LocalDateTime
     *
     * @param localDateTime
     * @return
     */
    public static DateTimeUtils of(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return DateTimeUtils.of(localDateTime.atZone(DateTimeUtils.ZONEID).toInstant());
    }
    
    /**
     * 获取当前月的第一秒
     *
     * @return
     */
    public LocalDateTime firstLocalDateTimeOfMonth() {
        return this.toYearMonth().atDay(1).atStartOfDay();
    }
    
    /**
     * YearMonth
     *
     * @return
     */
    public YearMonth toYearMonth() {
        return YearMonth.of(this.toYear().getValue(), this.toMonth());
    }
    
    /**
     * Year
     *
     * @return
     */
    public Year toYear() {
        LocalDateTime localDateTime = this.toLocalDateTime();
        return Year.of(localDateTime.getYear());
    }
    
    /**
     * Month
     *
     * @return
     */
    public Month toMonth() {
        LocalDateTime localDateTime = this.toLocalDateTime();
        return localDateTime.getMonth();
    }
    
    /**
     * LocalDateTime
     *
     * @return
     */
    public LocalDateTime toLocalDateTime() {
        return this.instant.atZone(DateTimeUtils.ZONEID).toLocalDateTime();
    }
    
    /**
     * 获取当前季节的第一秒
     *
     * @return
     */
    public LocalDateTime firstLocalDateTimeOfSeason() {
        DateTimeSeason season = this.toSeason();
        if (DateTimeSeason.SPRING.equals(season)) {
            return LocalDateTime.of(this.toYear().getValue(), 1, 1, 0, 0, 0, 0);
        } else if (DateTimeSeason.SUMMER.equals(season)) {
            return LocalDateTime.of(this.toYear().getValue(), 4, 1, 0, 0, 0, 0);
        } else if (DateTimeSeason.AUTUMN.equals(season)) {
            return LocalDateTime.of(this.toYear().getValue(), 7, 1, 0, 0, 0, 0);
        } else {
            return LocalDateTime.of(this.toYear().getValue(), 10, 1, 0, 0, 0, 0);
        }
    }
    
    /**
     * Season
     *
     * @return
     */
    public DateTimeSeason toSeason() {
        Month month = this.toMonth();
        if (Month.JANUARY.equals(month) || Month.FEBRUARY.equals(month) || Month.MARCH.equals(month)) {
            return DateTimeSeason.SPRING;
        } else if (Month.APRIL.equals(month) || Month.MAY.equals(month) || Month.JUNE.equals(month)) {
            return DateTimeSeason.SUMMER;
        } else if (Month.JULY.equals(month) || Month.AUGUST.equals(month) || Month.SEPTEMBER.equals(month)) {
            return DateTimeSeason.AUTUMN;
        } else {
            return DateTimeSeason.WINTER;
        }
    }
    
    /**
     * 获取当前星期的第一秒(按照国标,第一天为周一)
     *
     * @return
     */
    public LocalDateTime firstLocalDateTimeOfWeek() {
        LocalDateTime localDateTime = this.toLocalDateTime();
        while (!DayOfWeek.MONDAY.equals(localDateTime.getDayOfWeek())) {
            localDateTime = localDateTime.minusDays(1);
        }
        return localDateTime.toLocalDate().atTime(LocalTime.of(0, 0, 0, 0));
    }
    
    /**
     * 获取当前年的第一秒
     *
     * @return
     */
    public LocalDateTime firstLocalDateTimeOfYear() {
        return LocalDateTime.of(this.toYear().getValue(), 1, 1, 0, 0, 0, 0);
    }
    
    /**
     * 获取当前月的最后一秒
     *
     * @return
     */
    public LocalDateTime lastLocalDateTimeOfMonth() {
        LocalDate localDate = this.toYearMonth().atEndOfMonth();
        return localDate.atTime(LocalTime.of(23, 59, 59, 999999999));
    }
    
    /**
     * 获取当前季节的最后一秒
     *
     * @return
     */
    public LocalDateTime lastLocalDateTimeOfSeason() {
        DateTimeSeason season = this.toSeason();
        if (DateTimeSeason.SPRING.equals(season)) {
            return LocalDateTime.of(this.toYear().getValue(), 3, 31, 23, 59, 59, 999999999);
        } else if (DateTimeSeason.SUMMER.equals(season)) {
            return LocalDateTime.of(this.toYear().getValue(), 6, 30, 23, 59, 59, 999999999);
        } else if (DateTimeSeason.AUTUMN.equals(season)) {
            return LocalDateTime.of(this.toYear().getValue(), 9, 30, 23, 59, 59, 999999999);
        } else {
            return LocalDateTime.of(this.toYear().getValue(), 12, 31, 23, 59, 59, 999999999);
        }
    }
    
    /**
     * 获取当前星期的最后一秒(按照国标,最后一天为周日)
     *
     * @return
     */
    public LocalDateTime lastLocalDateTimeOfWeek() {
        LocalDateTime localDateTime = this.toLocalDateTime();
        while (!DayOfWeek.SUNDAY.equals(localDateTime.getDayOfWeek())) {
            localDateTime = localDateTime.plusDays(1);
        }
        return localDateTime.toLocalDate().atTime(LocalTime.of(23, 59, 59, 999999999));
    }
    
    /**
     * 获取当前年的最后一秒
     *
     * @return
     */
    public LocalDateTime lastLocalDateTimeOfYear() {
        return LocalDateTime.of(this.toYear().getValue(), 12, 31, 23, 59, 59, 999999999);
    }
    
    /**
     * 获取当前日的第一秒
     *
     * @return
     */
    public LocalDateTime firstLocalDateTime() {
        return this.toLocalDate().atTime(LocalTime.of(0, 0, 0, 0));
    }
    
    /**
     * LocalDate
     *
     * @return
     */
    public LocalDate toLocalDate() {
        return this.instant.atZone(DateTimeUtils.ZONEID).toLocalDate();
    }
    
    /**
     * 获取当前日的最后一秒
     *
     * @return
     */
    public LocalDateTime lastLocalDateTime() {
        return this.toLocalDate().atTime(LocalTime.of(23, 59, 59, 999999999));
    }
    
    /**
     * Calendar
     *
     * @return
     */
    public Calendar toCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate());
        return calendar;
    }
    
    /**
     * Date
     *
     * @return
     */
    public Date toDate() {
        return Date.from(this.instant);
    }
    
    /**
     * Instant
     *
     * @return
     */
    public Instant toInstant() {
        return this.instant;
    }
    
    /**
     * String
     *
     * @param formater
     * @return
     */
    public String toString(DateTimeFormater formater) {
        LocalDateTime localDateTime = this.toLocalDateTime();
        return localDateTime.format(DateTimeFormatter.ofPattern(formater.getPattern()));
    }
}
