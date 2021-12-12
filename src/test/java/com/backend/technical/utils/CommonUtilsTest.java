package com.backend.technical.utils;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CommonUtilsTest {

    @Test
    public void testParseDateToString_1() {
        final String pattern = "yyyy-MM-dd";

        final String actual = CommonUtils.dateToString(newDate(2021, 12, 10, 0, 0, 0), pattern);

        assertEquals("2021-12-10", actual);

    }

    @Test
    public void testParseDateToString_2() {
        final String pattern = "yyyy-MM-dd HH:mm:ss";

        final String actual = CommonUtils.dateToString(newDate(2020, 11, 9, 22, 22, 56), pattern);

        assertEquals("2020-11-09 22:22:56", actual);
    }

    @Test
    public void testParseDateToString_3() {
        final String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";

        final String actual = CommonUtils.dateToString(newDate(2019, 9, 9, 18, 30, 50), pattern);

        assertEquals("2019-09-09T18:30:50Z", actual);
    }

    @Test
    public void testParseDateToString_4() {
        final String pattern = "yyyyMMdd";

        final String actual = CommonUtils.dateToString(newDate(2019, 9, 9, 0, 0, 0), pattern);

        assertEquals("20190909", actual);
    }

    @Test
    public void testParseDateToString_5() {
        final String pattern = "yyyyMMddHHmmss";

        final String actual = CommonUtils.dateToString(newDate(2019, 9, 9, 18, 30, 50), pattern);

        assertEquals("20190909183050", actual);
    }

    @Test
    public void testIsBlank_1() {
        final String input = "string";
        assertFalse(CommonUtils.isBlank(input));
    }

    @Test
    public void testIsBlank_2() {
        final String input = "";
        assertTrue(CommonUtils.isBlank(input));
    }

    @Test
    public void testIsBlank_3() {
        assertTrue(CommonUtils.isBlank(null));
    }

    @Test
    public void testIsBlank_4() {
        final String input = "    ";
        assertTrue(CommonUtils.isBlank(input));
    }

    private Date newDate(final int y, final int m, final int d, final int h, final int min, final int sec) {
        final Calendar cal = Calendar.getInstance();
        final int monthIndex = Month.of(m-1).getValue();
        cal.set(y, monthIndex, d, h, min, sec);
        return cal.getTime();
    }
}
