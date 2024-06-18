package org.app.service;

import org.app.common.Utils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static org.app.common.Constants.ALL;
import static org.app.common.Constants.INCREMENT;
import static org.app.common.Constants.RANGE;
import static org.app.common.Constants.SPACES;
import static org.app.common.Constants.VALUES;
import static org.app.common.Utils.getField;
import static org.app.common.Utils.getRange;
import static org.app.common.Utils.inRange;
import static org.app.common.Utils.isDigit;
import static org.app.common.Utils.isInRange;
import static org.app.common.Utils.specialCharacters;

public class ParserService {

    private int fieldIndex;

    private ParserService() {
    }

    /**
     * Utils method that act as an Object provider for this class.
     *
     * @return ParserService.
     */
    public static ParserService of() {
        return new ParserService();
    }

    /**
     * This method parse each component of the expression and identify the special character
     * and process them accordingly
     *
     * @param instruction
     * @param fieldIndex
     * @return the result string that needs to be displayed.
     */
    public String parse(String instruction, int index) {
        this.fieldIndex = index;
        Set<Character> specialCharacters = specialCharacters(instruction);
        if (isDigit(instruction) || fieldIndex == 5) {
            return instruction;
        } else {
            if (specialCharacters.contains(VALUES) && !specialCharacters.contains(INCREMENT)) {
                String[] defRange = getRange(getField(fieldIndex)).split(String.valueOf(RANGE));
                return Arrays.stream(instruction.split(String.valueOf(VALUES)))
                        .filter(item -> isInRange(Integer.parseInt(item), defRange))
                        .collect(joining(" "));
            } else {
                if (specialCharacters.contains(INCREMENT)) {
                    String[] parts = instruction.split(String.valueOf(INCREMENT));
                    if (parts[0].contains(String.valueOf(VALUES))) {
                        return Arrays.stream(parts[0].split(String.valueOf(VALUES)))
                                .map(item -> parse(item + INCREMENT + parts[1], fieldIndex))
                                .collect(joining(SPACES));
                    } else {
                        return getCollect(evaluate(parts[0]), Integer.parseInt(parts[1]));
                    }
                }
                return getCollect(evaluate(instruction), 1);
            }
        }
    }

    /**
     * This method evaluates range related special character like * and -
     *
     * @param instruction
     * @return the range in a string array.
     */
    public String[] evaluate(String instruction) {
        Set<Character> specialCharacters = specialCharacters(instruction);
        if (specialCharacters.contains(ALL)) {
            return getRange(getField(fieldIndex)).split(String.valueOf(RANGE));

        } else if (specialCharacters.contains(RANGE)) {
            String[] defRange = getRange(getField(fieldIndex)).split(String.valueOf(RANGE));
            return inRange(instruction.split(String.valueOf(RANGE)), defRange);
        }
        return new String[0];
    }

    /**
     * Run this when collect values between a range. Added step in order to make it multipurpose.
     *
     * @param range
     * @param step
     * @return String separated by spaces.
     */
    public String getCollect(String[] range, int step) {
        return IntStream.iterate(Integer.parseInt(range[0]),
                        n -> n <= Integer.parseInt(range[1]),
                        n -> n + step)
                .mapToObj(String::valueOf)
                .collect(joining(SPACES));
    }

    public void setFieldIndex(int fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

}
