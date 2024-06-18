package org.app.common;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.app.common.Constants.ALL;
import static org.app.common.Constants.INCREMENT;
import static org.app.common.Constants.RANGE;
import static org.app.common.Constants.VALUES;

/**
 * Utils.
 */
public class Utils {

    private Utils() {
        throw new IllegalArgumentException("Not Supported.");
    }

    public static boolean isDigit(String input) {
        return input.chars()
                .allMatch(Character::isDigit);
    }

    public static Set<Character> specialCharacters(String input) {
        Set<Character> characters = supportedExpression();
        return input.chars()
                .mapToObj(c -> (char) c)
                .filter(characters::contains)
                .collect(Collectors.toSet());
    }

    public static boolean isInRange(int value, String[] range) {
        return value >= Integer.parseInt(range[0]) && value <= Integer.parseInt(range[1]);
    }

    public static String[] inRange(String[] range, String[] defRange) {
        range[0] = String.valueOf(Math.max(Integer.parseInt(range[0]), Integer.parseInt(defRange[0])));
        range[1] = String.valueOf(Math.min(Integer.parseInt(range[1]), Integer.parseInt(defRange[1])));
        return range;
    }

    public static Set<Character> supportedExpression() {
        return Set.of(ALL, VALUES, RANGE, INCREMENT);
    }

    public static Map<String, String> fieldWithRange() {
        Map<String, String> lookup = new LinkedHashMap<>();
        lookup.put("minute", "0-59");
        lookup.put("hour", "0-23");
        lookup.put("day of month", "1-31");
        lookup.put("month", "1-12");
        lookup.put("day of week", "0-7");
        lookup.put("command", "");
        return lookup;
    }


    /**
     * This returns the range for each field key.
     *
     * @param field - key
     * @return String separated by '-'
     */
    public static String getRange(String field) {
        return fieldWithRange()
                .get(field);
    }

    /**
     * Return field name based on index.
     * 1 represent minute , 2 represent hour and so on.
     *
     * @param fieldIndex - index
     * @return field
     */
    public static String getField(int fieldIndex) {
        return (String) Utils.fieldWithRange()
                .keySet()
                .toArray()[fieldIndex];
    }
}
