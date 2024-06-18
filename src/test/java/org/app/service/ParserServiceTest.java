package org.app.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserServiceTest {

    private static ParserService parserService = null;


    @BeforeAll
    public static void setUp() {
        parserService = ParserService.of();
        parserService.setFieldIndex(0);
    }


    @Test
    public void testParseWithIncrement() {
        assertEquals("1 2 3", parserService.parse("1,2,3", 0));
    }

    @Test
    public void testParseCmd() {
        assertEquals("anything", parserService.parse("anything", 5));
    }

    @Test
    public void testParseRange() {
        assertEquals("20 21 22 23 24 25", parserService.parse("20-25", 0));
    }

    @Test
    public void testParseRangeOverflow() {
        assertEquals("20 21 22 23", parserService.parse("20-25", 1));
    }

    @Test
    public void testParseIncrement() {
        assertEquals("20 25 30", parserService.parse("20-30/5", 0));
    }

    @Test
    public void testParseIncrementOverFlow() {
        assertEquals("20", parserService.parse("20-30/5", 1));
    }

    @Test
    public void testEvaluateWithAll() {
        String[] result = parserService.evaluate("*");
        assertArrayEquals(new String[]{"0", "59"}, result);
    }

    @Test
    public void testEvaluateWithRange() {
        String[] result = parserService.evaluate("30-45");
        assertArrayEquals(new String[]{"30", "45"}, result);
    }

    @Test
    public void testEvaluateWithRangeOverFlow() {
        String[] result = parserService.evaluate("0-90");
        assertArrayEquals(new String[]{"0", "59"}, result);
    }

    @Test
    void testGetCollect() {
        String[] range = {"1", "10"};
        int step = 2;
        String expectedOutput = "1 3 5 7 9";
        String result = parserService.getCollect(range, step);
        assertEquals(expectedOutput, result, "The result should match the expected output");
    }

    @Test
    void testGetCollectWithInvalidRange() {
        String[] range = {"10", "1"};
        int step = 1;
        assertEquals(parserService.getCollect(range, step), "");
    }

}
