package com.example.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateUtilTest {

    @Test
    public void testValidDate() {
        assertTrue(DateUtil.isValidDate("14/12/2025"));
    }

    @Test
    public void testInvalidDate_WrongFormat() {
        assertFalse(DateUtil.isValidDate("2025-12-14"));
    }

    @Test
    public void testInvalidDate_InvalidDay() {
        assertFalse(DateUtil.isValidDate("32/12/2025"));
    }

    @Test
    public void testInvalidDate_InvalidMonth() {
        assertFalse(DateUtil.isValidDate("14/13/2025"));
    }

    @Test
    public void testInvalidDate_Empty() {
        assertFalse(DateUtil.isValidDate(""));
    }

    @Test
    public void testInvalidDate_Null() {
        assertFalse(DateUtil.isValidDate(null));
    }

    @Test
    public void testGetCurrentDate() {
        String date = DateUtil.getCurrentDate();
        assertNotNull(date);
        assertTrue(DateUtil.isValidDate(date));
    }
}
