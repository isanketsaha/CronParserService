package org.app.common;


import org.junit.jupiter.api.Test;

import static org.app.common.Utils.getField;
import static org.app.common.Utils.getRange;
import static org.app.common.Utils.inRange;
import static org.app.common.Utils.isDigit;
import static org.app.common.Utils.isInRange;
import static org.app.common.Utils.specialCharacters;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilsTest {

    @Test
    public void isDigitTest() {
        assertTrue(isDigit("4"));
    }

    @Test
    public void isDigitTestNegative() {
        assertFalse(isDigit("4ab"));
    }

    @Test
    public void testSpecialCharactersNone() {
        assertTrue(specialCharacters("abcdef").isEmpty());
    }


    @Test
    public void testIsInRange() {
        String[] range = {"10", "20"};
        assertTrue(isInRange(15, range));
    }

    @Test
    public void testIsInRangeNegative() {
        String[] range = {"10", "20"};
        assertFalse(isInRange(25, range));
    }

    @Test
    public void testInRange() {
        String[] range = {"5", "25"};
        String[] defRange = {"10", "20"};
        String[] expected = {"10", "20"};

        String[] actual = inRange(range, defRange);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetRange() {
        String result = getRange("minute");
        assertNotNull(result);
        assertEquals(result, "0-59");
    }

    @Test
    public void testGetRangeWithInvalidKey() {
        assertNull(getRange("test"));
    }

    @Test
    public void testGetField() {
        String result = getField(0);
        assertNotNull(result);
        assertEquals(result, "minute");
    }

    @Test
    public void testGetFieldWithInvalidIndex() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            getField(10);
        }, "Should throw ArrayIndexOutOfBoundsException for invalid index");

    }

}
