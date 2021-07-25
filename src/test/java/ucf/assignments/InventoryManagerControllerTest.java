/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerControllerTest {

    @Test
    void deleteItem_test_true_set1() {
        InventoryListModel listModel = new InventoryListModel();

        BigDecimal price = BigDecimal.valueOf(1125.00);
        String serialNumber = "ABCDE12345";
        String name = "Samsung Smart Fridge";

        InventoryItem item = new InventoryItem(price, serialNumber, name);

        listModel.getItems().add(item);
        listModel.getItems().remove(item);

        assertEquals(0, listModel.getItems().size());
    }

    @Test
    void deleteItem_test_true_set2() {
        InventoryListModel listModel = new InventoryListModel();

        BigDecimal price1 = BigDecimal.valueOf(1125.00);
        String serialNumber1 = "ABCDE12345";
        String name1 = "Samsung Smart Fridge";

        BigDecimal price2 = BigDecimal.valueOf(525.00);
        String serialNumber2 = "12345ABCDE";
        String name2 = "XBOX-2000";

        BigDecimal price3 = BigDecimal.valueOf(325.00);
        String serialNumber3 = "A1B2C3D4E5";
        String name3 = "Nintendo Botch";

        InventoryItem item1 = new InventoryItem(price1, serialNumber1, name1);
        InventoryItem item2 = new InventoryItem(price2, serialNumber2, name2);
        InventoryItem item3 = new InventoryItem(price3, serialNumber3, name3);

        listModel.getItems().add(item1);
        listModel.getItems().add(item2);
        listModel.getItems().add(item3);

        listModel.getItems().remove(item2);

        assertEquals(2, listModel.getItems().size());
    }

    // Tests for sort(?) and search will go here
}