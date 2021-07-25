/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.junit.jupiter.api.Assertions.*;

class InventoryItemTest {

    @Test
    void getName_test_that_returns_string() {
        // Create a new item
        InventoryItem item = new InventoryItem(BigDecimal.valueOf(100), "ASFB1K234F", "Yep");
        String expected = "Wowza";

        // Use setName() to set item's name to the expected
        item.setName("Wowza");

        // Check that getName() returns the appropriate string
        String actual = item.getName();
        assertEquals(expected, actual);
    }

    @Test
    void getSerialNumber_test_that_returns_string() {
        // Create a new item
        InventoryItem item = new InventoryItem(BigDecimal.valueOf(100), "ASFB1K234F", "Yep");
        String expected = "ABCDEFGHIJ";

        // Use setSerialNumber() to set item's name to the expected
        item.setSerialNumber("ABCDEFGHIJ");

        // Check that getSerialNumber() returns the appropriate string
        String actual = item.getSerialNumber();
        assertEquals(expected, actual);
    }

    @Test
    void getPrice_test_that_returns_big_decimal() {
        // Create a new item
        InventoryItem item = new InventoryItem(BigDecimal.valueOf(200), "ASFB1K234F", "Yep");
        BigDecimal expected = BigDecimal.valueOf(100.).setScale(2, RoundingMode.HALF_UP);

        // Use setPrice() to set item's name to the expected
        item.setPrice(BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP));

        // Check that getPrice() returns the appropriate BigDecimal, rounded to two decimal places
        BigDecimal actual = item.getPrice();
        assertEquals(expected, actual);
    }

}