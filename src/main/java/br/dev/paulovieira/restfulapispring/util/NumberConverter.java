package br.dev.paulovieira.restfulapispring.util;

public class NumberConverter {

    public static Double convertToDouble(String number) {
        if (number == null) {
            return 0D;
        }
        String numberFormatted = number.replaceAll(",", ".");
        if (NumberCheck.isNumeric(numberFormatted)) {
            return Double.parseDouble(numberFormatted);
        }
        return 0D;
    }
}
