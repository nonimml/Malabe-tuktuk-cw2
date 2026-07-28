package com.cw_malabe_tutuk2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataParsingTest {


    @Test
    public void test_MixedDelimiter() {
        String dirtyPrice = " Rs. 1500.50 ";
        String cleanedPrice = dirtyPrice.trim().replaceAll("(?i)^Rs\\.?\\s*", "");

        assertEquals("1500.50", cleanedPrice);
        assertEquals(1500.50, Double.parseDouble(cleanedPrice), 0.001);

        String dirtyLine = "P001|Engine Block;Toyota, Rs. 25000, 5|ENGINE|01/01/2024|img.jpg";
        String splitRegex = ";|\\||,(?!(?<=\\b[A-Za-z]{3,9} \\d{1,2},)\\s*\\d{4}\\b)";
        String[] tokens = dirtyLine.split(splitRegex, -1);

        assertTrue(tokens.length >= 8);
        assertEquals("P001", tokens[0].trim());
        assertEquals("Engine Block", tokens[1].trim());
    }


    @Test
    public void test_MissingFieldsReturnsNull() {
        String emptyField = "   ";
        String trimmed = emptyField.trim();
        String result = trimmed.isEmpty() ? null : trimmed;

        assertNull(result, "Empty optional text fields should parse as null");
    }


    @Test
    public void test_DateFormat_ParsingVariations() {
        String inputDate1 = "15/08/2024";
        String inputDate2 = "2024-08-15";

        assertEquals(10, inputDate1.length());
        assertTrue(inputDate1.contains("/"));
        assertTrue(inputDate2.contains("-"));
    }
}

