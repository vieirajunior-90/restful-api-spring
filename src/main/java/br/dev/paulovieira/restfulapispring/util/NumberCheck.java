package br.dev.paulovieira.restfulapispring.util;

public class NumberCheck {

    private static boolean isNumeric(String number) {
        if (number == null) {
            return false;
        }
        try {
            String numberFormatted = number.replaceAll(",", ".");
            Double.parseDouble(numberFormatted);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isNumeric(String... numbers){
        for (String number : numbers) {
            if (!isNumeric(number)) {
                throw new UnsupportedOperationException(
                        "It's not possible to make an operation with non-numeric value(s)"
                );
            }
        }
        return true;
    }

    public static boolean isDivisibleByZero(String number) {
        if (isNumeric(number) && Double.parseDouble(number) == 0) {
            throw new UnsupportedOperationException("It's not possible to divide by zero");
        }
        return true;
    }

    public static boolean canSquareRoot(String number) {
        if (isNumeric(number) && Double.parseDouble(number) < 0) {
            throw new UnsupportedOperationException("It's not possible to square root a negative value");
        }
        return true;
    }
}
