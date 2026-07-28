package com.cw_malabe_tutuk2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InputValidationTest {


    @Test
    public void test_Invalid_Item_CodePrefixAndLength() {
        String invalidPrefixCode = "X001";
        String invalidLengthCode = "P01";

        boolean validPrefix = invalidPrefixCode.startsWith("P");
        boolean validLength = invalidLengthCode.length() == 4;

        assertFalse(validPrefix, "Item code must start with 'P'");
        assertFalse(validLength, "Item code must be exactly 4 characters long");
    }


    @Test
    public void testNonNumericPriceInputThrowsException() {
        String rawPrice = "ABC_PRICE";


        assertThrows(NumberFormatException.class, () -> Double.parseDouble(rawPrice));
    }

    @Test
    public void testNegativePriceAndStockValidation() {
        double negativePrice = -50.0;
        int negativeStock = -5;

        boolean isPriceValid = negativePrice >= 1.0;
        boolean isStockValid = negativeStock >= 0;

        assertFalse(isPriceValid, "Price cannot be negative or zero");
        assertFalse(isStockValid, "Stock quantity cannot be negative");
    }


    @Test
    public void testInvalidDateHandling() {
        String invalidMonthDate = "15/13/2024";
        String[] parts = invalidMonthDate.split("/");

        int month = Integer.parseInt(parts[1]);
        boolean validMonth = (month >= 1 && month <= 12);

        assertFalse(validMonth, "Month must be between 1 and 12");
    }
}
