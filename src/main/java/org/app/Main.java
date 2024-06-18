package org.app;


import org.app.service.ParserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.app.common.Utils.getField;


public class Main {

    static ParserService parserService = ParserService.of();

    public static void main(String[] args) {
        process(args[0].trim());
    }

    /**
     * Method that takes the input and token them based on the field and process.
     *
     * @param input - provided cron expression.
     */
    public static void process(String input) {
        String[] instructions = input.split(" ");
        preprocess(instructions);
        List<String> result = IntStream.range(0, instructions.length)
                .mapToObj(i -> parserService.parse(instructions[i], i))
                .collect(Collectors.toList());

        IntStream.range(0, result.size())
                .forEach(index -> print(index, result.get(index)));

    }

    /**
     * Handle any pre-processing or any check, before actual logic begins.
     *
     * @param instructions - tokenized array.
     */
    private static void preprocess(String[] instructions) {
        if (instructions.length != 5) {
            throw new IllegalArgumentException("Invalid Expression");
        }
    }

    /**
     * Method that print to give a uniform look.
     *
     * @param index - Index to find key(Minute, Hour and so on).
     * @param value - Deduced value for the Key.
     */
    public static void print(int index, String value) {
        System.out.printf("%-14s %s%n", getField(index), value);
    }
}