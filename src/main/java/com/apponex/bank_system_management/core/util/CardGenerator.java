package com.apponex.bank_system_management.core.util;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CardGenerator {
    private static final Integer CARD_NUMBER_LENGTH =16;

    public static String generateCardNumber() {
        SecureRandom random = new SecureRandom();
        StringBuilder cardNumber = new StringBuilder();

        // Generate the first 15 digits randomly
        for (int i = 0; i < CARD_NUMBER_LENGTH - 1; i++) {
            int digit = random.nextInt(10);
            cardNumber.append(digit);
        }

        // Calculate the check digit using the Luhn algorithm
        int checkDigit = calculateLuhnCheckDigit(cardNumber.toString());
        cardNumber.append(checkDigit);

        return cardNumber.toString();
    }

    private static int calculateLuhnCheckDigit(String number) {
        int sum = 0;
        boolean alternate = false;

        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        return (10 - (sum % 10)) % 10;
    }

    public static LocalDate generateExpiryDate() {
        return LocalDate.now().plus(4, ChronoUnit.YEARS);
    }

    public static String generateCvv() {
        SecureRandom secureRandom = new SecureRandom();
        int cvv = secureRandom.nextInt(900)+100;
        return String.valueOf(cvv);
    }
}
