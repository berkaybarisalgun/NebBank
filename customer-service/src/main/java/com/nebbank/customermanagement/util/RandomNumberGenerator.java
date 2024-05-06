package com.nebbank.customermanagement.util;

import java.util.Random;

public class RandomNumberGenerator {
    private static final Random RANDOM = new Random();

    public static long generateSevenDigitNumber() {
        return 1000000L + (long) (RANDOM.nextDouble() * 9000000);
    }
}
