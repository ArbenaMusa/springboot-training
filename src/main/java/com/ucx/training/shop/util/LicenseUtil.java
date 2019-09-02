package com.ucx.training.shop.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class LicenseUtil {

    private static final String CHAR_SEQUENCE_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_SEQUENCE_UPPER = CHAR_SEQUENCE_LOWER.toUpperCase();
    private static final String NUMBER_SEQUENCE = "0123456789";
    private static final int LICENCE_LENGTH = 16;
    private static final String DATA_STRING = CHAR_SEQUENCE_UPPER + NUMBER_SEQUENCE;
    private static final SecureRandom RANDOM_GENERATOR = new SecureRandom();


    /*public static void main(String[] args) {
        List<String> licemces = generateLicence(5);
        licemces.forEach(e-> System.out.println(e));
    }*/

    public static List<String> generateLicence(int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("Invalid quantity");

        List<String> licences = new ArrayList<>();

        for(int quantityCounter = 0; quantityCounter < quantity; quantityCounter++) {
            StringBuilder sb = new StringBuilder();

            for (int licenseLength = 0; licenseLength < LICENCE_LENGTH; licenseLength++) {
                int rndCharAt = RANDOM_GENERATOR.nextInt(DATA_STRING.length());
                char rndChar = DATA_STRING.charAt(rndCharAt);
                sb.append(rndChar);
            }

            licences.add(sb.toString());
        }
        return licences;
    }
}
