/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class InventoryListModelTest {

    @Test
    void getItems_check_that_returns_correct_observable_list() {
        // given
        InventoryListModel listModel = new InventoryListModel();

        // add an item
        InventoryItem expected = new InventoryItem(
                BigDecimal.valueOf(10.00), "ABCDEFGHIJ", "TikTok Cringe");
        listModel.getItems().add(expected);

        // check that the item exists in the obs list
        assertTrue(listModel.getItems().contains(expected));
    }
}