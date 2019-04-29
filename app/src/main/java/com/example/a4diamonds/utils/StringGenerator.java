package com.example.a4diamonds.utils;

import java.util.Random;

public class StringGenerator {
    final private static String ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String generateGameId(int length) {
        return get(length);
    }

    public String generateGameId() {
        return get(6);
    }

    private String get(int length) {
        Random r = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int idOfChar = r.nextInt(ALLOWED_CHARS.length());
            result.append(ALLOWED_CHARS.charAt(idOfChar));
        }

        return result.toString();
    }
}
