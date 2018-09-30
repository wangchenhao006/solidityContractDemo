package com.wch.contract.utils;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilTest {

    @Test
    public void dateToLong() {
        long timeLong = DateUtil.dateToLong(new Date());
        int timeInt = (int) timeLong;
        System.out.println("-0-----------------");
        System.out.println(timeLong);
        System.out.println(timeInt);
        String myString = DateUtil.dateToString(new Date(),DateUtil.Format_YearDay);
        int foo = Integer.parseInt(myString);
        System.out.println(foo);
        int time = DateUtil.dateToInt(new Date());
        System.out.println(time);
    }
}